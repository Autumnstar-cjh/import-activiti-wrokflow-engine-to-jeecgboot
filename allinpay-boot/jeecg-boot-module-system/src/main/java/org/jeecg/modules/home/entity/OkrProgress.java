package org.jeecg.modules.home.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OkrProgress {

    BigDecimal newAdd;//新增客户完成率
    BigDecimal tradingNum;//交易笔数完成率
    BigDecimal finishOfTrading;//交易金额完成率
    BigDecimal finishOfIncome;//公司收入完成率
}