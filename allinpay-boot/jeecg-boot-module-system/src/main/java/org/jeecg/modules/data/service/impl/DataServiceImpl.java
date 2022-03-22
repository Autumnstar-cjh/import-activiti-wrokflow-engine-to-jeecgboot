package org.jeecg.modules.data.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.jeecg.modules.data.entity.HalfMonthData;
import org.jeecg.modules.data.entity.MomData;
import org.jeecg.modules.data.entity.WeekData;
import org.jeecg.modules.data.mapper.DataMapper;
import org.jeecg.modules.data.service.IDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 *
 * @Author WenRenfei
 * @Date 2021/7/12
 * Version: V1.0
 */
@Service
public class DataServiceImpl implements IDataService {

    @Autowired
    private DataMapper dataMapper;
    /**
     * 月环比-最近一个月环比交易查询
     * @return
     */
    @Override
    @DS("multi-datasource1")//多数据源配置
    public List<MomData> getLastMomData(String merchantCode,String mom) {
        return dataMapper.getLastMomData(merchantCode,mom);
    }

    /**
     * 月环比-根据商户号查询环比交易，除去最近一个月的数据
     * @param merchantCode
     * @return
     */
    @Override
    @DS("multi-datasource1")//多数据源配置
    public List<MomData> getMomData(String merchantCode,String mom) {
        List<MomData> list = dataMapper.getMomData(merchantCode,mom);

        if(list != null && !list.isEmpty()) {
            if (list.get(0).getTransDate().equals(mom)) {
                list = null;
            }
        }
        return list;
    }

    /**
     * 周环比-最近7天环比交易查询
     * @return
     */
    @Override
    @DS("multi-datasource1")//多数据源配置
    public List<WeekData> getWeekData(String merchantCode,String expandPeople,String belongsTo) {
        List<WeekData> list = dataMapper.getWeekData(merchantCode,expandPeople,belongsTo);

        for(WeekData w:list){
            w.setWeekRateStr();
        }
        return list;
    }

    /**
     * 指定商户近14天交易数据
     * @param merchantCode
     * @return
     */
    @Override
    @DS("multi-datasource1")//多数据源配置
    public List<HalfMonthData> getWeekDataDetail(String merchantCode) {
        return dataMapper.getWeekDataDetail(merchantCode);
    }
}
