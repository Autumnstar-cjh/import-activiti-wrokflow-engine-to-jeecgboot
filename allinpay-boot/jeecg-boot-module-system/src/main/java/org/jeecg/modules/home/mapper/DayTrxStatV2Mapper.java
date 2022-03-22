package org.jeecg.modules.home.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.home.entity.*;
import org.jeecg.modules.home.playload.BarData;
import org.jeecg.modules.home.playload.TopProject;

import java.util.List;

/**
 * @Description: day_trx_stat
 * @Author: jeecg-boot
 * @Date:   2019-12-24
 * @Version: V1.0
 */
public interface DayTrxStatV2Mapper extends BaseMapper<DayTrxStat> {

    //首页杂项数据
    public HomeData queryData(@Param("periodTime") PeriodTime periodTime);

    //mini面积图数据
    public List<ChartData> areaData();

    // 存量优质商户收入条形图
    public  List<BarData> getTrxStatByMonthOther(@Param("periodTime") PeriodTime periodTime);

    // 按新增优质商户收入条形图
    public  List<BarData> getFeeStatByMonthOther(@Param("periodTime") PeriodTime periodTime);

    // 存量优质商户收入排行榜
    public  List<TopProject> getTrxTopProject(@Param("periodTime") PeriodTime periodTime);

    // 新增优质商户收入排行榜
    public  List<TopProject> getFeeTopProject(@Param("periodTime") PeriodTime periodTime);

    // 获取OKR完成度数据
    public OkrProgress getPercent(@Param("startTime")String startTime, @Param("endTime") String endTime);

    //首页条形图、排行榜鼠标悬浮显示数值权限列表查询
    public List<String> barDataDic();

    //首页顶部滚动文字数据
    public MarqueeData marqueeData(@Param("startTime")String startTime, @Param("endTime") String endTime);
}
