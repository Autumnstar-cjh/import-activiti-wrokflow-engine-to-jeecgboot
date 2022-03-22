package org.jeecg.modules.email.job;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.email.controller.EmailController;
import org.jeecg.modules.email.service.IEmailService;
import org.jeecg.modules.system.entity.SysUser;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 定时发送邮件
 */
@Slf4j
@Component
public class RegularlySendEmail implements Job {
    @Autowired
    EmailController ec;

    @Autowired
    IEmailService emailService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        List<SysUser> sendList = emailService.querySendEmail();
//        List<SysUser> copyList = emailService.queryCopyEmail();
//        String[] sendTo = new String[sendList.size()];
//        String[] copyTo = new String[copyList.size()];
//
//        if(sendList != null){
//            for(int i = 0; i < sendList.size(); i++){
//                System.out.println(sendList.get(i).getEmail());
//                sendTo[i] = sendList.get(i).getEmail();
//            }
//        }
//        if(copyList != null){
//            for(int i = 0; i < copyList.size(); i++){
//                System.out.println(copyList.get(i).getEmail());
//                copyTo[i] = copyList.get(i).getEmail();
//            }
//        }

        //开发环境下使用以下参数，将以上代码注释
//        String[] sendTo = {"liuzh3@allinpay.com"};
//        String[] copyTo = {};




//        ec.doEmailSend(sendTo,copyTo).getMessage();
        log.info(String.format(" The email has been sent !  The time is:" + DateUtils.getTimestamp()));
    }


}
