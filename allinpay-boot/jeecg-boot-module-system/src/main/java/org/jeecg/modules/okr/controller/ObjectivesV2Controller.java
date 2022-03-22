package org.jeecg.modules.okr.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.okr.entity.*;
import org.jeecg.modules.okr.service.IKeyResultV2Service;
import org.jeecg.modules.okr.service.IKeyResultsService;
import org.jeecg.modules.okr.service.IObjectivesService;
import org.jeecg.modules.okr.service.IObjectivesV2Service;
import org.jeecg.modules.okr.vo.ObjectivesPage;
import org.jeecg.modules.okr.vo.ObjectivesV2Page;
import org.jeecg.modules.system.entity.SysDepart;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
* @Description: OKR目标表
* @Author: jeecg-boot
* @Date:   2020-03-26
* @Version: V1.0
*/
@RestController
@RequestMapping("/okrv2/objectives")
@Slf4j
public class ObjectivesV2Controller {
   @Autowired
   private IObjectivesV2Service objectivesService;
   @Autowired
   private IKeyResultV2Service keyResultsService;

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
   public Result<?> queryPageList(ObjectivesV2 objectives,
                                  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                  @RequestParam Boolean isAll,
                                  HttpServletRequest req) {
       QueryWrapper<ObjectivesV2> queryWrapper = QueryGenerator.initQueryWrapper(objectives, req.getParameterMap());
       LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

       UserRole userRole = objectivesService.queryAllPlanRole(sysUser.getUsername());
//		log.info("当前用户名长度："+sysUser.getUsername().length());
//		log.info("查看所有工作计划权限查询:"+userRole);
       if(userRole == null){
           queryWrapper.eq("charge_person",  sysUser.getUsername());
       }

       if(!isAll){ //false查询已完成，true查询全部
           queryWrapper.ge("percent",1);
       }
       Page<ObjectivesV2> page = new Page<ObjectivesV2>(pageNo, pageSize);
       IPage<ObjectivesV2> pageList = objectivesService.page(page, queryWrapper);
//		 System.out.println("=====================================================奇妙的分割线=====================================================");
//		 System.out.println("workplan参数 ：" + workPlan.toString());
//		 System.out.println("当前查询第"+pageList.getCurrent()+"页");
//		 System.out.println("当前页查询"+pageList.getSize()+"条数据");
//		 System.out.println("查询结果为"+pageList.getRecords().size()+"条数据");
//		 System.out.println("数据总条数"+pageList.getTotal());
//         System.out.println("当前共"+pageList.getPages()+"页");

       List<Map<String,Object>> objectivesList = new ArrayList<>();
       for (ObjectivesV2 o:pageList.getRecords()) {
//            System.out.println("返回的数据是："+o.toString());
           Map<String,Object> objectivesMap = new LinkedHashMap<>();
           List<KeyResultV2> keyResultsList = keyResultsService.selectByMainId(o.getId());
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
   public Result<?> add(@RequestBody ObjectivesV2Page objectivesPage) {
       List<KeyResultV2> keyResultsList = objectivesPage.getKeyResultsV2List();

       double p = 0;
       for (int i=0; i<keyResultsList.size(); i++){
           p += (keyResultsList.get(i).getFinishedAmount().doubleValue()/keyResultsList.get(i).getTargetAmount().doubleValue());
       }
       DecimalFormat df = new DecimalFormat("0.00");
       p = p/keyResultsList.size();
       objectivesPage.setPercent(new BigDecimal(df.format(p)));
       System.out.println("完成度为："+objectivesPage.getPercent());

       ObjectivesV2 objectives = new ObjectivesV2();
       BeanUtils.copyProperties(objectivesPage, objectives);
       objectivesService.saveMain(objectives, objectivesPage.getKeyResultsV2List());
       return Result.ok("添加成功！");
   }

   /**
    *  编辑
    *
    * @param objectivesPage
    * @return
    */
   @PutMapping(value = "/edit")
   public Result<?> edit(@RequestBody ObjectivesV2Page objectivesPage) {
       List<KeyResultV2> keyResultsList = objectivesPage.getKeyResultsV2List();

       double p = 0;
       for (int i=0; i<keyResultsList.size(); i++){
           p += (keyResultsList.get(i).getFinishedAmount().doubleValue()/keyResultsList.get(i).getTargetAmount().doubleValue());
       }
       DecimalFormat df = new DecimalFormat("0.00");
       p = p/keyResultsList.size();
       objectivesPage.setPercent(new BigDecimal(df.format(p)));
       System.out.println("完成度值为："+objectivesPage.getPercent());

       ObjectivesV2 objectives = new ObjectivesV2();
       BeanUtils.copyProperties(objectivesPage, objectives);
       ObjectivesV2 objectivesEntity = objectivesService.getById(objectives.getId());
       if(objectivesEntity==null) {
           return Result.error("未找到对应数据");
       }
       objectivesService.updateMain(objectives, objectivesPage.getKeyResultsV2List());
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
       ObjectivesV2 objectives = objectivesService.getById(id);
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
       List<KeyResultV2> keyResultsList = keyResultsService.selectByMainId(id);
       return Result.ok(keyResultsList);
   }

    /**
     * 获取目标数
     * @param objectivesEvent
     * @param req
     * @return
     */
    @GetMapping(value = "/countCurUser")
    public Result<?> countCurUser(ObjectivesV2 objectivesEvent,
                                  HttpServletRequest req) {

        QueryWrapper<ObjectivesV2> queryWrapper = QueryGenerator.initQueryWrapper(objectivesEvent, req.getParameterMap());
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        UserRole userRole = objectivesService.queryAllPlanRole(sysUser.getUsername());
//        log.info("查看所有工作计划权限查询:"+userRole);
        if(userRole == null){
            queryWrapper.eq("charge_person",  sysUser.getUsername());
        }
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
   public ModelAndView exportXls(HttpServletRequest request, ObjectivesV2 objectives) {
     // Step.1 组装查询条件查询数据
     QueryWrapper<ObjectivesV2> queryWrapper = QueryGenerator.initQueryWrapper(objectives, request.getParameterMap());
     LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

     //Step.2 获取导出数据
     List<ObjectivesV2> queryList = objectivesService.list(queryWrapper);
     // 过滤选中数据
     String selections = request.getParameter("selections");
     List<ObjectivesV2> objectivesList = new ArrayList<ObjectivesV2>();
     if(oConvertUtils.isEmpty(selections)) {
         objectivesList = queryList;
     }else {
         List<String> selectionList = Arrays.asList(selections.split(","));
         objectivesList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
     }

     // Step.3 组装pageList
     List<ObjectivesV2Page> pageList = new ArrayList<ObjectivesV2Page>();
     for (ObjectivesV2 main : objectivesList) {
         ObjectivesV2Page vo = new ObjectivesV2Page();
         BeanUtils.copyProperties(main, vo);
         List<KeyResultV2> keyResultsList = keyResultsService.selectByMainId(main.getId());
         vo.setKeyResultsV2List(keyResultsList);
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
             List<ObjectivesV2Page> list = ExcelImportUtil.importExcel(file.getInputStream(), ObjectivesPage.class, params);
             for (ObjectivesV2Page page : list) {
                 ObjectivesV2 po = new ObjectivesV2();
                 BeanUtils.copyProperties(page, po);
                 objectivesService.saveMain(po, page.getKeyResultsV2List());
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
