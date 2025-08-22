package com.gig.collide.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gig.collide.domain.blo.Blo;
import com.gig.collide.dto.bloDto.BloDTO;
import com.gig.collide.dto.bloDto.BloQueryRequest;
import com.gig.collide.dto.bloDto.CrawlerBloDTO;
import com.gig.collide.dto.bloDto.CrawlerBloQueryRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 博主信息Mapper接口
 */
@Mapper
public interface BloMapper extends BaseMapper<Blo> {

    /**
     * 查询博主信息（返回所有字段，支持条件查询和分页）
     * @param request 查询条件
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 博主列表
     */
    List<BloDTO> selectBlosWithAllFields(@Param("request") BloQueryRequest request, 
                                        @Param("offset") long offset, 
                                        @Param("limit") long limit);

    /**
     * 查询博主总数
     * @param request 查询条件
     * @return 博主总数
     */
    Long selectBloCount(@Param("request") BloQueryRequest request);

    /**
     * 根据ID查询单个博主（返回所有字段）
     * @param id 博主ID
     * @return 博主详情
     */
    BloDTO selectBloById(@Param("id") Long id);

    /**
     * 软删除博主
     * @param id 博主ID
     * @return 更新结果
     */
    int softDeleteBlo(@Param("id") Long id);

    // ========== 爬虫列表管理方法 ==========

    /**
     * 查询爬虫列表博主信息（返回所有字段，支持条件查询和分页）
     * @param request 查询条件
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 爬虫列表博主列表
     */
    List<CrawlerBloDTO> selectCrawlerBlosWithAllFields(@Param("request") CrawlerBloQueryRequest request, 
                                                       @Param("offset") long offset, 
                                                       @Param("limit") long limit);

    /**
     * 查询爬虫列表博主总数
     * @param request 查询条件
     * @return 博主总数
     */
    Long selectCrawlerBloCount(@Param("request") CrawlerBloQueryRequest request);

    /**
     * 根据ID查询单个爬虫列表博主（返回所有字段）
     * @param id 博主ID
     * @return 爬虫列表博主详情
     */
    CrawlerBloDTO selectCrawlerBloById(@Param("id") Long id);

    /**
     * 查询博主信息（分页）
     * @param request 查询条件
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 博主列表
     */
    List<BloDTO> selectBlosWithLimit(@Param("request") BloQueryRequest request, 
                                    @Param("offset") int offset, 
                                    @Param("limit") int limit);

    /**
     * 查询博主信息（不分页，返回所有符合条件的记录）
     * @param request 查询条件
     * @return 博主列表
     */
    List<BloDTO> selectBlosWithoutPagination(@Param("request") BloQueryRequest request);
}
