package com.thagedy.footballclub.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
//import net.sf.json.JSONObject;
/**
 * Created by thagedy on 2017/2/19.
 */
public class WxConfig {

    /**
     * 公众账号ID
     */
    public static String appid = "";
    public static String appsecret = "";

    /**
     * 商户号（mch_id）
     */
    public static String partner = "";
    public static String partnerkey = "";

    /**
     * 交易类型
     */
    public static String trade_type = "JSAPI";
    public static String signType = "MD5";

    /**
     * 微信授权回跳连接
     */
    public static String baseUrl = "http://weixin.footballclub.xyz";

    public static String genOrderNo(){
        String orderNo = "xf";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmssSSS");
        String nowTime = sdf.format(new Date());
        orderNo+=nowTime;
        return orderNo;
    }

//    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
//        JSONObject jsonObject = null;
//        try {
//            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
//            TrustManager[] tm = { new TrustAnyTrustManager() };
//            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
//            sslContext.init(null, tm, new java.security.SecureRandom());
//            // 从上述SSLContext对象中得到SSLSocketFactory对象
//            SSLSocketFactory ssf = sslContext.getSocketFactory();
//
//            URL url = new URL(requestUrl);
//            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
//            conn.setSSLSocketFactory(ssf);
//
//            conn.setDoOutput(true);
//            conn.setDoInput(true);
//            conn.setUseCaches(false);
//            // 设置请求方式（GET/POST）
//            conn.setRequestMethod(requestMethod);
//
//            // 当outputStr不为null时向输出流写数据
//            if (null != outputStr) {
//                OutputStream outputStream = conn.getOutputStream();
//                // 注意编码格式
//                outputStream.write(outputStr.getBytes("UTF-8"));
//                outputStream.close();
//            }
//
//            // 从输入流读取返回内容
//            InputStream inputStream = conn.getInputStream();
//            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//            String str = null;
//            StringBuffer buffer = new StringBuffer();
//            while ((str = bufferedReader.readLine()) != null) {
//                buffer.append(str);
//            }
//
//            // 释放资源
//            bufferedReader.close();
//            inputStreamReader.close();
//            inputStream.close();
//            inputStream = null;
//            conn.disconnect();
//            jsonObject = JSONObject.fromObject(buffer.toString());
//        } catch (ConnectException ce) {
//        } catch (Exception e) {
//        }
//        return jsonObject;
//    }



}
