package org.jeecg.modules.order.entity;

import lombok.Data;

/**
 * C 扫 B 请求参数封装成类
 */
@Data
public class CtBRequestParams {
    private long trxamt;//交易金额 单位：分

    private String paytype;//交易方式

    private String body;//订单标题

    private String remark;//备注

    private int validtime;//有效时间

    private String acct;//支付平台用户标识  JS支付时使用

    private String notify_url;//交易结果通知地址

    private String limit_pay;//支付限制

    private String sub_appid;//微信子appid

    private String goods_tag;//订单优惠标识

    private String benefitdetail;//优惠信息

    private String chnlstoreid;//渠道门店编号

    private String subbranch;//门店号

    private String extendparams;//拓展参数

    private String cusip;//终端ip

    private String front_url;//支付完成跳转

    private String idno;//证件号

    private String truename;//付款人真实姓名

    private String asinfo;//分账信息

    private String fqnum;//花呗分期\

    private String goodsId;//商品id

    private String goodsName;//商品名称
}
