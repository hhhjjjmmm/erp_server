package com.player.toys.erp.server.logic.controller;

import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.sms.SmsClient;
import com.baidubce.services.sms.SmsClientConfiguration;
import com.baidubce.services.sms.model.SendMessageV2Request;
import com.baidubce.services.sms.model.SendMessageV2Response;
import com.player.toys.erp.server.util.HttpClientUtil;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SendMessageExample {

    public static void main(String[] args) {
       // getduanxin("18737873431");
    }
        @RequestMapping(value = "/zyx/duanxin")
        public static String getduanxin(HttpServletRequest requests,String uphone){
        // 相关参数定义
        String endPoint = "http://sms.bj.baidubce.com"; // SMS服务域名，可根据环境选择具体域名
        String accessKeyId = "20f5d54832884745bae4601721d1e10b";  // 发送账号安全认证的Access Key ID
        String secretAccessKy = "a8e8a6af133c4680aa5d75f1db7b2ab5"; // 发送账号安全认证的Secret Access Key

        // ak、sk等config
        SmsClientConfiguration config = new SmsClientConfiguration();
        config.setCredentials(new DefaultBceCredentials(accessKeyId, secretAccessKy));
        config.setEndpoint(endPoint);

        // 实例化发送客户端
        SmsClient smsClient = new SmsClient(config);
        //随机验证码
             int a= (int) ((Math.random()*9+1)*100000);
             String number=String.valueOf(a);

        // 定义请求参数
        String invokeId = "9Y7uZsVR-WYNy-QERm"; // 发送使用签名的调用ID
        String phoneNumber = uphone; // 要发送的手机号码(只能填写一个手机号)
        String templateCode = "smsTpl:e7476122a1c24e37b3b0de19d04ae902"; // 本次发送使用的模板Code
        Map<String, String> vars =
                new HashMap<String, String>(); // 若模板内容为：您的验证码是${code},在${time}分钟内输入有效
        vars.put("code", number);
        vars.put("hour","1");
        //实例化请求对象
        SendMessageV2Request request = new SendMessageV2Request();
        request.withInvokeId(invokeId)
                .withPhoneNumber(phoneNumber)
                .withTemplateCode(templateCode)
                .withContentVar(vars);
        // 发送请求
        SendMessageV2Response response = smsClient.sendMessage(request);
        // 解析请求响应 response.isSuccess()为true 表示成功
        if (response != null && response.isSuccess()) {
            System.out.println("ok"+response);
        } else {
            System.out.println("失败"+response);;
    }
            HttpSession session = requests.getSession();
            session.setAttribute("number",number);
        return number;
    }
}
