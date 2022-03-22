package org.jeecg.modules.project.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: 项目信息
 * @Author: jeecg-boot
 * @Date:   2019-12-25
 * @Version: V1.0
 */
@Data
@TableName("project_info")
public class ProjectInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**Id*/
	@TableId(type = IdType.ID_WORKER_STR)
    private String id;
	/**项目名称*/
    private String projectName;
	/**立项时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startTime;
	/**项目主办部门*/
    private String hostDepartment;
	/**项目编号*/
    private String projectCode;
	/**预算总额*/
    private String budget;
	/**预计上线时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date expectOnlineTime;
	/**项目负责人*/
    private String projectLeader;
	/**项目经办人*/
    private String projectTransactor;
	/**项目方案简述*/
    private String projectPlanDescription;
	/**市场战略分析*/
    private String marketStrategyAnalysis;
	/**业务实现模式*/
    private String businessRealizationPattern;
	/**业务流程详述*/
    private String businessProcessDescribe;
	/**风控合规分析*/
    private String riskControlComplianceAnalysis;
	/**投资效益分析*/
    private String investmentBenefitAnalysis;
	/**结论及建议*/
    private String conclusionsRecommendations;
	/**创建人登录名称*/
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createTime;
	/**更新人登录名称*/
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updateTime;
}
