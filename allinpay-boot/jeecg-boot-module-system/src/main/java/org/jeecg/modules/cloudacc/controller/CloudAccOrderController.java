package org.jeecg.modules.cloudacc.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.formula.functions.T;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.cloudacc.entity.CloudAccOrder;
import org.jeecg.modules.cloudacc.entity.CloudAccRpt;
import org.jeecg.modules.cloudacc.model.CloudAccOrderExcelModel;
import org.jeecg.modules.cloudacc.service.ICloudAccOrderService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.cloudacc.service.ICloudAccRptService;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;

 /**
 * @Description: 通商云订单交易报表
 * @Author: jeecg-boot
 * @Date:   2022-04-01
 * @Version: V1.0
 */
@RestController
@RequestMapping("/cloudacc/cloudAccOrder")
@Slf4j
public class CloudAccOrderController extends JeecgController<CloudAccOrder, ICloudAccOrderService> {
	@Autowired
	private ICloudAccOrderService cloudAccOrderService;

	@Autowired
	private ICloudAccRptService cloudAccRptService;
	
	/**
	 * 分页列表查询
	 *
	 * @param cloudAccOrder
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(CloudAccOrder cloudAccOrder,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<CloudAccOrder> queryWrapper = QueryGenerator.initQueryWrapper(cloudAccOrder, req.getParameterMap());
		Page<CloudAccOrder> page = new Page<CloudAccOrder>(pageNo, pageSize);
		IPage<CloudAccOrder> pageList = cloudAccOrderService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param cloudAccOrder
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody CloudAccOrder cloudAccOrder) {
		cloudAccOrderService.save(cloudAccOrder);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param cloudAccOrder
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody CloudAccOrder cloudAccOrder) {
		cloudAccOrderService.updateById(cloudAccOrder);
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
		cloudAccOrderService.removeById(id);
		cloudAccRptService.removeById(id);
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
		this.cloudAccOrderService.removeByIds(Arrays.asList(ids.split(",")));
		cloudAccRptService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		CloudAccOrder cloudAccOrder = cloudAccOrderService.getById(id);
		if(cloudAccOrder==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(cloudAccOrder);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param cloudAccOrder
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CloudAccOrder cloudAccOrder) {
        return super.exportXls(request, cloudAccOrder, CloudAccOrder.class, "通商云订单交易报表");
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
			params.setTitleRows(1);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<CloudAccOrderExcelModel> list = ExcelImportUtil.importExcel(file.getInputStream(), CloudAccOrderExcelModel.class, params);
				List<CloudAccOrder> orderList = new ArrayList<>();//订单交易报表集合
				List<CloudAccRpt> rptList = new ArrayList<>();//账户交易报表集合
				for (CloudAccOrderExcelModel cloudAccOrder : list){
					//往订单交易报表中添加数据
					CloudAccOrder order = new CloudAccOrder(cloudAccOrder);
					orderList.add(order);


					//往订单交易报表中添加数据
					CloudAccRpt rpt = new CloudAccRpt(cloudAccOrder);
					rptList.add(rpt);
				}
				//update-begin-author:taoyan date:20190528 for:批量插入数据

				long start = System.currentTimeMillis();
				//插入数据库：订单交易报表
				cloudAccOrderService.saveBatch(orderList);
				//插入数据库：账户交易报表
				cloudAccRptService.saveBatch(rptList);
				log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");

				return Result.ok("文件导入成功！数据行数：" + list.size());
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return Result.error("文件导入失败:" + e.getMessage());
			} finally {
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return Result.error("文件导入失败！");
    }

}
