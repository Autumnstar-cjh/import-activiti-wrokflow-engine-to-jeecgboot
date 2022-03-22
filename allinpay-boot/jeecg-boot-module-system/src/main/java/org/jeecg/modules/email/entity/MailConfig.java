package org.jeecg.modules.email.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;

/**
 * @Description: 邮件发送配置
 * @Author: jeecg-boot
 * @Date:   2020-10-22
 * @Version: V1.0
 */
@Data
@TableName("mail_config")
public class MailConfig implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**id*/
	@TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
	/**服务器地址*/
	@Excel(name = "服务器地址", width = 15)
    private java.lang.String mailHost;
	/**端口号*/
	@Excel(name = "端口号", width = 15)
    private java.lang.Integer mailPort;
	/**用户名*/
	@Excel(name = "用户名", width = 15)
    private java.lang.String mailUsername;
	/**密码*/
	@Excel(name = "密码", width = 15)
    private java.lang.String mailPassword;
	/**发送者邮箱号*/
	@Excel(name = "发送者邮箱号", width = 15)
    private java.lang.String mailFrom;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
    private java.lang.String createBy;
	/**创建时间*/
	@Excel(name = "创建时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date createTime;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
    private java.lang.String updateBy;
	/**更时间*/
	@Excel(name = "更时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date updateTime;
}
