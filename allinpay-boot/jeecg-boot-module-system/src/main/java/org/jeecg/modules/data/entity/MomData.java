package org.jeecg.modules.data.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Description:环比交易
 *
 * @Author WenRenfei
 * @Date 2021/6/24
 * Version: V1.0
 */

@Data
public class MomData {

    private String key;
    //月份
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
    //环比金额
    private BigDecimal momAmount;
    //环比率
    private BigDecimal momRate;
    //环比率百分比
    private String momRateStr;

    //子节点，作用：返回到前端的集合只展示最近一个月的数据，之前每个月的数据将封装在子集合中
    private List<MomData> children;

    public void setMomRateStr() {
        this.momRateStr = this.momRate.toString() + "%";
    }

    //将前几个月的数据添加到子集合中
    public List<MomData> toSetChildren(){

        return null;
    }
}
