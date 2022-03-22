package org.jeecg.modules.cloudacc.mapper;

import java.util.List;
import java.util.Map;

import org.jeecg.modules.cloudacc.entity.CloudAccRpt;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.cloudacc.model.CloudAccAppTrxSummary;
import org.jeecg.modules.cloudacc.model.CloudAccTrxMonthSummary;
import org.jeecg.modules.cloudacc.model.CloudAccTrxSummary;

/**
 * @Description: 云商通账户交易报表
 * @Author: jeecg-boot
 * @Date:   2021-07-04
 * @Version: V1.0
 */
public interface CloudAccRptMapper extends BaseMapper<CloudAccRpt> {
    List<CloudAccAppTrxSummary> cloudAccAppTransSummary(Map<String, Object> params);
    List<CloudAccAppTrxSummary> topCloudAccAppTransSummary(Map<String, Object> params);
    List<CloudAccTrxSummary> cloudAccTransSummaryByDate(Map<String, Object> params);
    List<CloudAccTrxMonthSummary> cloudAccTrxSummaryByMonth(Map<String, Object> params);
    Map<String, Object> cloudAccTransSummary(Map<String, Object> params);


}
