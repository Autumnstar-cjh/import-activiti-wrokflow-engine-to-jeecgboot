package org.jeecg.modules.home.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ChartData implements Serializable {
    public String transactionDate;
    //日交易总额
    public BigDecimal totalTradingMoney;
    //公司日收入
    public BigDecimal totalIncome;
    //日交易总笔数
    public BigDecimal totalNum;

}
