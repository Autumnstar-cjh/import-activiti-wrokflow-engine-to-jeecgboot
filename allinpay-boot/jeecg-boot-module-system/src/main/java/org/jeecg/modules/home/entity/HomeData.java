package org.jeecg.modules.home.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class HomeData implements Serializable {
    //交易总额
    public BigDecimal totalTradingMoney;
    //日均交易金额
    public BigDecimal avgDayTradingMoney;
    //公司总收入
    public BigDecimal totalIncome;
    //日均收入
    public BigDecimal avgDayIncome;
    //交易总笔数
    public BigDecimal totalNum;
    //日均日交易笔数
    public BigDecimal avgDayNum;

}
