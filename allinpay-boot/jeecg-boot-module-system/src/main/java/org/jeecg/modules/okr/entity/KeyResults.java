package org.jeecg.modules.okr.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.jeecg.common.aspect.annotation.Dict;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;

/**
 * @Description: 关键结果
 * @Author: jeecg-boot
 * @Date:   2020-03-26
 * @Version: V1.0
 */
@Data
@TableName("key_results")
public class KeyResults implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**id*/
	@TableId(type = IdType.ID_WORKER_STR)
	private java.lang.String id;
	/**指标内容*/
	@Excel(name = "指标内容", width = 15)
	private java.lang.String content;
	/**度量单位*/
	@Excel(name = "度量单位", width = 15, dicCode = "OKR_UNIT")
	@Dict(dicCode = "OKR_UNIT")
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
	private java.math.BigDecimal ranking;
	/**备注*/
	@Excel(name = "备注", width = 15)
	private java.lang.String remark;
	/**外键*/
	private java.lang.String objId;
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
