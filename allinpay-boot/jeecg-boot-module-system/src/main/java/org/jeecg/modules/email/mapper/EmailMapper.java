package org.jeecg.modules.email.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.email.entity.SysDepart;
import org.jeecg.modules.email.entity.WorkPlan;
import org.jeecg.modules.system.entity.SysUser;

import java.util.List;


public interface EmailMapper extends BaseMapper<SysDepart> {

    /**获取发送邮件的数据*/
    public List<SysDepart> getEmailContent();
    /**获取与目标相关联的工作计划*/
    public List<WorkPlan> getPlans(@Param("oId") String oId);
    /**查询邮件接收者列表*/
    public List<SysUser> querySendEmail();

    /**查询邮件抄送者列表*/
    public List<SysUser> queryCopyEmail();
}
