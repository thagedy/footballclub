package com.thagedy.footballclub.controller;

import com.thagedy.footballclub.Constants;
import com.thagedy.footballclub.common.pojo.ClubResult;
import com.thagedy.footballclub.common.util.JsonUtils;
import com.thagedy.footballclub.common.util.Sha1Util;
import com.thagedy.footballclub.common.util.WxConfig;
import com.thagedy.footballclub.common.util.wechat.CommonUtil;
import com.thagedy.footballclub.common.util.wechat.RequestHandler;
import com.thagedy.footballclub.common.util.wechat.TxtUtil;
import com.thagedy.footballclub.common.util.wechat.WeixinPayUtil;
import com.thagedy.footballclub.config.WxPayConfig;
import com.thagedy.footballclub.pojo.OrderInfo;
import com.thagedy.footballclub.pojo.PayResult;
import com.thagedy.footballclub.service.OrderInfoService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.thagedy.footballclub.common.util.WxConfig.baseUrl;

/**
 * Created by Kaijia Wei on 2017/2/21.
 */
@RestController
@RequestMapping("/wx")
public class WeChatController {

    private Logger logger = LoggerFactory.getLogger(WeChatController.class);
    @Value("${weixin.totalfee}")
    private String totalFee;
    @Autowired
    private OrderInfoService orderInfoService;


    @RequestMapping("/")
    public void test(HttpServletRequest request,HttpServletResponse response){
            output(response,"success");
    }

    @GetMapping("/userAuth")
    public void userAuth(HttpServletRequest request, HttpServletResponse response){
        try {
            //授权后要跳转的链接
            String backUri = baseUrl + "/wx/getOpenId";
            //URLEncoder.encode 后可以在backUri 的url里面获取传递的所有参数
            backUri = URLEncoder.encode(backUri);
            //scope 参数视各自需求而定，这里用scope=snsapi_base 不弹出授权页面直接授权目的只获取统一支付接口的openid
            String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
                    "appid=" + WxConfig.appid +
                    "&redirect_uri=" +
                    backUri+
                    "&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
            System.out.println("微信授权url:" + url);
            response.sendRedirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 微信网页授权回调函数
     * 获取微信授权用户的openId返回给前端h5
     * @param request
     * @return
     */
    @GetMapping("/getOpenId")
    public void getOpenId(HttpServletRequest request,HttpServletResponse response) {

        String url = "http://weixin.footballclub.xyz/view/payment.html?openid=";
        //网页授权后获取传递的参数
        String code = request.getParameter("code");
        System.out.println("code:" + code);

        //获取统一下单需要的openid
        String openId = "";
        String URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + WxConfig.appid + "&secret=" + WxConfig.appsecret + "&code=" + code + "&grant_type=authorization_code";
        System.out.println("URL:" + URL);
        JSONObject jsonObject = CommonUtil.httpsRequest(URL, "GET", null);
        if (null != jsonObject) {
            //// TODO: 2017/2/21 判断失败情况
            openId = jsonObject.getString("openid");
            System.out.println("openId:" + openId);
            try {
                response.sendRedirect(url+openId);
            } catch (IOException e1) {
                e1.printStackTrace();
                logger.error("footballclub项目中，回掉方法跳转注册页失败",e1);
            }
        }
    }

    @GetMapping("/getPrePayNo")
    public ClubResult getPrePayno(@RequestBody OrderInfo orderInfo,HttpServletRequest request, HttpServletResponse response) {

        String orderNo=this.genOrderNo();
        //随机数
        String nonce_str = UUID.randomUUID().toString().replaceAll("-", "");
        //商品描述
        String body = this.genOrderNo();;
        //商户订单号
        String out_trade_no = orderNo;
        //订单生成的机器 IP
        String spbill_create_ip = request.getRemoteAddr();
        //总金额
        //TODO
        Integer total_fee = Integer.parseInt(totalFee)*100;
        //商户号
        //String mch_id = partner;
        //子商户号  非必输
        //String sub_mch_id="";
        //设备号   非必输
        //String device_info="";
        //附加数据
        //String attach = userId;
        //总金额以分为单位，不带小数点
        //int total_fee = intMoney;
        //订 单 生 成 时 间   非必输
        //String time_start ="";
        //订单失效时间      非必输
        //String time_expire = "";
        //商品标记   非必输
        //String goods_tag = "";
        //非必输
        //String product_id = "";

        //这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
        String notify_url = baseUrl + "/wx/notifyUrl";

        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", WxPayConfig.appid);
        packageParams.put("mch_id", WxPayConfig.partner);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("body", body);
        packageParams.put("out_trade_no", out_trade_no);
        packageParams.put("total_fee", total_fee+"");
        packageParams.put("spbill_create_ip", spbill_create_ip);
        packageParams.put("notify_url", notify_url);
        packageParams.put("trade_type", WxPayConfig.trade_type);
        packageParams.put("openid", orderInfo.getWxAppid());

        RequestHandler reqHandler = new RequestHandler(request, response);
        reqHandler.init(WxPayConfig.appid, WxPayConfig.appsecret, WxPayConfig.partnerkey);

        String sign = reqHandler.createSign(packageParams);
        System.out.println("sign:"+sign);
        String xml="<xml>"+
                "<appid>"+WxPayConfig.appid+"</appid>"+
                "<mch_id>"+WxPayConfig.partner+"</mch_id>"+
                "<nonce_str>"+nonce_str+"</nonce_str>"+
                "<sign>"+sign+"</sign>"+
                "<body><![CDATA["+body+"]]></body>"+
                "<attach>"+ JsonUtils.objectToJson(orderInfo)+"</attach>"+
                "<out_trade_no>"+out_trade_no+"</out_trade_no>"+
                "<total_fee>"+total_fee+""+"</total_fee>"+
                "<spbill_create_ip>"+spbill_create_ip+"</spbill_create_ip>"+
                "<notify_url>"+notify_url+"</notify_url>"+
                "<trade_type>"+WxPayConfig.trade_type+"</trade_type>"+
                "<openid>"+orderInfo.getWxAppid()+"</openid>"+
                "</xml>";
        System.out.println("xml："+xml);

        String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        String prepay_id="";
        try {
            prepay_id = WeixinPayUtil.getPayNo(createOrderURL, xml);
            System.out.println("prepay_id:" + prepay_id);
            if(prepay_id.equals("")){
                //request.setAttribute("ErrorMsg", "统一支付接口获取预支付订单出错");
                //response.sendRedirect("error.jsp");
                return ClubResult.error("统一支付接口获取预支付订单出错");
            }
        } catch (Exception e1) {
            logger.error("统一支付接口获取预支付订单出错",e1);
        }

        SortedMap<String, String> finalpackage = new TreeMap<String, String>();
        String timestamp = Sha1Util.getTimeStamp();
        String packages = "prepay_id="+prepay_id;
        finalpackage.put("appId", WxPayConfig.appid);
        finalpackage.put("timeStamp", timestamp);
        finalpackage.put("nonceStr", nonce_str);
        finalpackage.put("package", packages);
        finalpackage.put("signType", WxPayConfig.signType);
        String finalsign = reqHandler.createSign(finalpackage);
        System.out.println("/jsapi?appid="+WxPayConfig.appid+"&timeStamp="+timestamp+"&nonceStr="+nonce_str+"&package="+packages+"&sign="+finalsign);

        /*
        * 跳转由后端进行控制
        model.addAttribute("appid", WxPayConfig.appid);
        model.addAttribute("timeStamp", timestamp);
        model.addAttribute("nonceStr", nonce_str);
        model.addAttribute("packageValue", packages);
        model.addAttribute("sign", finalsign);

        model.addAttribute("bizOrderId", orderId);
        model.addAttribute("orderId", orderId);
        model.addAttribute("payPrice", total_fee);
        return "/jsapi";*/

        /**
         *  返回给前端参数，跳蛛转支付页又前端发起
         */
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("appid", WxPayConfig.appid);
        resultMap.put("timeStamp", timestamp);
        resultMap.put("nonceStr", nonce_str);
        resultMap.put("packageValue", packages);
        resultMap.put("sign", finalsign);
        resultMap.put("bizOrderId", orderNo);
        resultMap.put("orderId", orderNo);
        resultMap.put("payPrice", total_fee);
		return ClubResult.ok(resultMap);
    }



    @GetMapping("/getPayId")
    public ClubResult getPayId(){
        return  ClubResult.ok("HELLO WORLD!!!");
    }



    /**
     * 微信异步回调，不会跳转页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/notifyUrl")
    public String weixinReceive(HttpServletRequest request,HttpServletResponse response){
        System.out.println("==开始进入h5支付回调方法==");
        String xml_review_result = WeixinPayUtil.getXmlRequest(request);
        System.out.println("微信支付结果:"+xml_review_result);
        Map resultMap = null;
        try {
            resultMap = WeixinPayUtil.doXMLParse(xml_review_result);
            System.out.println("resultMap:"+resultMap);
            if(resultMap != null && resultMap.size() > 0){
                String sign_receive = (String)resultMap.get("sign");
                System.out.println("sign_receive:"+sign_receive);
                resultMap.remove("sign");
                String checkSign = WeixinPayUtil.getSign(resultMap,WxPayConfig.partnerkey);
                System.out.println("checkSign:"+checkSign);

                //签名校验成功
                if(checkSign != null && sign_receive != null &&
                        checkSign.equals(sign_receive.trim())){
                    System.out.println("weixin receive check Sign sucess");
                    try{
                        //获得返回结果
                        String return_code = (String)resultMap.get("return_code");

                        if("SUCCESS".equals(return_code)){
                            String out_trade_no = (String)resultMap.get("out_trade_no");
                            System.out.println("weixin pay sucess,out_trade_no:"+out_trade_no);
                            //处理支付成功以后的逻辑，这里是写入相关信息到文本文件里面，如果有订单的处理订单
                            try{
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh24:mi:ss");
                                String content = out_trade_no+"        "+sdf.format(new Date());
                                String fileUrl = System.getProperty("user.dir") + File.separator+"WebContent" + File.separator + "data" + File.separator + "order.txt";
                                TxtUtil.writeToTxt(content, fileUrl);
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }else{
//                            model.addAttribute("payResult", "0");
//                            model.addAttribute("err_code_des", "通信错误");
                            logger.error("通信错误");
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }else{
                    //签名校验失败
                    System.out.println("weixin receive check Sign fail");
                    String checkXml = "<xml><return_code><![CDATA[FAIL]]></return_code>"+
                            "<return_msg><![CDATA[check sign fail]]></return_msg></xml>";
                    WeixinPayUtil.getTradeOrder("http://weixin.xinfor.com/wx/notifyUrl", checkXml);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 页面js返回支付成功后，查询微信后台是否支付成功，然后跳转结果页面
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/checkSucess")
    public ClubResult checkSucess( HttpServletRequest request,
                                  HttpServletResponse response) throws IOException{
        PayResult payResult = new PayResult();
        String id = request.getParameter("orderId");
        System.out.println("toWXPaySuccess, orderId: " + id);
        try {
            Map resultMap = WeixinPayUtil.checkWxOrderPay(id);
            System.out.println("resultMap:" + resultMap);
            String return_code = (String)resultMap.get("return_code");
            String result_code = (String)resultMap.get("result_code");
            System.out.println("return_code:" + return_code + ",result_code:" + result_code);
            if("SUCCESS".equals(return_code)){
                if("SUCCESS".equals(result_code)){
//                    model.addAttribute("orderId", id);
//                    model.addAttribute("payResult", "1");
                    String trade_state = (String)resultMap.get("trade_state");
//                    if ("SUCCESS".equals(trade_state)){
//
//                    }
                    OrderInfo orderInfo = JsonUtils.jsonToPojo((String) resultMap.get("attach"),OrderInfo.class);
                    orderInfoService.saveOrderInfo(orderInfo);
                    payResult.setOrderNo(id);
                    payResult.setPayStatus(Constants.SUCC_PAY_STATUS);
                    return ClubResult.ok(payResult);
                }else{

                    String err_code = (String)resultMap.get("err_code");
                    String err_code_des = (String)resultMap.get("err_code_des");
                    System.out.println("weixin resultCode:"+result_code+",err_code:"+err_code+",err_code_des:"+err_code_des);
                    logger.info("weixin resultCode:"+result_code+",err_code:"+err_code+",err_code_des:"+err_code_des);
//                    model.addAttribute("err_code", err_code);
//                    model.addAttribute("err_code_des", err_code_des);
//                    model.addAttribute("payResult", "0");
                    payResult.setOrderNo(id);
                    payResult.setPayStatus(Constants.FAIL_PAY_STATUS);
                    payResult.setErrCode(err_code);
                    payResult.setErrCodeDes(err_code_des);
                    return ClubResult.ok(payResult);
                }
            }else{
//                model.addAttribute("payResult", "0");
//                model.addAttribute("err_code_des", "通信错误");
                return ClubResult.error("通信错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("checkSuccess 方法失败",e);
            return ClubResult.error("网络异常");
        }
        //return "/payResult";
    }

    /**
     * 订单号生成方法
     * @return
     */
    private String genOrderNo(){
        String orderNo = "FTB";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmssSSS");
        String nowTime = sdf.format(new Date());
        orderNo+=nowTime;
        return orderNo;
    }

    /**
     * 工具类：在響應中添加內容
     *
     * @param response
     * @param returnvaleue
     */
    public void output(HttpServletResponse response, String returnvaleue) {
        try {
            PrintWriter pw = response.getWriter();
            pw.write(returnvaleue);
            System.out.println("****************return_valeue***************=" + returnvaleue);
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
