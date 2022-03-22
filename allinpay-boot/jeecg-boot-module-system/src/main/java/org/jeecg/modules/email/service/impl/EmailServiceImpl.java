package org.jeecg.modules.email.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.email.entity.SysDepart;
import org.jeecg.modules.email.entity.WorkPlan;
import org.jeecg.modules.email.mapper.EmailMapper;
import org.jeecg.modules.email.service.IEmailService;
import org.jeecg.modules.system.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class EmailServiceImpl extends ServiceImpl<EmailMapper, SysDepart> implements IEmailService {

    @Autowired
    EmailMapper emailMapper;

    @Override
    public List<SysDepart> getEmailContent() {
        return emailMapper.getEmailContent();
    }

    @Override
    public List<WorkPlan> getPlans(String oId) {
        return emailMapper.getPlans(oId);
    }

    /**本周一日期*/
    @Override
    public Date getThisMonday(Date date) {
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

    /**下周一日期*/
    @Override
    public Date getNextMonday() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisMonday(new Date()));
        cal.add(Calendar.DATE, 7);
//        cal.add(Calendar.DATE, -7);
        return cal.getTime();//返回下周一日期
    }

    /**下下周一日期*/
    @Override
    public Date getNextNextMonday() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisMonday(new Date()));
        cal.add(Calendar.DATE, 14);
//        cal.add(Calendar.DATE, 7);
        return cal.getTime();//返回上周一日期
    }

    /**查询邮件接受者列表*/
    @Override
    public List<SysUser> querySendEmail() {
        return emailMapper.querySendEmail();
    }

    /**查询邮件抄送者列表*/
    @Override
    public List<SysUser> queryCopyEmail() {
        return emailMapper.queryCopyEmail();
    }


}
