package com.feng.service;

import com.feng.pojo.dto.CategoryDTO;
import com.feng.pojo.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.pojo.vo.CategoryVo;
import com.feng.pojo.vo.ConditionVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
public interface CategoryService extends IService<Category> {
    List<CategoryDTO> listCategories();

    void saveOrUpdateCategory(CategoryVo categoryVo);

    void deleteCategory(List<Integer> categoryIdList);

    List<CategoryDTO> listCategoriesBySearch(ConditionVo conditionVo);
}
