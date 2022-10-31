package com.feng.controller;


import com.feng.common.result.R;
import com.feng.pojo.dto.CategoryDTO;
import com.feng.pojo.entity.Category;
import com.feng.pojo.vo.CategoryVo;
import com.feng.pojo.vo.ConditionVo;
import com.feng.service.CategoryService;
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
@RequestMapping("/category/core")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @ApiOperation("查看后台分类列表")
    @GetMapping("categoryList")
    public R categoryList(){
        List<CategoryDTO> list = categoryService.listCategories();
        return R.ok().data("list", list);
    }

    @ApiOperation("添加或修改分类")
    @PostMapping("saveOrUpdate")
    public R saveOrUpdate(@Valid @RequestBody CategoryVo categoryVo){
        categoryService.saveOrUpdateCategory(categoryVo);
        return R.ok();
    }

    @ApiOperation("删除分类")
    @DeleteMapping("deleteCategories")
    public R deleteCategories(@RequestBody List<Integer> categoryIdList) {
        categoryService.deleteCategory(categoryIdList);
        return R.ok();
    }

    @ApiOperation(value = "搜索文章分类")
    @PostMapping("search")
    public R search(@RequestBody ConditionVo conditionVo) {
        List<CategoryDTO> searchList = categoryService.listCategoriesBySearch(conditionVo);
        return R.ok().data("searchList", searchList);
    }
}

