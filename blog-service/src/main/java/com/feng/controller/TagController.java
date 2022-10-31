package com.feng.controller;


import com.feng.common.result.R;
import com.feng.pojo.dto.CategoryDTO;
import com.feng.pojo.dto.TagDTO;
import com.feng.pojo.entity.Tag;
import com.feng.pojo.vo.CategoryVo;
import com.feng.pojo.vo.ConditionVo;
import com.feng.pojo.vo.TagVo;
import com.feng.service.TagService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
@RestController
@RequestMapping("/tag/core")
public class TagController {
    @Autowired
    private TagService tagService;

    @ApiOperation("查看后台标签列表")
    @GetMapping("tagList")
    public R tagList(){
        List<TagDTO> tagList = tagService.listTags();
        return R.ok().data("list", tagList);
    }

    @ApiOperation(value = "搜索表标签分类")
    @PostMapping("search")
    public R search(@RequestBody ConditionVo conditionVo) {
        List<TagDTO> searchList = tagService.listTagBySearch(conditionVo);
        return R.ok().data("searchList", searchList);
    }

    @ApiOperation("添加或修改标签分类")
    @PostMapping("saveOrUpdate")
    public R saveOrUpdate(@Valid @RequestBody TagVo tagVo){
        tagService.saveOrUpdateCategory(tagVo);
        return R.ok();
    }

    @ApiOperation("删除分类")
    @DeleteMapping("deleteTags")
    public R deleteTags(@RequestBody List<Integer> tagIdList) {
        tagService.deleteCategory(tagIdList);
        return R.ok();
    }
}

