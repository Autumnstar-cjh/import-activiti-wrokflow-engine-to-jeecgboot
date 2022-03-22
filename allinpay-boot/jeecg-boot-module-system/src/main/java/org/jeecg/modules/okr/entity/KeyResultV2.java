package org.jeecg.modules.okr.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;

/**
 * @Description: 关键指标-版本2
 * @Author: jeecg-boot
 * @Date:   2021-03-05
 * @Version: V1.0
 */
@Data
@TableName("key_result_v2")
public class KeyResultV2 implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
	private java.lang.String id;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
	private java.lang.String createBy;
	/**创建日期*/
	@Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
	private java.lang.String updateBy;
	/**更新日期*/
	@Excel(name = "更新日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date updateTime;
	/**指标内容*/
	@Excel(name = "指标内容", width = 15)
	private java.lang.String content;
	/**度量单位*/
	@Excel(name = "度量单位", width = 15)
	private java.lang.String unit;
	/**完成数*/
	@Excel(name = "完成数", width = 15)
	private java.math.BigDecimal finishedAmount;
	/**目标数*/
	@Excel(name = "目标数", width = 15)
	private java.math.BigDecimal targetAmount;
	/**执行人*/
	@Excel(name = "执行人", width = 15)
	private java.lang.String assignUser;
	/**信心指数*/
	@Excel(name = "信心指数", width = 15)
	private java.lang.String confidence;
	/**评分*/
	@Excel(name = "评分", width = 15)
	private java.lang.String ranking;
	/**备注*/
	@Excel(name = "备注", width = 15)
	private java.lang.String remark;
	/**外键*/
	private java.lang.String objId;
}
