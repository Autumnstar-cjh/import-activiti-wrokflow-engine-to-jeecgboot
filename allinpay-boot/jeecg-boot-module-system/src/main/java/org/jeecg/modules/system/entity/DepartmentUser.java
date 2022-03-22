package org.jeecg.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 部门-用户表
 * 手机端用户选择器使用
 * <p>
 *
 * @Author Wen
 * @Since  2020-04-26
 */
@Data
public class DepartmentUser implements Serializable {
    private static final long serialVersionUID = 1L;

    /**ID*/
    private String id;
    /**机构/部门名称*/
    private String label;
    /**排序*/
    @Excel(name="排序",width=15)
    private Integer departOrder;
    /**用户*/
    private List<SysUser> children;

}
