package org.jeecg.modules.cloudacc.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import java.util.Iterator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.jeecg.modules.cloudacc.model.CloudAccOrderExcelModel;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;

/**
 * @Description: 通商云订单交易报表
 * @Author: jeecg-boot
 * @Date:   2022-04-01
 * @Version: V1.0
 */
@Data
@TableName("cloud_acc_order")
public class CloudAccOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**id*/
	@TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
    private java.lang.String createBy;
	/**创建日期*/
	@Excel(name = "创建日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date createTime;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
    private java.lang.String updateBy;
	/**更新日期*/
	@Excel(name = "更新日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date updateTime;
	/**所属部门*/
	@Excel(name = "所属部门", width = 15)
    private java.lang.String sysOrgCode;
	/**应用编号*/
	@Excel(name = "应用编号", width = 15)
    private java.lang.String appId;
	/**应用名称*/
	@Excel(name = "应用名称", width = 15)
    private java.lang.String appName;
	/**起始时间*/
	@Excel(name = "起始时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date reportDate;
	/**总金额*/
	@Excel(name = "总金额", width = 15)
    private java.math.BigDecimal amountTotal;
	/**总笔数*/
	@Excel(name = "总笔数", width = 15)
    private java.lang.Integer orderTotal;
	/**充值金额*/
	@Excel(name = "充值金额", width = 15)
    private java.math.BigDecimal popupAmount;
	/**充值笔数*/
	@Excel(name = "充值笔数", width = 15)
    private java.lang.Integer popupNum;
	/**消费笔数*/
	@Excel(name = "消费笔数", width = 15)
    private java.math.BigDecimal consAmount;
	/**消费笔数*/
	@Excel(name = "消费笔数", width = 15)
    private java.lang.Integer consNum;
	/**提现金额*/
	@Excel(name = "提现金额", width = 15)
    private java.math.BigDecimal withdrAmount;
	/**提现笔数*/
	@Excel(name = "提现笔数", width = 15)
    private java.lang.Integer withdrNum;
	/**代收金额*/
	@Excel(name = "代收金额", width = 15)
    private java.math.BigDecimal collAmount;
	/**代收笔数*/
	@Excel(name = "代收笔数", width = 15)
    private java.lang.Integer collNum;
	/**代付金额*/
	@Excel(name = "代付金额", width = 15)
    private java.math.BigDecimal paidAmount;
	/**代付笔数*/
	@Excel(name = "代付笔数", width = 15)
    private java.lang.Integer paidNum;
	/**批量代付金额*/
	@Excel(name = "批量代付金额", width = 15)
    private java.math.BigDecimal batPaidAmount;
	/**批量代付笔数*/
	@Excel(name = "批量代付笔数", width = 15)
    private java.lang.Integer batPaidNum;
	/**跨应用转账金额*/
	@Excel(name = "跨应用转账金额", width = 15)
    private java.math.BigDecimal transferAmount;
	/**跨应用转账笔数*/
	@Excel(name = "跨应用转账笔数", width = 15)
    private java.lang.Integer transferNum;
	/**退款金额*/
	@Excel(name = "退款金额", width = 15)
    private java.math.BigDecimal refundAmount;
	/**退款笔数*/
	@Excel(name = "退款笔数", width = 15)
    private java.lang.Integer refundNum;
	/**平台转账金额*/
	@Excel(name = "平台转账金额", width = 15)
    private java.math.BigDecimal plTraAmount;
	/**平台转账笔数*/
	@Excel(name = "平台转账笔数", width = 15)
    private java.lang.Integer plaTraNum;
	/**理财金额*/
	@Excel(name = "理财金额", width = 15)
    private java.math.BigDecimal fiAmount;
	/**理财笔数*/
	@Excel(name = "理财笔数", width = 15)
    private java.lang.Integer fiNum;
	/**协议消费金额*/
	@Excel(name = "协议消费金额", width = 15)
    private java.math.BigDecimal agrPayAmount;
	/**协议消费笔数*/
	@Excel(name = "协议消费笔数", width = 15)
    private java.lang.Integer agrPayNum;
	/**协议代收金额*/
	@Excel(name = "协议代收金额", width = 15)
    private java.math.BigDecimal agrCollAmount;
	/**协议代收笔数*/
	@Excel(name = "协议代收笔数", width = 15)
    private java.lang.Integer agrCollNum;
	/**跨境提现金额*/
	@Excel(name = "跨境提现金额", width = 15)
    private java.math.BigDecimal crossAmount;
	/**跨境提现笔数*/
	@Excel(name = "跨境提现笔数", width = 15)
    private java.lang.Integer crossNum;

	public CloudAccOrder(){}


	public CloudAccOrder(CloudAccOrderExcelModel model){
//		CloudAccOrder order = new CloudAccOrder();
		this.orderTotal = substringHandler(model.getOrderTotal());
		this.popupNum = substringHandler(model.getPopupNum());
		this.consNum = substringHandler(model.getConsNum());
		this.withdrNum = substringHandler(model.getWithdrNum());
		this.collNum = substringHandler(model.getCollNum());
		this.paidNum = substringHandler(model.getPaidNum());
		this.batPaidNum = substringHandler(model.getBatPaidNum());
		this.transferNum = substringHandler(model.getTransferNum());
		this.refundNum = substringHandler(model.getRefundNum());
		this.plaTraNum = substringHandler(model.getPlaTraNum());
		this.fiNum = substringHandler(model.getFiNum());
		this.agrPayNum = substringHandler(model.getAgrPayNum());
		this.agrCollNum = substringHandler(model.getAgrCollNum());
		this.crossNum = substringHandler(model.getCrossNum());

		this.id = model.getId();
		this.createBy = model.getCreateBy();
		this.createTime = model.getCreateTime();
		this.updateBy = model.getUpdateBy();
		this.updateTime = model.getUpdateTime();
		this.sysOrgCode = model.getSysOrgCode();
		this.appId = model.getAppId();
		this.appName = model.getAppName();
		this.reportDate = model.getReportDate();
		this.amountTotal = model.getAmountTotal();
		this.popupAmount = model.getPopupAmount();
		this.consAmount = model.getConsAmount();
		this.withdrAmount = model.getWithdrAmount();
		this.collAmount = model.getCollAmount();
		this.paidAmount = model.getPaidAmount();
		this.batPaidAmount = model.getBatPaidAmount();
		this.transferAmount = model.getTransferAmount();
		this.refundAmount = model.getRefundAmount();
		this.plTraAmount = model.getPlTraAmount();
		this.fiAmount = model.getFiAmount();
		this.agrPayAmount = model.getAgrPayAmount();
		this.agrCollAmount = model.getAgrCollAmount();
		this.crossAmount = model.getCrossAmount();
	}

	public Integer substringHandler(String str){
//		System.out.println("要处理的字符串："+str);
		String filds =str.trim().substring(0,str.trim().length()-2);
		Integer result = Integer.valueOf(filds);
		return result;
	}
}
