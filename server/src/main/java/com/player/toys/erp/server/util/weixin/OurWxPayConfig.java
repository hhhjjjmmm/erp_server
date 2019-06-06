package com.player.toys.erp.server.util.weixin;

import com.github.wxpay.sdk.WXPayConfig;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class OurWxPayConfig implements WXPayConfig {

    /** 加载证书  这里证书需要到微信商户平台进行下载*/
    private byte [] certData;

    public OurWxPayConfig() throws  Exception{
        InputStream certStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("cert/wxpay/apiclient_cert.p12");
        this.certData = IOUtils.toByteArray(certStream);
        certStream.close();
    }
    /*
     *
     * 配置！
     * */
    @Override
    public String getAppID() {
        return "";
    }

    @Override
    public String getMchID() {
        return "1520406361";
    }

    @Override
    public String getKey() {
        return "";
    }

    @Override
    public InputStream getCertStream() {
        return new ByteArrayInputStream(this.certData);
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 0;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 0;
    }
}
