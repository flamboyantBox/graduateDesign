package com.feng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.feng.common.exception.Assert;
import com.feng.common.exception.BlogException;
import com.feng.common.result.ResponseEnum;
import com.feng.common.util.BeanCopyUtils;
import com.feng.common.util.RedisUtils;
import com.feng.pojo.dto.*;
import com.feng.pojo.entity.Menu;
import com.feng.mapper.MenuMapper;
import com.feng.pojo.entity.RoleMenu;
import com.feng.pojo.vo.ConditionVo;
import com.feng.pojo.vo.MenuVo;
import com.feng.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.service.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<LabelOptionDTO> menuOptionsList() {
        // 查询菜单数据
        List<Menu> menuList = this.list(new LambdaQueryWrapper<Menu>()
                .select(Menu::getId, Menu::getName, Menu::getParentId, Menu::getOrderNum));

        // 获取目录列表
        List<Menu> catalogList = listCatalog(menuList);

        // 获取目录下的子菜单
        Map<Integer, List<Menu>> childrenMap = getMenuMap(menuList);

        // 组装目录菜单数据
        return catalogList.stream().map(item -> {
            // 封装的DTO数据
            List<LabelOptionDTO> collect = new ArrayList<>();

            // 获取目录下的菜单排序
            List<Menu> menuChildrenMap = childrenMap.get(item.getId());

            // 获取目录下菜单的列表
            if (CollectionUtils.isNotEmpty(menuChildrenMap)){
                collect = menuChildrenMap.stream()
                        .sorted(Comparator.comparing(Menu::getOrderNum))
                        .map(menu -> LabelOptionDTO.builder()
                                .id(menu.getId())
                                .label(menu.getName())
                                .build())
                        .collect(Collectors.toList());
            }

            return LabelOptionDTO.builder()
                    .id(item.getId())
                    .label(item.getName())
                    .children(collect)
                    .build();

        }).collect(Collectors.toList());
    }

    @Override
    public List<MenuDTO> menuList(ConditionVo conditionVo) {
        // 查询菜单数据
        List<Menu> menuList = baseMapper.selectList(new LambdaQueryWrapper<Menu>()
                .like(StringUtils.isNotBlank(conditionVo.getKeywords()), Menu::getName, conditionVo.getKeywords()));

        // 获取目录列表
        List<Menu> catalogMenu = listCatalog(menuList);

        // 获取目录下的子菜单
        Map<Integer, List<Menu>> childrenMenu = getMenuMap(menuList);

        // 组装目录菜单数
        List<MenuDTO> menuDTOS = catalogMenu.stream().map(item -> {
            MenuDTO menuDTO = BeanCopyUtils.copyObject(item, MenuDTO.class);
            // 获取目录下的菜单排序
            List<MenuDTO> menuDTOList = BeanCopyUtils.copyList(childrenMenu.get(item.getId()), MenuDTO.class).stream()
                    .sorted(Comparator.comparing(MenuDTO::getOrderNum))
                    .collect(Collectors.toList());
            menuDTO.setChildren(menuDTOList);
            childrenMenu.remove(item.getId());
            return menuDTO;
        }).sorted(Comparator.comparing(MenuDTO::getOrderNum)).collect(Collectors.toList());

        // 封装没有目录菜单的子菜单列表
        if (CollectionUtils.isNotEmpty(childrenMenu)) {
            List<Menu> childrenList = new ArrayList<>();
            childrenMenu.values().forEach(childrenList::addAll);
            List<MenuDTO> childrenDTOList = childrenList.stream()
                    .map(item -> BeanCopyUtils.copyObject(item, MenuDTO.class))
                    .sorted(Comparator.comparing(MenuDTO::getOrderNum))
                    .collect(Collectors.toList());
            menuDTOS.addAll(childrenDTOList);
        }

        return menuDTOS;
    }

    @Override
    public List<UserMenuDTO> listUserMenus(Integer userId) {
        // 查询用户菜单信息
        UserInfoDTO userInfoDTO  = (UserInfoDTO) redisUtils.get(userId.toString());
        if (userInfoDTO == null){
            throw new BlogException(ResponseEnum.LOGOUT_USER_ERROR.getMessage());
        }
        Integer userInfoId = userInfoDTO.getUserInfoId();

        List<Menu> menuList = baseMapper.listMenusByUserInfoId(userInfoId);
        // 获取目录列表
        List<Menu> catalogList = listCatalog(menuList);

        // 获取目录下的子菜单
        Map<Integer, List<Menu>> childrenMap = getMenuMap(menuList);

        // 转换前端菜单格式
        return convertUserMenuList(catalogList, childrenMap);
    }

    @Override
    public void saveOrUpdateMenu(MenuVo menuVo) {
        Menu menu = BeanCopyUtils.copyObject(menuVo, Menu.class);
        this.saveOrUpdate(menu);
    }

    @Override
    public void deleteMenu(Integer menuId) {
        // 查询是否有角色关联
        int count = roleMenuService.count(new LambdaQueryWrapper<RoleMenu>()
                .eq(RoleMenu::getMenuId, menuId));
        Assert.ltValue(count, 0, ResponseEnum.ROLE_MENU_ERROR);

        // 查询子菜单
        List<Integer> menuIdList = baseMapper.selectList(new LambdaQueryWrapper<Menu>()
                .select(Menu::getId)
                .eq(Menu::getParentId, menuId))
                .stream()
                .map(Menu::getId)
                .collect(Collectors.toList());
        menuIdList.add(menuId);
        baseMapper.deleteBatchIds(menuIdList);
    }

    private List<UserMenuDTO> convertUserMenuList(List<Menu> catalogList, Map<Integer, List<Menu>> childrenMap) {
        return catalogList.stream().map(item -> {
            // 获取目录
            UserMenuDTO userMenuDTO = new UserMenuDTO();
            List<UserMenuDTO> userMenuDTOList = new ArrayList<>();

            // 获取子菜单数据
            List<Menu> children = childrenMap.get(item.getId());

            if (CollectionUtils.isNotEmpty(children)){
                // 处理多级菜单
                userMenuDTO = BeanCopyUtils.copyObject(item, UserMenuDTO.class);

                // 处理当前目录的子菜单
                userMenuDTOList = children.stream()
                        .sorted(Comparator.comparing(Menu::getOrderNum))
                        .map(menu -> BeanCopyUtils.copyObject(menu, UserMenuDTO.class))
                        .collect(Collectors.toList());
            }else {
                // 一级菜单处理
                userMenuDTO.setPath(item.getPath());
                userMenuDTO.setComponent("Layout");
                userMenuDTOList.add(UserMenuDTO.builder()
                        .path("")
                        .name(item.getName())
                        .icon(item.getIcon())
                        .component(item.getComponent())
                        .build());
            }
            userMenuDTO.setChildren(userMenuDTOList);
            return userMenuDTO;
        }).collect(Collectors.toList());
    }

    private List<Menu> listCatalog(List<Menu> menuList) {
        return menuList.stream()
                .filter(item -> Objects.isNull(item.getParentId()))
                .sorted(Comparator.comparing(Menu::getOrderNum))
                .collect(Collectors.toList());
    }

    private Map<Integer, List<Menu>> getMenuMap(List<Menu> menuList) {
        return menuList.stream()
                .filter(item -> Objects.nonNull(item.getParentId()))
                .collect(Collectors.groupingBy(Menu::getParentId));
    }
}
