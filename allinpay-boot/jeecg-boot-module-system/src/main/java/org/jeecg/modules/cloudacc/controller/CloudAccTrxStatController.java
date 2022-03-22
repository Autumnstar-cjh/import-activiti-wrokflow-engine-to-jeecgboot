package org.jeecg.modules.cloudacc.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.cloudacc.entity.CloudAccTrxStat;
import org.jeecg.modules.cloudacc.model.CloudAccAppTrxSummary;
import org.jeecg.modules.cloudacc.service.ICloudAccRptService;
import org.jeecg.modules.cloudacc.service.ICloudAccTrxStatService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

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
 * @Description: 云商通账户交易统计报表
 * @Author: jeecg-boot
 * @Date:   2021-07-07
 * @Version: V1.0
 */
@RestController
@RequestMapping("/cloudacc/cloudAccTrxStat")
@Slf4j
public class CloudAccTrxStatController extends JeecgController<CloudAccTrxStat, ICloudAccTrxStatService> {
	@Autowired
	private ICloudAccTrxStatService cloudAccTrxStatService;

	 @Autowired
	 private ICloudAccRptService cloudAccRptService;

	 /**
	  * 查询近七日交易汇总，按应用维度统计
	  * @return
	  */
	 @GetMapping(value = "/appTransStat")
	 public Result<?> appTransStat(@RequestParam String beginDate,
								   @RequestParam String endDate,
								   @RequestParam(required = false) String appId,
								   @RequestParam(required = false) String appName){

		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		 List<CloudAccAppTrxSummary> summaryList = null;
		 IPage<CloudAccAppTrxSummary> pageList = null;
		 try {
		 	Map<String, Object> params = new HashMap<>();
			 params.put("beginDate", sdf.parse(beginDate));
			 params.put("endDate", sdf.parse(endDate));

			 if (StringUtils.isNotBlank(appId)) {
				 params.put("appId", appId);
			 }

			 if (StringUtils.isNotBlank(appName)) {
				 params.put("appName", appName);
			 }

			 summaryList = cloudAccRptService.cloudAccAppTransSummary( params );

			 pageList = new Page<>();

			 pageList.setRecords(summaryList);
		 } catch (ParseException e) {
			 e.printStackTrace();
		 }

		 return Result.ok(pageList);
	 }
	
	/**
	 * 分页列表查询
	 *
	 * @param cloudAccTrxStat
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(CloudAccTrxStat cloudAccTrxStat,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<CloudAccTrxStat> queryWrapper = QueryGenerator.initQueryWrapper(cloudAccTrxStat, req.getParameterMap());
		Page<CloudAccTrxStat> page = new Page<CloudAccTrxStat>(pageNo, pageSize);
		IPage<CloudAccTrxStat> pageList = cloudAccTrxStatService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param cloudAccTrxStat
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody CloudAccTrxStat cloudAccTrxStat) {
		cloudAccTrxStatService.save(cloudAccTrxStat);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param cloudAccTrxStat
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody CloudAccTrxStat cloudAccTrxStat) {
		cloudAccTrxStatService.updateById(cloudAccTrxStat);
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
		cloudAccTrxStatService.removeById(id);
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
		this.cloudAccTrxStatService.removeByIds(Arrays.asList(ids.split(",")));
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
		CloudAccTrxStat cloudAccTrxStat = cloudAccTrxStatService.getById(id);
		if(cloudAccTrxStat==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(cloudAccTrxStat);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param cloudAccTrxStat
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CloudAccTrxStat cloudAccTrxStat) {
        return super.exportXls(request, cloudAccTrxStat, CloudAccTrxStat.class, "云商通账户交易统计报表");
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
        return super.importExcel(request, response, CloudAccTrxStat.class);
    }

}
