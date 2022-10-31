package com.feng.service;

import com.feng.pojo.entity.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.pojo.vo.PageVo;

import java.util.List;

/**
 * <p>
 * 页面 服务类
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
public interface PageService extends IService<Page> {

    List<PageVo> listPages();

    void deletePage(Integer pageId);

    void saveOrUpdatePage(PageVo pageVo);
}
