package com.feng.oss.service;

import java.io.InputStream;

/**
 * @author Mr.Feng
 * @date 7/14/2022 11:03 AM
 */
public interface FileService {
    String upload(InputStream inputStream, String originalFilename);

    void remove(String url);
}
