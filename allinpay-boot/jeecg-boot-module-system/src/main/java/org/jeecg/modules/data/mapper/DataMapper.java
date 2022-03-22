package org.jeecg.modules.data.mapper;

import org.apache.ibatis.annotations.Param;
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
public interface DataMapper {

    //月环比-最近一个月环比交易查询
    public List<MomData> getLastMomData(@Param("merchantCode") String merchantCode,@Param("mom") String mom);

    //月环比-根据商户号查询环比交易，除去最近一个月的数据
    public List<MomData> getMomData(@Param("merchantCode") String merchantCode,@Param("mom") String mom);

    //周环比-最近7天环比交易查询
    public List<WeekData> getWeekData(@Param("merchantCode") String merchantCode,@Param("expandPeople") String expandPeople,
                                      @Param("belongsTo") String belongsTo);

    //指定商户近14天交易数据
    public List<HalfMonthData> getWeekDataDetail(@Param("merchantCode") String merchantCode);

}
