package com.feng.service;

import com.feng.common.vo.SecurityUsernamePasswordVo;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface FaceService {

    String registeredEngine(String appId, String sdkKey, String dllPath);

    SecurityUsernamePasswordVo faceSearch(InputStream byteArrayInputStream) throws IOException;
}
