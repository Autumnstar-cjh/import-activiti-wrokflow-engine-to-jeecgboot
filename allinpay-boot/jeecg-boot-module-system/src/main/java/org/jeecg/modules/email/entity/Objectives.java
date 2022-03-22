package org.jeecg.modules.email.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.jeecg.common.aspect.annotation.Dict;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: OKR目标
 * @Author: jeecg-boot
 * @Date:   2020-03-26
 * @Version: V1.0
 */
@Data
public class Objectives implements Serializable {
    private static final long serialVersionUID = 1L;

    /**id*/
    private java.lang.String id;
    /**部门id*/
    private java.lang.String sysDepart;
    /**目标*/
    private java.lang.String objectives;
    /**开始时间*/
    private java.util.Date startTime;
    /**结束时间*/
    private java.util.Date endTime;
    /**负责人*/
    private java.lang.String chargePerson;

    /**关键结果*/
    private java.util.List<KeyResults> keyResults;

}
