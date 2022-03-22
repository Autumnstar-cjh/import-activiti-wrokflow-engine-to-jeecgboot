package org.jeecg.modules.home.service.impl;

import org.jeecg.modules.home.entity.ChartData;
import org.jeecg.modules.home.entity.DayTrxStat;
import org.jeecg.modules.home.entity.HomeData;
import org.jeecg.modules.home.entity.PeriodTime;
import org.jeecg.modules.home.mapper.DayTrxStatMapper;
import org.jeecg.modules.home.playload.BarData;
import org.jeecg.modules.home.playload.BarDataFinal;
import org.jeecg.modules.home.playload.TopProject;
import org.jeecg.modules.home.service.IDayTrxStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: day_trx_stat
 * @Author: jeecg-boot
 * @Date:   2019-12-24
 * @Version: V1.0
 */
@Service
public class DayTrxStatServiceImpl extends ServiceImpl<DayTrxStatMapper, DayTrxStat> implements IDayTrxStatService {

    @Autowired
    DayTrxStatMapper dayTrxStatMapper;

    /**
     * 首页杂项数据加载
     * @param periodTime
     * @return
     */
    @Override
    public Map<String,Object> queryData(PeriodTime periodTime) {
        SimpleDateFormat ssws = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
//        System.out.println("首页杂项到达时间是："+ssws.format(new Date()));
        PeriodTime p = timeTransformation(periodTime);
        Map<String,Object> map = new HashMap<>();
//        System.out.println("首页杂项执行第一个sql前的时间是："+ssws.format(new Date()));
        HomeData tourismIndustryData = dayTrxStatMapper.queryData(p);//旅游产业
//        System.out.println("首页杂项执行第一个sql后的时间是："+ssws.format(new Date()));
        HomeData fourIndustryData = dayTrxStatMapper.queryDataOther(p);//四个产业
//        System.out.println("首页杂项执行第二个sql后的时间是："+ssws.format(new Date()));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            p.setStartTime(sdf1.format(sdf.parse(p.startTime)));
            p.setEndTime(sdf1.format(sdf.parse(p.endTime)));
        }catch (Exception e){
            System.out.println(e);
        }
        map.put("tourismIndustryData",tourismIndustryData);
        map.put("fourIndustryData",fourIndustryData);
        map.put("periodTime",p);
//        System.out.println("首页杂项返回时间是："+ssws.format(new Date()));
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
        List<ChartData> tourismIndustryData = dayTrxStatMapper.areaData("tourismIndustry");//旅游产业
        List<ChartData> fourIndustryData = dayTrxStatMapper.areaData("fourIndustry");//四个产业
        map.put("tourismIndustryData",tourismIndustryData);
        map.put("fourIndustryData",fourIndustryData);
        return map;
    }

    /**
     * 交易条形图数据
     * @param periodTime
     * @return
     */
    @Override
    public Map<String,Map<String,Object>> getTrxStatByMonth(PeriodTime periodTime) {
        SimpleDateFormat ssws = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
//        System.out.println("交易条形图到达时间是："+ssws.format(new Date()));
        PeriodTime p = timeTransformation(periodTime);
        Map<String,Map<String,Object>> resultMap = new  HashMap<>();

        //四个产业 封装成map：key=日期，value=数值
        Map<String,Object> fourIndustry = new LinkedHashMap<>();//时间和数据
        Map<String,Object> monthMap = new LinkedHashMap<>();//封装月份的map
        List<String> monthList = new ArrayList<>();
        fourIndustry.put("type","四个产业");
//        System.out.println("交易条形图第一个sql执行前时间是："+ssws.format(new Date()));
        List<BarData> fourIndustryList = baseMapper.getTrxStatByMonthOther(p);
//        System.out.println("交易条形图第一个sql执行后时间是："+ssws.format(new Date()));
        for (BarData b:fourIndustryList) {
            fourIndustry.put(b.getMonth().substring(0,4)+"-"+b.getMonth().substring(4,6),b.getMoney());
            monthList.add(b.getMonth().substring(0,4)+"-"+b.getMonth().substring(4,6));
        }
        monthMap.put("monthList",monthList);


        //旅游产业 封装成map：key=日期，value=数值
        Map<String,Object> tourismIndustry = new LinkedHashMap<>();
        tourismIndustry.put("type","旅游产业");
//        System.out.println("交易条形图第二个sql执行前时间是："+ssws.format(new Date()));
        List<BarData> tourismIndustryList = baseMapper.getTrxStatByMonth(p);
//        System.out.println("交易条形图第二个sql执行后时间是："+ssws.format(new Date()));
        for (BarData b:tourismIndustryList) {
            tourismIndustry.put(b.getMonth().substring(0,4)+"-"+b.getMonth().substring(4,6),b.getMoney());
        }
        //将旅游产业map、4个产业map、时间map封装在一个大的map中返回，满足前端数据格式需求
        resultMap.put("fourIndustry",fourIndustry);
        resultMap.put("tourismIndustry",tourismIndustry);
        resultMap.put("monthMap",monthMap);
        System.out.println("交易条形图返回时间是："+ssws.format(new Date()));
        return resultMap;
    }

    /**
     * 收入条形图数据
     * @param periodTime
     * @return
     */
    @Override
    public Map<String,Map<String,Object>> getFeeStatByMonth(PeriodTime periodTime) {
        SimpleDateFormat ssws = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
//        System.out.println("收入条形图到达时间是："+ssws.format(new Date()));
        PeriodTime p = timeTransformation(periodTime);
        Map<String,Map<String,Object>> resultMap = new  HashMap<>();

        //四个产业 封装成map：key=日期，value=数值
        Map<String,Object> fourIndustry = new LinkedHashMap<>();//时间和数据
        Map<String,Object> monthMap = new LinkedHashMap<>();//封装月份的map
        List<String> monthList = new ArrayList<>();
        fourIndustry.put("type","四个产业");
//        System.out.println("收入条形图第一个sql执行前时间是："+ssws.format(new Date()));
        List<BarData> fourIndustryList = baseMapper.getFeeStatByMonthOther(p);
//        System.out.println("收入条形图第一个sql执行后时间是："+ssws.format(new Date()));
        for (BarData b:fourIndustryList) {
            fourIndustry.put(b.getMonth().substring(0,4)+"-"+b.getMonth().substring(4,6),b.getMoney());
            monthList.add(b.getMonth().substring(0,4)+"-"+b.getMonth().substring(4,6));
        }
//        encapsulationMonth(monthList);

        monthMap.put("monthList",monthList);


        //旅游产业 封装成map：key=日期，value=数值
        Map<String,Object> tourismIndustry = new LinkedHashMap<>();
        tourismIndustry.put("type","旅游产业");
//        System.out.println("收入条形图第二个sql执行前时间是："+ssws.format(new Date()));
        List<BarData> tourismIndustryList = baseMapper.getFeeStatByMonth(p);
//        System.out.println("收入条形图第二个sql执行后时间是："+ssws.format(new Date()));
        for (BarData b:tourismIndustryList) {
            tourismIndustry.put(b.getMonth().substring(0,4)+"-"+b.getMonth().substring(4,6),b.getMoney());
        }
        //将旅游产业map、4个产业map、时间map封装在一个大的map中返回，满足前端数据格式需求
        resultMap.put("fourIndustry",fourIndustry);
        resultMap.put("tourismIndustry",tourismIndustry);
        resultMap.put("monthMap",monthMap);
//        System.out.println("收入条形图返回时间是："+ssws.format(new Date()));
        return resultMap;
    }

    /**
     * 项目交易排行榜
     * @param periodTime
     * @return
     */
    @Override
    public List<TopProject> getTrxTopProject(PeriodTime periodTime) {
        SimpleDateFormat ssws = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
//        System.out.println("交易排行榜到达时间是："+ssws.format(new Date()));
        PeriodTime p = timeTransformation(periodTime);
        List<TopProject> list = baseMapper.getTrxTopProject(p);
//        return baseMapper.getTrxTopProject(p);
//        System.out.println("交易排行榜返回时间是："+ssws.format(new Date()));
        return list;
    }

    /**
     * 项目收入排行榜
     * @param periodTime
     * @return
     */
    @Override
    public List<TopProject> getFeeTopProject(PeriodTime periodTime) {
        SimpleDateFormat ssws = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
//        System.out.println("收入排行榜到达时间是："+ssws.format(new Date()));
        PeriodTime p = timeTransformation(periodTime);
        List<TopProject> list = baseMapper.getFeeTopProject(p);
//        return baseMapper.getFeeTopProject(p);
//        System.out.println("收入排行榜返回时间是："+ssws.format(new Date()));
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

}
