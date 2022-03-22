package org.jeecg.modules.home.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.home.entity.ChartData;
import org.jeecg.modules.home.entity.DayTrxStat;
import org.jeecg.modules.home.entity.MarqueeData;
import org.jeecg.modules.home.entity.PeriodTime;
import org.jeecg.modules.home.playload.TopProject;
import org.jeecg.modules.home.service.IDayTrxStatV2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: day_trx_stat
 * @Author: jeecg-boot
 * @Date:   2019-12-24
 * @Version: V1.0
 */
@RestController
@RequestMapping("/home/dayTrxStatV2")
@Slf4j
public class DayTrxStatV2Controller extends JeecgController<DayTrxStat, IDayTrxStatV2Service> {
	@Autowired
	private IDayTrxStatV2Service dayTrxStatService;

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
		System.out.println("加载mimi面积图数据...");
        Map<String,List<ChartData>> chartDatas = dayTrxStatService.areaData();

		Iterator<Map.Entry<String,List<ChartData>>> it = chartDatas.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String,List<ChartData>> entry = it.next();
			System.out.println(entry.getKey()+":");
			for (ChartData c: entry.getValue()) {
				System.out.println(c.toString());
			}
		}
		return Result.ok(chartDatas);
	}

	/**
	 * 取目标整体完成数
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/getPercent")
	public Result<?> getPercent(HttpServletRequest req){
		Map<String,Object> percent = dayTrxStatService.getPercent();
		return Result.ok(percent);
	}

	/**
	 * 	存量优质商户收入条形图
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
	 *  新增优质商户收入条形图
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
     * 存量优质商户收入排行榜
     * @param req
     * @return
     */
	@GetMapping(value = "/getTrxTopProject")
	public Result<?> getTrxTopProject(PeriodTime periodTime,HttpServletRequest req) {
		List<TopProject> listData = dayTrxStatService.getTrxTopProject(periodTime);
		return Result.ok(listData);
	}

    /**
     * 新增优质商户收入排行榜
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

	/**
	 * 首页条形图、排行榜鼠标悬浮显示数值权限列表查询
	 * @return
	 */
	@GetMapping(value = "/barDataDic")
	public Result<?> barDataDic(){
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		List<String> list = dayTrxStatService.barDataDic();
		if(!list.contains(sysUser.getUsername())){
			return Result.ok(false);
		}

		return Result.ok(true);
	}

	/**
	 * 顶部滚动文字数据
	 * @return
	 */
	@GetMapping(value = "/marqueeData")
	public Result<?> marqueeData(){
		MarqueeData marqueeData = dayTrxStatService.marqueeData();
		return Result.ok(marqueeData);
	}

}
