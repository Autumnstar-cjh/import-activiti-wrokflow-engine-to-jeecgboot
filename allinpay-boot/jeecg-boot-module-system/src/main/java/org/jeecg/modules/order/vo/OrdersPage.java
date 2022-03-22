package org.jeecg.modules.order.vo;

import java.util.List;
import org.jeecg.modules.order.entity.Orders;
import org.jeecg.modules.order.entity.Pay;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelEntity;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

/**
 * @Description: 订单表
 * @Author: jeecg-boot
 * @Date:   2020-06-22
 * @Version: V1.0
 */
@Data
public class OrdersPage {
	
	/**主键*/
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
	/**所属部门*/
	@Excel(name = "所属部门", width = 15)
	private java.lang.String sysOrgCode;
	/**流水号*/
	@Excel(name = "流水号", width = 15)
	private java.lang.String sn;
	/**金额*/
	@Excel(name = "金额", width = 15)
	private java.math.BigDecimal amount;
	/**商品id*/
	@Excel(name = "商品id", width = 15)
	private java.lang.String goodsId;
	/**商品名称*/
	@Excel(name = "商品名称", width = 15)
	private java.lang.String goodsName;
	/**支付状态*/
	@Excel(name = "支付状态", width = 15)
	private java.lang.String payStatus;
	
	@ExcelCollection(name="支付表")
	private List<Pay> payList;	
	
}
