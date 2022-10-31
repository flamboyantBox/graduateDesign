package com.feng.mapper;

import com.feng.pojo.dto.ResourceRoleDTO;
import com.feng.pojo.dto.RoleDTO;
import com.feng.pojo.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.feng.pojo.vo.ConditionVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    List<RoleDTO> roleList(@Param("conditionVo") ConditionVo conditionVo);

    List<String> listRolesByUserInfoId(@Param("id") Integer id);

    List<ResourceRoleDTO> listResourceRoles();
}
