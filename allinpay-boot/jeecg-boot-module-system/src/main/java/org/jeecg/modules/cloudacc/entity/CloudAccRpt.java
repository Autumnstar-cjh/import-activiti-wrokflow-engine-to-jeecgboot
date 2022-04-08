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
import org.jeecg.modules.cloudacc.model.CloudAccOrderExcelModel;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;

/**
 * @Description: 云商通账户交易报表
 * @Author: jeecg-boot
 * @Date:   2021-07-04
 * @Version: V1.0
 */
@Data
@TableName("cloud_acc_rpt")
public class CloudAccRpt implements Serializable {
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
	/**起始时间*/
	@Excel(name = "起始时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date reportDate;
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

	public CloudAccRpt(){}

	public CloudAccRpt(CloudAccOrderExcelModel excelModel){
		this.id = excelModel.getId();
		this.createBy = excelModel.getCreateBy();
		this.createTime = excelModel.getCreateTime();
		this.updateBy = excelModel.getUpdateBy();
		this.updateTime = excelModel.getUpdateTime();
		this.sysOrgCode = excelModel.getSysOrgCode();

		this.appId = excelModel.getAppId();
		this.appName = excelModel.getAppName();
		this.reportDate = excelModel.getReportDate();
		this.amountTotal = excelModel.getAmountTotal();
		this.orderTotal = substringHandler(excelModel.getOrderTotal());

		//入金金额 = 充值金额+消费金额+代收金额
		this.incomeAmountTotal = excelModel.getPopupAmount() //充值金额
				.add(excelModel.getConsAmount()) //消费金额
				.add(excelModel.getCollAmount()) //代收金额
				.add(excelModel.getAgrCollAmount()); //协议代收金额

		//入金笔数 = 充值笔数+消费笔数+代收笔数
		this.incomeOrderTotal = substringHandler(excelModel.getPopupNum())
				+ substringHandler(excelModel.getConsNum())
				+ substringHandler(excelModel.getCollNum())
				+ substringHandler(excelModel.getAgrCollNum());

		this.refundAmountTotal = excelModel.getRefundAmount(); //退款金额
		this.refundOrderTotal = substringHandler(excelModel.getRefundNum()); //退款笔数

		//出金金额 = 退款金额+提现金额，这里退款单独成一个字段，所以出金金额仅为提现金额
		this.withdrawAmountTotal = excelModel.getWithdrAmount() //提现金额
				.add(excelModel.getCrossAmount()); //跨境提现金额
		//出金笔数
		this.withdrawOrderTotal = substringHandler(excelModel.getWithdrNum())
				+substringHandler(excelModel.getCrossNum());

	}

	public Integer substringHandler(String str){
//		System.out.println("要处理的字符串："+str);
		String filds =str.trim().substring(0,str.trim().length()-2);
		Integer result = Integer.valueOf(filds);
		return result;
	}
}
