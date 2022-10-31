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
@ConfigurationProperties(prefix = "face")
public class FaceUtil implements InitializingBean {
    private String dllPath;
    private String appId;
    private String sdkKey;

    public static String DLL_PATH;
    public static String APPID;
    public static String SDK_KEY;

    @Override
    public void afterPropertiesSet() throws Exception {
        DLL_PATH = dllPath;
        APPID = appId;
        SDK_KEY = sdkKey;
    }
}
