package org.jeecg.modules.okr.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.modules.okr.entity.UserRole;
import org.jeecg.modules.system.entity.SysDepart;
import org.jeecg.modules.system.service.ISysDepartService;
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
import org.jeecg.modules.okr.entity.KeyResults;
import org.jeecg.modules.okr.entity.Objectives;
import org.jeecg.modules.okr.vo.ObjectivesPage;
import org.jeecg.modules.okr.service.IObjectivesService;
import org.jeecg.modules.okr.service.IKeyResultsService;
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
 * @Description: OKR目标表
 * @Author: jeecg-boot
 * @Date:   2020-03-26
 * @Version: V1.0
 */
@RestController
@RequestMapping("/okr/objectives")
@Slf4j
public class ObjectivesController {
	@Autowired
	private IObjectivesService objectivesService;
	@Autowired
	private IKeyResultsService keyResultsService;
	
	/**
	 * 分页列表查询
	 *
	 * @param objectives
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/listByCurrentUser")
	public Result<?> queryPageList(Objectives objectives,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   @RequestParam Boolean isAll,
								   HttpServletRequest req) {
		QueryWrapper<Objectives> queryWrapper = QueryGenerator.initQueryWrapper(objectives, req.getParameterMap());
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
//		System.out.println("当前登录的用户为："+sysUser);
//		if(!(sysUser.getUsername().equals("13987657295") || sysUser.getUsername().equals("18313775206") || sysUser.getUsername().equals("admin") || sysUser.getUsername().equals("15911636679"))){
//			queryWrapper.eq("charge_person",  sysUser.getUsername());
//		}
		UserRole userRole = objectivesService.queryAllPlanRole(sysUser.getUsername());
//		log.info("当前用户名长度："+sysUser.getUsername().length());
//		log.info("查看所有工作计划权限查询:"+userRole);
		if(userRole == null){
			queryWrapper.eq("charge_person",  sysUser.getUsername());
		}

		if(!isAll){ //false查询已完成，true查询全部
			queryWrapper.ge("percent",1);
		}
		Page<Objectives> page = new Page<Objectives>(pageNo, pageSize);
		IPage<Objectives> pageList = objectivesService.page(page, queryWrapper);
//		 System.out.println("=====================================================奇妙的分割线=====================================================");
//		 System.out.println("workplan参数 ：" + workPlan.toString());
//		 System.out.println("当前查询第"+pageList.getCurrent()+"页");
//		 System.out.println("当前页查询"+pageList.getSize()+"条数据");
//		 System.out.println("查询结果为"+pageList.getRecords().size()+"条数据");
//		 System.out.println("数据总条数"+pageList.getTotal());
//         System.out.println("当前共"+pageList.getPages()+"页");

        List<Map<String,Object>> objectivesList = new ArrayList<>();
        for (Objectives o:pageList.getRecords()) {
//            System.out.println("返回的数据是："+o.toString());
            Map<String,Object> objectivesMap = new LinkedHashMap<>();
			List<KeyResults> keyResultsList = keyResultsService.selectByMainId(o.getId());
			SysDepart sysDepart = objectivesService.getDepartById(o.getSysDepart());
			objectivesMap.put("objectives",o);
			objectivesMap.put("keyResultList",keyResultsList);
			objectivesMap.put("sysDepart",sysDepart);
			objectivesMap.put("pageList",pageList);
			objectivesList.add(objectivesMap);
        }
		return Result.ok(objectivesList);
	}
	
	/**
	 *   添加
	 *
	 * @param objectivesPage
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ObjectivesPage objectivesPage) {
		List<KeyResults> keyResultsList = objectivesPage.getKeyResultsList();

		double p = 0;
		for (int i=0; i<keyResultsList.size(); i++){
			p += (keyResultsList.get(i).getFinishedAmount().doubleValue()/keyResultsList.get(i).getTargetAmount().doubleValue());
		}
		DecimalFormat df = new DecimalFormat("0.00");
		p = p/keyResultsList.size();
		objectivesPage.setPercent(new BigDecimal(df.format(p)));
		System.out.println("完成度为："+objectivesPage.getPercent());

		Objectives objectives = new Objectives();
		BeanUtils.copyProperties(objectivesPage, objectives);
		objectivesService.saveMain(objectives, objectivesPage.getKeyResultsList());
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param objectivesPage
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ObjectivesPage objectivesPage) {
		List<KeyResults> keyResultsList = objectivesPage.getKeyResultsList();

		double p = 0;
		for (int i=0; i<keyResultsList.size(); i++){
			p += (keyResultsList.get(i).getFinishedAmount().doubleValue()/keyResultsList.get(i).getTargetAmount().doubleValue());
		}
		DecimalFormat df = new DecimalFormat("0.00");
		p = p/keyResultsList.size();
		objectivesPage.setPercent(new BigDecimal(df.format(p)));
		System.out.println("完成度值为："+objectivesPage.getPercent());

		Objectives objectives = new Objectives();
		BeanUtils.copyProperties(objectivesPage, objectives);
		Objectives objectivesEntity = objectivesService.getById(objectives.getId());
		if(objectivesEntity==null) {
			return Result.error("未找到对应数据");
		}
		objectivesService.updateMain(objectives, objectivesPage.getKeyResultsList());
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
		objectivesService.delMain(id);
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
		this.objectivesService.delBatchMain(Arrays.asList(ids.split(",")));
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
		Objectives objectives = objectivesService.getById(id);
		if(objectives==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(objectives);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryKeyResultsByMainId")
	public Result<?> queryKeyResultsListByMainId(@RequestParam(name="id",required=true) String id) {
		List<KeyResults> keyResultsList = keyResultsService.selectByMainId(id);
		return Result.ok(keyResultsList);
	}

	 /**
	  * 取当前用户的目标数
	  * @param objectivesEvent
	  * @param req
	  * @return
	  */
	 @GetMapping(value = "/countCurUser")
	 public Result<?> countCurUser(Objectives objectivesEvent,
								   HttpServletRequest req) {

		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		 QueryWrapper<Objectives> queryWrapper = QueryGenerator.initQueryWrapper(objectivesEvent, req.getParameterMap());

		 queryWrapper.eq("charge_person", sysUser.getUsername() ) ;

		 int count = objectivesService.count(queryWrapper);

		 return Result.ok(count);
	 }

    /**
    * 导出excel
    *
    * @param request
    * @param objectives
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Objectives objectives) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<Objectives> queryWrapper = QueryGenerator.initQueryWrapper(objectives, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<Objectives> queryList = objectivesService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<Objectives> objectivesList = new ArrayList<Objectives>();
      if(oConvertUtils.isEmpty(selections)) {
          objectivesList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          objectivesList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<ObjectivesPage> pageList = new ArrayList<ObjectivesPage>();
      for (Objectives main : objectivesList) {
          ObjectivesPage vo = new ObjectivesPage();
          BeanUtils.copyProperties(main, vo);
          List<KeyResults> keyResultsList = keyResultsService.selectByMainId(main.getId());
          vo.setKeyResultsList(keyResultsList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "OKR目标表列表");
      mv.addObject(NormalExcelConstants.CLASS, ObjectivesPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("OKR目标表数据", "导出人:"+sysUser.getRealname(), "OKR目标表"));
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
              List<ObjectivesPage> list = ExcelImportUtil.importExcel(file.getInputStream(), ObjectivesPage.class, params);
              for (ObjectivesPage page : list) {
                  Objectives po = new Objectives();
                  BeanUtils.copyProperties(page, po);
                  objectivesService.saveMain(po, page.getKeyResultsList());
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
