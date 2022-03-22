package org.jeecg.modules.order.entity;

import lombok.Data;

/**
 * 请求参数封装成类
 */
@Data
public class RequestParams {

    private long trxamt;//交易金额，单位：分

    private String reqsn;//商户交易单号

    private String body;//订单标题

    private String remark;//备注

    private String authcode;//支付授权码

    private String limit_pay;//支付限制

    private String goods_tag;//订单优惠标记

    private String benefitdetail;//优惠信息

    private String chnlstoreid;//渠道门店编号

    private String subbranch;//门店号

    private String idno;//证件号

    private String extendparams;//拓展参数

    private String truename;//付款人真实姓名

    private String asinfo;//分账信息

    private String fqnum;//花呗分期

    private String goodsId;//商品id

    private String goodsName;//商品名称


    }
