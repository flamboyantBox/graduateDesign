package com.feng.util;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Mr.Feng
 * @date 10/24/2022 9:57 PM
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat.pay")
public class WechatUtil implements InitializingBean {
    private String appid;
    private String partner;
    private String partnerKey;
    private String notifyUrl;

    public static String APPID;
    public static String PARTNER;
    public static String PARTNER_KEY;
    public static String NOTIFY_URL;

    @Override
    public void afterPropertiesSet() throws Exception {
        APPID = appid;
        PARTNER = partner;
        PARTNER_KEY = partnerKey;
        NOTIFY_URL = notifyUrl;
    }
}
