package com.feng.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.feng.common.result.R;
import com.feng.pojo.dto.WebSiteNavDTO;
import com.feng.pojo.entity.WebsiteNav;
import com.feng.pojo.entity.WebsiteTags;
import com.feng.pojo.vo.ConditionVo;
import com.feng.service.WebsiteNavService;
import com.feng.service.WebsiteTagsService;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 网站标签 前端控制器
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
@RestController
@RequestMapping("/websiteTags/core")
public class WebsiteTagsController {
    @Autowired
    private WebsiteTagsService websiteTagsService;

    @Autowired
    private WebsiteNavService websiteNavService;

    @ApiOperation(value = "查看后台网站标签列表")
    @GetMapping("websiteTagList")
    public R getWebsiteNavList() {
        List<WebsiteTags> websiteTagList = websiteTagsService.getWebsiteTagList();
        return R.ok().data("list", websiteTagList);
    }

    @ApiOperation(value = "添加或修改网站导航标签")
    @PostMapping("saveOrUpdateSiteNavTags")
    public R saveOrUpdateSiteNavTags(@RequestBody WebsiteTags websiteTags) {
        String message = websiteTagsService.saveOrUpdateSiteNavTags(websiteTags);
        return R.ok().message(message);
    }

    @ApiOperation(value = "删除网站导航标签")
    @DeleteMapping("deleteSiteTags")
    public R deleteSiteTags(@RequestBody List<Integer> siteTagsList) {
        // 判断该导航网站标签下是否有网站
        int count = websiteNavService.count(new LambdaQueryWrapper<WebsiteNav>()
                .in(WebsiteNav::getTagsId,siteTagsList));
        if (count > 0) {
            return R.ok().message("此标签下有导航网站，禁止删除！");
        } else {
            websiteTagsService.removeByIds(siteTagsList);
            return R.ok().message("删除成功");
        }

    }
}

