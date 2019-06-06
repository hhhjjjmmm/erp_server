package com.player.toys.erp.server.logic.VO;

import com.player.toys.erp.server.util.WeiXinUtils;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Data
public class Singleton {
    private  Map<String,String> map=new HashMap<String, String>();
    private Singleton(){};
    private static Singleton singleton=null;
    public static Singleton getInstance(){
        if (singleton==null){
            singleton=new Singleton();
        }
        return singleton;
    }
    /**
     * 获取 accessToken Jsapi_ticket 已加入缓存机制
     * @return
     */
    public Map<String,Object> getAccessTokenAndJsapiTicket() {
        Map<String,Object> result = new HashMap<String,Object>();
        Singleton singleton = Singleton.getInstance();
        Map<String, String> map = singleton.getMap();
        String time = map.get("time");//从缓存中拿数据
        String accessToken = map.get("access_token");//从缓存中拿数据
        String jsapiticket = map.get("jsapiticket");//从缓存中拿数据
        Long nowDate = new Date().getTime();
        //这里设置过期时间 3000*1000就好了
        if (accessToken != null && time != null && nowDate - Long.parseLong(time) < 1.5*60*60*1000) {
            System.out.println("-----从缓存读取access_token："+accessToken);
            //从缓存中拿数据为返回结果赋值
            result.put("access_token", accessToken);
            result.put("jsapiticket", jsapiticket);
        } else {
            String accessTokens = WeiXinUtils.getAccessToken();
            String jsapitickets=WeiXinUtils.getTicket(accessTokens);

            //将信息放置缓存中
            map.put("time", nowDate + "");
            map.put("access_token", accessTokens);
            map.put("jsapiticket", jsapitickets);
            //为返回结果赋值
            result.put("access_token", accessTokens);
            result.put("jsapiticket", jsapitickets);
        }
        return result;
    }

    public static void main(String[] args) {
        Singleton singleton=new Singleton();
        System.out.println(singleton.getAccessTokenAndJsapiTicket());
        System.out.println(singleton.getAccessTokenAndJsapiTicket());
    }
}
