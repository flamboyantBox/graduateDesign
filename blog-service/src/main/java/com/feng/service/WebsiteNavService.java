package com.feng.service;

import com.feng.pojo.dto.WebSiteNavDTO;
import com.feng.pojo.entity.WebsiteNav;
import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.pojo.vo.ConditionVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站导航 服务类
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
public interface WebsiteNavService extends IService<WebsiteNav> {

    List<WebSiteNavDTO> getAdminSiteNavList(ConditionVo conditionVo);

    String saveOrUpdateSiteNav(Map<String, Object> map);
}
