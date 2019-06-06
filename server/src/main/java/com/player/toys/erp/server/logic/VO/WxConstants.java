package com.player.toys.erp.server.logic.VO;

public class WxConstants {
    public static final  String APPID="wxf4a5d1ea39d4117c";
    public static final  String APPSECRET="9d6171e474a2f6b9c7137d1aa6e03d65";
    //授权
    public static final  String AUTH_BASE_URL="https://open.weixin.qq.com/connect/oauth2/authorize?";
    //获取token
    public static final  String ACCESS_TOKEN_BASE_URL="https://api.weixin.qq.com/sns/oauth2/access_token?";
    //获取用户信息
    public static final  String INFO_BASE_URL="https://api.weixin.qq.com/sns/userinfo?";
    //回调
    public static final  String REDIRECT_URL="http://di3c6n.natappfree.cc/api/huidiao";
    //范围
    public static final  String SCOPE="snsapi_userinfo";
    //token
    public static final  String TOKEN="good";
    //跳转登录页面
    public static final  String WECHAT_LOGIN="";
    //授权成功
    public static final  String SUCCESS_INDEX="";
    //注册成功后
    public static final  String REGISTER_SUCCESS="";
    //激活成功后过渡页面
    public static final  String VALIDATE_SUCCESS="";
}
