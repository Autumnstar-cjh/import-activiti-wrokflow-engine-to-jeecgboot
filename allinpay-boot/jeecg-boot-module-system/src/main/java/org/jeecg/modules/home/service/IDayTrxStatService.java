package org.jeecg.modules.home.service;

import org.jeecg.modules.home.entity.ChartData;
import org.jeecg.modules.home.entity.DayTrxStat;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.home.entity.HomeData;
import org.jeecg.modules.home.entity.PeriodTime;
import org.jeecg.modules.home.playload.BarData;
import org.jeecg.modules.home.playload.BarDataFinal;
import org.jeecg.modules.home.playload.TopProject;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Description: day_trx_stat
 * @Author: jeecg-boot
 * @Date:   2019-12-24
 * @Version: V1.0
 */
public interface IDayTrxStatService extends IService<DayTrxStat> {
    //首页数据
    public Map<String,Object> queryData(PeriodTime periodTime);

    //首页面积图数据
    public Map<String,List<ChartData>> areaData();

    // 按月统计交易额
    public  Map<String,Map<String,Object>> getTrxStatByMonth(PeriodTime periodTime);

    // 按月统计收入
    public  Map<String,Map<String,Object>> getFeeStatByMonth(PeriodTime periodTime);

    // 项目交易排行
    public  List<TopProject> getTrxTopProject(PeriodTime periodTime);

    // 项目收入排行
    public  List<TopProject> getFeeTopProject(PeriodTime periodTime);
}
