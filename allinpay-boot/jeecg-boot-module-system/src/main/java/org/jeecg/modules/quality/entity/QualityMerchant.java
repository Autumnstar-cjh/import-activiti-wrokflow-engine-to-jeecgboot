package org.jeecg.modules.quality.entity;

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
 * @Description: 特色产业优质商户表
 * @Author: jeecg-boot
 * @Date:   2021-03-08
 * @Version: V1.0
 */
@Data
@TableName("quality_merchant")
public class QualityMerchant implements Serializable {
	private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
	private java.lang.String id;
	/**商户号*/
	@Excel(name = "商户号", width = 15)
	private java.lang.String merchantCode;
	/**商户名称*/
	@Excel(name = "商户名称", width = 15)
	private java.lang.String merchantName;
	/**拓展人*/
	@Excel(name = "拓展人", width = 15)
	private java.lang.String developPeople;
	/**部门*/
	@Excel(name = "部门", width = 15)
	private java.lang.String department;
	/**是否为新增商户，0是，1否*/
	@Excel(name = "是否为新增商户，0是，1否", width = 15)
	private java.lang.String isNew;
	/**是否为新增商户，0是，1否*/
	@Excel(name = "是否已导入历史数据：0 未导入，1 已导入。新增商户为0", width = 15)
	private java.lang.String historicalData;
	/**外键*/
	private java.lang.String typeId;
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
}
