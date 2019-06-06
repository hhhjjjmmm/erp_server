package com.player.toys.erp.server.logic.controller;

import com.alibaba.fastjson.JSONObject;
import com.player.toys.erp.server.logic.bean.TbUser;
import com.player.toys.erp.server.logic.service.impl.TbUserServiceImpl;
import com.player.toys.erp.server.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
public class WxController {
    @Autowired
    TbUserServiceImpl tbUserService;
    //appid
    private static final String APPID = "wxc7518f7fc5fb1bc6";//id wxc7518f7fc5fb1bc6 测试wxd22df5384d05d253
    //授权回调（natapp 内网穿透地址免费）
    private static final String redirectUrl = "http://zyx.nat200.top/api/aaa";
    //请求形式是否有跳转页面
    private static final String scope="snsapi_userinfo";

    private String TOKEN = "good";
    @RequestMapping(value = { "/api/token" }, method = { RequestMethod.POST, RequestMethod.GET })
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain;charset=utf-8");
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 随机字符串
        String echostr = request.getParameter("echostr");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");

        String[] str = { TOKEN, timestamp, nonce };
        Arrays.sort(str); // 字典序排序
        String bigStr = str[0] + str[1] + str[2];
        // SHA1加密，我这里用的是common-codec的jar包，你们也可以用java自带的消息消息摘要来写，只不过要多写几行代码，但结果都一样的
        DigestUtils.sha1Hex(bigStr);
        String digest = DigestUtils.sha1Hex(bigStr);

        // 确认请求来至微信
        if (digest.equals(signature)) {
            response.getWriter().print(echostr);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }


    /*
     * 获取code
     * */
    @RequestMapping("/api/ownerCheck")
    public String getRequestCodeUrl(HttpServletRequest request) {
        String url="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+APPID+"&redirect_uri="+redirectUrl+"&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect";
        return "redirect:"+url;
    }
    //重定向的url 也就是上面controller重定向的微信url里面重定向自己的url（http://grddz4.natappfree.cc/api/aaa）
    @GetMapping("/api/aaa")
    @ResponseBody
    public Result aa(ModelMap modelMap, HttpServletRequest req, HttpServletResponse response) throws IOException {
          /*
         * start 获取微信用户基本信息
         */
        String code = req.getParameter("code");
        //第二步：通过code换取网页授权access_token
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WXAuthUtil.APPID
                + "&secret=" + WXAuthUtil.APPSECRET
                + "&code=" + code
                + "&grant_type=authorization_code";

        System.out.println("url:" + url);
        JSONObject jsonObject = WXAuthUtil.doGetJson(url);
/*
         { "access_token":"ACCESS_TOKEN",
            "expires_in":7200,
            "refresh_token":"REFRESH_TOKEN",
            "openid":"OPENID",
            "scope":"SCOPE" 
           }
         */
        String openid = jsonObject.getString("openid");
        String access_token = jsonObject.getString("access_token");
        String refresh_token = jsonObject.getString("refresh_token");
        //第五步验证access_token是否失效；展示都不需要
        String chickUrl = "https://api.weixin.qq.com/sns/auth?access_token=" + access_token + "&openid=" + openid;

        JSONObject chickuserInfo = WXAuthUtil.doGetJson(chickUrl);
        System.out.println(chickuserInfo.toString());
        if (!"0".equals(chickuserInfo.getString("errcode"))) {
// 第三步：刷新access_token（如果需要）-----暂时没有使用,参考文档https://mp.weixin.qq.com/wiki，
            String refreshTokenUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + openid + "&grant_type=refresh_token&refresh_token=" + refresh_token;

            JSONObject refreshInfo = WXAuthUtil.doGetJson(chickUrl);
            System.out.println(refreshInfo.toString());
            access_token = refreshInfo.getString("access_token");
        }
// 第四步：拉取用户信息(需scope为 snsapi_userinfo)
        String infoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token
                + "&openid=" + openid
                + "&lang=zh_CN";
        System.out.println("infoUrl:" + infoUrl);
        JSONObject userInfo = WXAuthUtil.doGetJson(infoUrl);
        System.out.println("JSON-----" + userInfo.toString());
        System.out.println("名字-----" + userInfo.getString("nickname"));
        System.out.println("头像-----" + userInfo.getString("headimgurl"));


        //下面是自己业务逻辑，存储用户信息到数据库。
        //随机店铺号
        int a = (int) ((Math.random() * 9 + 1) * 1000000);
        String number = String.valueOf(a);
        String openids = userInfo.getString("openid");
        String nickname = userInfo.getString("nickname");
        String headimgurl = userInfo.getString("headimgurl");
        String ushopname = nickname + "的店铺";
        //map作为返回数据
        Map<String,String> map=new HashMap<>();
        map.put("openids",openids);
        map.put("nickname",nickname);
        map.put("headimgurl",headimgurl);
            /*
         * 新增微信用户基本信息
         */
        TbUser user = new TbUser();
        try {
            TbUser tbUser = tbUserService.userfind(openids);
            if (tbUser == null) {
                user.setUname(nickname);
                user.setUimg(headimgurl);
                user.setUshop(number);
                user.setOpenid(openids);
                user.setUshopname(ushopname);
                tbUserService.save(user);
            }
            return Result.failure(ResultCode.SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.failure(ResultCode.ERROR);
    }
}