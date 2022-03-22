package org.jeecg.modules.order.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.order.entity.Pay;
import org.jeecg.modules.order.entity.Orders;
import org.jeecg.modules.order.vo.OrdersPage;
import org.jeecg.modules.order.service.IOrdersService;
import org.jeecg.modules.order.service.IPayService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;

 /**
 * @Description: 订单表
 * @Author: jeecg-boot
 * @Date:   2020-06-22
 * @Version: V1.0
 */
@RestController
@RequestMapping("/order/orders")
@Slf4j
public class OrdersController {
	@Autowired
	private IOrdersService ordersService;
	@Autowired
	private IPayService payService;
	
	/**
	 * 分页列表查询
	 *
	 * @param orders
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(Orders orders,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Orders> queryWrapper = QueryGenerator.initQueryWrapper(orders, req.getParameterMap());
		Page<Orders> page = new Page<Orders>(pageNo, pageSize);
		IPage<Orders> pageList = ordersService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param ordersPage
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody OrdersPage ordersPage) {
		Orders orders = new Orders();
		BeanUtils.copyProperties(ordersPage, orders);
		ordersService.saveMain(orders, ordersPage.getPayList());
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param ordersPage
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody OrdersPage ordersPage) {
		Orders orders = new Orders();
		BeanUtils.copyProperties(ordersPage, orders);
		Orders ordersEntity = ordersService.getById(orders.getId());
		if(ordersEntity==null) {
			return Result.error("未找到对应数据");
		}
		ordersService.updateMain(orders, ordersPage.getPayList());
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		ordersService.delMain(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.ordersService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		Orders orders = ordersService.getById(id);
		if(orders==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(orders);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryPayByMainId")
	public Result<?> queryPayListByMainId(@RequestParam(name="id",required=true) String id) {
		List<Pay> payList = payService.selectByMainId(id);
		return Result.ok(payList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param orders
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Orders orders) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<Orders> queryWrapper = QueryGenerator.initQueryWrapper(orders, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<Orders> queryList = ordersService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<Orders> ordersList = new ArrayList<Orders>();
      if(oConvertUtils.isEmpty(selections)) {
          ordersList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          ordersList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<OrdersPage> pageList = new ArrayList<OrdersPage>();
      for (Orders main : ordersList) {
          OrdersPage vo = new OrdersPage();
          BeanUtils.copyProperties(main, vo);
          List<Pay> payList = payService.selectByMainId(main.getId());
          vo.setPayList(payList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "订单表列表");
      mv.addObject(NormalExcelConstants.CLASS, OrdersPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("订单表数据", "导出人:"+sysUser.getRealname(), "订单表"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
    }

    /**
    * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          MultipartFile file = entity.getValue();// 获取上传文件对象
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<OrdersPage> list = ExcelImportUtil.importExcel(file.getInputStream(), OrdersPage.class, params);
              for (OrdersPage page : list) {
                  Orders po = new Orders();
                  BeanUtils.copyProperties(page, po);
                  ordersService.saveMain(po, page.getPayList());
              }
              return Result.ok("文件导入成功！数据行数:" + list.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.ok("文件导入失败！");
    }

}
