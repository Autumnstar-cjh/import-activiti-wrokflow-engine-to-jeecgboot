package org.jeecg.modules.project.vo;

import java.util.List;
import org.jeecg.modules.project.entity.ProjectInfo;
import org.jeecg.modules.project.entity.Merchant;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelEntity;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

/**
 * @Description: 项目信息
 * @Author: jeecg-boot
 * @Date:   2019-12-25
 * @Version: V1.0
 */
@Data
public class ProjectInfoPage {
	
	/**Id*/
	private String id;
	/**项目名称*/
	@Excel(name = "项目名称", width = 15)
	private String projectName;
	/**立项时间*/
	@Excel(name = "立项时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startTime;
	/**项目主办部门*/
	@Excel(name = "项目主办部门", width = 15)
	private String hostDepartment;
	/**项目编号*/
	@Excel(name = "项目编号", width = 15)
	private String projectCode;
	/**预算总额*/
	@Excel(name = "预算总额", width = 15)
	private String budget;
	/**预计上线时间*/
	@Excel(name = "预计上线时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private Date expectOnlineTime;
	/**项目负责人*/
	@Excel(name = "项目负责人", width = 15)
	private String projectLeader;
	/**项目经办人*/
	@Excel(name = "项目经办人", width = 15)
	private String projectTransactor;
	/**项目方案简述*/
	@Excel(name = "项目方案简述", width = 15)
	private String projectPlanDescription;
	/**市场战略分析*/
	@Excel(name = "市场战略分析", width = 15)
	private String marketStrategyAnalysis;
	/**业务实现模式*/
	@Excel(name = "业务实现模式", width = 15)
	private String businessRealizationPattern;
	/**业务流程详述*/
	@Excel(name = "业务流程详述", width = 15)
	private String businessProcessDescribe;
	/**风控合规分析*/
	@Excel(name = "风控合规分析", width = 15)
	private String riskControlComplianceAnalysis;
	/**投资效益分析*/
	@Excel(name = "投资效益分析", width = 15)
	private String investmentBenefitAnalysis;
	/**结论及建议*/
	@Excel(name = "结论及建议", width = 15)
	private String conclusionsRecommendations;
	/**创建人登录名称*/
	@Excel(name = "创建人登录名称", width = 15)
	private String createBy;
	/**创建日期*/
	@Excel(name = "创建日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createTime;
	/**更新人登录名称*/
	@Excel(name = "更新人登录名称", width = 15)
	private String updateBy;
	/**更新日期*/
	@Excel(name = "更新日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private Date updateTime;
	
	@ExcelCollection(name="商户信息")
	private List<Merchant> merchantList;	
	
}
