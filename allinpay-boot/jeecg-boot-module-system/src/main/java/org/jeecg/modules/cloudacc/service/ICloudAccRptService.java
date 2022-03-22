package org.jeecg.modules.cloudacc.service;

import org.jeecg.modules.cloudacc.entity.CloudAccRpt;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.cloudacc.model.CloudAccAppTrxSummary;
import org.jeecg.modules.cloudacc.model.CloudAccTrxMonthSummary;
import org.jeecg.modules.cloudacc.model.CloudAccTrxSummary;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 云商通账户交易报表
 * @Author: jeecg-boot
 * @Date:   2021-07-04
 * @Version: V1.0
 */
public interface ICloudAccRptService extends IService<CloudAccRpt> {
    List<CloudAccAppTrxSummary> cloudAccAppTransSummary(Map<String, Object> params);
    List<CloudAccAppTrxSummary> cloudAccAppTransSummary(Date beginDate, Date endDate);
    List<CloudAccAppTrxSummary> topCloudAccAppTransSummary(Date beginDate, Date endDate, int limit);
    List<CloudAccTrxSummary> cloudAccTransSummaryByDate(Date beginDate, Date endDate);
    Map<String, Object> cloudAccTransSummary(Date beginDate, Date endDate);
    List<CloudAccTrxMonthSummary> cloudAccTrxSummaryByMonth(Date beginDate, Date endDate);
}
