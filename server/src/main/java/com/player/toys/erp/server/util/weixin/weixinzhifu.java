package com.player.toys.erp.server.util.weixin;

import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPayUtil;
import com.player.toys.erp.server.logic.bean.TbUser;
import com.player.toys.erp.server.logic.service.impl.TbUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.player.toys.erp.server.util.WXPayUtil.getCurrentTimestamp;
@Controller
public class weixinzhifu {

    @Autowired
    TbUserServiceImpl tbUserService;

    @RequestMapping(value="/api/orders", method = RequestMethod.GET)
    @ResponseBody
    public Map orders(HttpServletRequest request) {
        try {

           TbUser tbUser= tbUserService.getusernoeByid((Integer) request.getSession().getAttribute("id"));
           String openid=tbUser.getOpenid();

            //拼接统一下单地址参数
            Map<String, String> paraMap = new HashMap<String, String>();
            //获取请求ip地址
            String ip = request.getHeader("x-forwarded-for");
            if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
                ip = request.getHeader("Proxy-Client-IP");
            }
            if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
                ip = request.getRemoteAddr();
            }
            if(ip.indexOf(",")!=-1){
                String[] ips = ip.split(",");
                ip = ips[0].trim();
            }
            int a=1;
            String aa=String.valueOf(a);
            paraMap.put("appid", "wxc7518f7fc5fb1bc6");
            paraMap.put("body", "zyx-订单结算");
            paraMap.put("mch_id", "1537797831");
            paraMap.put("nonce_str", WXPayUtil.generateNonceStr());
            paraMap.put("openid", openid);
            paraMap.put("out_trade_no", "201906058043382");//订单号
            paraMap.put("spbill_create_ip", ip);
            paraMap.put("total_fee",aa);
            paraMap.put("trade_type", "JSAPI");
            paraMap.put("notify_url","http://zyx.nat200.top/api/notify");// 此路径是微信服务器调用支付结果通知路径随意写
            String sign = WXPayUtil.generateSignature(paraMap, "182EC21452BAE7887584ED09F78C439B");
            paraMap.put("sign", sign);
            String xml = WXPayUtil.mapToXml(paraMap);//将所有参数(map)转xml格式
            // 统一下单 https://api.mch.weixin.qq.com/pay/unifiedorder
            String unifiedorder_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
            String xmlStr = HttpRequest.sendPost(unifiedorder_url, xml);//发送post请求"统一下单接口"返回预支付id:prepay_id
            //以下内容是返回前端页面的json数据
            String prepay_id = "";//预支付id
            if (xmlStr.indexOf("SUCCESS") != -1) {
                Map<String, String> map = WXPayUtil.xmlToMap(xmlStr);
                prepay_id = (String) map.get("prepay_id");
            }
            Map<String, String> payMap = new HashMap<String, String>();
            payMap.put("appId", "wxc7518f7fc5fb1bc6");
            String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
            payMap.put("timeStamp",timestamp);
            payMap.put("nonceStr", WXPayUtil.generateNonceStr());
            payMap.put("signType", "MD5");
            payMap.put("package", "prepay_id=" + prepay_id);
            String paySign = WXPayUtil.generateSignature(payMap, "182EC21452BAE7887584ED09F78C439B");
            payMap.put("paySign", paySign);
            return payMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/api/notify")
    public String callBack(HttpServletRequest request, HttpServletResponse response){
        //System.out.println("微信支付成功,微信发送的callback信息,请注意修改订单信息");
        InputStream is = null;
        try {
            is = request.getInputStream();//获取请求的流信息(这里是微信发的xml格式所有只能使用流来读)
            String xml = "";//WXPayUtil.inputStream2String(is, "UTF-8");
            Map<String, String> notifyMap = WXPayUtil.xmlToMap(xml);//将微信发的xml转map

            if(notifyMap.get("return_code").equals("SUCCESS")){
                if(notifyMap.get("result_code").equals("SUCCESS")){
                    String ordersSn = notifyMap.get("out_trade_no");//商户订单号
                    String amountpaid = notifyMap.get("total_fee");//实际支付的订单金额:单位 分
                    BigDecimal amountPay = (new BigDecimal(amountpaid).divide(new BigDecimal("100"))).setScale(2);//将分转换成元-实际支付金额:元
                    //String openid = notifyMap.get("openid");  //如果有需要可以获取
                    //String trade_type = notifyMap.get("trade_type");
                    }
                }
            //告诉微信服务器收到信息了，不要在调用回调action了========这里很重要回复微信服务器信息用流发送一个xml即可
            response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>");
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}