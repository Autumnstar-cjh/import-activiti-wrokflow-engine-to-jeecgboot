package org.jeecg.modules.sybData.entity;

import lombok.Data;

/**
 * @Author: WenRenfei
 * @Date: Create in 2022/1/21 15:39
 * @Description:
 * @Version: V1.0
 */
@Data
public class MerchantQueryParam {

    private String merchantNo;

    private String merchantName;

    private String createDate;

    private String changeDate;

    private String startCreateDate;

    private String endCreateDate;

    private String startChangeDate;

    private String endChangeDate;
}
