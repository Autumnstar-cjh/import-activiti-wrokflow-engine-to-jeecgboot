package org.jeecg.modules.data.controller;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.data.entity.HalfMonthData;
import org.jeecg.modules.data.entity.MomData;
import org.jeecg.modules.data.entity.WeekData;
import org.jeecg.modules.data.service.IDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Description:
 *
 * @Author WenRenfei
 * @Date 2021/7/12
 * Version: V1.0
 */
@Slf4j
@RestController
@RequestMapping("/data/fluctuation")
public class DataController {

    @Autowired
    private IDataService dataService;


    /**
     * 月环比-最近一个月环比交易查询
     * @return
     */
    @GetMapping(value = "/getLastMomData")
    public Result<?> getLastMomData(@RequestParam(name = "merchantCode") String merchantCode,@RequestParam(name = "mom") String mom){
        log.info("api=getLastMomData");
//        log.info("merchantCode={},mom={}",merchantCode,mom);
        List<MomData> momData = dataService.getLastMomData(merchantCode,mom);

        for(MomData d:momData){
            d.setMomRateStr();
        }
        return Result.ok(momData);
    }

    /**
     * 月环比-根据商户号查询环比交易，除去最近一个月的数据
     * @return
     */
    @GetMapping(value = "/getMomData")
    public Result<?> getMomData(@RequestParam(name = "merchantCode") String merchantCode,@RequestParam(name = "date") String date){
        log.info("api=getMomData...");
        log.info("merchantCode={},date={}",merchantCode,date);
        List<MomData> momData = dataService.getMomData(merchantCode,date);
        if(momData != null && !momData.isEmpty()){
            for (MomData d:momData){
                d.setMomRateStr();
            }
        }
        return Result.ok(momData);
    }


    /**
     * 周环比-最近7天环比交易查询
     * @return
     */
    @GetMapping(value = "/getWeekData")
    public Result<?> geteWeekData(@RequestParam(name = "merchantCode") String merchantCode,
                                  @RequestParam(name = "expandPeople") String expandPeople,
                                  @RequestParam(name = "belongsTo") String belongsTo){
        log.info("api=getWeekData...");
        log.info("merchantCode={}，expandPeople={}，belongsTo={}",merchantCode,expandPeople,belongsTo);
        List<WeekData> list = dataService.getWeekData(merchantCode,expandPeople,belongsTo);
        return Result.ok(list);
    }

    /**
     * 指定商户近14天交易数据
     * @param merchantCode
     * @return
     */
    @GetMapping(value = "/getWeekDataDetail")
    public Result<?> getWeekDataDetail(@RequestParam(name = "merchantCode") String merchantCode){
        log.info("api=getWeekDataDetail...");
//        log.info("merchantCode={}",merchantCode);

        List<HalfMonthData> list = dataService.getWeekDataDetail(merchantCode);

//        for(HalfMonthData h : list){
//            System.out.println("结果："+ h.toString());
//        }

        return Result.ok(list);
    }

}
