package com.feng.service;

import com.feng.pojo.dto.RoleDTO;
import com.feng.pojo.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.pojo.vo.ConditionVo;
import com.feng.pojo.vo.RoleVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
public interface RoleService extends IService<Role> {

    List<RoleDTO> listRoles(ConditionVo conditionVo);

    void saveOrUpdateRole(RoleVo roleVo);

    void deleteRole(List<Integer> roleIdList);

    void disableRole(RoleVo roleVo);
}
