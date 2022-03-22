package org.jeecg.modules.home.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.home.entity.*;
import org.jeecg.modules.home.mapper.DayTrxStatV2Mapper;
import org.jeecg.modules.home.playload.BarData;
import org.jeecg.modules.home.playload.TopProject;
import org.jeecg.modules.home.service.IDayTrxStatV2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: day_trx_stat
 * @Author: jeecg-boot
 * @Date:   2019-12-24
 * @Version: V1.0
 */
@Service
public class DayTrxStatV2ServiceImpl extends ServiceImpl<DayTrxStatV2Mapper, DayTrxStat> implements IDayTrxStatV2Service {

    @Autowired
    DayTrxStatV2Mapper dayTrxStatMapper;

    /**
     * 首页杂项数据加载
     * @param periodTime
     * @return
     */
    @Override
    public Map<String,Object> queryData(PeriodTime periodTime) {
        SimpleDateFormat ssws = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        PeriodTime p = timeTransformation(periodTime);
        Map<String,Object> map = new HashMap<>();
        HomeData tourismIndustryData = dayTrxStatMapper.queryData(p);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            p.setStartTime(sdf1.format(sdf.parse(p.startTime)));
            p.setEndTime(sdf1.format(sdf.parse(p.endTime)));
        }catch (Exception e){
            System.out.println(e);
        }
        map.put("tourismIndustryData",tourismIndustryData);
        map.put("periodTime",p);
        return map;
    }

    /**
     * mini面积图数据
     * 展示最近七天数据
     * @return
     */
    @Override
    public Map<String,List<ChartData>> areaData() {
        Map<String,List<ChartData>> map = new HashMap<>();
        List<ChartData> tourismIndustryData = dayTrxStatMapper.areaData();
        map.put("tourismIndustryData",tourismIndustryData);
        return map;
    }

    @Override
    public Map<String,Object> getPercent() {
        //时间参数 今年1月1日至今天
        Calendar cal =Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);//今年年份
        String startTime = year+"0101";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String endTime = sdf.format(date);

        OkrProgress op = baseMapper.getPercent(startTime,endTime);

        //返回到前端的日期要重新换一下格式
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        startTime = year+"-01-01";
        endTime = sdf.format(date);
        Map<String,Object> map = new HashMap<>();
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("okrProgress",op);
        return map;

    }

    /**
     * 存量优质商户收入条形图
     * @param periodTime
     * @return
     */
    @Override
    public Map<String,Map<String,Object>> getTrxStatByMonth(PeriodTime periodTime) {
        SimpleDateFormat ssws = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        PeriodTime p = timeTransformation(periodTime);
        Map<String,Map<String,Object>> resultMap = new  HashMap<>();

        //四个产业 封装成map：key=日期，value=数值
        Map<String,Object> fourIndustry = new LinkedHashMap<>();//时间和数据
        Map<String,Object> monthMap = new LinkedHashMap<>();//封装月份的map
        List<String> monthList = new ArrayList<>();
        fourIndustry.put("type","存量优质商户收入");
        List<BarData> fourIndustryList = baseMapper.getTrxStatByMonthOther(p);
        for (BarData b:fourIndustryList) {
            fourIndustry.put(b.getMonth().substring(0,4)+"-"+b.getMonth().substring(4,6),b.getMoney());
            monthList.add(b.getMonth().substring(0,4)+"-"+b.getMonth().substring(4,6));
        }
        monthMap.put("monthList",monthList);

        resultMap.put("fourIndustry",fourIndustry);
        resultMap.put("monthMap",monthMap);
        System.out.println("交易条形图返回时间是："+ssws.format(new Date()));
        return resultMap;
    }

    /**
     * 新增优质商户收入条形图
     * @param periodTime
     * @return
     */
    @Override
    public Map<String,Map<String,Object>> getFeeStatByMonth(PeriodTime periodTime) {
        SimpleDateFormat ssws = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        PeriodTime p = timeTransformation(periodTime);
        Map<String,Map<String,Object>> resultMap = new  HashMap<>();

        //四个产业 封装成map：key=日期，value=数值
        Map<String,Object> fourIndustry = new LinkedHashMap<>();//时间和数据
        Map<String,Object> monthMap = new LinkedHashMap<>();//封装月份的map
        List<String> monthList = new ArrayList<>();
        fourIndustry.put("type","新增优质商户收入");
        List<BarData> fourIndustryList = baseMapper.getFeeStatByMonthOther(p);
        for (BarData b:fourIndustryList) {
            fourIndustry.put(b.getMonth().substring(0,4)+"-"+b.getMonth().substring(4,6),b.getMoney());
            monthList.add(b.getMonth().substring(0,4)+"-"+b.getMonth().substring(4,6));
        }

        monthMap.put("monthList",monthList);

        resultMap.put("fourIndustry",fourIndustry);
        resultMap.put("monthMap",monthMap);
        return resultMap;
    }

    /**
     * 存量优质商户收入排行榜
     * @param periodTime
     * @return
     */
    @Override
    public List<TopProject> getTrxTopProject(PeriodTime periodTime) {
//        SimpleDateFormat ssws = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        PeriodTime p = timeTransformation(periodTime);
        List<TopProject> list = baseMapper.getTrxTopProject(p);
        return list;
    }

    /**
     * 新增优质商户收入排行榜
     * @param periodTime
     * @return
     */
    @Override
    public List<TopProject> getFeeTopProject(PeriodTime periodTime) {
//        SimpleDateFormat ssws = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        PeriodTime p = timeTransformation(periodTime);
        List<TopProject> list = baseMapper.getFeeTopProject(p);
        return list;
    }

    public static PeriodTime timeTransformation(PeriodTime periodTime){
        PeriodTime sqlTime = new PeriodTime();
        Date dt=new Date();
        String year=String.format("%tY", dt);
        String month=String .format("%tm", dt);
        String day=String .format("%td", dt);
        String startTime = ""+year+"0101";
        String endTime = ""+year+month+day;
        if (periodTime.startTime == null || periodTime.startTime == "" || periodTime.endTime == null || periodTime.endTime == ""){
            sqlTime.setStartTime(startTime);
            sqlTime.setEndTime(endTime);
        }else{
            sqlTime = periodTime;
        }
        return sqlTime;
    }

    //首页条形图、排行榜鼠标悬浮显示数值权限列表查询
    public List<String> barDataDic(){
        return baseMapper.barDataDic();
    }


    /**
     * 首页顶部滚动文字数据
     */
    @Override
    public MarqueeData marqueeData() {

        //时间参数 今年1月1日至今天
        Calendar cal =Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);//今年年份
        String startTime = year+"0101";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String endTime = sdf.format(date);

        MarqueeData md = baseMapper.marqueeData(startTime,endTime);

        //返回到前端的日期要重新换一下格式
        sdf = new SimpleDateFormat("yyyy年MM月dd日");
        startTime = year+"-01-01";
        endTime = sdf.format(date);

        md.setStartDate(startTime);
        md.setEndDate(endTime);
        return md;
    }

}
