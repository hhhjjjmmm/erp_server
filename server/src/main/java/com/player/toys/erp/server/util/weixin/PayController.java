package com.player.toys.erp.server.util.weixin;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.lly835.bestpay.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.Random;
@Slf4j
public class PayController {

    @Autowired
    private BestPayServiceImpl bestPayService;

    /**
     * 发起支付
     */
    @GetMapping(value = "/api/pay")
    public ModelAndView pay(@RequestParam("openid") String openid,
                            Map<String, Object> map) {
        //微信公众账号支付配置
        WxPayH5Config wxPayH5Config = new WxPayH5Config();
        wxPayH5Config.setAppId("wxc7518f7fc5fb1bc6");
        wxPayH5Config.setAppSecret("9cfe6cad37129ec1724cd93c00b2ca7f");
        wxPayH5Config.setMchId("1537797831");
        wxPayH5Config.setMchKey("182EC21452BAE7887584ED09F78C439B");
        wxPayH5Config.setNotifyUrl("http://zyx.nat200.top/api/notify");
        //支付类, 所有方法都在这个类里
        bestPayService.setWxPayH5Config(wxPayH5Config);

        PayRequest request = new PayRequest();
        Random random = new Random();

        //支付请求参数
        request.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        request.setOrderId(String.valueOf(random.nextInt(1000000000)));
        request.setOrderAmount(0.01);
        request.setOrderName("最好的支付sdk");
        request.setOpenid(openid);
        log.info("【发起支付】request={}", JsonUtil.toJson(request));

        PayResponse payResponse = bestPayService.pay(request);
        log.info("【发起支付】response={}", JsonUtil.toJson(payResponse));
        map.put("payResponse", payResponse);
        return new ModelAndView("pay/create", map);
    }

    /**
     * 异步回调
     */
    @PostMapping(value = "/api/notify")
    public String notify(@RequestBody String notifyData) throws Exception {
        log.info("【异步回调】request={}", notifyData);
        PayResponse response = bestPayService.asyncNotify(notifyData);
        log.info("【异步回调】response={}", JsonUtil.toJson(response));

        return response.toString();
    }



}
