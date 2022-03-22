package org.jeecg.modules.cloudacc.service.impl;

import org.jeecg.modules.cloudacc.entity.CloudAccRpt;
import org.jeecg.modules.cloudacc.mapper.CloudAccRptMapper;
import org.jeecg.modules.cloudacc.model.CloudAccAppTrxSummary;
import org.jeecg.modules.cloudacc.model.CloudAccTrxMonthSummary;
import org.jeecg.modules.cloudacc.model.CloudAccTrxSummary;
import org.jeecg.modules.cloudacc.service.ICloudAccRptService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 云商通账户交易报表
 * @Author: jeecg-boot
 * @Date:   2021-07-04
 * @Version: V1.0
 */
@Service
public class CloudAccRptServiceImpl extends ServiceImpl<CloudAccRptMapper, CloudAccRpt> implements ICloudAccRptService {

    @Override
    public List<CloudAccAppTrxSummary> cloudAccAppTransSummary(Map<String, Object> params) {
        return baseMapper.cloudAccAppTransSummary(params);
    }

    @Override
    public List<CloudAccAppTrxSummary> cloudAccAppTransSummary(Date beginDate, Date endDate) {
        Map<String, Object> params = new HashMap<>();
        params.put("beginDate", beginDate);
        params.put("endDate", endDate);
        return baseMapper.cloudAccAppTransSummary(params);
    }

    @Override
    public List<CloudAccAppTrxSummary> topCloudAccAppTransSummary(Date beginDate, Date endDate, int limit) {
        Map<String, Object> params = new HashMap<>();
        params.put("beginDate", beginDate);
        params.put("endDate", endDate);
        params.put("limit", limit);
        return baseMapper.topCloudAccAppTransSummary(params);
    }

    @Override
    public List<CloudAccTrxSummary> cloudAccTransSummaryByDate(Date beginDate, Date endDate) {
        Map<String, Object> params = new HashMap<>();
        params.put("beginDate", beginDate);
        params.put("endDate", endDate);
        return baseMapper.cloudAccTransSummaryByDate(params);
    }

    @Override
    public Map<String, Object> cloudAccTransSummary(Date beginDate, Date endDate) {
        Map<String, Object> params = new HashMap<>();
        params.put("beginDate", beginDate);
        params.put("endDate", endDate);
        return baseMapper.cloudAccTransSummary(params);
    }

    @Override
    public List<CloudAccTrxMonthSummary> cloudAccTrxSummaryByMonth(Date beginDate, Date endDate) {

        Map<String, Object> params = new HashMap<>();
        params.put("beginDate", beginDate);
        params.put("endDate", endDate);

        return baseMapper.cloudAccTrxSummaryByMonth(params);
    }
}
