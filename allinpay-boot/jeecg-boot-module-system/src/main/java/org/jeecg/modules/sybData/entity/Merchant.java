package org.jeecg.modules.sybData.entity;

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
 * @Description: 收银宝商户
 * @Author: jeecg-boot
 * @Date:   2022-01-21
 * @Version: V1.0
 */
@Data
@TableName("收银宝商户")
public class Merchant implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**注册日期*/
	@Excel(name = "注册日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date 注册日期;
	/**变更日期*/
	@Excel(name = "变更日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date 变更日期;
	/**商户号*/
	@Excel(name = "商户号", width = 15)
    private java.lang.String 商户号;
	/**商户名称*/
	@Excel(name = "商户名称", width = 15)
    private java.lang.String 商户名称;
	/**商户简称*/
	@Excel(name = "商户简称", width = 15)
    private java.lang.String 商户简称;
	/**所属分公司*/
	@Excel(name = "所属分公司", width = 15)
    private java.lang.String 所属分公司;
	/**拓展机构*/
	@Excel(name = "拓展机构", width = 15)
    private java.lang.String 拓展机构;
	/**维护机构*/
	@Excel(name = "维护机构", width = 15)
    private java.lang.String 维护机构;
	/**父商户号*/
	@Excel(name = "父商户号", width = 15)
    private java.lang.String 父商户号;
	/**来源渠道*/
	@Excel(name = "来源渠道", width = 15)
    private java.lang.String 来源渠道;
	/**合作方编号*/
	@Excel(name = "合作方编号", width = 15)
    private java.lang.String 合作方编号;
	/**渠道方备注*/
	@Excel(name = "渠道方备注", width = 15)
    private java.lang.String 渠道方备注;
	/**开户所在省*/
	@Excel(name = "开户所在省", width = 15)
    private java.lang.String 开户所在省;
	/**开户所在市*/
	@Excel(name = "开户所在市", width = 15)
    private java.lang.String 开户所在市;
	/**注册地址*/
	@Excel(name = "注册地址", width = 15)
    private java.lang.String 注册地址;
	/**mcc大类*/
	@Excel(name = "mcc大类", width = 15)
    private java.lang.String mcc大类;
	/**mcc42域*/
	@Excel(name = "mcc42域", width = 15)
    private java.lang.String mcc42域;
	/**mcc32域*/
	@Excel(name = "mcc32域", width = 15)
    private java.lang.String mcc32域;
	/**营业证件号码*/
	@Excel(name = "营业证件号码", width = 15)
    private java.lang.String 营业证件号码;
	/**法人姓名*/
	@Excel(name = "法人姓名", width = 15)
    private java.lang.String 法人姓名;
	/**法人身份证*/
	@Excel(name = "法人身份证", width = 15)
    private java.lang.String 法人身份证;
	/**财务联系人*/
	@Excel(name = "财务联系人", width = 15)
    private java.lang.String 财务联系人;
	/**财务联系人电话*/
	@Excel(name = "财务联系人电话", width = 15)
    private java.lang.String 财务联系人电话;
	/**拓展人*/
	@Excel(name = "拓展人", width = 15)
    private java.lang.String 拓展人;
	/**商户状态*/
	@Excel(name = "商户状态", width = 15)
    private java.lang.String 商户状态;
	/**账户类型*/
	@Excel(name = "账户类型", width = 15)
    private java.lang.String 账户类型;
	/**卡折类型*/
	@Excel(name = "卡折类型", width = 15)
    private java.lang.String 卡折类型;
	/**维护人*/
	@Excel(name = "维护人", width = 15)
    private java.lang.String 维护人;
	/**维护人联系方式*/
	@Excel(name = "维护人联系方式", width = 15)
    private java.lang.String 维护人联系方式;
	/**营业执照名称*/
	@Excel(name = "营业执照名称", width = 15)
    private java.lang.String 营业执照名称;
	/**营业执照有效期*/
	@Excel(name = "营业执照有效期", width = 15)
    private java.lang.String 营业执照有效期;
	/**法人身份证有效期*/
	@Excel(name = "法人身份证有效期", width = 15)
    private java.lang.String 法人身份证有效期;
	/**公司控股股东*/
	@Excel(name = "公司控股股东", width = 15)
    private java.lang.String 公司控股股东;
	/**控股股东或公司实际控制人姓名*/
	@Excel(name = "控股股东或公司实际控制人姓名", width = 15)
    private java.lang.String 控股股东或公司实际控制人姓名;
	/**签约类型*/
	@Excel(name = "签约类型", width = 15)
    private java.lang.String 签约类型;
	/**商户性质*/
	@Excel(name = "商户性质", width = 15)
    private java.lang.String 商户性质;
	/**结算账户*/
	@Excel(name = "结算账户", width = 15)
    private java.lang.String 结算账户;
	/**结算账户名*/
	@Excel(name = "结算账户名", width = 15)
    private java.lang.String 结算账户名;
	/**商户结算开户行名称*/
	@Excel(name = "商户结算开户行名称", width = 15)
    private java.lang.String 商户结算开户行名称;
	/**支行联行号*/
	@Excel(name = "支行联行号", width = 15)
    private java.lang.String 支行联行号;
	/**结算方式*/
	@Excel(name = "结算方式", width = 15)
    private java.lang.String 结算方式;
	/**结算时间*/
	@Excel(name = "结算时间", width = 15)
    private java.lang.String 结算时间;
	/**节假日清算*/
	@Excel(name = "节假日清算", width = 15)
    private java.lang.String 节假日清算;
	/**是否绑定二维码*/
	@Excel(name = "是否绑定二维码", width = 15)
    private java.lang.String 是否绑定二维码;
	/**商户评级*/
	@Excel(name = "商户评级", width = 15)
    private java.lang.String 商户评级;
	/**组织机构代码证*/
	@Excel(name = "组织机构代码证", width = 15)
    private java.lang.String 组织机构代码证;
	/**代码证有效期*/
	@Excel(name = "代码证有效期", width = 15)
    private java.lang.String 代码证有效期;
	/**法人代表证件类型*/
	@Excel(name = "法人代表证件类型", width = 15)
    private java.lang.String 法人代表证件类型;
	/**双免截止日期*/
	@Excel(name = "双免截止日期", width = 15)
    private java.lang.String 双免截止日期;
	/**录入用户*/
	@Excel(name = "录入用户", width = 15)
    private java.lang.String 录入用户;
	/**是否开通实时到账*/
	@Excel(name = "是否开通实时到账", width = 15)
    private java.lang.String 是否开通实时到账;
	/**一级行业*/
	@Excel(name = "一级行业", width = 15)
    private java.lang.String 一级行业;
	/**二级行业*/
	@Excel(name = "二级行业", width = 15)
    private java.lang.String 二级行业;
	/**控制人身份证号*/
	@Excel(name = "控制人身份证号", width = 15)
    private java.lang.String 控制人身份证号;
	/**控制人身份证有效期*/
	@Excel(name = "控制人身份证有效期", width = 15)
    private java.lang.String 控制人身份证有效期;
	/**注册资本*/
	@Excel(name = "注册资本", width = 15)
    private java.lang.String 注册资本;
	/**合作方营业证件号码*/
	@Excel(name = "合作方营业证件号码", width = 15)
    private java.lang.String 合作方营业证件号码;
	/**合作方法人身份证号*/
	@Excel(name = "合作方法人身份证号", width = 15)
    private java.lang.String 合作方法人身份证号;
	/**是否三证合一*/
	@Excel(name = "是否三证合一", width = 15)
    private java.lang.String 是否三证合一;
	/**统一社会信用代码证*/
	@Excel(name = "统一社会信用代码证", width = 15)
    private java.lang.String 统一社会信用代码证;
	/**社会信用代码证有效期*/
	@Excel(name = "社会信用代码证有效期", width = 15)
    private java.lang.String 社会信用代码证有效期;
	/**税务登记号码*/
	@Excel(name = "税务登记号码", width = 15)
    private java.lang.String 税务登记号码;
	/**税务登记号码有效期*/
	@Excel(name = "税务登记号码有效期", width = 15)
    private java.lang.String 税务登记号码有效期;
	/**商户类型*/
	@Excel(name = "商户类型", width = 15)
    private java.lang.String 商户类型;
	/**银联商户号*/
	@Excel(name = "银联商户号", width = 15)
    private java.lang.String 银联商户号;
	/**受益所有人姓名*/
	@Excel(name = "受益所有人姓名", width = 15)
    private java.lang.String 受益所有人姓名;
	/**受益所有人证件类型*/
	@Excel(name = "受益所有人证件类型", width = 15)
    private java.lang.String 受益所有人证件类型;
	/**受益所有人证件号码*/
	@Excel(name = "受益所有人证件号码", width = 15)
    private java.lang.String 受益所有人证件号码;
	/**受益所有人证件有效日期*/
	@Excel(name = "受益所有人证件有效日期", width = 15)
    private java.lang.String 受益所有人证件有效日期;
	/**受益所有人地址*/
	@Excel(name = "受益所有人地址", width = 15)
    private java.lang.String 受益所有人地址;
	/**商户网站*/
	@Excel(name = "商户网站", width = 15)
    private java.lang.String 商户网站;
	/**结算身份证号*/
	@Excel(name = "结算身份证号", width = 15)
    private java.lang.String 结算身份证号;
	/**直联结算标识*/
	@Excel(name = "直联结算标识", width = 15)
    private java.lang.String 直联结算标识;
	/**客服电话*/
	@Excel(name = "客服电话", width = 15)
    private java.lang.String 客服电话;
	/**所属事业部*/
	@Excel(name = "所属事业部", width = 15)
    private java.lang.String 所属事业部;
	/**直联机构号*/
	@Excel(name = "直联机构号", width = 15)
    private java.lang.String 直联机构号;
	/**结算归集*/
	@Excel(name = "结算归集", width = 15)
    private java.lang.String 结算归集;
}
