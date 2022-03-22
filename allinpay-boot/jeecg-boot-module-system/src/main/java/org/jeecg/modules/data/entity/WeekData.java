package org.jeecg.modules.data.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Description:
 *
 * @Author WenRenfei
 * @Date 2021/7/29
 * Version: V1.0
 */
@Data
public class WeekData {

    private String key;
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
    //环比金额
    private BigDecimal weekAmount;
    //环比率
    private BigDecimal weekRate;
    //环比率百分比
    private String weekRateStr;

    public void setWeekRateStr() {
        this.weekRateStr = this.weekRate.toString() + "%";
    }
}
