package com.player.toys.erp.server.logic.VO;

import lombok.Data;

import java.io.Serializable;
@Data
public class RedPack implements Serializable {
    private String sign; //根据属性生成的验证
    private String mch_billno; //订单号
    private String MCH_ID;  //商户号
    private String APPID; // 微信appid
    private String send_name;// 商户名称
    private String re_openid;// 用户openid
    private String total_amount;// 付款金额
    private String total_num;//红包接收人数  现金红包只能是  1
    private String wishing;// 红包祝福语
    private String client_ip;// 调用接口机器的IP
    private String act_name;// 活动名称
    private String remark;// 备注
    private String nonce_str;// 随机字符串
}
