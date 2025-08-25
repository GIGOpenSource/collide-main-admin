package com.gig.collide.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gig.collide.domain.category.Category;
import com.gig.collide.dto.categoryDto.CategoryDTO;
import com.gig.collide.dto.categoryDto.CategoryQueryRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 分类Mapper接口
 * 
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
    
    /**
     * 分页查询分类列表（支持条件筛选）
     * 
     * @param page 分页对象
     * @param request 查询条件
     * @return 分页结果
     */
    IPage<CategoryDTO> selectCategoryList(IPage<CategoryDTO> page, @Param("req") CategoryQueryRequest request);
    
    /**
     * 查询分类总数
     * 
     * @param request 查询条件
     * @return 总数
     */
    Long selectCategoryCount(@Param("req") CategoryQueryRequest request);
    
    /**
     * 分页查询分类列表（带LIMIT）
     * 
     * @param request 查询条件
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 分类列表
     */
    List<CategoryDTO> selectCategoryListWithLimit(@Param("req") CategoryQueryRequest request, 
                                                 @Param("offset") long offset, 
                                                 @Param("limit") long limit);
    
    /**
     * 根据ID查询分类详情
     * 
     * @param id 分类ID
     * @return 分类详情
     */
    CategoryDTO selectCategoryById(@Param("id") Long id);
    
    /**
     * 检查分类名称是否已存在（排除指定ID）
     * 
     * @param name 分类名称
     * @param parentId 父分类ID
     * @param excludeId 排除的分类ID（编辑时使用）
     * @return 存在数量
     */
    int checkCategoryNameExists(@Param("name") String name, @Param("parentId") Long parentId, @Param("excludeId") Long excludeId);
    
    /**
     * 检查分类是否存在且未删除
     * 
     * @param id 分类ID
     * @return 存在数量
     */
    int checkCategoryExists(@Param("id") Long id);
    
    /**
     * 检查分类下是否有子分类
     * 
     * @param parentId 父分类ID
     * @return 子分类数量
     */
    int countSubCategories(@Param("parentId") Long parentId);
    
    /**
     * 检查分类下是否有内容
     * 
     * @param categoryId 分类ID
     * @return 内容数量
     */
    int countContentByCategory(@Param("categoryId") Long categoryId);
    
    /**
     * 逻辑删除分类（将status置为delete）
     * 
     * @param id 分类ID
     * @return 影响行数
     */
    int deleteCategoryById(@Param("id") Long id);
    
    /**
     * 查询所有有效分类（用于构建树结构）
     * 
     * @return 分类列表
     */
    List<CategoryDTO> selectAllActiveCategories();
    
    /**
     * 根据父级ID计算分类级别
     * 
     * @param parentId 父分类ID
     * @return 分类级别
     */
    Integer calculateCategoryLevel(@Param("parentId") Long parentId);
    
    /**
     * 查询分类名称
     * 
     * @param id 分类ID
     * @return 分类名称
     */
    String selectCategoryNameById(@Param("id") Long id);
    
    /**
     * 查询分类的父分类ID
     * 
     * @param id 分类ID
     * @return 父分类ID
     */
    Long selectParentIdById(@Param("id") Long id);
}
