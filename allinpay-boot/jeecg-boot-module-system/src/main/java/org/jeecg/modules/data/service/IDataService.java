package org.jeecg.modules.data.service;

import org.jeecg.modules.data.entity.HalfMonthData;
import org.jeecg.modules.data.entity.MomData;
import org.jeecg.modules.data.entity.WeekData;

import java.util.List;

/**
 * Description:
 *
 * @Author WenRenfei
 * @Date 2021/7/12
 * Version: V1.0
 */
public interface IDataService {

    //月环比-最近一个月环比交易查询
    public List<MomData> getLastMomData(String merchantCode,String mom);

    //月环比-根据商户号查询环比交易，除去最近一个月的数据
    public List<MomData> getMomData(String merchantCode,String mom);

    //周环比-最近7天环比交易查询
    public List<WeekData> getWeekData(String merchantCode,String expandPeople,String belongsTo);

    //指定商户近14天交易数据
    public List<HalfMonthData> getWeekDataDetail(String merchantCode);
}
