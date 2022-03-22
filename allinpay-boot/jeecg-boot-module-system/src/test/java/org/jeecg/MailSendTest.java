package org.jeecg;

import org.jeecg.modules.email.entity.MailConfig;
import org.jeecg.modules.email.service.IMailConfigService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.List;

@SpringBootTest
public class MailSendTest {

    @Autowired
    public IMailConfigService mailConfigService;

    @Test
    public void doSend(){

        //查询邮件发送配置表
        List<MailConfig>  list = mailConfigService.queryList();
        System.out.println("是否为空？");
        System.out.println(list==null);
        for(MailConfig m:list){
            System.out.println(m.toString());
        }
        MailConfig mailConfig = list.get(0);
        System.out.println("MailConfig:"+mailConfig.toString());

        JavaMailSenderImpl jms = new JavaMailSenderImpl();
        jms.setHost(mailConfig.getMailHost());
        jms.setPort(mailConfig.getMailPort());
        jms.setUsername(mailConfig.getMailUsername());
        jms.setPassword(mailConfig.getMailPassword());

        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setFrom(mailConfig.getMailFrom());
        smm.setTo("liuzh3@allinpay.com");
        smm.setCc("hanyz1@allinpay.com");
        smm.setSubject("测试");
        smm.setText("测试测试测试测试测试测试测试");

        jms.send(smm);
    }


}
