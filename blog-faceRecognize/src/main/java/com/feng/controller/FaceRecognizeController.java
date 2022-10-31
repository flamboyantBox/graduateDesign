package com.feng.controller;

import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.toolkit.ImageInfo;
import com.feng.client.FaceLoginClient;
import com.feng.client.FaceUploadClient;
import com.feng.common.result.R;
import com.feng.common.vo.SecurityUsernamePasswordVo;
import com.feng.common.vo.UserRegisterVo;
import com.feng.faceModel.FaceEngineModel;
import com.feng.service.FaceService;
import com.feng.util.Base64Util;
import com.feng.util.FaceUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.arcsoft.face.toolkit.ImageFactory.getRGBData;

/**
 * @author Mr.Feng
 * @date 10/20/2022 2:03 PM
 */
@RestController
@RequestMapping("/faceRecognize/core")
@CrossOrigin
public class FaceRecognizeController {
    @Autowired
    private FaceService faceService;

    @Autowired
    private FaceUploadClient faceUploadClient;

    @Autowired
    private FaceLoginClient faceLoginClient;

    @ApiOperation("激活引擎")
    @GetMapping("registeredEngine")
    public R registeredEngine() {
        String result = faceService.registeredEngine(FaceUtil.APPID, FaceUtil.SDK_KEY, FaceUtil.DLL_PATH);
        return R.ok().data("result", result);
    }

    @ApiOperation("人脸注册")
    @PostMapping(value = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R upload(@ApiParam(value = "文件", required = true)
                    @RequestPart("file") MultipartFile file,
                    @RequestParam("username") String username,
                    @RequestParam("password") String password) throws IOException {

        // 转化照片格式
        InputStream inputStream = file.getInputStream();
        ImageInfo imageInfo = getRGBData(inputStream);
        FaceEngine faceEngine = FaceEngineModel.getFaceEngine();

        // 检测人脸
        List<FaceInfo> faceInfoList = new ArrayList<>();
        faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList);

        // 特征提取
        FaceFeature faceFeature = new FaceFeature();
        faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList.get(0), faceFeature);

        // 将注册信息保存进数据库
        String faceInfo = Base64Util.encode(faceFeature.getFeatureData());
        UserRegisterVo userRegisterVo = new UserRegisterVo();
        userRegisterVo.setFaceInfo(faceInfo);
        userRegisterVo.setUsername(username);
        userRegisterVo.setPassword(password);

        faceLoginClient.userRegister(userRegisterVo);

        // 上传至oss
        return faceUploadClient.upload(file);
    }

    @ApiOperation("人脸搜寻")
    @PostMapping("faceSearch")
    public R faceSearch(@RequestParam(value = "imgBase64") String imageBase64) throws IOException {
        byte[] bytes = Base64Util.base64ToImgByteArray(imageBase64);
        InputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        SecurityUsernamePasswordVo usernamePasswordVo = faceService.faceSearch(byteArrayInputStream);

        return R.ok().data("usernamePassword", usernamePasswordVo);
    }
}
