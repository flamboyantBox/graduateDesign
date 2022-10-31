package com.feng.controller;


import com.feng.common.result.R;
import com.feng.pojo.dto.WebSiteNavDTO;
import com.feng.pojo.entity.WebsiteNav;
import com.feng.pojo.vo.ConditionVo;
import com.feng.service.WebsiteNavService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站导航 前端控制器
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
@RestController
@RequestMapping("/websiteNav/core")
public class WebsiteNavController {
    @Autowired
    private WebsiteNavService websiteNavService;

    @ApiOperation(value = "查看后台网站导航列表")
    @GetMapping("websiteNavList")
    public R getWebsiteNavList(ConditionVo conditionVo) {
        List<WebSiteNavDTO> websiteNavList = websiteNavService.getAdminSiteNavList(conditionVo);
        return R.ok().data("list", websiteNavList);
    }

    @ApiOperation(value = "添加或修改网站导航")
    @PostMapping("saveOrUpdateNav")
    public String saveOrUpdateWebsite(@RequestBody Map<String, Object> map) {
        return websiteNavService.saveOrUpdateSiteNav(map);
    }

    @ApiOperation(value = "删除网站导航")
    @DeleteMapping("deleteSiteNav")
    public R deleteSiteNav(@RequestBody List<Integer> siteNavId) {
        websiteNavService.removeByIds(siteNavId);
        return R.ok().message("删除成功");
    }
}

