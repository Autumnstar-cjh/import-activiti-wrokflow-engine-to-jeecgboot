package org.jeecg.modules.cloudacc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecg.modules.cloudacc.entity.CloudAccOrder;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Author: WenRenfei
 * @Date: Create in 2022/4/1 16:05
 * @Description:
 * @Version: V1.0
 */
@Data
public class CloudAccOrderExcelModel {
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
    @Excel(name = "总笔数", width = 15, type = 10)
    private java.lang.String orderTotal;
    /**充值金额*/
    @Excel(name = "充值金额", width = 15)
    private java.math.BigDecimal popupAmount;
    /**充值笔数*/
    @Excel(name = "充值笔数", width = 15)
    private java.lang.String popupNum;
    /**消费笔数*/
    @Excel(name = "消费金额", width = 15)
    private java.math.BigDecimal consAmount;
    /**消费笔数*/
    @Excel(name = "消费笔数", width = 15)
    private java.lang.String consNum;
    /**提现金额*/
    @Excel(name = "提现金额", width = 15)
    private java.math.BigDecimal withdrAmount;
    /**提现笔数*/
    @Excel(name = "提现笔数", width = 15)
    private java.lang.String withdrNum;
    /**代收金额*/
    @Excel(name = "代收金额", width = 15)
    private java.math.BigDecimal collAmount;
    /**代收笔数*/
    @Excel(name = "代收笔数", width = 15)
    private java.lang.String collNum;
    /**代付金额*/
    @Excel(name = "代付金额", width = 15)
    private java.math.BigDecimal paidAmount;
    /**代付笔数*/
    @Excel(name = "代付笔数", width = 15)
    private java.lang.String paidNum;
    /**批量代付金额*/
    @Excel(name = "批量代付金额", width = 15)
    private java.math.BigDecimal batPaidAmount;
    /**批量代付笔数*/
    @Excel(name = "批量代付笔数", width = 15)
    private java.lang.String batPaidNum;
    /**跨应用转账金额*/
    @Excel(name = "跨应用转账金额", width = 15)
    private java.math.BigDecimal transferAmount;
    /**跨应用转账笔数*/
    @Excel(name = "跨应用转账笔数", width = 15)
    private java.lang.String transferNum;
    /**退款金额*/
    @Excel(name = "退款金额", width = 15)
    private java.math.BigDecimal refundAmount;
    /**退款笔数*/
    @Excel(name = "退款笔数", width = 15)
    private java.lang.String refundNum;
    /**平台转账金额*/
    @Excel(name = "平台转账金额", width = 15)
    private java.math.BigDecimal plTraAmount;
    /**平台转账笔数*/
    @Excel(name = "平台转账笔数", width = 15)
    private java.lang.String plaTraNum;
    /**理财金额*/
    @Excel(name = "理财金额", width = 15)
    private java.math.BigDecimal fiAmount;
    /**理财笔数*/
    @Excel(name = "理财笔数", width = 15)
    private java.lang.String fiNum;
    /**协议消费金额*/
    @Excel(name = "协议消费金额", width = 15)
    private java.math.BigDecimal agrPayAmount;
    /**协议消费笔数*/
    @Excel(name = "协议消费笔数", width = 15)
    private java.lang.String agrPayNum;
    /**协议代收金额*/
    @Excel(name = "协议代收金额", width = 15)
    private java.math.BigDecimal agrCollAmount;
    /**协议代收笔数*/
    @Excel(name = "协议代收笔数", width = 15)
    private java.lang.String agrCollNum;
    /**跨境提现金额*/
    @Excel(name = "跨境提现金额", width = 15)
    private java.math.BigDecimal crossAmount;
    /**跨境提现笔数*/
    @Excel(name = "跨境提现笔数", width = 15)
    private java.lang.String crossNum;


}
