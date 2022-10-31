package com.feng.faceModel;

import com.arcsoft.face.FaceEngine;

public class FaceEngineModel {
    private static FaceEngine faceEngine;
    private String dllPath;

    public static FaceEngine init(String dllPath){
        faceEngine = new FaceEngine(dllPath);
        return faceEngine;
    }
    public static FaceEngine getFaceEngine(){
        return faceEngine;
    }
}
