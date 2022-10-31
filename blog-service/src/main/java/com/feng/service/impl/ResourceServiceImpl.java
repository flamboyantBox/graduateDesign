package com.feng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.feng.common.util.BeanCopyUtils;
import com.feng.pojo.dto.LabelOptionDTO;
import com.feng.pojo.dto.ResourceDTO;
import com.feng.pojo.entity.Resource;
import com.feng.mapper.ResourceMapper;
import com.feng.pojo.vo.ConditionVo;
import com.feng.service.ResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {

    @Override
    public List<ResourceDTO> resourceList(ConditionVo conditionVo) {
        // 查询资源列表
        List<Resource> resourceList = baseMapper.selectList(new LambdaQueryWrapper<Resource>()
                .like(StringUtils.isNotBlank(conditionVo.getKeywords()), Resource::getResourceName, conditionVo.getKeywords()));

        // 获取所有模块
        List<Resource> parentList = resourceModuleList(resourceList);

        // 获取模块下的资源
        Map<Integer, List<Resource>> childrenMap = resourceChildrenList(resourceList);

        // 绑定模块下的所有接口
        List<ResourceDTO> resourceDTOList = parentList.stream().map(item -> {
            ResourceDTO resourceDTO = BeanCopyUtils.copyObject(item, ResourceDTO.class);
            List<ResourceDTO> childrenList = BeanCopyUtils.copyList(childrenMap.get(item.getId()), ResourceDTO.class);
            resourceDTO.setChildren(childrenList);
            childrenMap.remove(item.getId());
            return resourceDTO;
        }).collect(Collectors.toList());
        // 若还有资源未取出则拼接
        if (CollectionUtils.isNotEmpty(childrenMap)) {
            List<Resource> childrenList = new ArrayList<>();
            childrenMap.values().forEach(childrenList::addAll);
            List<ResourceDTO> childrenDTOList = childrenList.stream()
                    .map(item -> BeanCopyUtils.copyObject(item, ResourceDTO.class))
                    .collect(Collectors.toList());
            resourceDTOList.addAll(childrenDTOList);
        }
        return resourceDTOList;
    }

    @Override
    public List<LabelOptionDTO> menuOptionsList() {
        // 查询资源列表
        List<Resource> resourceList = baseMapper.selectList(new LambdaQueryWrapper<Resource>()
                .select(Resource::getId, Resource::getResourceName, Resource::getParentId)
                .eq(Resource::getAnonymous, 0));
        // 获取所有模块
        List<Resource> parentList = resourceModuleList(resourceList);
        // 根据父id分组获取模块下的资源
        Map<Integer, List<Resource>> childrenMap = resourceChildrenList(resourceList);
        // 组装父子数据
        return parentList.stream().map(item -> {
            List<LabelOptionDTO> list = new ArrayList<>();
            List<Resource> children = childrenMap.get(item.getId());
            if (CollectionUtils.isNotEmpty(children)) {
                list = children.stream()
                        .map(resource -> LabelOptionDTO.builder()
                                .id(resource.getId())
                                .label(resource.getResourceName())
                                .build())
                        .collect(Collectors.toList());
            }
            return LabelOptionDTO.builder()
                    .id(item.getId())
                    .label(item.getResourceName())
                    .children(list)
                    .build();
        }).collect(Collectors.toList());
    }

    private Map<Integer, List<Resource>> resourceChildrenList(List<Resource> parentList) {
        return parentList.stream()
                .filter(item -> Objects.nonNull(item.getParentId()))
                .collect(Collectors.groupingBy(Resource::getParentId));
    }

    private List<Resource> resourceModuleList(List<Resource> resourceList) {
        return resourceList.stream()
                .filter(item -> Objects.isNull(item.getParentId()))
                .collect(Collectors.toList());
    }


}
