package org.jeecg.modules.tourism.controller;

import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.UUIDGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.project.entity.Merchant;
import org.jeecg.modules.project.service.IMerchantService;
import org.jeecg.modules.tourism.entity.TourismMerchants;
import org.jeecg.modules.tourism.service.ITourismMerchantsService;

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
 * @Description: 旅游商户表
 * @Author: jeecg-boot
 * @Date:   2020-03-11
 * @Version: V1.0
 */
@RestController
@RequestMapping("/tourism/tourismMerchants")
@Slf4j
public class TourismMerchantsController extends JeecgController<TourismMerchants, ITourismMerchantsService> {
	@Autowired
	private ITourismMerchantsService tourismMerchantsService;
	@Autowired
	private IMerchantService merchantService;
	
	/**
	 * 分页列表查询
	 *
	 * @param tourismMerchants
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(TourismMerchants tourismMerchants,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<TourismMerchants> queryWrapper = QueryGenerator.initQueryWrapper(tourismMerchants, req.getParameterMap());
		Page<TourismMerchants> page = new Page<TourismMerchants>(pageNo, pageSize);
		IPage<TourismMerchants> pageList = tourismMerchantsService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param tourismMerchants
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody TourismMerchants tourismMerchants) {
		Merchant merchant = new Merchant();
		merchant.setCode(tourismMerchants.getMerchantCode());
		merchant.setName(tourismMerchants.getMerchantsName());
		merchant.setProjectId("1240496202256531457");
		merchant.setIndustryType("1");
		merchantService.save(merchant);
		tourismMerchantsService.save(tourismMerchants);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param tourismMerchants
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody TourismMerchants tourismMerchants) {
		log.info("编辑旅游商户："+tourismMerchants.toString());
		Merchant merchant = new Merchant();
		merchant.setCode(tourismMerchants.getMerchantCode());
		merchant.setName(tourismMerchants.getMerchantsName());
		Merchant m = merchantService.queryByCode(tourismMerchants.getMerchantCode());
		merchant.setId(m.getId());
		merchantService.updateById(merchant);
		tourismMerchantsService.updateById(tourismMerchants);
		return Result.ok("编辑成功!");
	}

	 /**
	  * 批量审核
	  * @param map
	  * @return
	  */
	@PutMapping(value = "/batchEdit")
	public Result<?> batchEdit(@RequestBody HashMap<String, String> map){
//		System.out.println("ids参数是："+map.get("ids")+",auditStatus参数是："+map.get("auditStatus")+",comment参数是："+map.get("comment"));
		List<TourismMerchants> tmList = new ArrayList<>();
		String ids = map.get("ids");
		if (map.get("comment") == null){
			map.put("comment","");
		}
		String tmIds[] = ids.split(",");
		for(int i = 0; i < tmIds.length; i++){
			TourismMerchants tm = new TourismMerchants();
			tm.setId(tmIds[i]);
			tm.setAuditStatus(map.get("auditStatus"));
			tm.setComment(map.get("comment"));
			tmList.add(tm);
		}
//		System.out.println("遍历封装的list**********************************");
//		for (TourismMerchants tmm:tmList) {
//			System.out.println(tmm.toString());
//		}
		tourismMerchantsService.updateBatchById(tmList);
		return Result.ok("批量审核成功！");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		Merchant merchant = merchantService.queryByCode(tourismMerchantsService.getById(id).getMerchantCode());
		merchantService.removeById(merchant.getId());
		tourismMerchantsService.removeById(id);

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
		List<String> tms = tourismMerchantsService.queryByIds(ids);
		List<String> mids = merchantService.getIdsByCodes(tms);
		merchantService.removeByIds(mids);
		this.tourismMerchantsService.removeByIds(Arrays.asList(ids.split(",")));
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
		TourismMerchants tourismMerchants = tourismMerchantsService.getById(id);
		if(tourismMerchants==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(tourismMerchants);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param tourismMerchants
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TourismMerchants tourismMerchants) {
        return super.exportXls(request, tourismMerchants, TourismMerchants.class, "旅游商户表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InstantiationException {
		System.out.println("========================================旅游商户导入========================================");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			System.out.println("文件上传对象："+file.toString());
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<TourismMerchants> list = ExcelImportUtil.importExcel(file.getInputStream(), TourismMerchants.class, params);
				System.out.println("=============================================文件内容读取start=============================================");
				List<Merchant> merchantList = new ArrayList<>();
				for(TourismMerchants tm:list){
					System.out.println(tm.toString());
					Merchant merchant = new Merchant();
					merchant.setId(UUIDGenerator.generate());//主键用uuid生成
					merchant.setName(tm.getMerchantsName());
					merchant.setCode(tm.getMerchantCode());
					merchant.setIndustryType("1");
					merchant.setProjectId("1291653981881479170");
					merchantList.add(merchant);
				}
				tourismMerchantsService.addToMerchant(merchantList);
				System.out.println("=============================================文件内容读取end=============================================");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
        return super.importExcel(request, response, TourismMerchants.class);
    }

}
