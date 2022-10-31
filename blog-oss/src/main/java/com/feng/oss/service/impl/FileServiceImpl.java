package com.feng.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.feng.oss.service.FileService;
import com.feng.oss.util.OSSProperties;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Mr.Feng
 * @date 7/14/2022 11:03 AM
 */
@Service
public class FileServiceImpl implements FileService {
    @Override
    public String upload(InputStream inputStream, String originalFilename) {
        OSS ossClient = new OSSClientBuilder().build(
                OSSProperties.ENDPOINT,
                OSSProperties.KEY_ID,
                OSSProperties.KEY_SECRET
        );

        if (!ossClient.doesBucketExist(OSSProperties.BUCKET_NAME)){
            ossClient.createBucket(OSSProperties.BUCKET_NAME);
            ossClient.setBucketAcl(OSSProperties.BUCKET_NAME, CannedAccessControlList.PublicRead);
        }

        String date = new DateTime().toString("yyyy-MM-dd/");
        originalFilename = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
        String key = date +originalFilename;
        ossClient.putObject(OSSProperties.BUCKET_NAME, key, inputStream);
        ossClient.shutdown();
        return "https://" + OSSProperties.BUCKET_NAME + "." + OSSProperties.ENDPOINT + "/" + key;
    }

    @Override
    public void remove(String url) {
        OSS ossClient = new OSSClientBuilder().build(
                OSSProperties.ENDPOINT,
                OSSProperties.KEY_ID,
                OSSProperties.KEY_SECRET
        );
        String host = "https://" + OSSProperties.BUCKET_NAME + "." + OSSProperties.ENDPOINT + "/";
        String objectName = url.substring(host.length());

        ossClient.deleteObject(OSSProperties.BUCKET_NAME, objectName);

        ossClient.shutdown();
    }
}
