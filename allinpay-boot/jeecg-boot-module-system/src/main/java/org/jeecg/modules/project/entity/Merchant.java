package org.jeecg.modules.project.entity;

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
 * @Description: 商户信息
 * @Author: jeecg-boot
 * @Date:   2019-12-25
 * @Version: V1.0
 */
@Data
@TableName("merchant")
public class Merchant implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键id*/
	@TableId(type = IdType.ID_WORKER_STR)
	private String id;
	/**商户编号*/
	@Excel(name = "商户编号", width = 15)
	private String code;
	/**商户姓名*/
	@Excel(name = "商户姓名", width = 15)
	private String name;
	/**外键*/
	private String projectId;
	/**商户类型 1旅游商户  0四个产业*/
	@Excel(name = "商户类型", width = 15)
	private String industryType;
}
