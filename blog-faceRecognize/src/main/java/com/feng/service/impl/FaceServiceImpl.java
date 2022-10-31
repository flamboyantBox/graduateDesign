package com.feng.service.impl;

import com.arcsoft.face.*;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.face.enums.DetectOrient;
import com.arcsoft.face.enums.ErrorInfo;
import com.arcsoft.face.toolkit.ImageInfo;
import com.feng.client.FaceInfoClient;
import com.feng.common.exception.Assert;
import com.feng.common.result.ResponseEnum;
import com.feng.common.util.MD5;
import com.feng.common.vo.SecurityUsernamePasswordVo;
import com.feng.faceModel.FaceEngineModel;
import com.feng.service.FaceService;
import com.feng.util.Base64Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.arcsoft.face.toolkit.ImageFactory.getRGBData;


@Service
public class FaceServiceImpl implements FaceService {

    @Autowired
    private FaceInfoClient userNameClient;

    @Override
    public String registeredEngine(String appId, String sdkKey, String dllPath) {
        // 单例模式创建faceEngine
        FaceEngine faceEngine = FaceEngineModel.init(dllPath);

        //激活引擎
        int errorCode = faceEngine.activeOnline(appId, sdkKey);

        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            return "引擎激活失败";
        }
        ActiveFileInfo activeFileInfo = new ActiveFileInfo();
        errorCode = faceEngine.getActiveFileInfo(activeFileInfo);
        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            return "获取激活文件信息失败";
        }

        //引擎配置
        EngineConfiguration engineConfiguration = new EngineConfiguration();
        engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);
        engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_ALL_OUT);
        engineConfiguration.setDetectFaceMaxNum(10);
        engineConfiguration.setDetectFaceScaleVal(16);
        //功能配置
        FunctionConfiguration functionConfiguration = new FunctionConfiguration();
        functionConfiguration.setSupportAge(true);
        functionConfiguration.setSupportFace3dAngle(true);
        functionConfiguration.setSupportFaceDetect(true);
        functionConfiguration.setSupportFaceRecognition(true);
        functionConfiguration.setSupportGender(true);
        functionConfiguration.setSupportLiveness(true);
        functionConfiguration.setSupportIRLiveness(true);
        engineConfiguration.setFunctionConfiguration(functionConfiguration);

        //初始化引擎
        errorCode = faceEngine.init(engineConfiguration);

        if (errorCode != ErrorInfo.MOK.getValue()) {
            return "初始化引擎失败";
        }

        return "激活成功";
    }

    /**
     * 由于前端访问量巨大需要加锁保证线程安全
     *
     * 前台大量的IO访问该接口，导致堆积的线程量剧增。从而占据java的内存当内存满后直接宕机
     * 解决方案：1、集成redis，利用redis的雪崩机制
     *         2、集成RabbitMQ，通过消息队列的方式，延缓吞吐量的涌入
     *         3、使用java同步锁机制，方便，可用性强 故采用此机制
     */
    @Override
    public synchronized SecurityUsernamePasswordVo faceSearch(InputStream byteArrayInputStream) throws IOException {
        // 查询所有用户名
        List<com.feng.common.dto.FaceInfo> userList = userNameClient.getFaceInfo();

        // 将传过来的脸导入虹软检测库
        ImageInfo imageInfo = getRGBData(byteArrayInputStream);
        FaceEngine faceEngine = FaceEngineModel.getFaceEngine();

        List<FaceInfo> faceInfoList = new ArrayList<>();
        faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList);

        FaceFeature targetFaceFeature = new FaceFeature();
        faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList.get(0), targetFaceFeature);
        // 遍历查询数据库进行对比
        for (com.feng.common.dto.FaceInfo faceInfo:
             userList) {
            byte[] bytes = Base64Util.base64ToImgByteArray(String.valueOf(faceInfo.getFaceInfo()));
            FaceFeature sourceFaceFeature = new FaceFeature();
            sourceFaceFeature.setFeatureData(bytes);
            FaceSimilar faceSimilar = new FaceSimilar();
            faceEngine.compareFaceFeature(targetFaceFeature, sourceFaceFeature, faceSimilar);
            float score = faceSimilar.getScore();
            if (score >= 0.8){
                return SecurityUsernamePasswordVo.builder()
                        .username(faceInfo.getUsername())
                        .password(faceInfo.getPassword())
                        .build();
            }
        }
        throw new RuntimeException(ResponseEnum.FACE_ERROR.getMessage());
    }
}
