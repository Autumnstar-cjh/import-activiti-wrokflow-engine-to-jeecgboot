package org.jeecg.modules.plan.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.plan.entity.WorkPlan;
import org.jeecg.modules.plan.entity.WorkPlanV2;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;

/**
 * @Description: 工作计划-版本2
 * @Author: jeecg-boot
 * @Date:   2021-03-05
 * @Version: V1.0
 */
public interface IWorkPlanV2Service extends IService<WorkPlanV2> {
    //获取工作计划列表
    public Page<WorkPlanV2> getWorkPlanList(Page<WorkPlanV2> page, Wrapper<WorkPlanV2> workPlanWrapper);
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
    public IPage<WorkPlanV2> getAllPlan(Page<WorkPlanV2> page, Wrapper<WorkPlanV2> workPlanWrapper);
    //通过id获取工作目标
    public String getObjectivesById(String oid);
}
