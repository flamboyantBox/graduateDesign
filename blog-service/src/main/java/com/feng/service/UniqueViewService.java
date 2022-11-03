package com.feng.service;

import com.feng.pojo.dto.UniqueViewDTO;
import com.feng.pojo.entity.UniqueView;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
public interface UniqueViewService extends IService<UniqueView> {

    List<UniqueViewDTO> listUniqueViews();
}
