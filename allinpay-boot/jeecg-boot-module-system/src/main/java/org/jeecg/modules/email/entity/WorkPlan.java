package org.jeecg.modules.email.entity;

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
 * @Description: 工作计划
 * @Author: jeecg-boot
 * @Date:   2020-01-02
 * @Version: V1.0
 */
@Data
public class WorkPlan implements Serializable {
    /**id*/
    private String id;
    /**计划名称*/
    private String title;
    /**负责人*/
    private String person;
    /**开始时间*/
    private Date startTime;
    /**结束时间*/
    private Date endTime;
    /**优先级*/
    private String priority;
    /**关联目标*/
    private String associatedTarget;
    /**完后情况*/
    private String completedSituation;
    /**状态*/
    private java.lang.String status;
}
