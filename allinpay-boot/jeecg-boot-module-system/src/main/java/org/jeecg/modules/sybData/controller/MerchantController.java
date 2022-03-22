package org.jeecg.modules.sybData.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.sybData.entity.Merchant;
import org.jeecg.modules.sybData.entity.MerchantQueryParam;
import org.jeecg.modules.sybData.service.MerchantService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: Merchant
 * @Author: jeecg-boot
 * @Date:   2022-01-21
 * @Version: V1.0
 */
@RestController
@RequestMapping("/sybData/Merchant")
@Slf4j
public class MerchantController extends JeecgController<Merchant, MerchantService> {
	@Autowired
	private MerchantService MerchantService;
	
	/**
	 * 分页列表查询
	 *
	 * @param merchant
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(MerchantQueryParam merchant,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {

		System.out.println("请求到达,Merchant = "+merchant.toString());
//		QueryWrapper<Merchant> queryWrapper = QueryGenerator.initQueryWrapper(merchant, req.getParameterMap());

		List<Merchant> mercahnts = MerchantService.queryMerchantBankInfo(merchant);

		IPage<Merchant> pageList = new IPage<Merchant>() {
			@Override
			public List<OrderItem> orders() {
				return null;
			}
			@Override
			public List<org.jeecg.modules.sybData.entity.Merchant> getRecords() {
				return mercahnts;
			}
			@Override
			public IPage<org.jeecg.modules.sybData.entity.Merchant> setRecords(List<org.jeecg.modules.sybData.entity.Merchant> records) {
				return null;
			}
			@Override
			public long getTotal() {
				return 0;
			}
			@Override
			public IPage<org.jeecg.modules.sybData.entity.Merchant> setTotal(long total) {
				return null;
			}
			@Override
			public long getSize() {
				return 0;
			}
			@Override
			public IPage<org.jeecg.modules.sybData.entity.Merchant> setSize(long size) {
				return null;
			}
			@Override
			public long getCurrent() {
				return 0;
			}
			@Override
			public IPage<org.jeecg.modules.sybData.entity.Merchant> setCurrent(long current) {
				return null;
			}
		};
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param Merchant
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody Merchant Merchant) {
		MerchantService.save(Merchant);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param Merchant
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody Merchant Merchant) {
		MerchantService.updateById(Merchant);
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
		MerchantService.removeById(id);
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
		this.MerchantService.removeByIds(Arrays.asList(ids.split(",")));
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
		Merchant Merchant = MerchantService.getById(id);
		if(Merchant ==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(Merchant);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param Merchant
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Merchant Merchant) {
        return super.exportXls(request, Merchant, Merchant.class, "Merchant");
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
        return super.importExcel(request, response, Merchant.class);
    }

}
