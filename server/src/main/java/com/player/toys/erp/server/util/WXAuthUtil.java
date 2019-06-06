package com.player.toys.erp.server.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class WXAuthUtil {
    //appid
    public static final String APPID="wxc7518f7fc5fb1bc6";
    //appsecret  9cfe6cad37129ec1724cd93c00b2ca7f   9d6171e474a2f6b9c7137d1aa6e03d65
    public static final String APPSECRET ="9cfe6cad37129ec1724cd93c00b2ca7f";

    //token(自己设置保持一致)
    private static final String TOKEN = "good";
    public static JSONObject doGetJson(String url) throws ClientProtocolException, IOException {
        JSONObject jsonObject =null;
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet =new HttpGet(url);
        HttpResponse response = client.execute(httpGet);
        HttpEntity entity =response.getEntity();
        if(entity!=null)
        {
            //把返回的结果转换为JSON对象
            String result = EntityUtils.toString(entity, "UTF-8");
            jsonObject = JSON.parseObject(result);
        }
        return jsonObject;
    }
}
