package org.jeecg.modules.email.service;

import org.jeecg.modules.email.entity.MailConfig;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 邮件发送配置
 * @Author: jeecg-boot
 * @Date:   2020-10-22
 * @Version: V1.0
 */
public interface IMailConfigService extends IService<MailConfig> {

    public List<MailConfig> queryList();

}
