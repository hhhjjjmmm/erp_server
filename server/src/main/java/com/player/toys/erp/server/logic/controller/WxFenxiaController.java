package com.player.toys.erp.server.logic.controller;

import com.player.toys.erp.server.logic.VO.Singleton;
import com.player.toys.erp.server.util.Result;
import com.player.toys.erp.server.util.ResultCode;
import com.player.toys.erp.server.util.WXPayUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@RestController
public class WxFenxiaController {
    /*
    * 分享
    * */
    @RequestMapping(value = "/zyx/fenxiang")
    public Result getfenxiang() {
        Singleton singleton = Singleton.getInstance();
        Map info = singleton.getAccessTokenAndJsapiTicket();
        String url = "";
        String noncestr = WXPayUtil.generateNonceStr();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String jsapi_ticket = info.get("jsapiticket").toString();
        String str = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url=" + url;
        String signature = getSha1(str);
        Map<String, String> map = new HashMap();
        map.put("timestamp", timestamp);
        map.put("jsapi_ticket", jsapi_ticket);
        map.put("noncestr", noncestr);
        map.put("signature", signature);
        System.out.println(map);
        return Result.failure(ResultCode.SUCCESS, map);
    }

    public static void main(String[] args) {
        WxFenxiaController wxFenxiaController = new WxFenxiaController();
        System.out.println(wxFenxiaController.getfenxiang());
    }


    //创建签名SHA1
    public static String getSha1(String str) {
        if (null == str || 0 == str.length()) {
            return null;
        }
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] buf = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}