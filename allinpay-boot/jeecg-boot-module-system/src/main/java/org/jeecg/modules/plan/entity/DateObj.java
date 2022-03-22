package org.jeecg.modules.plan.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class DateObj implements Serializable {
    public String startTime;
    public String endTime;

}
