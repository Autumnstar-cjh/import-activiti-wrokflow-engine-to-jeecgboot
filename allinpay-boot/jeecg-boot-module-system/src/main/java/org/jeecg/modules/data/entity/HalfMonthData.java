package org.jeecg.modules.data.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Description:
 *
 * @Author WenRenfei
 * @Date 2021/7/30
 * Version: V1.0
 */
@Data
public class HalfMonthData {

    private String key;
    //交易时间
    private String transDate;
    //商户号
    private String merchantCode;
    //商户名称
    private String merchantName;
    //拓展人
    private String expandPeople;
    //所属分公司
    private String belongsTo;
    //交易金额
    private BigDecimal amount;
}
