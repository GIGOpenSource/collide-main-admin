package com.gig.collide.service.Impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gig.collide.domain.blo.Blo;
import com.gig.collide.dto.bloDto.BloDTO;
import com.gig.collide.dto.bloDto.BloQueryRequest;
import com.gig.collide.dto.bloDto.BloCreateRequest;
import com.gig.collide.dto.bloDto.BloUpdateRequest;
import com.gig.collide.dto.bloDto.CrawlerBloDTO;
import com.gig.collide.dto.bloDto.CrawlerBloQueryRequest;
import com.gig.collide.dto.bloDto.CrawlerBloCreateRequest;
import com.gig.collide.dto.bloDto.CrawlerBloUpdateRequest;
import com.gig.collide.util.PageResult;

/**
 * 博主信息服务接口
 */
public interface BloService extends IService<Blo> {

    /**
     * 查询博主信息（支持条件查询和分页）
     * @param request 查询条件
     * @return 分页结果
     */
    PageResult<BloDTO> queryBlos(BloQueryRequest request);

    /**
     * 创建博主
     * @param request 创建请求
     * @return 是否成功
     */
    boolean createBlo(BloCreateRequest request);

    /**
     * 更新博主信息
     * @param request 更新请求
     * @return 是否成功
     */
    boolean updateBlo(BloUpdateRequest request);

    /**
     * 软删除博主
     * @param id 博主ID
     * @return 是否成功
     */
    boolean deleteBlo(Long id);

    // ========== 爬虫列表管理接口 ==========

    /**
     * 查询爬虫列表博主信息（支持条件查询和分页）
     * @param request 查询条件
     * @return 分页结果
     */
    PageResult<CrawlerBloDTO> queryCrawlerBlos(CrawlerBloQueryRequest request);

    /**
     * 创建爬虫列表博主
     * @param request 创建请求
     * @return 是否成功
     */
    boolean createCrawlerBlo(CrawlerBloCreateRequest request);

    /**
     * 更新爬虫列表博主信息
     * @param request 更新请求
     * @return 是否成功
     */
    boolean updateCrawlerBlo(CrawlerBloUpdateRequest request);

    /**
     * 删除爬虫列表博主
     * @param id 博主ID
     * @return 是否成功
     */
    boolean deleteCrawlerBlo(Long id);
}
