package org.jeecg.modules.cloudacc.entity;

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
 * @Description: 通商云交易统计
 * @Author: jeecg-boot
 * @Date:   2021-07-03
 * @Version: V1.0
 */
@Data
@TableName("cloud_acc_trx")
public class CloudAccTrx implements Serializable {
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
	/**报表日期*/
	@Excel(name = "起始时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date reportDate;
	/**充值金额*/
	@Excel(name = "充值金额", width = 15)
    private BigDecimal rechargeTotal;
	/**充值订单笔数*/
	@Excel(name = "充值笔数", width = 15)
    private Integer rechargeOrderTotal;
	/**消费金额*/
	@Excel(name = "消费金额", width = 15)
    private BigDecimal purchaseTotal;
	/**消费订单笔数*/
	@Excel(name = "消费笔数", width = 15)
    private Integer purchaseOrderTotal;
	/**提现金额*/
	@Excel(name = "提现金额", width = 15)
    private BigDecimal withdrawTotal;
	/**提现订单笔数*/
	@Excel(name = "提现笔数", width = 15)
    private Integer withdrawOrderTotal;
	/**委托代收金额*/
	@Excel(name = "代收金额", width = 15)
    private BigDecimal agentCollectTotal;
	/**委托代收笔数*/
	@Excel(name = "代收笔数", width = 15)
    private Integer agentCollectOrderTotal;
	/**委托代付金额*/
	@Excel(name = "代付金额", width = 15)
    private BigDecimal agentPayTotal;
	/**委托代付笔数*/
	@Excel(name = "代付笔数", width = 15)
    private Integer agentPayOrderTotal;
	/**批量代付金额*/
	@Excel(name = "批量代付金额", width = 15)
    private BigDecimal batchPayTotal;
	/**批量代付笔数*/
	@Excel(name = "批量代付笔数", width = 15)
    private Integer batchPayOrderTotal;
	/**退款金额*/
	@Excel(name = "退款金额", width = 15)
    private BigDecimal refundTotal;
	/**退款笔数*/
	@Excel(name = "退款笔数", width = 15)
    private Integer refundOrderTotal;
}
