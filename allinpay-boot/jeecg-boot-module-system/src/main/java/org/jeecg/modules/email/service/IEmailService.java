package org.jeecg.modules.email.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.email.entity.SysDepart;
import org.jeecg.modules.email.entity.WorkPlan;
import org.jeecg.modules.system.entity.SysUser;

import java.util.Date;
import java.util.List;

public interface IEmailService extends IService<SysDepart> {

    /**获取发送邮件的数据*/
    public List<SysDepart> getEmailContent();
    /**获取与目标相关联的工作计划*/
    public List<WorkPlan> getPlans(String oId);

    public Date getThisMonday(Date date);

    /**下周一日期*/
    public Date getNextMonday();

    /**上周一日期*/
    public Date getNextNextMonday();

    /**查询邮件接收者列表*/
    public List<SysUser> querySendEmail();

    /**查询邮件抄送者列表*/
    public List<SysUser> queryCopyEmail();
}
