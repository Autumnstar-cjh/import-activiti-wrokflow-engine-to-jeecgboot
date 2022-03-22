package org.jeecg.modules.quality.vo;

import java.util.List;
import org.jeecg.modules.quality.entity.QualityMerchantType;
import org.jeecg.modules.quality.entity.QualityMerchant;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelEntity;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

/**
 * @Description: 特色产业优质客户商户类型
 * @Author: jeecg-boot
 * @Date:   2021-03-01
 * @Version: V1.0
 */
@Data
public class QualityMerchantTypePage {
	
	/**主键*/
	private java.lang.String id;
	/**类型*/
	@Excel(name = "类型", width = 15)
	private java.lang.String type;
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
	
	@ExcelCollection(name="特色产业优质商户表")
	private List<QualityMerchant> qualityMerchantList;	
	
}
