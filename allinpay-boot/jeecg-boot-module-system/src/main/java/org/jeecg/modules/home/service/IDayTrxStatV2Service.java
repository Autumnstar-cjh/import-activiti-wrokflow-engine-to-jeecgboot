package org.jeecg.modules.home.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.home.entity.ChartData;
import org.jeecg.modules.home.entity.DayTrxStat;
import org.jeecg.modules.home.entity.MarqueeData;
import org.jeecg.modules.home.entity.PeriodTime;
import org.jeecg.modules.home.playload.TopProject;

import java.util.List;
import java.util.Map;

/**
 * @Description: day_trx_stat
 * @Author: jeecg-boot
 * @Date:   2019-12-24
 * @Version: V1.0
 */
public interface IDayTrxStatV2Service extends IService<DayTrxStat> {
    //首页数据
    public Map<String,Object> queryData(PeriodTime periodTime);

    //首页面积图数据
    public Map<String,List<ChartData>> areaData();

    // 存量优质商户收入条形图
    public  Map<String,Map<String,Object>> getTrxStatByMonth(PeriodTime periodTime);

    // 新增优质商户收入条形图
    public  Map<String,Map<String,Object>> getFeeStatByMonth(PeriodTime periodTime);

    // 存量优质商户收入排行榜
    public  List<TopProject> getTrxTopProject(PeriodTime periodTime);

    // 新增优质商户收入排行榜
    public  List<TopProject> getFeeTopProject(PeriodTime periodTime);

    // OKR完成度
    public Map<String,Object> getPercent();

    //首页条形图、排行榜鼠标悬浮显示数值权限列表查询
    public List<String> barDataDic();

    //首页顶部滚动文字数据
    public MarqueeData marqueeData();
}
