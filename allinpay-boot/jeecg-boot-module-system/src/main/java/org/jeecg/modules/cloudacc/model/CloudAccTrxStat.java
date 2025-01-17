package org.jeecg.modules.cloudacc.model;

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
 * @Description: 云商通账户交易统计报表
 * @Author: jeecg-boot
 * @Date:   2021-07-07
 * @Version: V1.0
 */
@Data
@TableName("cloud_acc_trx_stat")
public class CloudAccTrxStat implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    private String id;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
    private String createBy;
	/**创建日期*/
	@Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
    private String updateBy;
	/**更新日期*/
	@Excel(name = "更新日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
	/**所属部门*/
	@Excel(name = "所属部门", width = 15)
    private String sysOrgCode;
	/**应用编号*/
	@Excel(name = "应用编号", width = 15)
    private String appId;
	/**应用名称*/
	@Excel(name = "应用名称", width = 15)
    private String appName;
	/**总金额*/
	@Excel(name = "总金额", width = 15)
    private BigDecimal amountTotal;
	/**总笔数*/
	@Excel(name = "总笔数", width = 15)
    private Integer orderTotal;
	/**入金金额*/
	@Excel(name = "入金金额", width = 15)
    private BigDecimal incomeAmountTotal;
	/**入金笔数*/
	@Excel(name = "入金笔数", width = 15)
    private Integer incomeOrderTotal;
	/**退款金额*/
	@Excel(name = "退款金额", width = 15)
    private BigDecimal refundAmountTotal;
	/**退款笔数*/
	@Excel(name = "退款笔数", width = 15)
    private Integer refundOrderTotal;
	/**出金金额*/
	@Excel(name = "出金金额", width = 15)
    private BigDecimal withdrawAmountTotal;
	/**出金笔数*/
	@Excel(name = "出金笔数", width = 15)
    private Integer withdrawOrderTotal;
}
