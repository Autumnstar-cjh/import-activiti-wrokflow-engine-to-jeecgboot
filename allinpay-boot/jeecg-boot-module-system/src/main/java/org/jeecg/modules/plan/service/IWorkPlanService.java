package org.jeecg.modules.plan.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.plan.entity.WorkPlan;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

/**
 * @Description: 工作计划
 * @Author: jeecg-boot
 * @Date:   2020-01-02
 * @Version: V1.0
 */
public interface IWorkPlanService extends IService<WorkPlan> {
    //获取工作计划列表
    public Page<WorkPlan> getWorkPlanList(Page<WorkPlan> page,Wrapper<WorkPlan> workPlanWrapper);
    //获取本周一的日期
    public Date getThisWeekMonday(Date date);
    //获取本周日的日期
    public Date getThisWeekFriday(Date date);
    //获取下周一的日期
    public Date getNextWeekMonday(Date date);
    //获取下周日的日期
    public Date getNextWeekFriday(Date date);
    //获取真实姓名
    public String getRealName(String username);
    //获取所有人工作计划列表
    public IPage<WorkPlan> getAllPlan(Page<WorkPlan> page,Wrapper<WorkPlan> workPlanWrapper);
    //通过id获取工作目标
    public String getObjectivesById(String oid);
}
