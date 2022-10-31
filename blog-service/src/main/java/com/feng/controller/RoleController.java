package com.feng.controller;


import com.feng.common.result.R;
import com.feng.pojo.dto.RoleDTO;
import com.feng.pojo.dto.UserRoleDTO;
import com.feng.pojo.vo.ConditionVo;
import com.feng.pojo.vo.RoleVo;
import com.feng.service.RoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
@RequestMapping("/role/core")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "查询角色列表")
    @GetMapping("roleList")
    public R listRoles(ConditionVo conditionVo) {
        List<RoleDTO> roleList = roleService.listRoles(conditionVo);
        return R.ok().data("roleList", roleList);
    }

    @ApiOperation(value = "保存或更新角色")
    @PostMapping("saveOrUpdateRole")
    public R saveOrUpdateRole(@RequestBody @Valid RoleVo roleVo) {
        roleService.saveOrUpdateRole(roleVo);
        return R.ok();
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("deleteRole")
    public R deleteRole(@RequestBody List<Integer> roleIdList) {
        roleService.deleteRole(roleIdList);
        return R.ok();
    }

    @ApiOperation(value = "禁用角色")
    @PutMapping("disableRole")
    public R disableRole(@RequestBody RoleVo roleVo) {
        roleService.disableRole(roleVo);
        return R.ok();
    }
}

