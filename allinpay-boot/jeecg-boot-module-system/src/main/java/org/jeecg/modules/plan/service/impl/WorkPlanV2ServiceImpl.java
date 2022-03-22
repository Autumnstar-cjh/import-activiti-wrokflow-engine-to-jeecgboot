package org.jeecg.modules.plan.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.plan.entity.WorkPlan;
import org.jeecg.modules.plan.entity.WorkPlanV2;
import org.jeecg.modules.plan.mapper.WorkPlanMapper;
import org.jeecg.modules.plan.mapper.WorkPlanV2Mapper;
import org.jeecg.modules.plan.service.IWorkPlanV2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Description: 工作计划-版本2
 * @Author: jeecg-boot
 * @Date:   2021-03-05
 * @Version: V1.0
 */
@Service
public class WorkPlanV2ServiceImpl extends ServiceImpl<WorkPlanV2Mapper, WorkPlanV2> implements IWorkPlanV2Service {
    @Autowired
    WorkPlanV2Mapper workPlanMapper;

    @Override
    public Page<WorkPlanV2> getWorkPlanList(Page<WorkPlanV2> page, Wrapper<WorkPlanV2> workPlanWrapper) {
        List<WorkPlanV2> workPlans = workPlanMapper.getWorkPlanList(page,workPlanWrapper);
        for (WorkPlanV2 workPlan : workPlans){
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
    public IPage<WorkPlanV2> getAllPlan(Page<WorkPlanV2> page, Wrapper<WorkPlanV2> workPlanWrapper) {
        IPage<WorkPlanV2> iPage = workPlanMapper.getAllPlan(page,workPlanWrapper);
        List<WorkPlanV2> workPlans = new ArrayList<>();
        for (WorkPlanV2 w:iPage.getRecords()){
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
