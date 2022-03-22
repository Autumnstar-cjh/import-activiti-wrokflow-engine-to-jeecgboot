package org.jeecg.modules.home.entity;

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
 * @Description: day_trx_stat
 * @Author: jeecg-boot
 * @Date:   2019-12-24
 * @Version: V1.0
 */
@Data
@TableName("day_trx_stat")
public class DayTrxStat implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**id*/
	@TableId(type = IdType.ID_WORKER_STR)
    private String id;
	/**transactionDate*/
	@Excel(name = "transactionDate", width = 15)
    private String transactionDate;
	/**merchantCode*/
	@Excel(name = "merchantCode", width = 15)
    private String merchantCode;
	/**terminalCode*/
	@Excel(name = "terminalCode", width = 15)
    private String terminalCode;
	/**transactionMony*/
	@Excel(name = "transactionMoney", width = 15)
    private BigDecimal transactionMoney;
	/**transactionNumber*/
	@Excel(name = "transactionNumber", width = 15)
    private Integer transactionNumber;
	/**companyIncome*/
	@Excel(name = "companyIncome", width = 15)
    private Integer companyIncome;
}
