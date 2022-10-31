package com.feng.service;

import com.feng.pojo.dto.LabelOptionDTO;
import com.feng.pojo.dto.ResourceDTO;
import com.feng.pojo.entity.Resource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.pojo.vo.ConditionVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
public interface ResourceService extends IService<Resource> {

    List<ResourceDTO> resourceList(ConditionVo conditionVo);

    List<LabelOptionDTO> menuOptionsList();
}
