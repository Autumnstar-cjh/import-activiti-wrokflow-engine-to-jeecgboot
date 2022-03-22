package org.jeecg.modules.quality.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;
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
import org.jeecg.modules.quality.entity.QualityMerchant;
import org.jeecg.modules.quality.entity.QualityMerchantType;
import org.jeecg.modules.quality.vo.QualityMerchantTypePage;
import org.jeecg.modules.quality.service.IQualityMerchantTypeService;
import org.jeecg.modules.quality.service.IQualityMerchantService;
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
 * @Description: 特色产业优质客户商户类型
 * @Author: jeecg-boot
 * @Date:   2021-03-01
 * @Version: V1.0
 */
@RestController
@RequestMapping("/quality/qualityMerchantType")
@Slf4j
public class QualityMerchantTypeController {
	@Autowired
	private IQualityMerchantTypeService qualityMerchantTypeService;
	@Autowired
	private IQualityMerchantService qualityMerchantService;
	
	/**
	 * 分页列表查询-未使用
	 *
	 * @param qualityMerchantType
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(QualityMerchantType qualityMerchantType,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<QualityMerchantType> queryWrapper = QueryGenerator.initQueryWrapper(qualityMerchantType, req.getParameterMap());
		Page<QualityMerchantType> page = new Page<QualityMerchantType>(pageNo, pageSize);
		IPage<QualityMerchantType> pageList = qualityMerchantTypeService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	 /**
	  * 分页列表查询-已使用
	  * @param qualityMerchantType
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	@GetMapping(value = "/pagelist")
	public Result<?> queryPage(QualityMerchantType qualityMerchantType,
							   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
							   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
							   HttpServletRequest req){

		List<QualityMerchantTypePage> pages = qualityMerchantTypeService.queryPage();
		List<Map<String,Object>> typeList = new ArrayList<>();
		if(!pages.isEmpty()){//非空判断
			for (QualityMerchantTypePage q : pages){
//				System.out.println("查询结果："+q.toString());
				Map<String,Object> typeMap = new HashMap<>();
				typeMap.put("key",q.getId());
				typeMap.put("name",q.getType());

				//封装内表数据
				List<Map<String,String>> merchantList = new ArrayList<>();
				if(!q.getQualityMerchantList().isEmpty()){//非空判断
					for(QualityMerchant m:q.getQualityMerchantList()){
						Map<String,String> merchantMap = new HashMap<>();
						merchantMap.put("key",m.getId());
						merchantMap.put("merchantCode",m.getMerchantCode());
						merchantMap.put("merchantName",m.getMerchantName());
						merchantMap.put("developPeople",m.getDevelopPeople());
						merchantMap.put("department",m.getDepartment());
						merchantMap.put("isNew",m.getIsNew());

						merchantList.add(merchantMap);
					}
				}
				typeMap.put("qualityMerchantList",merchantList);
				typeList.add(typeMap);
			}
		}else{
			return Result.ok("结果为空");
		}
		return Result.ok(typeList);
	}

	 /**
	  * 分页列表查询-已使用-带参数
	  * @param type
	  * @param merchantCode
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @GetMapping(value = "/pagelistByParam")
	 public Result<?> queryPageByParam(@RequestParam(name="type") String type,
									   @RequestParam(name="merchantCode") String merchantCode,
									   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									   HttpServletRequest req){

		System.out.println("接收到查询参数，type="+type+"，merchantCode="+merchantCode);

		 List<QualityMerchantTypePage> pages = qualityMerchantTypeService.queryPage(type,merchantCode);
		 List<Map<String,Object>> typeList = new ArrayList<>();
		 if(!pages.isEmpty()){//非空判断
			 for (QualityMerchantTypePage q : pages){
				System.out.println("查询结果："+q.toString());
				 Map<String,Object> typeMap = new HashMap<>();
				 typeMap.put("key",q.getId());
				 typeMap.put("name",q.getType());

				 //封装内表数据
				 List<Map<String,String>> merchantList = new ArrayList<>();
				 if(!q.getQualityMerchantList().isEmpty()){//非空判断
					 for(QualityMerchant m:q.getQualityMerchantList()){
						 Map<String,String> merchantMap = new HashMap<>();
						 merchantMap.put("key",m.getId());
						 merchantMap.put("merchantCode",m.getMerchantCode());
						 merchantMap.put("merchantName",m.getMerchantName());
						 merchantMap.put("developPeople",m.getDevelopPeople());
						 merchantMap.put("department",m.getDepartment());
						 merchantMap.put("isNew",m.getIsNew());
						 merchantList.add(merchantMap);
					 }
				 }
				 typeMap.put("qualityMerchantList",merchantList);
				 typeList.add(typeMap);
			 }
		 }else{
			 return Result.ok("结果为空");
		 }
		 return Result.ok(typeList);
	 }

	
	/**
	 *   添加
	 *
	 * @param qualityMerchantTypePage
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody QualityMerchantTypePage qualityMerchantTypePage) {
		QualityMerchantType qualityMerchantType = new QualityMerchantType();
		//新增商户，默认historicalData字段为0，表示未导入历史数据
		List<QualityMerchant> merchants = qualityMerchantTypePage.getQualityMerchantList();
		for (QualityMerchant q:merchants){
			q.setHistoricalData("0");
		}
		qualityMerchantTypePage.setQualityMerchantList(merchants);
		BeanUtils.copyProperties(qualityMerchantTypePage, qualityMerchantType);
		qualityMerchantTypeService.saveMain(qualityMerchantType, qualityMerchantTypePage.getQualityMerchantList());
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param qualityMerchantTypePage
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody QualityMerchantTypePage qualityMerchantTypePage) {
		QualityMerchantType qualityMerchantType = new QualityMerchantType();
		BeanUtils.copyProperties(qualityMerchantTypePage, qualityMerchantType);
		QualityMerchantType qualityMerchantTypeEntity = qualityMerchantTypeService.getById(qualityMerchantType.getId());
		if(qualityMerchantTypeEntity==null) {
			return Result.error("未找到对应数据");
		}
		qualityMerchantTypeService.updateMain(qualityMerchantType, qualityMerchantTypePage.getQualityMerchantList());
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
		qualityMerchantTypeService.delMain(id);
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
		this.qualityMerchantTypeService.delBatchMain(Arrays.asList(ids.split(",")));
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
		QualityMerchantType qualityMerchantType = qualityMerchantTypeService.getById(id);
		if(qualityMerchantType==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(qualityMerchantType);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryQualityMerchantByMainId")
	public Result<?> queryQualityMerchantListByMainId(@RequestParam(name="id",required=true) String id) {

		System.out.println("收到外表id="+id);
		List<QualityMerchant> qualityMerchantList = qualityMerchantService.selectByMainId(id);

		for(QualityMerchant q:qualityMerchantList){
			System.out.println("内表数据："+q.toString());
		}
		return Result.ok(qualityMerchantList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param qualityMerchantType
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, QualityMerchantType qualityMerchantType) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<QualityMerchantType> queryWrapper = QueryGenerator.initQueryWrapper(qualityMerchantType, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<QualityMerchantType> queryList = qualityMerchantTypeService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<QualityMerchantType> qualityMerchantTypeList = new ArrayList<QualityMerchantType>();
      if(oConvertUtils.isEmpty(selections)) {
          qualityMerchantTypeList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          qualityMerchantTypeList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<QualityMerchantTypePage> pageList = new ArrayList<QualityMerchantTypePage>();
      for (QualityMerchantType main : qualityMerchantTypeList) {
          QualityMerchantTypePage vo = new QualityMerchantTypePage();
          BeanUtils.copyProperties(main, vo);
          List<QualityMerchant> qualityMerchantList = qualityMerchantService.selectByMainId(main.getId());
          vo.setQualityMerchantList(qualityMerchantList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "特色产业优质客户商户类型列表");
      mv.addObject(NormalExcelConstants.CLASS, QualityMerchantTypePage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("特色产业优质客户商户类型数据", "导出人:"+sysUser.getRealname(), "特色产业优质客户商户类型"));
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
              List<QualityMerchantTypePage> list = ExcelImportUtil.importExcel(file.getInputStream(), QualityMerchantTypePage.class, params);

              for (QualityMerchantTypePage page : list) {
              	List<QualityMerchant> qualityMerchants = page.getQualityMerchantList();
              	for(QualityMerchant q:qualityMerchants){
              		if(!("0".equals(q.getIsNew()) || "1".equals(q.getIsNew()))){
//						System.out.println("isNew="+q.getIsNew());
              			return Result.error("文件导入失败！isNew字段不正确！");
					}
				}
              	QualityMerchantType po = new QualityMerchantType();
              	BeanUtils.copyProperties(page, po);
              	qualityMerchantTypeService.saveMain(po, page.getQualityMerchantList());
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
