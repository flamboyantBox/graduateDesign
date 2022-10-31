package com.feng.service;

import com.feng.pojo.dto.LabelOptionDTO;
import com.feng.pojo.dto.MenuDTO;
import com.feng.pojo.dto.UserMenuDTO;
import com.feng.pojo.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.pojo.vo.ConditionVo;
import com.feng.pojo.vo.MenuVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
public interface MenuService extends IService<Menu> {

    List<LabelOptionDTO> menuOptionsList();

    List<MenuDTO> menuList(ConditionVo conditionVo);

    List<UserMenuDTO> listUserMenus(Integer userId);

    void saveOrUpdateMenu(MenuVo menuVO);

    void deleteMenu(Integer menuId);
}
