package com.feng.client;

import com.feng.common.result.R;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @author Mr.Feng
 * @date 10/27/2022 6:58 PM
 */
@FeignClient(value = "service-oss", path = "/api/oss/file")
public interface FaceUploadClient {
    @PostMapping(value = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    R upload(@ApiParam(value = "文件", required = true)
             @RequestPart("file") MultipartFile file);

    @GetMapping("downloadFile")
    String downloadFile(@RequestParam String filename);
}
