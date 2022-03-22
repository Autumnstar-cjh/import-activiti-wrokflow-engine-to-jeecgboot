package org.jeecg.modules.order.lib;

import java.util.Map;
import java.util.TreeMap;

public class SybPayService {
	/**
	 * 统一扫码接口
	 *
	 * 必填项：
	 * 		trxamt 交易金额
	 * 		reqsn 交易单号
	 * 		authcode 支付授权码
	 *
	 * 非必填项
	 * 		 	body  订单标题
	 * 		 	remark   备注
	 * 		    limit_pay  支付限制
	 * 		    goods_tag  订单优惠标记
	 * 		    benefitdetail 优惠信息
	 * 		    chnlstoreid  渠道门店编号
	 * 		    subbranch  门店号
	 * 		    idno  证件号
	 * 		    extendparams  拓展参数
	 * 		    truename  付款人真实姓名
	 * 		    asinfo  分账信息
	 * 		    fqnum  花呗分期
	 *
	 * */
	public Map<String,String> scanqrpay(long trxamt,String reqsn,String body,String remark,String authcode,String limit_pay,String goods_tag,String benefitdetail,String chnlstoreid,
										String subbranch,String idno,String extendparams,String truename,String asinfo,String fqnum) throws Exception {

		HttpConnectionUtil http = new HttpConnectionUtil(SybConstants.SYB_APIURL+"/scanqrpay");
		http.init();
		TreeMap<String,String> params = new TreeMap<String,String>();

		params.put("cusid", SybConstants.SYB_CUSID);
		params.put("appid", SybConstants.SYB_APPID);
		params.put("version", "11");
		params.put("randomstr", SybUtil.getValidatecode(8));
		params.put("trxamt", String.valueOf(trxamt));
		params.put("reqsn", reqsn);
		params.put("body", body);
		params.put("remark", remark);
		params.put("authcode", authcode);
		params.put("limit_pay", limit_pay);
		params.put("goods_tag", goods_tag);
		params.put("benefitdetail", benefitdetail);
		params.put("chnlstoreid", chnlstoreid);
		params.put("subbranch", subbranch);
		params.put("idno", idno);
		params.put("extendparams", extendparams);
		params.put("truename", truename);
		params.put("asinfo", asinfo);
		params.put("fqnum", fqnum);
		params.put("sign", SybUtil.sign(params,SybConstants.SYB_APPKEY));

		byte[] bys = http.postParams(params, true);
		String result = new String(bys,"UTF-8");
		Map<String,String> map = handleResult(result);
		return map;
	}



	/**
	 * 统一支付接口
	 * */
	public Map<String,String> pay(long trxamt,String reqsn,String paytype,String body,String remark,String acct,String authcode,String notify_url,String limit_pay,String idno,String truename,String asinfo) throws Exception{
		HttpConnectionUtil http = new HttpConnectionUtil(SybConstants.SYB_APIURL+"/pay");
		http.init();
		TreeMap<String,String> params = new TreeMap<String,String>();
		params.put("cusid", SybConstants.SYB_CUSID);
		params.put("appid", SybConstants.SYB_APPID);
		params.put("version", "11");
		params.put("trxamt", String.valueOf(trxamt));
		params.put("reqsn", reqsn);
		params.put("paytype", paytype);
		params.put("randomstr", SybUtil.getValidatecode(8));
		params.put("body", body);
		params.put("remark", remark);
		params.put("acct", acct);
		params.put("authcode", authcode);
		params.put("notify_url", notify_url);
		params.put("limit_pay", limit_pay);
		params.put("idno", idno);
		params.put("truename", truename);
		params.put("asinfo", asinfo);
		params.put("sign", SybUtil.sign(params,SybConstants.SYB_APPKEY));
		byte[] bys = http.postParams(params, true);
		String result = new String(bys,"UTF-8");
		Map<String,String> map = handleResult(result);
		return map;
		
	}

	/**
	 * 交易撤销
	 * */
	public Map<String,String> cancel(long trxamt,String reqsn,String oldtrxid,String oldreqsn) throws Exception{
		HttpConnectionUtil http = new HttpConnectionUtil(SybConstants.SYB_APIURL+"/cancel");
		http.init();
		TreeMap<String,String> params = new TreeMap<String,String>();
		params.put("cusid", SybConstants.SYB_CUSID);
		params.put("appid", SybConstants.SYB_APPID);
		params.put("version", "11");
		params.put("trxamt", String.valueOf(trxamt));
		params.put("reqsn", reqsn);
		params.put("oldtrxid", oldtrxid);
		params.put("oldreqsn", oldreqsn);
		params.put("randomstr", SybUtil.getValidatecode(8));
		params.put("sign", SybUtil.sign(params,SybConstants.SYB_APPKEY));
		byte[] bys = http.postParams(params, true);
		String result = new String(bys,"UTF-8");
		Map<String,String> map = handleResult(result);
		return map;
	}

	/**
	 * 交易退款
	 * */
	public Map<String,String> refund(long trxamt,String reqsn,String oldtrxid,String oldreqsn) throws Exception{
		HttpConnectionUtil http = new HttpConnectionUtil(SybConstants.SYB_APIURL+"/refund");
		http.init();
		TreeMap<String,String> params = new TreeMap<String,String>();
		params.put("cusid", SybConstants.SYB_CUSID);
		params.put("appid", SybConstants.SYB_APPID);
		params.put("version", "11");
		params.put("trxamt", String.valueOf(trxamt));
		params.put("reqsn", reqsn);
		params.put("oldreqsn", oldreqsn);
		params.put("oldtrxid", oldtrxid);
		params.put("randomstr", SybUtil.getValidatecode(8));
		params.put("sign", SybUtil.sign(params,SybConstants.SYB_APPKEY));
		byte[] bys = http.postParams(params, true);
		String result = new String(bys,"UTF-8");
		Map<String,String> map = handleResult(result);
		return map;
	}

	/**
	 * 交易查询
	 * */
	public Map<String,String> query(String reqsn,String trxid) throws Exception{
		HttpConnectionUtil http = new HttpConnectionUtil(SybConstants.SYB_APIURL+"/query");
		http.init();
		TreeMap<String,String> params = new TreeMap<String,String>();
		params.put("cusid", SybConstants.SYB_CUSID);
		params.put("appid", SybConstants.SYB_APPID);
		params.put("version", "11");
		params.put("reqsn", reqsn);
		params.put("trxid", trxid);
		params.put("randomstr", SybUtil.getValidatecode(8));
		params.put("sign", SybUtil.sign(params,SybConstants.SYB_APPKEY));
		byte[] bys = http.postParams(params, true);
		String result = new String(bys,"UTF-8");
		Map<String,String> map = handleResult(result);
		return map;
	}
	
	/**
	 * 返回结果
	 * */
	public static Map<String,String> handleResult(String result) throws Exception{
		Map map = SybUtil.json2Obj(result, Map.class);
		if(map == null){
			throw new Exception("返回数据错误");
		}
		if("SUCCESS".equals(map.get("retcode"))){
			TreeMap tmap = new TreeMap();
			tmap.putAll(map);
			String sign = tmap.remove("sign").toString();
			String sign1 = SybUtil.sign(tmap,SybConstants.SYB_APPKEY);
			if(sign1.toLowerCase().equals(sign.toLowerCase())){
				return map;
			}else{
				throw new Exception("验证签名失败");
			}
			
		}else{
			throw new Exception(map.get("retmsg").toString());
		}
	}
	
	
}
