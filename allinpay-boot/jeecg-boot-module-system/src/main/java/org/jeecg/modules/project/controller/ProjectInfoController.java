package org.jeecg.modules.project.controller;

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
import org.jeecg.modules.project.entity.Merchant;
import org.jeecg.modules.project.entity.ProjectInfo;
import org.jeecg.modules.project.vo.ProjectInfoPage;
import org.jeecg.modules.project.service.IProjectInfoService;
import org.jeecg.modules.project.service.IMerchantService;
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
 * @Description: 项目信息
 * @Author: jeecg-boot
 * @Date:   2019-12-25
 * @Version: V1.0
 */
@RestController
@RequestMapping("/project/projectInfo")
@Slf4j
public class ProjectInfoController {
	@Autowired
	private IProjectInfoService projectInfoService;
	@Autowired
	private IMerchantService merchantService;
	
	/**
	 * 分页列表查询
	 *
	 * @param projectInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ProjectInfo projectInfo,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ProjectInfo> queryWrapper = QueryGenerator.initQueryWrapper(projectInfo, req.getParameterMap());
		Page<ProjectInfo> page = new Page<ProjectInfo>(pageNo, pageSize);
		IPage<ProjectInfo> pageList = projectInfoService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param projectInfoPage
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ProjectInfoPage projectInfoPage) {
		ProjectInfo projectInfo = new ProjectInfo();
		BeanUtils.copyProperties(projectInfoPage, projectInfo);
		projectInfoService.saveMain(projectInfo, projectInfoPage.getMerchantList());
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param projectInfoPage
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ProjectInfoPage projectInfoPage) {
		ProjectInfo projectInfo = new ProjectInfo();
		BeanUtils.copyProperties(projectInfoPage, projectInfo);
		ProjectInfo projectInfoEntity = projectInfoService.getById(projectInfo.getId());
		if(projectInfoEntity==null) {
			return Result.error("未找到对应数据");
		}
		projectInfoService.updateMain(projectInfo, projectInfoPage.getMerchantList());
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
		projectInfoService.delMain(id);
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
		this.projectInfoService.delBatchMain(Arrays.asList(ids.split(",")));
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
		ProjectInfo projectInfo = projectInfoService.getById(id);
		if(projectInfo==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(projectInfo);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryMerchantByMainId")
	public Result<?> queryMerchantListByMainId(@RequestParam(name="id",required=true) String id) {
		List<Merchant> merchantList = merchantService.selectByMainId(id);
		return Result.ok(merchantList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param projectInfo
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ProjectInfo projectInfo) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<ProjectInfo> queryWrapper = QueryGenerator.initQueryWrapper(projectInfo, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<ProjectInfo> queryList = projectInfoService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<ProjectInfo> projectInfoList = new ArrayList<ProjectInfo>();
      if(oConvertUtils.isEmpty(selections)) {
          projectInfoList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          projectInfoList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<ProjectInfoPage> pageList = new ArrayList<ProjectInfoPage>();
      for (ProjectInfo main : projectInfoList) {
          ProjectInfoPage vo = new ProjectInfoPage();
          BeanUtils.copyProperties(main, vo);
          List<Merchant> merchantList = merchantService.selectByMainId(main.getId());
          vo.setMerchantList(merchantList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "项目信息列表");
      mv.addObject(NormalExcelConstants.CLASS, ProjectInfoPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("项目信息数据", "导出人:"+sysUser.getRealname(), "项目信息"));
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
              List<ProjectInfoPage> list = ExcelImportUtil.importExcel(file.getInputStream(), ProjectInfoPage.class, params);
              for (ProjectInfoPage page : list) {
                  ProjectInfo po = new ProjectInfo();
                  BeanUtils.copyProperties(page, po);
                  projectInfoService.saveMain(po, page.getMerchantList());
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
