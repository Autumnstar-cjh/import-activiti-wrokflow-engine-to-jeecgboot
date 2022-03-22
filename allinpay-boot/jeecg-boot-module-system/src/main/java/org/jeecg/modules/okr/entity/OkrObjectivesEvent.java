package org.jeecg.modules.okr.entity;

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
 * @Description: okr_objectives_event
 * @Author: jeecg-boot
 * @Date:   2019-12-24
 * @Version: V1.0
 */
@Data
@TableName("okr_objectives_event")
public class OkrObjectivesEvent implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    private String id;
	/**createBy*/
	@Excel(name = "createBy", width = 15)
    private String createBy;
	/**createTime*/
	@Excel(name = "createTime", width = 15, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
	/**updateBy*/
	@Excel(name = "updateBy", width = 15)
    private String updateBy;
	/**updateTime*/
	@Excel(name = "updateTime", width = 15, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd  HH:mm:ss")
    private Date updateTime;
	/**目标*/
	@Excel(name = "目标", width = 15)
    private String okrObjectives;
	/**执行人*/
	@Excel(name = "执行人", width = 15)
    private String okrAssignUser;
	/**targetAmount*/
	@Excel(name = "targetAmount", width = 15)
    private BigDecimal targetAmount;
	/**执行人id*/
	@Excel(name = "执行人id", width = 15)
    private String okrObjectivesId;
	/**更新动作*/
	@Excel(name = "更新动作", width = 15)
    private String userAction;
	/**完成原值*/
	@Excel(name = "完成原值", width = 15)
    private BigDecimal oldFinishedAmount;
	/**完成新值*/
	@Excel(name = "完成新值", width = 15)
    private BigDecimal newFinishedAmount;
	/**目标原值*/
	@Excel(name = "目标原值", width = 15)
    private BigDecimal oldTargetAmount;
	/**目标新值*/
	@Excel(name = "目标新值", width = 15)
    private BigDecimal newTargetAmount;
	/**备注*/
	@Excel(name = "备注", width = 15)
    private String remark;
}
