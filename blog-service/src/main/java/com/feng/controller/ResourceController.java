package com.feng.controller;


import com.feng.common.result.R;
import com.feng.pojo.dto.LabelOptionDTO;
import com.feng.pojo.dto.ResourceDTO;
import com.feng.pojo.vo.ConditionVo;
import com.feng.service.ResourceService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
@RestController
@RequestMapping("/resource/core")
public class ResourceController {
    @Autowired
    private ResourceService resourceService;

    @ApiOperation(value = "查看角色资源列表")
    @GetMapping("resourceList")
    public R listResource(ConditionVo conditionVo) {
        List<ResourceDTO> resourceDTO = resourceService.resourceList(conditionVo);
        return R.ok().data("resourceDTO", resourceDTO);
    }

    @ApiOperation(value = "查看角色资源选项")
    @GetMapping("labelOptionDTOList")
    public R listResourceOption() {
        List<LabelOptionDTO> labelOptionDTOList = resourceService.menuOptionsList();
        return R.ok().data("labelOptionDTOList", labelOptionDTOList);
    }
}

