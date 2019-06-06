package com.player.toys.erp.server.common;

/**
 * @ClassName Const
 * @Description
 * @Author Player
 * @Date 2019-05-14 11:19
 * @Version 1.0
 **/
public class Const {
    public static final long EXPIRATION_TIME = 432_000_000;     // 5天(以毫秒ms计)
    public static final String SECRET = "yuanxiaohan";      // JWT密码
    public static final String TOKEN_PREFIX = "";         // Token前缀
    public static final String HEADER_STRING = "Authorization"; // 存放Token的Header Key
}
