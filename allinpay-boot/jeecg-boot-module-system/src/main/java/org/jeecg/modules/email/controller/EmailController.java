package org.jeecg.modules.email.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.email.entity.*;
import org.jeecg.modules.email.service.IEmailService;
import org.jeecg.modules.email.service.IMailConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/email/sendEmail")
@Slf4j
public class EmailController {

    @Autowired
    private IEmailService emailService;

    @Autowired
    private IMailConfigService mailConfigService;

    @GetMapping(value = "/toSendMail")
    public Result<?> sendMail(String sendTo,String copyTo){
        String[] sendto = null;
        String[] copyto = null;
        if(sendTo.length()>1){
            sendto = sendTo.split(",");
        }else{
            sendto = insert(sendto,sendTo);
        }
        if(!("".equals(copyTo)) && copyTo!=null){
            copyto = copyTo.split(",");
        }else{
            copyto = null;
        }
        return doEmailSend(sendto,copyto);
    }

    public Result<?> doEmailSend(String[] sendTo,String[] copyTo){

        log.info("Send email start...");

        List<SysDepart> sysDeparts = emailService.getEmailContent();
        String emailContent = "";
        for (SysDepart s:sysDeparts) {
            emailContent+="\t=============================="+s.getDepartName()+"==============================\n\n";
            for (Objectives o:s.getObjectivesList()) {
                if(o.getId().equals(s.getObjectivesList().get(0).getId())){
                    emailContent+="\t目标："+o.getObjectives()+"\n";
                }else{
                    emailContent+="\t------------------------------------------------------\n\t目标："+o.getObjectives()+"\n";
                }

                for(KeyResults k:o.getKeyResults()){
                    emailContent+="\t关键结果："+k.getContent()+"("+k.getConfidence()+")\n";
                }
                emailContent+="\n";
                List<WorkPlan> workPlans = emailService.getPlans(o.getId());
                emailContent+="\t本周：\n";
                for(WorkPlan w:workPlans){
                    if (judgeWeek(w.getStartTime())==1){
                        switch (w.getPriority()){
                            case "100":
                                if(w.getCompletedSituation() != null){
                                    emailContent+="\tP1:"+w.getTitle()+"/"+w.getCompletedSituation()+"\n";
                                }else{
                                    emailContent+="\tP1:"+w.getTitle()+"\n";
                                }
                                break;
                            case "75":
                                if(w.getCompletedSituation() != null){
                                    emailContent+="\tP2:"+w.getTitle()+"/"+w.getCompletedSituation()+"\n";
                                }else{
                                    emailContent+="\tP2:"+w.getTitle()+"\n";
                                }
                                break;
                            case "50":
                                if(w.getCompletedSituation() != null){
                                    emailContent+="\tP3:"+w.getTitle()+"/"+w.getCompletedSituation()+"\n";
                                }else{
                                    emailContent+="\tP3:"+w.getTitle()+"\n";
                                }
                                break;
                            case "0":
                                if(w.getCompletedSituation() != null){
                                    emailContent+="\tP4:"+w.getTitle()+"/"+w.getCompletedSituation()+"\n";
                                }else{
                                    emailContent+="\tP4:"+w.getTitle()+"\n";
                                }
                                break;
                        }
                    }
                }
                emailContent+="\n";
                emailContent+="\t下周：\n";
                for(WorkPlan w:workPlans){
                    if (judgeWeek(w.getStartTime())==2){
                        switch (w.getPriority()){
                            case "100":
                                emailContent+="\tP1:"+w.getTitle()+"\n";
                                break;
                            case "75":
                                emailContent+="\tP2:"+w.getTitle()+"\n";
                                break;
                            case "50":
                                emailContent+="\tP3:"+w.getTitle()+"\n";
                                break;
                            case "0":
                                emailContent+="\tP4:"+w.getTitle()+"\n";
                                break;
                        }
                    }
                }
                emailContent+="\n";
            }
        }
        log.info(emailContent);

        log.info("==========Send email to================================");
        for(int i=0;i<sendTo.length;i++){
            log.info(sendTo[i]);
        }
        if(copyTo!=null){
            log.info("==========Copy email to================================");
            for(int j=0;j<copyTo.length;j++){
                log.info(copyTo[j]);
            }
        }
        /**
         * 版本一：发送邮件的相关参数从application-pro.yml(生产环境)或application-dev.yml(测试环境)中读取
         */
//        JavaMailSender mailSender = (JavaMailSender) SpringContextUtils.getBean("mailSender");
//        message.setFrom("hanyz1@allinpay.com");
//        message.setTo(sendTo);
//        if(!("".equals(copyTo))){
//            message.setCc(copyTo);
//        }
//        message.setSubject("okr目标");
//        message.setText(emailContent);
//        mailSender.send(message);

        /**
         * 版本二：发送邮件的相关参数从数据库中读取
         */
        //查询邮件发送配置表
        List<MailConfig>  list = mailConfigService.queryList();
        if(list != null){
            MailConfig mailConfig = list.get(0);

            JavaMailSenderImpl jms = new JavaMailSenderImpl();
            jms.setHost(mailConfig.getMailHost());
            jms.setPort(mailConfig.getMailPort());
            jms.setUsername(mailConfig.getMailUsername());
            jms.setPassword(mailConfig.getMailPassword());
            jms.setDefaultEncoding("utf-8");

            SimpleMailMessage smm = new SimpleMailMessage();
            smm.setFrom(mailConfig.getMailFrom());
            smm.setTo(sendTo);
            smm.setCc(copyTo);
            smm.setSubject("okr目标");
            smm.setText(emailContent);


            jms.send(smm);
            log.info("Send email success!");
            return Result.ok("邮件已发送");
        }
        log.info("Send email failure!");
        return Result.error("发送失败！请先进行邮件发送配置");
    }


    /**
     * 判断工作计划为上周还是下周
     * @param startTime
     * @return 本周计划 1，下周计划 2，其他 0
     */
    public int judgeWeek(Date startTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String staTime = sdf.format(startTime);
        Date thisMonday = emailService.getThisMonday(new Date());
        Date nextMonday = emailService.getNextMonday();
        Date nextNextMonday = emailService.getNextNextMonday();
//        System.out.println("本周一："+sdf.format(thisMonday)+",上周一："+sdf.format(nextMonday)+",下周一："+sdf.format(nextNextMonday));
        if(thisMonday.getTime()<=startTime.getTime() && startTime.getTime()<nextMonday.getTime()){
            return 1;
        }
        if(nextMonday.getTime()<=startTime.getTime() && startTime.getTime()<nextNextMonday.getTime()){
            return 2;
        }
//        System.out.println("上周："+nextMonday.getTime()+"，本周："+thisMonday.getTime()+",下周："+nextNextMonday.getTime());
//        if(nextMonday.getTime()<=startTime.getTime() && startTime.getTime()<thisMonday.getTime()){
//            return 1;
//        }
//        if(thisMonday.getTime()<=startTime.getTime() && startTime.getTime()<nextNextMonday.getTime()){
//            return 2;
//        }
        return 0;
    }
    // 往字符串数组追加新数据
    private static String[] insert(String[] arr, String... str) {
        int size = arr.length; // 获取原数组长度
        int newSize = size + str.length; // 原数组长度加上追加的数据的总长度

        // 新建临时字符串数组
        String[] tmp = new String[newSize];
        // 先遍历将原来的字符串数组数据添加到临时字符串数组
        for (int i = 0; i < size; i++) {
            tmp[i] = arr[i];
        }
        // 在末尾添加上需要追加的数据
        for (int i = size; i < newSize; i++) {
            tmp[i] = str[i - size];
        }
        return tmp; // 返回拼接完成的字符串数组
    }
}
