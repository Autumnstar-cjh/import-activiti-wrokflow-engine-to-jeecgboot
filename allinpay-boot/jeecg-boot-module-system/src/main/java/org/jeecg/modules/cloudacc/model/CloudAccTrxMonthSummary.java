package org.jeecg.modules.cloudacc.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class CloudAccTrxMonthSummary implements Serializable {
    /**统计时间*/
    private String reportMonth;

    /**总金额*/
    private BigDecimal amountTotal;
    /**总笔数*/
    private Integer orderTotal;
    /**入金金额*/
    private BigDecimal incomeAmountTotal;
    /**入金笔数*/
    private Integer incomeOrderTotal;
    /**退款金额*/
    private BigDecimal refundAmountTotal;
    /**退款笔数*/
    private Integer refundOrderTotal;
    /**出金金额*/
    private BigDecimal withdrawAmountTotal;
    @Excel(name = "出金笔数", width = 15)
    private Integer withdrawOrderTotal;
}