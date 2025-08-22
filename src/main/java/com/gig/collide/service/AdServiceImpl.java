package com.gig.collide.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gig.collide.domain.ad.Ad;
import com.gig.collide.dto.adDto.AdCreateRequest;
import com.gig.collide.dto.adDto.AdDTO;
import com.gig.collide.dto.adDto.AdQueryRequest;
import com.gig.collide.dto.adDto.AdUpdateRequest;
import com.gig.collide.mapper.AdMapper;
import com.gig.collide.service.AdService;
import com.gig.collide.util.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdServiceImpl extends ServiceImpl<AdMapper, Ad> implements AdService {

    @Autowired
    private AdMapper adMapper;

    @Override
    public List<AdDTO> queryAllAds() {
        QueryWrapper<Ad> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort_order", "create_time");

        List<Ad> ads = adMapper.selectList(queryWrapper);

        return ads.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AdDTO queryAdById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("广告ID无效");
        }

        Ad ad = adMapper.selectById(id);
        if (ad == null) {
            return null;
        }

        return convertToDTO(ad);
    }

    @Override
    public PageResult<AdDTO> queryAdsByPage(AdQueryRequest request) {
        // 设置默认分页参数
        int page = request.getPage() != null ? request.getPage() : 1;
        int size = request.getSize() != null ? request.getSize() : 10;

        // 构建查询条件
        QueryWrapper<Ad> queryWrapper = buildQueryWrapper(request);
        queryWrapper.orderByAsc("sort_order", "create_time");

        // 执行分页查询
        Page<Ad> pageParam = new Page<>(page, size);
        Page<Ad> result = adMapper.selectPage(pageParam, queryWrapper);

        // 转换为DTO
        List<AdDTO> adDTOs = result.getRecords().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        // 构建分页结果
        return new PageResult<>(
                adDTOs,
                result.getTotal(),
                Long.valueOf(page),
                Long.valueOf(size)
        );
    }

    @Override
    @Transactional
    public boolean createAd(AdCreateRequest request) {
        // 验证请求参数
        validateCreateRequest(request);

        // 创建广告实体
        Ad ad = new Ad();
        BeanUtils.copyProperties(request, ad);

        // 设置时间字段
        LocalDateTime now = LocalDateTime.now();
        ad.setCreateTime(now);
        ad.setUpdateTime(now);

        // 设置默认值
        if (ad.getSortOrder() == null) {
            ad.setSortOrder(0L);
        }

        // 保存到数据库
        return adMapper.insert(ad) > 0;
    }

    @Override
    @Transactional
    public boolean updateAd(AdUpdateRequest request) {
        // 验证请求参数
        validateUpdateRequest(request);

        // 检查广告是否存在
        Ad existingAd = adMapper.selectById(request.getId());
        if (existingAd == null) {
            return false;
        }

        // 更新广告实体
        Ad ad = new Ad();
        BeanUtils.copyProperties(request, ad);
        ad.setUpdateTime(LocalDateTime.now());

        // 更新到数据库
        return adMapper.updateById(ad) > 0;
    }

    @Override
    @Transactional
    public boolean deleteAd(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("广告ID无效");
        }

        // 检查广告是否存在
        Ad existingAd = adMapper.selectById(id);
        if (existingAd == null) {
            return false;
        }

        // 删除广告
        return adMapper.deleteById(id) > 0;
    }

    @Override
    @Transactional
    public boolean batchDeleteAds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new IllegalArgumentException("广告ID列表不能为空");
        }

        // 批量删除广告
        return adMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    @Transactional
    public boolean updateAdStatus(Long id, Boolean isActive) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("广告ID无效");
        }

        if (isActive == null) {
            throw new IllegalArgumentException("广告状态不能为空");
        }

        // 检查广告是否存在
        Ad existingAd = adMapper.selectById(id);
        if (existingAd == null) {
            return false;
        }

        // 更新状态
        UpdateWrapper<Ad> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id)
                .set("is_active", isActive)
                .set("update_time", LocalDateTime.now());

        return adMapper.update(null, updateWrapper) > 0;
    }

    /**
     * 构建查询条件
     */
    private QueryWrapper<Ad> buildQueryWrapper(AdQueryRequest request) {
        QueryWrapper<Ad> queryWrapper = new QueryWrapper<>();

        // 广告名称模糊查询
        if (StringUtils.hasText(request.getAdName())) {
            queryWrapper.like("ad_name", request.getAdName());
        }

        // 广告标题模糊查询
        if (StringUtils.hasText(request.getAdTitle())) {
            queryWrapper.like("ad_title", request.getAdTitle());
        }

        // 广告类型精确查询
        if (StringUtils.hasText(request.getAdType())) {
            queryWrapper.eq("ad_type", request.getAdType());
        }

        // 是否启用查询
        if (request.getIsActive() != null) {
            queryWrapper.eq("is_active", request.getIsActive());
        }

        // 排序权重范围查询
        if (request.getMinSortOrder() != null) {
            queryWrapper.ge("sort_order", request.getMinSortOrder());
        }
        if (request.getMaxSortOrder() != null) {
            queryWrapper.le("sort_order", request.getMaxSortOrder());
        }

        return queryWrapper;
    }

    /**
     * 验证创建请求参数
     */
    private void validateCreateRequest(AdCreateRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("创建请求不能为空");
        }
        if (!StringUtils.hasText(request.getAdName())) {
            throw new IllegalArgumentException("广告名称不能为空");
        }
        if (!StringUtils.hasText(request.getAdTitle())) {
            throw new IllegalArgumentException("广告标题不能为空");
        }
        if (!StringUtils.hasText(request.getAdType())) {
            throw new IllegalArgumentException("广告类型不能为空");
        }
        if (!StringUtils.hasText(request.getImageUrl())) {
            throw new IllegalArgumentException("广告图片URL不能为空");
        }
        if (request.getIsActive() == null) {
            throw new IllegalArgumentException("是否启用不能为空");
        }
    }

    /**
     * 验证更新请求参数
     */
    private void validateUpdateRequest(AdUpdateRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("更新请求不能为空");
        }
        if (request.getId() == null || request.getId() <= 0) {
            throw new IllegalArgumentException("广告ID无效");
        }
    }

    /**
     * 转换为DTO
     */
    private AdDTO convertToDTO(Ad ad) {
        if (ad == null) {
            return null;
        }

        AdDTO dto = new AdDTO();
        BeanUtils.copyProperties(ad, dto);
        return dto;
    }
}
