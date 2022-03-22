package org.jeecg.modules.plan.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;

/**
 * @Description: 工作计划
 * @Author: jeecg-boot
 * @Date:   2020-01-02
 * @Version: V1.0
 */
@Data
@TableName("work_plan")
public class WorkPlan implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**id*/
	@TableId(type = IdType.ID_WORKER_STR)
    private String id;
	/**计划名称*/
	@Excel(name = "计划名称", width = 15)
    private String title;
	/**负责人*/
	@Excel(name = "负责人", width = 15)
    private String person;
	/**开始时间*/
	@Excel(name = "开始时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startTime;
	/**结束时间*/
	@Excel(name = "结束时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endTime;
	/**优先级*/
	@Excel(name = "优先级", width = 15)
    private String priority;
	/**计划描述*/
	@Excel(name = "计划描述", width = 15)
    private String planDescribe;
	/**关联目标*/
	@Excel(name = "关联目标", width = 15)
    private String associatedTarget;
	/**关联目标内容*/
	@Excel(name = "关联目标内容", width = 15)
	private String associatedTargetContent;
	/**周*/
	@Excel(name = "周", width = 15)
	private java.lang.String week;
	/**状态*/
	@Excel(name = "状态", width = 15)
	private java.lang.String status;
	/**完成情况*/
	@Excel(name = "完成情况", width = 15)
	private java.lang.String completedSituation;
	/**是否顺延*/
	@Excel(name = "是否顺延", width = 15)
	private java.lang.String isPostpone;
	/**顺延计划*/
	@Excel(name = "顺延计划", width = 15)
	private java.lang.String postponeFrom;

	/**创建人登录名称*/
	@Excel(name = "创建人", width = 15)
	private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Excel(name = "创建日期", width = 15)
	private Date createTime;
	/**更新人登录名称*/
	@Excel(name = "更新人", width = 15)
	private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Excel(name = "更新日期", width = 15)
	private Date updateTime;
}
