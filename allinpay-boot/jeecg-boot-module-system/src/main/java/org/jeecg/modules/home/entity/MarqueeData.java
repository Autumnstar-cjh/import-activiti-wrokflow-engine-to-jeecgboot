package org.jeecg.modules.home.entity;

import lombok.Data;

import java.math.BigDecimal;


/**
 * 首页顶部滚动文字数据
 */
@Data
public class MarqueeData {
    //开始日期
    String startDate;
    //结束日期
    String endDate;

    //新增客户*****************************************************
    //新增客户数
    BigDecimal newAdd;
    //目标数
    BigDecimal newAddAll;
    //完成度
    BigDecimal newAddPercent;


    //交易笔数*****************************************************
    //交易笔数
    BigDecimal tradingNum;
    //目标数
    BigDecimal tradingNumAll;
    //完成度
    BigDecimal tradingNumPercent;


    //交易金额*****************************************************
    //交易金额
    BigDecimal finishOfTrading;
    //目标
    BigDecimal finishOfTradingAll;
    //完成度
    BigDecimal finishOfTradingPercent;


    //收入金额*****************************************************
    //收入金额
    BigDecimal finishOfIncome;
    //目标
    BigDecimal finishOfIncomeAll;
    //完成度
    BigDecimal finishOfIncomePercent;
}
