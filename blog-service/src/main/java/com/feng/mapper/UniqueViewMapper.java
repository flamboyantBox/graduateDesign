package com.feng.mapper;

import cn.hutool.core.date.DateTime;
import com.feng.pojo.dto.UniqueViewDTO;
import com.feng.pojo.entity.UniqueView;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
public interface UniqueViewMapper extends BaseMapper<UniqueView> {

    List<UniqueViewDTO> listUniqueViews(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
