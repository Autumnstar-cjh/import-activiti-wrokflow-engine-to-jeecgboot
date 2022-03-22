package org.jeecg.modules.plan.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.plan.entity.WorkPlan;
import org.jeecg.modules.plan.mapper.WorkPlanMapper;
import org.jeecg.modules.plan.service.IWorkPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Description: 工作计划
 * @Author: jeecg-boot
 * @Date:   2020-01-02
 * @Version: V1.0
 */
@Service
public class WorkPlanServiceImpl extends ServiceImpl<WorkPlanMapper, WorkPlan> implements IWorkPlanService {

    @Autowired
    WorkPlanMapper workPlanMapper;

    @Override
    public Page<WorkPlan> getWorkPlanList(Page<WorkPlan> page,Wrapper<WorkPlan> workPlanWrapper) {
        List<WorkPlan> workPlans = workPlanMapper.getWorkPlanList(page,workPlanWrapper);
        for (WorkPlan workPlan : workPlans){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日");
            String startTime = simpleDateFormat.format(workPlan.getStartTime());
            String endTime = simpleDateFormat.format(workPlan.getEndTime());
            workPlan.setWeek(startTime+"-"+endTime);
            if(!("无".equals(workPlan.getAssociatedTarget().trim()))){
                String associatedTarget = workPlanMapper.getObjectivesById(workPlan.getAssociatedTarget());
                workPlan.setAssociatedTargetContent(associatedTarget);
            }
            if("0".equals(workPlan.getStatus().trim())){
                workPlan.setStatus("未完成");
            }else if ("1".equals(workPlan.getStatus().trim())){
                workPlan.setStatus("已完成");
            }
        }
        page.setRecords(workPlans);
        return page;
    }

    @Override
    public Date getThisWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);//这样写的目的是避免使用Calendar获取毫秒数时的误差
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();//返回本周一日期
    }

    @Override
    public Date getThisWeekFriday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, 4);
        return cal.getTime();//返回本周五日期
    }

    @Override
    public Date getNextWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, 7);
        return cal.getTime();//返回下周一日期
    }

    @Override
    public Date getNextWeekFriday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, 11);
        return cal.getTime();//返回下周五日期
    }

    @Override
    public String getRealName(String username) {
        return workPlanMapper.getRealName(username);
    }

    @Override
    public IPage<WorkPlan> getAllPlan(Page<WorkPlan> page,Wrapper<WorkPlan> workPlanWrapper) {
        IPage<WorkPlan> iPage = workPlanMapper.getAllPlan(page,workPlanWrapper);
        List<WorkPlan> workPlans = new ArrayList<>();
        for (WorkPlan w:iPage.getRecords()){
            w.setAssociatedTarget(workPlanMapper.getObjectivesById(w.getAssociatedTarget()));
            workPlans.add(w);
        }
        iPage.setRecords(workPlans);
        return iPage;
    }

    @Override
    public String getObjectivesById(String oid){
        return workPlanMapper.getObjectivesById(oid);
    }
}
