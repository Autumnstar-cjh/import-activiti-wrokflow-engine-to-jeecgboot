package org.jeecg.modules.cloudacc.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.cloudacc.entity.CloudAccRpt;
import org.jeecg.modules.cloudacc.model.CloudAccAppTrxSummary;
import org.jeecg.modules.cloudacc.model.CloudAccTrxMonthSummary;
import org.jeecg.modules.cloudacc.model.CloudAccTrxSummary;
import org.jeecg.modules.cloudacc.service.ICloudAccRptService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: 云商通账户交易报表
 * @Author: jeecg-boot
 * @Date:   2021-07-04
 * @Version: V1.0
 */
@RestController
@RequestMapping("/cloudacc/cloudAccRpt")
@Slf4j
@RequiresPermissions("home:cloudacc")
public class CloudAccRptController extends JeecgController<CloudAccRpt, ICloudAccRptService> {
	@Autowired
	private ICloudAccRptService cloudAccRptService;

	 /**
	  * 查询近七日交易汇总，按应用维度统计
	  * @return
	  */
	 @GetMapping(value = "/lastSevenDaysAppTrans")
	 public Result<?> lastSevenDaysAppTrans(){
		 Date now = new Date();
		 LocalDate localDate=now.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		 Date endDate= java.sql.Date.valueOf(localDate);

		 Calendar cal = Calendar.getInstance();
		 cal.setTime(endDate);
		 cal.add(Calendar.DATE, -7);

		 Date beginDate = cal.getTime();
		 localDate = beginDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		 beginDate =java.sql.Date.valueOf(localDate);

		 List<CloudAccAppTrxSummary> summaryList =  cloudAccRptService.cloudAccAppTransSummary(beginDate, endDate);

		 return Result.ok(summaryList);
	 }


	/**
	 * 查询近七日交易汇总，按应用维度统计
	 * @return
	 */
	@GetMapping(value = "/topLastSevenDaysAppTrans")
	public Result<?> topLastSevenDaysAppTrans(){
		Date now = new Date();
		LocalDate localDate=now.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		Date endDate= java.sql.Date.valueOf(localDate);

		Calendar cal = Calendar.getInstance();
		cal.setTime(endDate);
		cal.add(Calendar.DATE, -7);

		Date beginDate = cal.getTime();
		localDate = beginDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		beginDate =java.sql.Date.valueOf(localDate);

		List<CloudAccAppTrxSummary> summaryList =
				cloudAccRptService.topCloudAccAppTransSummary(beginDate, endDate, 10);

		return Result.ok(summaryList);
	}



	/**
	 * 查询近七日交易汇总，按日期统计
	 * @return
	 */
	@GetMapping(value = "/lastSevenDaysTransList")
	public Result<?> lastSevenDaysTransList(){
		Date now = new Date();
		LocalDate localDate=now.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		Date endDate= java.sql.Date.valueOf(localDate);

		Calendar cal = Calendar.getInstance();
		cal.setTime(endDate);
		cal.add(Calendar.DATE, -7);

		Date beginDate = cal.getTime();
		localDate = beginDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		beginDate =java.sql.Date.valueOf(localDate);

		List<CloudAccTrxSummary> summaryList =  cloudAccRptService.cloudAccTransSummaryByDate(beginDate, endDate);

		return Result.ok(summaryList);
	}

	@GetMapping(value = "/cloudAccTransSummaryByMonth")
	public Result<?> cloudAccTransSummaryByMonth(@RequestParam String beginDate, @RequestParam String endDate ){

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<CloudAccTrxMonthSummary> summaryList =
				null;
		try {
			summaryList = cloudAccRptService.cloudAccTrxSummaryByMonth(sdf.parse(beginDate), sdf.parse(endDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return Result.ok(summaryList);
	}

	@GetMapping(value = "/topCurrentYearAppTrans")
	public Result<?> topCurrentYearAppTrans(@RequestParam String beginDate, @RequestParam String endDate ){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<CloudAccAppTrxSummary> summaryList =
				null;
		try {
			summaryList =
					cloudAccRptService.topCloudAccAppTransSummary(sdf.parse(beginDate), sdf.parse(endDate), 10);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return Result.ok(summaryList);
	}

	 /**
	  * 查询近七日交易汇总，所有应用合计
	  * @return
	  */
	 @GetMapping(value = "/lastSevenDaysTransSummay")
	 public Result<?> lastSevenDaysTransSummay(){
		 Date now = new Date();
		 LocalDate localDate=now.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		 Date endDate= java.sql.Date.valueOf(localDate);

		 Calendar cal = Calendar.getInstance();
		 cal.setTime(endDate);
		 cal.add(Calendar.DATE, -7);

		 Date beginDate = cal.getTime();
		 localDate = beginDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		 beginDate =java.sql.Date.valueOf(localDate);

		 Map<String, Object> summary =  cloudAccRptService.cloudAccTransSummary(beginDate, endDate);

		 return Result.ok(summary);
	 }

	 /**
	 * 分页列表查询
	 *
	 * @param cloudAccRpt
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(CloudAccRpt cloudAccRpt,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<CloudAccRpt> queryWrapper = QueryGenerator.initQueryWrapper(cloudAccRpt, req.getParameterMap());
		Page<CloudAccRpt> page = new Page<CloudAccRpt>(pageNo, pageSize);
		IPage<CloudAccRpt> pageList = cloudAccRptService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param cloudAccRpt
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody CloudAccRpt cloudAccRpt) {
		cloudAccRptService.save(cloudAccRpt);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param cloudAccRpt
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody CloudAccRpt cloudAccRpt) {
		cloudAccRptService.updateById(cloudAccRpt);
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
		this.cloudAccRptService.removeByIds(Arrays.asList(ids.split(",")));
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
		CloudAccRpt cloudAccRpt = cloudAccRptService.getById(id);
		if(cloudAccRpt==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(cloudAccRpt);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param cloudAccRpt
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CloudAccRpt cloudAccRpt) {
        return super.exportXls(request, cloudAccRpt, CloudAccRpt.class, "云商通账户交易报表");
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
		return super.importExcel(request, response, CloudAccRpt.class);
	}

}
