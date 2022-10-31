package com.feng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.feng.common.exception.BlogException;
import com.feng.pojo.dto.RoleDTO;
import com.feng.pojo.entity.*;
import com.feng.mapper.RoleMapper;
import com.feng.pojo.vo.ConditionVo;
import com.feng.pojo.vo.RoleVo;
import com.feng.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private RoleResourceService roleResourceService;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public List<RoleDTO> listRoles(ConditionVo conditionVo) {
        return baseMapper.roleList(conditionVo);
    }

    @Override
    public void saveOrUpdateRole(RoleVo roleVo) {
        // 判断角色名重复
        Role existRole = this.getOne(new LambdaQueryWrapper<Role>()
                .select(Role::getId)
                .eq(Role::getRoleName, roleVo.getRoleName()));

        if (Objects.nonNull(existRole) && !existRole.getId().equals(roleVo.getId())) {
            throw new BlogException("角色名已存在");
        }

        // 保存或更新角色信息
        Role role = new Role();
        BeanUtils.copyProperties(roleVo, role);

        this.saveOrUpdate(role);

        // 更新角色菜单关系
        if (CollectionUtils.isNotEmpty(roleVo.getMenuIdList())) {
            // 如果有RoleId则先清除所有的Role对应的Menu
            if (Objects.nonNull(roleVo.getId())) {
                roleMenuService.remove(new LambdaQueryWrapper<RoleMenu>()
                        .eq(RoleMenu::getRoleId, roleVo.getId()));
            }
            // 重新组合对应关系
            List<RoleMenu> roleMenuList = roleVo.getMenuIdList().stream().map(menuId -> RoleMenu.builder()
                    .roleId(role.getId())
                    .menuId(menuId)
                    .build())
                    .collect(Collectors.toList());

            roleMenuService.saveBatch(roleMenuList);
        }

        // 更新角色资源关系
        if (CollectionUtils.isNotEmpty(roleVo.getResourceIdList())) {
            // 如果有RoleId则先清除所有的Role对应的Resource
            if (Objects.nonNull(roleVo.getId())) {
                roleResourceService.remove(new LambdaQueryWrapper<RoleResource>()
                        .eq(RoleResource::getRoleId, roleVo.getId()));
            }

            // 重新组合对应关系
            List<RoleResource> roleResourceList = roleVo.getResourceIdList().stream().map(resourceId -> RoleResource.builder()
                    .roleId(role.getId())
                    .resourceId(resourceId)
                    .build())
                    .collect(Collectors.toList());

            roleResourceService.saveBatch(roleResourceList);
        }
    }

    @Override
    public void deleteRole(List<Integer> roleIdList) {
        // 判断角色下是否有用户
        int count = userRoleService.count(new LambdaQueryWrapper<UserRole>()
                .in(UserRole::getRoleId, roleIdList));

        if (count > 0){
            throw new BlogException("该角色下存在用户");
        }

        this.removeByIds(roleIdList);
    }

    @Override
    public void disableRole(RoleVo roleVo) {
        Role role = this.getById(roleVo.getId());
        role.setDisable(roleVo.getIsDisable());
        this.updateById(role);
    }
}
