package com.player.toys.erp.server.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
@Slf4j
public class WeiXinUtils{

        /**
         * access_token是公众号的全局唯一接口调用凭据
         * */
        public static String getAccessToken(){
            String accessToken = "";
            String grantType = "client_credential";// 获取access_token填写client_credential
            String appId ="wxd22df5384d05d253";// 第三方用户唯一凭证
            String secret = "9d6171e474a2f6b9c7137d1aa6e03d65";// 第三方用户唯一凭证密钥，即appsecret
            // 这个url链接地址和参数皆不能变
            String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=" + grantType + "&appid=" + appId + "&secret="+ secret;
            try {
                URL urlGet = new URL(url);
                HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
                http.setRequestMethod("GET"); // 必须是get方式请求
                http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                http.setDoOutput(true);
                http.setDoInput(true);
                System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
                System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
                http.connect();
                InputStream is = http.getInputStream();
                int size = is.available();
                byte[] jsonBytes = new byte[size];
                is.read(jsonBytes);
                String message = new String(jsonBytes, "UTF-8");
                log.debug("获取access_token返回的message:"+message);
                JSONObject demoJson = JSON.parseObject(message);
                log.info("获取access_token返回的json:"+demoJson);
                accessToken = demoJson.getString("access_token");
                log.info("新获取的access_token:"+accessToken);
                is.close();
            }catch (Exception e){
                log.debug("获取access_token发生异常",e);
            }
            return accessToken;
        }

        /**
         * sapi_ticket是公众号用于调用微信JS接口的临时票据,获得jsapi_ticket之后，就可以生成JSSDK权限验证的签名了
         * 参与签名的字段包括noncestr（随机字符串）, 有效的jsapi_ticket, timestamp（时间戳）, url（当前网页的URL，不包含#及其后面部分）
         * @param accessToken
         * @return
         */
        public static String getTicket(String accessToken) {
            String ticket = null;
            String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken + "&type=jsapi";// 这个url链接和参数不能变
            try {
                URL urlGet = new URL(url);
                HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
                http.setRequestMethod("GET"); // 必须是get方式请求
                http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                http.setDoOutput(true);
                http.setDoInput(true);
                System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
                System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
                http.connect();
                InputStream is = http.getInputStream();
                int size = is.available();
                byte[] jsonBytes = new byte[size];
                is.read(jsonBytes);
                String message = new String(jsonBytes, "UTF-8");
                JSONObject demoJson = JSON.parseObject(message);
                ticket = demoJson.getString("ticket");
                is.close();
            } catch (Exception e) {
                log.error("获取ticket发生错误", e);
            }
            return ticket;
        }

        public static String SHA1(String decript) {
            try {
                MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
                digest.update(decript.getBytes());
                byte messageDigest[] = digest.digest();
                // Create Hex String
                StringBuffer hexString = new StringBuffer();
                // 字节数组转换为 十六进制 数
                for (int i = 0; i < messageDigest.length; i++) {
                    String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                    if (shaHex.length() < 2) {
                        hexString.append(0);
                    }
                    hexString.append(shaHex);
                }
                return hexString.toString();

            } catch (NoSuchAlgorithmException e) {
                log.error("SHA1发生错误", e);
            }
            return "";
        }
}
