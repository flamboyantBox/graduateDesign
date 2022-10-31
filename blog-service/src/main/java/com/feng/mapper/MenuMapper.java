package com.feng.mapper;

import com.feng.pojo.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.feng.pojo.entity.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
public interface MenuMapper extends BaseMapper<Menu> {
    Tag tag();

    List<Menu> listMenusByUserInfoId(@Param("userInfoId") Integer userInfoId);
}
