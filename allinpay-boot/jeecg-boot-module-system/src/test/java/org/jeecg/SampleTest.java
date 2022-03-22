package org.jeecg;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import javax.annotation.Resource;

import org.jeecg.modules.cloudacc.model.CloudAccAppTrxSummary;
import org.jeecg.modules.cloudacc.service.ICloudAccRptService;
import org.jeecg.modules.demo.mock.MockController;
import org.jeecg.modules.demo.test.entity.JeecgDemo;
import org.jeecg.modules.demo.test.mapper.JeecgDemoMapper;
import org.jeecg.modules.demo.test.service.IJeecgDemoService;
import org.jeecg.modules.email.service.IEmailService;
import org.jeecg.modules.order.lib.SybPayService;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysDataLogService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SampleTest {

	@Resource
	private JeecgDemoMapper jeecgDemoMapper;
	@Resource
	private IJeecgDemoService jeecgDemoService;
	@Resource
	private ISysDataLogService sysDataLogService;
	@Resource
	private MockController mock;
	@Autowired
	private IEmailService emailService;
	@Autowired
	ICloudAccRptService cloudAccRptService;
	@Test
	public void testLastSevenDaysAppTrans(){


		Date now = new Date();
		LocalDate localDate=now.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		Date endDate= java.sql.Date.valueOf(localDate);

		Calendar cal = Calendar.getInstance();
		cal.setTime(endDate);
		cal.add(Calendar.DATE, -7);

		Date beginDate = cal.getTime();
		localDate = beginDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		beginDate =java.sql.Date.valueOf(localDate);

		List<CloudAccAppTrxSummary> summaryList =  cloudAccRptService.cloudAccAppTransSummary(beginDate, endDate);

		summaryList.forEach(System.out::println);
	}

	@Test
	public void testLastSevenDaysTrans(){

		Date now = new Date();
		LocalDate localDate=now.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		Date endDate= java.sql.Date.valueOf(localDate);

		Calendar cal = Calendar.getInstance();
		cal.setTime(endDate);
		cal.add(Calendar.DATE, -3);

		Date beginDate = cal.getTime();
		localDate = beginDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		beginDate =java.sql.Date.valueOf(localDate);


		Map<String, Object> summary =  cloudAccRptService.cloudAccTransSummary(beginDate, endDate);

		summary.forEach((k, v) -> {
			System.out.println("key:" +  k + ", value:" +  v.toString());
		});
	}


	@Test
	public void testSelect() {
		System.out.println(("----- selectAll method test ------"));
		List<JeecgDemo> userList = jeecgDemoMapper.selectList(null);
		Assert.assertEquals(5, userList.size());
		userList.forEach(System.out::println);
	}

	@Test
	public void testXmlSql() {
		System.out.println(("----- selectAll method test ------"));
		List<JeecgDemo> userList = jeecgDemoMapper.getDemoByName("Sandy12");
		userList.forEach(System.out::println);
	}

	/**
	 * 测试事务
	 */
	@Test
	public void testTran() {
		jeecgDemoService.testTran();
	}
	
	//author:lvdandan-----date：20190315---for:添加数据日志测试----
	/**
	 * 测试数据日志添加
	 */
	@Test
	public void testDataLogSave() {
		System.out.println(("----- datalog test ------"));
		String tableName = "jeecg_demo";
		String dataId = "4028ef81550c1a7901550c1cd6e70001";
		String dataContent = mock.sysDataLogJson();
		sysDataLogService.addDataLog(tableName, dataId, dataContent);
	}
	//author:lvdandan-----date：20190315---for:添加数据日志测试----


	/**
	 * 交易测试
	 */
	@Test
	public void testPay() throws Exception {
		SybPayService service = new SybPayService();
		String reqsn = String.valueOf(System.currentTimeMillis());
		Map<String,String> map = service.pay(1, reqsn, "W01", "标题", "备注", "", "134538429025667937","http://baidu.com","","","","");
		System.out.println(map);
	}

	/**
	 * 邮件测试
	 * */
	@Test
	public void emailTest(){

		List<SysUser> sendList = emailService.querySendEmail();
		List<SysUser> copyList = emailService.queryCopyEmail();
		String[] sendTo = new String[sendList.size()];
		String[] copyTo = new String[copyList.size()];

		if(sendList != null){
			for(int i = 0; i < sendList.size(); i++){
				System.out.println(sendList.get(i).getEmail());
				sendTo[i] = sendList.get(i).getEmail();
			}
		}
		if(copyList != null){
			for(int i = 0; i < copyList.size(); i++){
				System.out.println(copyList.get(i).getEmail());
				copyTo[i] = copyList.get(i).getEmail();
			}
		}
	}

}
