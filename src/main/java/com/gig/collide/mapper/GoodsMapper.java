package com.gig.collide.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gig.collide.domain.goods.Goods;
import com.gig.collide.dto.goodsDto.GoodsDTO;
import com.gig.collide.dto.goodsDto.GoodsQueryRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品信息Mapper接口
 * 
 * @author system
 * @since 2025-01-27
 * @version 1.0
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

    /**
     * 查询商品信息（返回所有字段，支持条件查询和分页）
     * @param request 查询条件
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 商品列表
     */
    List<GoodsDTO> selectGoodsWithAllFields(@Param("request") GoodsQueryRequest request, 
                                           @Param("offset") long offset, 
                                           @Param("limit") long limit);

    /**
     * 统计商品总数（支持条件查询）
     * @param request 查询条件
     * @return 总数
     */
    long countGoodsWithConditions(@Param("request") GoodsQueryRequest request);

    /**
     * 根据ID查询商品详情
     * @param id 商品ID
     * @return 商品详情
     */
    GoodsDTO selectGoodsById(@Param("id") Long id);

    /**
     * 更新商品上线下线状态
     * @param id 商品ID
     * @param isOnline 是否上线：Y-上线，N-下线
     * @return 影响行数
     */
    int updateOnlineStatus(@Param("id") Long id, @Param("isOnline") String isOnline);
}
