package org.jeecg.modules.home.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.home.entity.ChartData;
import org.jeecg.modules.home.entity.DayTrxStat;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.home.entity.HomeData;
import org.jeecg.modules.home.entity.PeriodTime;
import org.jeecg.modules.home.playload.BarData;
import org.jeecg.modules.home.playload.TopProject;

/**
 * @Description: day_trx_stat
 * @Author: jeecg-boot
 * @Date:   2019-12-24
 * @Version: V1.0
 */
public interface DayTrxStatMapper extends BaseMapper<DayTrxStat> {

    //首页杂项数据-旅游产业
    public HomeData queryData(@Param("periodTime")PeriodTime periodTime);
    //首页杂项数据-四个产业
    public HomeData queryDataOther(@Param("periodTime")PeriodTime periodTime);

    //mini面积图数据
    public List<ChartData> areaData(@Param("dataType") String dataType);

    // 按月统计交易额-旅游产业
    public  List<BarData> getTrxStatByMonth(@Param("periodTime")PeriodTime periodTime);
    // 按月统计交易额-四个产业
    public  List<BarData> getTrxStatByMonthOther(@Param("periodTime")PeriodTime periodTime);


    // 按月统计收入-旅游产业
    public  List<BarData> getFeeStatByMonth(@Param("periodTime")PeriodTime periodTime);
    // 按月统计收入-四个产业
    public  List<BarData> getFeeStatByMonthOther(@Param("periodTime")PeriodTime periodTime);

    // 项目交易排行
    public  List<TopProject> getTrxTopProject(@Param("periodTime")PeriodTime periodTime);

    // 项目收入排行
    public  List<TopProject> getFeeTopProject(@Param("periodTime")PeriodTime periodTime);
}
