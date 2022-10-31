package com.feng.common.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author Mr.Feng
 * @date 10/27/2022 9:28 PM
 */
@Data
@Builder
public class FaceInfo {
    private String username;
    private String password;
    private String faceInfo;
}
