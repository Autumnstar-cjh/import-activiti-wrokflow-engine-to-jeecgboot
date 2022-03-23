package org.jeecg.modules.liuzhuandan.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.liuzhuandan.entity.AllinpayUrgency;
import org.jeecg.modules.liuzhuandan.service.IAllinpayUrgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

 /**
 * @Description: 1
 * @Author: jeecg-boot
 * @Date:   2022-03-16
 * @Version: V1.0
 */
@RestController
@RequestMapping("/biz/allinpayUrgency")
@Slf4j
public class AllinpayUrgencyController extends JeecgController<AllinpayUrgency, IAllinpayUrgencyService> {
	@Autowired
	private IAllinpayUrgencyService allinpayUrgencyService;
	
	/**
	 * 分页列表查询
	 *
	 * @param allinpayUrgency
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(AllinpayUrgency allinpayUrgency,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<AllinpayUrgency> queryWrapper = QueryGenerator.initQueryWrapper(allinpayUrgency, req.getParameterMap());
		Page<AllinpayUrgency> page = new Page<AllinpayUrgency>(pageNo, pageSize);
		IPage<AllinpayUrgency> pageList = allinpayUrgencyService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param allinpayUrgency
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody AllinpayUrgency allinpayUrgency) {
		allinpayUrgencyService.save(allinpayUrgency);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param allinpayUrgency
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody AllinpayUrgency allinpayUrgency) {
		allinpayUrgencyService.updateById(allinpayUrgency);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		allinpayUrgencyService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.allinpayUrgencyService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		AllinpayUrgency allinpayUrgency = allinpayUrgencyService.getById(id);
		if(allinpayUrgency==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(allinpayUrgency);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param allinpayUrgency
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, AllinpayUrgency allinpayUrgency) {
        return super.exportXls(request, allinpayUrgency, AllinpayUrgency.class, "1");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, AllinpayUrgency.class);
    }

}
