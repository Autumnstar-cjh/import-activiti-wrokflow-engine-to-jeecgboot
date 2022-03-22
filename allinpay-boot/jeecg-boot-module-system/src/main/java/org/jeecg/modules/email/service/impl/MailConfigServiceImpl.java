package org.jeecg.modules.email.service.impl;

import org.jeecg.modules.email.entity.MailConfig;
import org.jeecg.modules.email.mapper.MailConfigMapper;
import org.jeecg.modules.email.service.IMailConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 邮件发送配置
 * @Author: jeecg-boot
 * @Date:   2020-10-22
 * @Version: V1.0
 */
@Service
public class MailConfigServiceImpl extends ServiceImpl<MailConfigMapper, MailConfig> implements IMailConfigService {

    @Autowired
    public MailConfigMapper mailConfigMapper;

    @Override
    public List<MailConfig> queryList() {
        return mailConfigMapper.queryList();
    }
}
