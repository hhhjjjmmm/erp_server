package com.player.toys.erp.server.logic.controller;

import com.player.toys.erp.server.logic.bean.ServerUser;
import com.player.toys.erp.server.logic.service.impl.ServerUserServiceImpl;
import com.player.toys.erp.server.util.AddressUtils;
import com.player.toys.erp.server.util.AuthUtil;
import com.player.toys.erp.server.util.Result;
import com.player.toys.erp.server.util.ResultCode;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * @ClassName UserCentreController
 * @Description
 * @Author Player
 * @Date 2019-05-16 17:01
 * @Version 1.0
 **/
@RestController
@Slf4j
public class UserCentreController {

    @Autowired
    private ServerUserServiceImpl serverUserService;
/*
* 后台登录
* */
    @CrossOrigin
    @RequestMapping(value = "/api/login",method = RequestMethod.POST)
    public Result getToken(String username ,String password) {
        String token = serverUserService.generatorToken(username, password);
        if (token != null||token!="") {
           return Result.failure(ResultCode.SUCCESS, token);
        }
       return Result.failure(ResultCode.ERROR, token);
    }

    /*
     * 获取时间
     * */
    public String getDateAfter(int day) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +day);
        date = calendar.getTime();
        return  sdf.format(date);
    }

    public  String suijishu(){
        Random random=new Random();
        String a=String.valueOf(random.nextInt(10000));
        return a;
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        if (bCryptPasswordEncoder.matches("123455312","$2a$10$vL2fq0wjpa/o15usoMg.qer/tQd5WKpI.BHKoVzp6SEmGY8.FdA62")){
            System.out.println("111");
        }
        System.out.println("222");
    }
    /*
    * 管理员新增
    * */
    @RequestMapping(value = "/api/adduser",method = RequestMethod.POST)
    public  String adduser(HttpServletRequest request, ServerUser serverUser){
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
            String password=request.getParameter("password");
            String salt=suijishu();
            password=bCryptPasswordEncoder.encode(password+salt);
                   serverUser.setPassword(password);
                   serverUser.setSalt(salt);
       if (serverUserService.save(serverUser)){
           return "新增成功";
       }
        return "新增失败";
    }


    /*
     *获取当前城市地址
     **/
    @RequestMapping(value = "/api/address")
    @ResponseBody
    public static Result address() throws Exception {
        log.info("获取地址");

        String address=  AddressUtils.getAddressByIp();
        return   Result.failure(ResultCode.SUCCESS,address);
    }


    /**
     * 微信授权登录
     */
    @RequestMapping("/api/wxLogin")
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // backUrl 需要遵循微信官方的定义，微信的接口只能用 https 来访问
        // 所以我这里是直接把整个项目打包成 jar 包，然后扔到自己的服务器上
        String backUrl = "IP地址";
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + AuthUtil.APPID + "&redirect_uri="
                + URLEncoder.encode(backUrl) + "&response_type=code" + "&scope=snsapi_userinfo"
                + "&state=STATE#wechat_redirect";
        response.sendRedirect(url);
    }

    /**
     * 登录成功的回调函数
     *
     * @param request
     * @param response
     * @throws ClientProtocolException
     * @throws IOException
     * @throws ServletException
     */
    @RequestMapping("/api/callBack")
    protected void deGet(HttpServletRequest request, HttpServletResponse response)
            throws ClientProtocolException, IOException, ServletException {
        String code = request.getParameter("code");
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + AuthUtil.APPID + "&secret="
                + AuthUtil.APPSECRET + "&code=" + code + "&grant_type=authorization_code";
        JSONObject jsonObject = AuthUtil.doGetJson(url);
        String openid = jsonObject.getString("openid");
        String token = jsonObject.getString("access_token");
        String infoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + token + "&openid=" + openid
                + "&lang=zh_CN";
        JSONObject userInfo = AuthUtil.doGetJson(infoUrl);

        if( userInfo != null ){
            // 这里是把授权成功后，获取到的东西放到 info 里面，前端可以通过 EL 表达式直接获取相关信息
            request.setAttribute("info", userInfo);
            // 这里是授权成功返回的页面
            request.getRequestDispatcher("/success.jsp").forward(request, response);
        }else{
            request.getRequestDispatcher("/fail.jsp").forward(request, response);
        }

    }
}
