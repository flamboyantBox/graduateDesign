package com.feng.util;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Mr.Feng
 * @date 10/20/2022 4:04 PM
 */
@Data
@Component
@ConfigurationProperties(prefix = "alipay")
public class AlipayUtil implements InitializingBean {
    private String appid;
    private String url;
    private String privateKey;
    private String publicKey;
    private String notifyUrl;
    private String returnUrl;

    public static String APPID;
    public static String URL;
    public static String PRIVATE_KEY;
    public static String PUBLIC_KEY;
    public static String NOTIFY_URL;
    public static String RETURN_URL;

    @Override
    public void afterPropertiesSet() throws Exception {
        APPID = appid;
        URL = url;
        PRIVATE_KEY = privateKey;
        PUBLIC_KEY = publicKey;
        NOTIFY_URL = notifyUrl;
        RETURN_URL = returnUrl;
    }
}
