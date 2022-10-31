package com.feng.service.impl;

import com.alibaba.fastjson.JSON;
import com.feng.pojo.dto.WebSiteNavDTO;
import com.feng.pojo.entity.WebsiteNav;
import com.feng.mapper.WebsiteNavMapper;
import com.feng.pojo.vo.ConditionVo;
import com.feng.service.WebsiteNavService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站导航 服务实现类
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
@Service
public class WebsiteNavServiceImpl extends ServiceImpl<WebsiteNavMapper, WebsiteNav> implements WebsiteNavService {

    @Override
    public List<WebSiteNavDTO> getAdminSiteNavList(ConditionVo conditionVo) {
        return baseMapper.listWebSiteDTO(conditionVo);
    }

    @Override
    public String saveOrUpdateSiteNav(Map<String, Object> map) {
        Map<String,Object> reqMap = new HashMap<>();
        try {
            if (map.get("submitType").equals("create")) {
                if (baseMapper.saveSiteNav(map).equals(1)) {
                    reqMap.put("code",200);
                    reqMap.put("message","新增网站成功！");
                } else {
                    reqMap.put("code",500);
                    reqMap.put("message","新增网站失败");
                }
            }
            if (map.get("submitType").equals("update")) {
                if (baseMapper.updateSiteNav(map).equals(1)) {
                    reqMap.put("code",200);
                    reqMap.put("message","修改网站成功！");
                } else {
                    reqMap.put("code",500);
                    reqMap.put("message","修改网站失败");
                }
            }
            return JSON.toJSONString(reqMap);
        } catch (Exception e) {
            System.out.println("操作失败=>"+e);
            return null;
        }
    }
}
