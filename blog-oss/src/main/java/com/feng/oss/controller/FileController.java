package com.feng.oss.controller;

import com.feng.common.exception.BlogException;
import com.feng.common.result.R;
import com.feng.common.result.ResponseEnum;
import com.feng.common.util.JwtUtils;
import com.feng.oss.client.UserInfoClient;
import com.feng.oss.service.FileService;
import com.feng.oss.vo.RemovePhotoVo;
import com.feng.oss.vo.UploadCoverVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Mr.Feng
 * @date 7/14/2022 11:01 AM
 */
@RestController
@RequestMapping("/api/oss/file")
public class FileController {
    @Autowired
    private FileService fileService;

    @Autowired
    private UserInfoClient userInfoClient;

    @ApiOperation("文件上传")
    @PostMapping("upload")
    public R upload(@ApiParam(value = "文件", required = true)
                    @RequestPart("file") MultipartFile file){
        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String url = fileService.upload(inputStream, originalFilename);

            return R.ok().message("上传成功").data("url", url);
        } catch (IOException e) {
            throw new BlogException(ResponseEnum.UPLOAD_ERROR, e);
        }
    }

    @ApiOperation("删除文件")
    @DeleteMapping("remove")
    public R remove(HttpServletRequest request){
        String token = request.getHeader("token");
        Long tokenId = JwtUtils.getUserId(token);
        String avatar = userInfoClient.getAvatar(tokenId);
        fileService.remove(avatar);
        return R.ok().message("删除成功");
    }

    @ApiOperation("删除网站头像")
    @GetMapping("removeWebImg")
    public R removeWebImg(){
        String avatar = userInfoClient.getWebImg();
        fileService.remove(avatar);
        return R.ok().message("删除成功");
    }

    @ApiOperation("删除用户头像")
    @GetMapping("removeUserImg")
    public R removeUserImg(){
        String avatar = userInfoClient.getUserImg();
        fileService.remove(avatar);
        return R.ok().message("删除成功");
    }

    @ApiOperation("删除游客头像")
    @GetMapping("removeGuestImg")
    public R removeGuestImg(){
        String avatar = userInfoClient.getGuestImg();
        fileService.remove(avatar);
        return R.ok().message("删除成功");
    }

    @ApiOperation("删除alipay")
    @GetMapping("removeAlipayImg")
    public R removeAlipayImg(){
        String avatar = userInfoClient.getAlipayImg();
        fileService.remove(avatar);
        return R.ok().message("删除成功");
    }

    @ApiOperation("删除微信支付")
    @GetMapping("removeWeChatImg")
    public R removeWeChatImg(){
        String avatar = userInfoClient.getWeChatImg();
        fileService.remove(avatar);
        return R.ok().message("删除成功");
    }

    @ApiOperation(value = "覆盖之前的cover")
    @DeleteMapping("removeCoverUrl")
    public R removeCoverUrl(@Valid @RequestBody UploadCoverVo uploadCoverVo) {
        fileService.remove(uploadCoverVo.getCoverUrl());
        return R.ok().message("删除成功");
    }

    @ApiOperation(value = "删除照片")
    @DeleteMapping("removePhoto")
    public R removePhoto(@Valid @RequestBody RemovePhotoVo removePhotoVo) {
        fileService.remove(removePhotoVo.getPhotoUrl());
        return R.ok().message("删除成功");
    }
}
