package org.jeecg.modules.cloudacc.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.cloudacc.entity.CloudAccTrx;
import org.jeecg.modules.cloudacc.service.ICloudAccTrxService;

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
 * @Description: 通商云交易统计
 * @Author: jeecg-boot
 * @Date:   2021-07-03
 * @Version: V1.0
 */
@RestController
@RequestMapping("/cloudacc/cloudAccTrx")
@Slf4j
public class CloudAccTrxController extends JeecgController<CloudAccTrx, ICloudAccTrxService> {
	@Autowired
	private ICloudAccTrxService cloudAccTrxService;
	
	/**
	 * 分页列表查询
	 *
	 * @param cloudAccTrx
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(CloudAccTrx cloudAccTrx,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<CloudAccTrx> queryWrapper = QueryGenerator.initQueryWrapper(cloudAccTrx, req.getParameterMap());
		Page<CloudAccTrx> page = new Page<CloudAccTrx>(pageNo, pageSize);
		IPage<CloudAccTrx> pageList = cloudAccTrxService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param cloudAccTrx
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody CloudAccTrx cloudAccTrx) {
		cloudAccTrxService.save(cloudAccTrx);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param cloudAccTrx
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody CloudAccTrx cloudAccTrx) {
		cloudAccTrxService.updateById(cloudAccTrx);
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
		cloudAccTrxService.removeById(id);
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
		this.cloudAccTrxService.removeByIds(Arrays.asList(ids.split(",")));
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
		CloudAccTrx cloudAccTrx = cloudAccTrxService.getById(id);
		if(cloudAccTrx==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(cloudAccTrx);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param cloudAccTrx
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CloudAccTrx cloudAccTrx) {
        return super.exportXls(request, cloudAccTrx, CloudAccTrx.class, "通商云交易统计");
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
        return super.importExcel(request, response, CloudAccTrx.class);
    }

}
