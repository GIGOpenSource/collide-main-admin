package com.gig.collide.service;

import com.gig.collide.dto.adDto.AdCreateRequest;
import com.gig.collide.dto.adDto.AdDTO;
import com.gig.collide.dto.adDto.AdQueryRequest;
import com.gig.collide.dto.adDto.AdUpdateRequest;
import com.gig.collide.util.PageResult;

import java.util.List;

public interface AdService {

    /**
     * 查询所有广告
     */
    List<AdDTO> queryAllAds();

    /**
     * 根据ID查询广告
     */
    AdDTO queryAdById(Long id);

    /**
     * 分页查询广告
     */
    PageResult<AdDTO> queryAdsByPage(AdQueryRequest request);

    /**
     * 新增广告
     */
    boolean createAd(AdCreateRequest request);

    /**
     * 修改广告
     */
    boolean updateAd(AdUpdateRequest request);

    /**
     * 删除广告
     */
    boolean deleteAd(Long id);

    /**
     * 批量删除广告
     */
    boolean batchDeleteAds(List<Long> ids);

    /**
     * 更新广告状态
     */
    boolean updateAdStatus(Long id, Boolean isActive);
}
