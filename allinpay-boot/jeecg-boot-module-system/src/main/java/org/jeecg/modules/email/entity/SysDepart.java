package org.jeecg.modules.email.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SysDepart implements Serializable {
    /**部门id*/
    private String id;
    /**部门名称*/
    private String departName;
    /**okr目标*/
    private List<Objectives> objectivesList;
}
