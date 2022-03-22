package org.jeecg.modules.okr.vo;

import java.util.List;
import org.jeecg.modules.okr.entity.ObjectivesV2;
import org.jeecg.modules.okr.entity.KeyResultV2;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelEntity;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

/**
 * @Description: 工作目标表-版本2
 * @Author: jeecg-boot
 * @Date:   2021-03-05
 * @Version: V1.0
 */
@Data
public class ObjectivesV2Page {
	
	/**主键*/
	private java.lang.String id;
	/**部门id*/
	@Excel(name = "部门id", width = 15)
	private java.lang.String sysDepart;
	/**目标*/
	@Excel(name = "目标", width = 15)
	private java.lang.String objectives;
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
	/**进度*/
	@Excel(name = "进度", width = 15)
	private java.math.BigDecimal percent;
	/**负责人*/
	@Excel(name = "负责人", width = 15)
	private java.lang.String chargePerson;
	/**目标说明*/
	@Excel(name = "目标说明", width = 15)
	private java.lang.String comment;
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
	
	@ExcelCollection(name="关键指标-版本2")
	private List<KeyResultV2> keyResultsV2List;
	
}
