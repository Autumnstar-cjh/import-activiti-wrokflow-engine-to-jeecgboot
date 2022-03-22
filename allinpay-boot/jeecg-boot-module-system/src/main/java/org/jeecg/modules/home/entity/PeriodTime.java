package org.jeecg.modules.home.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PeriodTime implements Serializable {
    public String startTime;
    public String endTime;

}
