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
 * @Description: 工作计划-版本2
 * @Author: jeecg-boot
 * @Date:   2021-03-05
 * @Version: V1.0
 */
@Data
@TableName("work_plan_v2")
public class WorkPlanV2 implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
	/**计划名称*/
	@Excel(name = "计划名称", width = 15)
    private java.lang.String title;
	/**负责人*/
	@Excel(name = "负责人", width = 15)
    private java.lang.String person;
	/**开始时间*/
	@Excel(name = "开始时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date startTime;
	/**结束时间*/
	@Excel(name = "结束时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date endTime;
	/**优先级*/
	@Excel(name = "优先级", width = 15)
    private java.lang.String priority;
	/**计划描述*/
	@Excel(name = "计划描述", width = 15)
    private java.lang.String planDescribe;
	/**关联目标*/
	@Excel(name = "关联目标", width = 15)
    private java.lang.String associatedTarget;
	/**周*/
	@Excel(name = "周", width = 15)
    private java.lang.String week;
	/**状态：0未完成，1已完成*/
	@Excel(name = "状态：0未完成，1已完成", width = 15)
    private java.lang.String status;
	/**255*/
	@Excel(name = "255", width = 15)
    private java.lang.String completedSituation;
	/**是否顺延：0否，1是*/
	@Excel(name = "是否顺延：0否，1是", width = 15)
    private java.lang.String isPostpone;
	/**顺眼计划*/
	@Excel(name = "顺眼计划", width = 15)
    private java.lang.String postponeFrom;
	/**关联目标内容*/
	@Excel(name = "关联目标内容", width = 15)
    private java.lang.String associatedTargetContent;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
    private java.lang.String createBy;
	/**创建时间*/
	@Excel(name = "创建时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date createTime;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
    private java.lang.String updateBy;
	/**更新时间*/
	@Excel(name = "更新时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date updateTime;
}
