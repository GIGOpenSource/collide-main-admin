package com.gig.collide.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gig.collide.domain.blo.BloPython;
import com.gig.collide.dto.bloPythonDto.BloPythonDTO;
import com.gig.collide.dto.bloPythonDto.BloPythonQueryRequest;
import com.gig.collide.dto.bloPythonDto.BloPythonCreateRequest;
import com.gig.collide.dto.bloPythonDto.BloPythonUpdateRequest;
import com.gig.collide.mapper.BloPythonMapper;
import com.gig.collide.util.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Python爬虫博主信息服务实现类
 * 
 * @author why
 * @since 2025-01-27
 * @version 1.0
 */
@Slf4j
@Service
public class BloPythonServiceImpl extends ServiceImpl<BloPythonMapper, BloPython> implements BloPythonService {

    @Override
    public PageResult<BloPythonDTO> queryBloPythons(BloPythonQueryRequest request) {
        log.info("查询Python爬虫博主信息，请求参数：{}", request);

        // 设置默认分页参数
        request.setDefaultPagination();

        // 构建查询条件
        LambdaQueryWrapper<BloPython> queryWrapper = new LambdaQueryWrapper<>();
        
        // 博主UID条件
        if (request.getBloggerUid() != null) {
            queryWrapper.eq(BloPython::getBloggerUid, request.getBloggerUid());
        }
        
        // 状态条件
        if (StringUtils.hasText(request.getStatus())) {
            queryWrapper.eq(BloPython::getStatus, request.getStatus());
        }
        
        // 按创建时间倒序
        queryWrapper.orderByDesc(BloPython::getCreateTime);

        // 分页查询
        Page<BloPython> page = new Page<>(request.getPage(), request.getSize());
        IPage<BloPython> result = this.page(page, queryWrapper);

        // 转换为DTO
        List<BloPythonDTO> dtoList = result.getRecords().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        // 构建分页结果
        PageResult<BloPythonDTO> pageResult = new PageResult<>(dtoList, result.getTotal(), Long.valueOf(request.getPage()), Long.valueOf(request.getSize()));

        log.info("查询Python爬虫博主信息完成，总数：{}，当前页：{}，每页大小：{}",
                pageResult.getTotal(), pageResult.getCurrent(), pageResult.getSize());

        return pageResult;
    }

    @Override
    public boolean createBloPython(BloPythonCreateRequest request) {
        log.info("创建Python爬虫博主，请求参数：{}", request);

        // 检查博主UID是否已存在
        LambdaQueryWrapper<BloPython> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BloPython::getBloggerUid, request.getBloggerUid());
        if (this.count(queryWrapper) > 0) {
            log.warn("博主UID已存在：{}", request.getBloggerUid());
            return false;
        }

        // 创建实体
        BloPython bloPython = new BloPython();
        bloPython.setBloggerUid(request.getBloggerUid());
        bloPython.setBloggerNickname(request.getBloggerNickname());
        bloPython.setHomepageUrl(request.getHomepageUrl());
        bloPython.setStatus("not_updated"); // 默认状态
        bloPython.setCreateTime(LocalDateTime.now());
        bloPython.setUpdateTime(LocalDateTime.now());

        boolean success = this.save(bloPython);
        
        if (success) {
            log.info("创建Python爬虫博主成功，ID：{}", bloPython.getId());
        } else {
            log.error("创建Python爬虫博主失败");
        }

        return success;
    }

    @Override
    public boolean updateBloPython(BloPythonUpdateRequest request) {
        log.info("更新Python爬虫博主信息，请求参数：{}", request);

        // 检查记录是否存在
        BloPython existingBloPython = this.getById(request.getId());
        if (existingBloPython == null) {
            log.warn("Python爬虫博主记录不存在，ID：{}", request.getId());
            return false;
        }

        // 检查博主UID是否已被其他记录使用
        LambdaQueryWrapper<BloPython> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BloPython::getBloggerUid, request.getBloggerUid())
                   .ne(BloPython::getId, request.getId());
        if (this.count(queryWrapper) > 0) {
            log.warn("博主UID已被其他记录使用：{}", request.getBloggerUid());
            return false;
        }

        // 更新实体
        BloPython bloPython = new BloPython();
        bloPython.setId(request.getId());
        bloPython.setBloggerUid(request.getBloggerUid());
        bloPython.setBloggerNickname(request.getBloggerNickname());
        bloPython.setHomepageUrl(request.getHomepageUrl());
        bloPython.setUpdateTime(LocalDateTime.now());

        boolean success = this.updateById(bloPython);
        
        if (success) {
            log.info("更新Python爬虫博主信息成功，ID：{}", request.getId());
        } else {
            log.error("更新Python爬虫博主信息失败，ID：{}", request.getId());
        }

        return success;
    }

    @Override
    public boolean deleteBloPython(Long id) {
        log.info("删除Python爬虫博主，ID：{}", id);

        // 检查记录是否存在
        BloPython existingBloPython = this.getById(id);
        if (existingBloPython == null) {
            log.warn("Python爬虫博主记录不存在，ID：{}", id);
            return false;
        }

        boolean success = this.removeById(id);
        
        if (success) {
            log.info("删除Python爬虫博主成功，ID：{}", id);
        } else {
            log.error("删除Python爬虫博主失败，ID：{}", id);
        }

        return success;
    }

    /**
     * 转换为DTO
     */
    private BloPythonDTO convertToDTO(BloPython bloPython) {
        BloPythonDTO dto = new BloPythonDTO();
        BeanUtils.copyProperties(bloPython, dto);
        return dto;
    }
}
