package org.jeecg.modules.email.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.email.entity.MailConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 邮件发送配置
 * @Author: jeecg-boot
 * @Date:   2020-10-22
 * @Version: V1.0
 */
public interface MailConfigMapper extends BaseMapper<MailConfig> {

    public List<MailConfig> queryList();
}
