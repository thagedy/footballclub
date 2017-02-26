package com.thagedy.footballclub.config;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WxPayConfig {
	
	/**
	 * 公众账号ID
	 */
	public static String appid = "wx44a6d5a41e9c173c";
	
	/**
	 * 
	 */
	public static String appsecret = "fc3986bfcd4060ede76a0af70fb815f3";
	
	/**
	 * 商户号（mch_id）
	 */
	public static String partner = "1440461202";
	
	public static String partnerkey = "sunyazhou123sunyazhou123sunyazho";
	
	/**
	 * 交易类型
	 */
	public static String trade_type = "JSAPI";
	
	public static String signType = "MD5";


	/**
	 * 微信授权回跳连接
	 */
	public static String baseUrl = "http://weixin.synapse.xin";

	public static String genOrderNo(){
		String orderNo = "xf";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmssSSS");
		String nowTime = sdf.format(new Date());
		orderNo+=nowTime;
		return orderNo;
	}
}
