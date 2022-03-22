package org.jeecg.modules.email.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.email.entity.MailConfig;
import org.jeecg.modules.email.service.IMailConfigService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;

 /**
 * @Description: 邮件发送配置
 * @Author: jeecg-boot
 * @Date:   2020-10-22
 * @Version: V1.0
 */
@RestController
@RequestMapping("/email/mailConfig")
@Slf4j
public class MailConfigController extends JeecgController<MailConfig, IMailConfigService> {
	@Autowired
	private IMailConfigService mailConfigService;
	
	/**
	 * 分页列表查询
	 *
	 * @param mailConfig
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(MailConfig mailConfig,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<MailConfig> queryWrapper = QueryGenerator.initQueryWrapper(mailConfig, req.getParameterMap());
		Page<MailConfig> page = new Page<MailConfig>(pageNo, pageSize);
		IPage<MailConfig> pageList = mailConfigService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	 /**
	  * 发送邮件版本二测试
	  */
	 @GetMapping(value = "/queryList")
	 public Result<?> queryList(){
		 List<MailConfig>  list = mailConfigService.queryList();
		 System.out.println("是否为空？");
		 System.out.println(list==null);
		 System.out.println("集合长度："+list.size());
		 for(MailConfig m:list){
			 System.out.println(m.toString());
		 }
		 MailConfig mailConfig = list.get(0);
		 System.out.println("MailConfig:"+mailConfig.toString());

		 JavaMailSenderImpl jms = new JavaMailSenderImpl();
		 jms.setHost(mailConfig.getMailHost());
		 jms.setPort(mailConfig.getMailPort());
		 jms.setUsername(mailConfig.getMailUsername());
		 jms.setPassword(mailConfig.getMailPassword());

		 SimpleMailMessage smm = new SimpleMailMessage();
		 smm.setFrom(mailConfig.getMailFrom());
		 smm.setTo("liuzh3@allinpay.com");
		 smm.setCc("hanyz1@allinpay.com");
		 smm.setSubject("测试");
		 smm.setText("测试测试测试测试测试测试测试");

		 jms.send(smm);
	 	return null;
	 }


	/**
	 *   添加
	 *
	 * @param mailConfig
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody MailConfig mailConfig) {
		mailConfigService.save(mailConfig);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param mailConfig
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody MailConfig mailConfig) {
		mailConfigService.updateById(mailConfig);
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
		mailConfigService.removeById(id);
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
		this.mailConfigService.removeByIds(Arrays.asList(ids.split(",")));
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
		MailConfig mailConfig = mailConfigService.getById(id);
		if(mailConfig==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(mailConfig);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param mailConfig
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, MailConfig mailConfig) {
        return super.exportXls(request, mailConfig, MailConfig.class, "邮件发送配置");
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
        return super.importExcel(request, response, MailConfig.class);
    }

}
