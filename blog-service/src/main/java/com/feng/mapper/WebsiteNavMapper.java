package com.feng.mapper;

import com.feng.pojo.dto.WebSiteNavDTO;
import com.feng.pojo.entity.WebsiteNav;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.feng.pojo.vo.ConditionVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站导航 Mapper 接口
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
public interface WebsiteNavMapper extends BaseMapper<WebsiteNav> {

    List<WebSiteNavDTO> listWebSiteDTO(@Param("condition") ConditionVo conditionVo);

    Integer saveSiteNav(Map<String, Object> map);

    Integer updateSiteNav(Map<String, Object> map);
}
