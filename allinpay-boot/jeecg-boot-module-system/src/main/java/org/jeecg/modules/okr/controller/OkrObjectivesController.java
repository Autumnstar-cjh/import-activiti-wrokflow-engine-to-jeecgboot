package org.jeecg.modules.okr.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.constant.CommonSendStatus;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.message.websocket.WebSocket;
import org.jeecg.modules.okr.entity.OkrObjectives;
import org.jeecg.modules.okr.entity.OkrObjectivesEvent;
import org.jeecg.modules.okr.entity.OkrProgress;
import org.jeecg.modules.okr.service.IOkrObjectivesEventService;
import org.jeecg.modules.okr.service.IOkrObjectivesService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.system.entity.SysAnnouncement;
import org.jeecg.modules.system.service.ISysAnnouncementSendService;
import org.jeecg.modules.system.service.ISysAnnouncementService;
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
 * @Description: OKR目标
 * @Author: jeecg-boot
 * @Date:   2019-12-03
 * @Version: V1.0
 */
@RestController
@RequestMapping("/okr/okrObjectives")
@Slf4j
public class OkrObjectivesController extends JeecgController<OkrObjectives, IOkrObjectivesService>{
	@Autowired
	private IOkrObjectivesService okrObjectivesService;

	@Autowired
	 IOkrObjectivesEventService okrObjectivesEventService;

	 @Autowired
	 private WebSocket webSocket;

	 @Autowired
	 private ISysAnnouncementService sysAnnouncementService;

	 @Autowired
	 private ISysAnnouncementSendService sysAnnouncementSendService;


	 /**
	  * 取目标整体完成数
	  * @param req
	  * @return
	  */
	 @GetMapping(value = "/getPercent")
	 public Result<?> getPercent(HttpServletRequest req) {

		 Map<String,Object> percent = okrObjectivesService.getPercent();

		 return Result.ok(percent);
	 }

	 /**
	  * 取当前用户的目标数
	  * @param okrObjectivesEvent
	  * @param req
	  * @return
	  */
	 @GetMapping(value = "/countCurUser")
	 public Result<?> countCurUser(OkrObjectives okrObjectivesEvent,
								   HttpServletRequest req) {

		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		 QueryWrapper<OkrObjectives> queryWrapper = QueryGenerator.initQueryWrapper(okrObjectivesEvent, req.getParameterMap());

		 queryWrapper.eq("assign_user", sysUser.getUsername() ) ;

		 int count = okrObjectivesService.count(queryWrapper);

		 return Result.ok(count);
	 }

	 @GetMapping(value = "/listByCurrentUser")
	 //@PermissionData(pageComponent="okr/OkrObjectivesList")
	 public Result<?> listByCurrentUser(OkrObjectives okrObjectives,
									@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									HttpServletRequest req) {

		 QueryWrapper<OkrObjectives> queryWrapper = QueryGenerator.initQueryWrapper(okrObjectives, req.getParameterMap());

		 Page<OkrObjectives> page = new Page<OkrObjectives>(pageNo, pageSize);
		 IPage<OkrObjectives> pageList = okrObjectivesService.page(page, queryWrapper);
		 return Result.ok(pageList);
	 }
	
	/**
	 * 分页列表查询
	 *
	 * @param okrObjectives
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/rootList")
	//@PermissionData(pageComponent="okr/OkrObjectivesList")
	public Result<?> queryPageList(OkrObjectives okrObjectives,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		if(oConvertUtils.isEmpty(okrObjectives.getParentId())){
			okrObjectives.setParentId("0");
		}
		QueryWrapper<OkrObjectives> queryWrapper = QueryGenerator.initQueryWrapper(okrObjectives, req.getParameterMap());
		Page<OkrObjectives> page = new Page<OkrObjectives>(pageNo, pageSize);
		IPage<OkrObjectives> pageList = okrObjectivesService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	 /**
      * 获取子数据
      * @param testTree
      * @param req
      * @return
      */
	@GetMapping(value = "/childList")
	public Result<?> queryPageList(OkrObjectives okrObjectives,HttpServletRequest req) {
		QueryWrapper<OkrObjectives> queryWrapper = QueryGenerator.initQueryWrapper(okrObjectives, req.getParameterMap());
		List<OkrObjectives> list = okrObjectivesService.list(queryWrapper);
		return Result.ok(list);
	}
	
	
	/**
	 *   添加
	 *
	 * @param okrObjectives
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody OkrObjectives okrObjectives) {
		okrObjectivesService.addOkrObjectives(okrObjectives);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param okrObjectives
	 * @return
	 */
	@PutMapping(value = "/editFinished")
	public Result<?> editFinished(@RequestBody OkrObjectives okrObjectives) {

		OkrObjectives oldOkrObjectives = okrObjectivesService.getById(okrObjectives.getId());

		if(  oldOkrObjectives.getHasChild() != null ){
			return Result.error(-1, "非明细末级目标不允许直接修改");
		}


		OkrObjectivesEvent okrObjectivesEvent = new OkrObjectivesEvent();

		okrObjectivesEvent.setOkrObjectivesId(okrObjectives.getId());
		okrObjectivesEvent.setOkrObjectives(okrObjectives.getObjectives());
		okrObjectivesEvent.setOkrAssignUser(okrObjectives.getAssignUser());
		okrObjectivesEvent.setNewFinishedAmount(okrObjectives.getFinishedAmount());
		okrObjectivesEvent.setOldFinishedAmount(oldOkrObjectives.getFinishedAmount());
		okrObjectivesEvent.setNewTargetAmount(okrObjectives.getTargetAmount());
		okrObjectivesEvent.setOldTargetAmount(oldOkrObjectives.getTargetAmount());
		okrObjectivesEvent.setRemark( okrObjectives.getRemark() );

		okrObjectivesEvent.setUserAction("目标完成值更新");
		okrObjectivesEvent.setCreateTime(new Date());

		okrObjectivesEventService.save(okrObjectivesEvent);

		// ToDo: 这里加个递归计算，把上级的目标值更新一下。

		if(oldOkrObjectives.getParentId() != null)
		{
			BigDecimal diff = okrObjectives.getFinishedAmount().subtract(oldOkrObjectives.getFinishedAmount());

			OkrObjectives parentOkr = okrObjectivesService.getById(oldOkrObjectives.getParentId());
			while (parentOkr != null){
				parentOkr.setFinishedAmount( parentOkr.getFinishedAmount().add(diff) );
				if(parentOkr.getTargetAmount().compareTo( BigDecimal.ZERO ) > 0 ) {
					BigDecimal percent = parentOkr.getFinishedAmount().divide(parentOkr.getTargetAmount(), 4, BigDecimal.ROUND_HALF_UP);
					parentOkr.setPercent(percent.multiply(BigDecimal.valueOf(100.0)));
				}
				okrObjectivesService.updateOkrObjectives(parentOkr);
				parentOkr = okrObjectivesService.getById(parentOkr.getParentId());
			}
		}

		if(okrObjectives.getTargetAmount().compareTo( BigDecimal.ZERO ) > 0 ) {
			BigDecimal percent = okrObjectives.getFinishedAmount().divide(okrObjectives.getTargetAmount(), 4, BigDecimal.ROUND_HALF_UP);
			okrObjectives.setPercent(percent.multiply(BigDecimal.valueOf(100.0)));
		}


		// 发个消息给大家
		sysAnnouncementService.saveSysAnnouncement("系统消息", okrObjectivesEvent.getOkrAssignUser() + "更新了目标" +
				okrObjectivesEvent.getOkrObjectives() + "。");

		okrObjectivesService.updateOkrObjectives(okrObjectives);
		return Result.ok("编辑成功!");
	}

	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody OkrObjectives okrObjectives) {

		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		OkrObjectives okr = okrObjectivesService.getById(okrObjectives.getId());

		if( sysUser.getUsername().compareToIgnoreCase( okr.getCreateBy() ) != 0
		   && sysUser.getUsername().compareToIgnoreCase( okr.getAssignUser() ) != 0){
			Result.error(401, "只可以修改和本人相关的目标");
		}


		okrObjectivesService.updateOkrObjectives(okrObjectives);
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
		okrObjectivesService.deleteOkrObjectives(id);
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
		this.okrObjectivesService.removeByIds(Arrays.asList(ids.split(",")));
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
		OkrObjectives okrObjectives = okrObjectivesService.getById(id);
		if(okrObjectives==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(okrObjectives);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param okrObjectives
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OkrObjectives okrObjectives) {
		return super.exportXls(request, okrObjectives, OkrObjectives.class, "OKR目标");
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
		return super.importExcel(request, response, OkrObjectives.class);
    }

}
