package org.jeecg.modules.okr.entity;

import java.io.Serializable;
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

import javax.persistence.Transient;

/**
 * @Description: OKR目标
 * @Author: jeecg-boot
 * @Date:   2019-12-03
 * @Version: V1.0
 */
@Data
@TableName("okr_objectives")
public class OkrObjectives implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键id*/
	@TableId(type = IdType.ID_WORKER_STR)
	private java.lang.String id;
	/**父级ID*/
	@Excel(name = "父级目标", width = 15, dicCode = "id",dictTable="okr_objectives",dicText="objectives")
	private java.lang.String parentId;
	/**部门id*/
	@Excel(name = "部门id", width = 15, dicCode = "id",dictTable="sys_depart",dicText="depart_name")
	@Dict(dicCode = "id",dictTable="sys_depart",dicText="depart_name")
	private java.lang.String sysDepart;
	/**目标*/
	@Excel(name = "目标", width = 15)
	private java.lang.String objectives;
	/**计量单位*/
	@Excel(name = "计量单位", width = 15, dicCode = "OKR_UNIT")
	@Dict(dicCode = "OKR_UNIT")
	private java.lang.String unit;
	/**完成数*/
	@Excel(name = "完成数", width = 15)
	private java.math.BigDecimal finishedAmount;
	/**目标数*/
	@Excel(name = "目标数", width = 15)
	private java.math.BigDecimal targetAmount;
	/**说明*/
	@Excel(name = "说明", width = 15)
	private java.lang.String comment;
	/**进度*/
	@Excel(name = "进度", width = 15)
	private java.math.BigDecimal percent;
	/**是否有子节点*/
	@Excel(name = "是否有子节点", width = 15)
	private java.lang.String hasChild;
	/**创建人登录名称*/
	@Excel(name = "创建人登录名称", width = 15)
	private java.lang.String createBy;
	/**创建日期*/
	@Excel(name = "创建日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date createTime;
	/**更新人登录名称*/
	@Excel(name = "更新人登录名称", width = 15)
	private java.lang.String updateBy;
	/**更新日期*/
	@Excel(name = "更新日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date updateTime;
	/**执行人*/
	@Excel(name = "执行人", width = 15)
	private java.lang.String assignUser;
	/**开时时间*/
	@Excel(name = "开时时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date startDate;
	/**结束时间*/
	@Excel(name = "结束时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date endDate;
	/**评分*/
	@Excel(name = "评分", width = 15)
	private java.math.BigDecimal ranking;

	private transient String remark;
}
