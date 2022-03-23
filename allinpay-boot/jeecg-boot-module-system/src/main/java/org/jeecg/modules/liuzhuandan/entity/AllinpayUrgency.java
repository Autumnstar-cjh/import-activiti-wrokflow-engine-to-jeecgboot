package org.jeecg.modules.liuzhuandan.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @Description: 1
 * @Author: jeecg-boot
 * @Date:   2022-03-16
 * @Version: V1.0
 */
@Data
@TableName("allinpay_urgency")
public class AllinpayUrgency implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
	/**提交人*/
	@Excel(name = "提交人", width = 15)
    private java.lang.String createBy;
	/**提交时间*/
	@Excel(name = "提交时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private java.util.Date createTime;
	/**提交部门*/
	@Excel(name = "提交部门", width = 15)
    private java.lang.String sysOrgCode;
	/**详细内容*/
	@Excel(name = "详细内容", width = 15)
    private java.lang.String details;
	/**是否紧急*/
	@Excel(name = "是否紧急", width = 15)
    private java.lang.String ifUrgency;
	/**是否有附件*/
	@Excel(name = "是否有附件", width = 15)
    private java.lang.String ifAddition;
	/**附件名称*/
	@Excel(name = "附件名称", width = 15)
    private java.lang.String additionname;
	/**期望完成时间*/
	@Excel(name = "期望完成时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date expectations;
	/**最迟完成时间*/
	@Excel(name = "最迟完成时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date latest;
	/**申请部门领导签章*/
	@Excel(name = "申请部门领导签章", width = 15)
    private java.lang.String applydepartmentleadersign;
	/**申请部门领导签章时间*/
	@Excel(name = "申请部门领导签章时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date applydepartmentleadersigntime;
	/**流程状态*/
	@Excel(name = "流程状态", width = 15)
    private java.lang.String actstatus;
	/**表名*/
	@Excel(name = "表名", width = 15)
    private java.lang.String tablename;
}
