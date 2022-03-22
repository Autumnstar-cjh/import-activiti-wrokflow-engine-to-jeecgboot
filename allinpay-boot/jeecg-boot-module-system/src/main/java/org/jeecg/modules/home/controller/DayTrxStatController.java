package org.jeecg.modules.home.controller;

import javax.servlet.http.HttpServletRequest;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.home.entity.DayTrxStat;
import org.jeecg.modules.home.entity.HomeData;
import org.jeecg.modules.home.entity.ChartData;
import org.jeecg.modules.home.entity.PeriodTime;
import org.jeecg.modules.home.playload.BarData;
import org.jeecg.modules.home.playload.BarDataFinal;
import org.jeecg.modules.home.playload.TopProject;
import org.jeecg.modules.home.service.IDayTrxStatService;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: day_trx_stat
 * @Author: jeecg-boot
 * @Date:   2019-12-24
 * @Version: V1.0
 */
@RestController
@RequestMapping("/home/dayTrxStat")
@Slf4j
public class DayTrxStatController extends JeecgController<DayTrxStat, IDayTrxStatService> {
	@Autowired
	private IDayTrxStatService dayTrxStatService;

	/**
	 * 加载首页数据
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/queryData")
	public Result<?> queryPageList(PeriodTime periodTime,HttpServletRequest req) {
		Map<String,Object> map = dayTrxStatService.queryData(periodTime);
		return Result.ok(map);
	}


	/**
	 *  面积图数据加载
	 * @return
	 */
	@PostMapping(value = "/areaData")
	public Result<?> areaDataLoad() {
        Map<String,List<ChartData>> chartDatas = dayTrxStatService.areaData();
		return Result.ok(chartDatas);
	}

	/**
	 * 	交易金额条形图数据
	 * @param periodTime
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/getTrxStatByMonth")
	public Result<?> getTrxStatByMonth(PeriodTime periodTime,HttpServletRequest req) {
		Map<String, Map<String,Object>> listData = dayTrxStatService.getTrxStatByMonth(periodTime);
		return Result.ok(listData);
	}

	/**
	 *  公司收入条形图数据
	 * @param periodTime
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/getFeeStatByMonth")
	public Result<?> getFeeStatByMonth(PeriodTime periodTime,HttpServletRequest req) {
		Map<String,Map<String,Object>> listData = dayTrxStatService.getFeeStatByMonth(periodTime);
		return Result.ok(listData);
	}

    /**
     * 交易金额排行榜查询
     * @param req
     * @return
     */
	@GetMapping(value = "/getTrxTopProject")
	public Result<?> getTrxTopProject(PeriodTime periodTime,HttpServletRequest req) {
		List<TopProject> listData = dayTrxStatService.getTrxTopProject(periodTime);
		return Result.ok(listData);
	}

    /**
     * 项目收入排行榜查询
     * @param req
     * @return
     */
	@GetMapping(value = "/getFeeTopProject")
	public Result<?> getFeeTopProject(PeriodTime periodTime,HttpServletRequest req) {
		List<TopProject> listData = dayTrxStatService.getFeeTopProject(periodTime);
		return Result.ok(listData);
	}

	@GetMapping(value = "/getDefaultTime")
	public Result<?> defaultTime(){
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);//今年年份
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String endTime = sdf.format(date);
		PeriodTime periodTime = new PeriodTime();
		periodTime.setStartTime(year+"-01-01");
		periodTime.setEndTime(endTime);
		return Result.ok(periodTime);
	}

}
