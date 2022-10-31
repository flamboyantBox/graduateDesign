package com.feng.controller;


import com.feng.common.result.R;
import com.feng.pojo.vo.PageVo;
import com.feng.service.PageService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 页面 前端控制器
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
@RestController
@RequestMapping("/page/core")
public class PageController {
    @Autowired
    private PageService pageService;

    @ApiOperation(value = "获取页面列表")
    @GetMapping("pageList")
    public R listPages() {
        List<PageVo> pageVoList = pageService.listPages();
        return R.ok().data("list", pageVoList);
    }

    @ApiImplicitParam(name = "pageId", value = "页面id", required = true, dataType = "Integer")
    @DeleteMapping("removePage/{pageId}")
    public R deletePage(@PathVariable("pageId") Integer pageId) {
        pageService.deletePage(pageId);
        return R.ok();
    }

    @ApiOperation(value = "保存或更新页面")
    @PostMapping("saveOrUpdatePage")
    public R saveOrUpdatePage(@Valid @RequestBody PageVo pageVo) {
        pageService.saveOrUpdatePage(pageVo);
        return R.ok();
    }
}

