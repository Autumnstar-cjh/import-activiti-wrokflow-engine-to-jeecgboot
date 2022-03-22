package org.jeecg.modules.email.entity;

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
public class KeyResults implements Serializable {

    /**id*/
    private java.lang.String id;
    /**指标内容*/
    private java.lang.String content;
    /**信心指数*/
    private java.lang.String confidence;
    /**评分*/
    private java.math.BigDecimal ranking;
}
