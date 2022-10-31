package com.feng.controller;


import com.feng.common.result.R;
import com.feng.pojo.dto.LabelOptionDTO;
import com.feng.pojo.dto.MenuDTO;
import com.feng.pojo.dto.UserDetailDTO;
import com.feng.pojo.dto.UserMenuDTO;
import com.feng.pojo.vo.ConditionVo;
import com.feng.pojo.vo.MenuVo;
import com.feng.service.MenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
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
@RequestMapping("/menu/core")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @ApiOperation(value = "查看菜单列表")
    @GetMapping("menuList")
    public R menuList(ConditionVo conditionVo) {
        List<MenuDTO> menuDTOList = menuService.menuList(conditionVo);
        return R.ok().data("menuDTOList", menuDTOList);
    }

    @ApiOperation(value = "查看当前用户菜单")
    @GetMapping("user/menuList")
    public R listUserMenus(@RequestParam("userId") Integer userId) {
        List<UserMenuDTO> menuDTOList = menuService.listUserMenus(userId);
        return R.ok().data("data", menuDTOList);
    }

    @ApiOperation(value = "新增或修改菜单")
    @PostMapping("saveOrUpdateMenu")
    public R saveOrUpdateMenu(@Valid @RequestBody MenuVo menuVO) {
        menuService.saveOrUpdateMenu(menuVO);
        return R.ok();
    }

    @ApiOperation(value = "删除菜单")
    @DeleteMapping("deleteMenu/{menuId}")
    public R deleteMenu(@PathVariable("menuId") Integer menuId){
        menuService.deleteMenu(menuId);
        return R.ok();
    }

    @ApiOperation(value = "查看角色菜单选项")
    @GetMapping("menuOptionsList")
    public R menuOptionsList() {
        List<LabelOptionDTO> labelOptionDTOList = menuService.menuOptionsList();
        return R.ok().data("labelOptionDTOList", labelOptionDTOList);
    }
}

