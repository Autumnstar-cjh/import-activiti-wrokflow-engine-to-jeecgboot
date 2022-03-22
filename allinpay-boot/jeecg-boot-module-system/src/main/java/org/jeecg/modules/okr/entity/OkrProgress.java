package org.jeecg.modules.okr.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OkrProgress {
    /*
    * 四个产业
    * */
    BigDecimal projectOnline;//项目上线完成率
    BigDecimal finishOfIncome;//收入金额完成率
    BigDecimal finishOfTrading;//交易金额完成率
    BigDecimal tradingNum;//交易笔数完成率

    /*
     * 旅游产业
     * */
    BigDecimal tourismProjectOnline;//项目上线完成率
    BigDecimal tourismFinishOfIncome;//收入金额完成率
    BigDecimal tourismFinishOfTrading;//交易金额完成率
    BigDecimal tourismTradingNum;//交易笔数完成率
}