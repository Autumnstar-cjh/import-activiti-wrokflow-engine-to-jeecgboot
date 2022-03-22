package org.jeecg.modules.plan.controller;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.plan.entity.DateObj;
import org.jeecg.modules.plan.entity.WorkPlan;
import org.jeecg.modules.plan.service.IWorkPlanService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: 工作计划
 * @Author: jeecg-boot
 * @Date:   2020-01-02
 * @Version: V1.0
 */
@RestController
@RequestMapping("/plan/workPlan")
@Slf4j
public class WorkPlanController extends JeecgController<WorkPlan, IWorkPlanService> {
	@Autowired
	private IWorkPlanService workPlanService;

	 /**
	  * 分页列表查询
	  *
	  * @param workPlan
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @GetMapping(value = "/list")
	 public Result<?> queryPageList(@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									WorkPlan workPlan,
									HttpServletRequest req) {
		 QueryWrapper<WorkPlan> queryWrapper = QueryGenerator.initQueryWrapper(workPlan, req.getParameterMap());
		 Page<WorkPlan> page = new Page<>(pageNo,pageSize);
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		 queryWrapper.eq("w.person",  sysUser.getUsername()) ;
		 IPage<WorkPlan> pageList = workPlanService.getWorkPlanList(page,queryWrapper);
//		 System.out.println("=====================================================奇妙的分割线=====================================================");
//		 System.out.println("workplan参数 ：" + workPlan.toString());
//		 System.out.println("当前查询第"+pageList.getCurrent()+"页");
//		 System.out.println("当前页查询"+pageList.getSize()+"条数据");
//		 System.out.println("查询结果为"+pageList.getRecords().size()+"条数据");
//		 System.out.println("数据总条数"+pageList.getTotal());
//         System.out.println("当前共"+pageList.getPages()+"页");
		 return Result.ok(pageList);
	 }

	/**
	 * 通过id获取工作目标内容
	 * @param oid
	 * @return
	 */
	 @GetMapping(value = "/getObjectivesById")
	 public Result<?> getObjectivesById(@RequestParam(name = "oid") String oid){
	 	String objectives = workPlanService.getObjectivesById(oid);
	 	return Result.ok(objectives);
	 }
	 /** efc
	  * 获取所有人的工作计划
	  * @param workPlan
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @GetMapping(value = "allPlanList")
	 public Result<?> allPlanList(@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                  WorkPlan workPlan,
								  HttpServletRequest req){
	 	QueryWrapper<WorkPlan> queryWrapper = QueryGenerator.initQueryWrapper(workPlan,req.getParameterMap());
	 	queryWrapper.orderByDesc("create_time");
	 	Page<WorkPlan> page = new Page<>(pageNo, pageSize);
	 	IPage<WorkPlan> pageList = workPlanService.getAllPlan(page,queryWrapper);
	 	List<WorkPlan> list = new ArrayList<>();
//		 System.out.println("所有人的工作计划："+pageList.getRecords().get(1));
		 for (WorkPlan ww:pageList.getRecords()) {
		 	if("0".equals(ww.getStatus().trim())){
		 		ww.setStatus("未完成");
			}else if("1".equals(ww.getStatus().trim())){
				 ww.setStatus("已完成");
			 }
		 	switch (ww.getPriority().trim()){
				case "0":
					ww.setPriority("无");
					break;
				case "50":
					ww.setPriority("低");
					break;
				case "75":
					ww.setPriority("中");
					break;
				case "100":
					ww.setPriority("高");
					break;
			}
			ww.setPerson(workPlanService.getRealName(ww.getPerson().trim()));
//			 System.out.println("所有人的工作计划："+ww);
			 list.add(ww);
		 }
		 pageList.setRecords(list);
	 	 return Result.ok(pageList);

	 }

	@GetMapping(value = "/getDate")
	public Result<?> getDate(HttpServletRequest req){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DateObj dateObj = new DateObj();
        dateObj.setStartTime(sdf.format(workPlanService.getNextWeekMonday(new Date())));//下周一
        dateObj.setEndTime(sdf.format(workPlanService.getNextWeekFriday(new Date())));//下周五
	    return Result.ok(dateObj);
    }

	/**
	 *   添加
	 *
	 * @param workPlan
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WorkPlan workPlan) {
		return addPlan(workPlan);
	}


	/**
	 *  编辑
	 *
	 * @param workPlan
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WorkPlan workPlan) {
		if(workPlan.getIsPostpone() == null || "".equals(workPlan.getIsPostpone())){
			workPlan.setIsPostpone("0");
		}
		workPlan.setAssociatedTargetContent("");
		workPlan.setWeek("");
		if("已完成".equals(workPlan.getStatus().trim())){
			workPlan.setStatus("1");
		}
		if("未完成".equals(workPlan.getStatus().trim())){
			workPlan.setStatus("0");
		}
        workPlan.setStartTime(addDay(workPlan.getStartTime()));
        workPlan.setEndTime(addDay(workPlan.getEndTime()));
		workPlanService.updateById(workPlan);
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
		workPlanService.removeById(id);
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
		this.workPlanService.removeByIds(Arrays.asList(ids.split(",")));
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
		WorkPlan workPlan = workPlanService.getById(id);
		if(workPlan==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(workPlan);
	}

	/**
	 * 获取当前用户计划数
	 *
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/countCurUser")
	public Result<?> countCurUser(WorkPlan workPlan, HttpServletRequest req){
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		QueryWrapper<WorkPlan> queryWrapper = QueryGenerator.initQueryWrapper(workPlan,req.getParameterMap());
		queryWrapper.eq("person",sysUser.getUsername());

		int count = workPlanService.count(queryWrapper);
		return Result.ok(count);
	}


    /**
    * 导出excel
    *
    * @param request
    * @param workPlan
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WorkPlan workPlan) {
        return super.exportXls(request, workPlan, WorkPlan.class, "工作计划");
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
        return super.importExcel(request, response, WorkPlan.class);
    }

    public Date addDay(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE,1);
        return cal.getTime();
    }

    public Result<?> addPlan(WorkPlan workPlan){
		//如果顺延状态为 1，则开始时间和结束时间往后+7天
		if("1".equals(workPlan.getIsPostpone())){
//			System.out.println("顺延状态为true，工作计划是："+workPlan.toString());
			Calendar startCalendar = new GregorianCalendar();
			startCalendar.setTime(workPlan.getStartTime());
			startCalendar.add(Calendar.DATE,7);
			workPlan.setStartTime(startCalendar.getTime());//开始时间往后推7天
			Calendar endCalendar = new GregorianCalendar();
			endCalendar.setTime(workPlan.getEndTime());
			endCalendar.add(Calendar.DATE,7);
			workPlan.setEndTime(endCalendar.getTime());//结束时间往后推7天
			if(!(workPlan.getTitle().contains("[顺延]"))){
				workPlan.setTitle(workPlan.getTitle()+"[顺延]");
			}
			workPlan.setId(null);
            workPlan.setCreateTime(null);
            workPlan.setCreateBy(null);
            workPlan.setUpdateTime(null);
            workPlan.setUpdateBy(null);
		}
    	if(workPlan.getIsPostpone() == null || "".equals(workPlan.getIsPostpone())){
			workPlan.setIsPostpone("0");
		}
		workPlan.setAssociatedTargetContent("");
		workPlan.setWeek("");
		if("".equals(workPlan.getAssociatedTarget().trim()) || workPlan.getAssociatedTarget().trim() == null){
			workPlan.setAssociatedTarget("无");
		}
		if("已完成".equals(workPlan.getStatus().trim())){
			workPlan.setStatus("1");
		}
		if("未完成".equals(workPlan.getStatus().trim())){
			workPlan.setStatus("0");
		}
		workPlan.setStartTime(addDay(workPlan.getStartTime()));
		workPlan.setEndTime(addDay(workPlan.getEndTime()));
		workPlanService.save(workPlan);
		return Result.ok("添加成功！");
	}
}
