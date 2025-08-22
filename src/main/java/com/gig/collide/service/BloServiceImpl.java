package com.gig.collide.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gig.collide.domain.blo.Blo;
import com.gig.collide.dto.bloDto.BloDTO;
import com.gig.collide.dto.bloDto.BloQueryRequest;
import com.gig.collide.dto.bloDto.BloCreateRequest;
import com.gig.collide.dto.bloDto.BloUpdateRequest;
import com.gig.collide.dto.bloDto.CrawlerBloDTO;
import com.gig.collide.dto.bloDto.CrawlerBloQueryRequest;
import com.gig.collide.dto.bloDto.CrawlerBloCreateRequest;
import com.gig.collide.dto.bloDto.CrawlerBloUpdateRequest;
import com.gig.collide.mapper.BloMapper;
import com.gig.collide.service.BloService;
import com.gig.collide.util.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import com.gig.collide.util.PageUtil;
import com.gig.collide.util.ValidationUtil;
import com.gig.collide.util.ExceptionUtil;
import java.util.stream.Collectors;

/**
 * 博主信息服务实现类
 */
@Slf4j
@Service
public class BloServiceImpl extends ServiceImpl<BloMapper, Blo> implements BloService {

    @Autowired
    private BloMapper bloMapper;

    @Override
    public PageResult<BloDTO> queryBlos(BloQueryRequest request) {
        try {
            log.debug("开始查询博主信息，请求参数：{}", request);

            // 判断是否为单条ID查询（不分页）
            boolean isSingleQuery = request.isSingleQuery() && isOnlyIdCondition(request);

            if (isSingleQuery) {
                // 单条ID查询，不分页，直接返回详情
                BloDTO singleResult = bloMapper.selectBloById(request.getId());
                if (singleResult != null) {
                    log.debug("单条ID查询成功，博主ID：{}", request.getId());
                    return new PageResult<>(List.of(singleResult), 1L, 1L, 1L);
                } else {
                    log.debug("单条ID查询无结果，博主ID：{}", request.getId());
                    return new PageResult<>(List.of(), 0L, 1L, 1L);
                }
            }

            // 验证并设置分页参数默认值
            Integer[] pageParams = PageUtil.validateAndSetDefaults(request.getPage(), request.getSize());
            Integer page = pageParams[0];
            Integer size = pageParams[1];
            int offset = (int) PageUtil.calculateOffset(page, size);

            // 查询总数
            Long total = bloMapper.selectBloCount(request);

            // 查询数据
            List<BloDTO> list = bloMapper.selectBlosWithLimit(request, offset, size);

            // 构建分页结果
            PageResult<BloDTO> result = new PageResult<>();
            result.setData(list);
            result.setTotal(total);
            result.setCurrent((long) page);
            result.setSize((long) size);

            log.debug("查询博主信息完成，总数：{}，当前页：{}，每页大小：{}", total, page, size);

            return result;
        } catch (Exception e) {
            throw ExceptionUtil.handleBusinessException(e, "查询博主信息");
        }
    }

    @Override
    public List<BloDTO> queryBlosWithoutPagination(BloQueryRequest request) {
        try {
            log.debug("开始查询博主信息（不分页），请求参数：{}", request);

            // 查询所有符合条件的记录
            List<BloDTO> list = bloMapper.selectBlosWithoutPagination(request);

            log.debug("查询博主信息（不分页）完成，返回{}条记录", list.size());

            return list;
        } catch (Exception e) {
            throw ExceptionUtil.handleBusinessException(e, "查询博主信息（不分页）");
        }
    }

    /**
     * 判断是否只有ID查询条件（用于确定是否为单条查询）
     * @param request 查询请求
     * @return 是否只有ID条件
     */
    private boolean isOnlyIdCondition(BloQueryRequest request) {
        return (request.getBloggerUid() == null || request.getBloggerUid().trim().isEmpty()) &&
               (request.getHomepageUrl() == null || request.getHomepageUrl().trim().isEmpty()) &&
               (request.getStatus() == null || request.getStatus().trim().isEmpty()) &&
               (request.getTags() == null || request.getTags().trim().isEmpty());
    }

    @Override
    public boolean createBlo(BloCreateRequest request) {
        try {
            Blo blo = new Blo();
            BeanUtils.copyProperties(request, blo);
            blo.setCreateTime(LocalDateTime.now());
            blo.setUpdateTime(LocalDateTime.now());
            blo.setIsDelete("N");

            // 设置默认值
            blo.setStatus("not_updated");
            blo.setIsEnter("N");
            blo.setFollowerCount(0L);
            blo.setFollowingCount(0L);
            blo.setWorkCount(0L);
            blo.setWorkRatio(0.0);
            blo.setIsPython(false);

            // 自动生成博主UID（如果没有提供）
            if (blo.getBloggerUid() == null) {
                // 使用时间戳+随机数确保唯一性
                long timestamp = System.currentTimeMillis();
                long random = (long) (Math.random() * 1000);
                blo.setBloggerUid(timestamp + random);
            }

            return this.save(blo);
        } catch (Exception e) {
            throw ExceptionUtil.handleBusinessException(e, "创建博主");
        }
    }

    @Override
    public boolean updateBlo(BloUpdateRequest request) {
        try {
            // 使用工具类验证
            ValidationUtil.validateRequest(request, "博主更新请求");
            ValidationUtil.validateIdPositive(request.getId(), "博主");

            // 先查询现有记录
            Blo existingBlo = this.getById(request.getId());
            ValidationUtil.validateEntityExists(existingBlo, "博主", request.getId());

            // 更新字段
            BeanUtils.copyProperties(request, existingBlo, "id", "createTime", "isDelete");
            existingBlo.setUpdateTime(LocalDateTime.now());

            return this.updateById(existingBlo);
        } catch (Exception e) {
            throw ExceptionUtil.handleBusinessException(e, "更新博主信息");
        }
    }

    @Override
    public boolean deleteBlo(Long id) {
        try {
            ValidationUtil.validateIdPositive(id, "博主");

            int result = bloMapper.softDeleteBlo(id);
            return result > 0;
        } catch (Exception e) {
            throw ExceptionUtil.handleBusinessException(e, "删除博主");
        }
    }

    // ========== 爬虫列表管理方法实现 ==========

    @Override
    public PageResult<CrawlerBloDTO> queryCrawlerBlos(CrawlerBloQueryRequest request) {
        try {
            log.debug("开始查询博主信息，请求参数：{}", request);

            // 判断是否为单条ID查询（不分页）
            boolean isSingleQuery = request.isSingleQuery() && isOnlyIdConditionForCrawler(request);

            if (isSingleQuery) {
                // 单条ID查询，不分页，直接返回详情
                CrawlerBloDTO singleResult = bloMapper.selectCrawlerBloById(request.getId());
                if (singleResult != null) {
                    log.debug("单条ID查询成功，博主ID：{}", request.getId());
                    return new PageResult<>(List.of(singleResult), 1L, 1L, 1L);
                } else {
                    log.debug("单条ID查询无结果，博主ID：{}", request.getId());
                    return new PageResult<>(List.of(), 0L, 1L, 1L);
                }
            }

            // 设置默认分页参数
            request.setDefaultPagination();

            // 使用PageUtil计算偏移量
            long offset = PageUtil.calculateOffset(request.getPage(), request.getSize());

            log.debug("执行分页查询，页码：{}，每页大小：{}，偏移量：{}",
                     request.getPage(), request.getSize(), offset);

            // 查询数据
            List<CrawlerBloDTO> list = bloMapper.selectCrawlerBlosWithAllFields(request, offset, request.getSize());
            Long total = bloMapper.selectCrawlerBloCount(request);

            // 构建分页结果
            PageResult<CrawlerBloDTO> result = new PageResult<>(list, total, Long.valueOf(request.getPage()), Long.valueOf(request.getSize()));
            log.debug("查询博主信息完成，返回{}条记录，总数：{}", list.size(), total);

            return result;
        } catch (Exception e) {
            log.error("查询博主信息失败，请求参数：{}", request, e);
            throw new RuntimeException("查询失败：" + e.getMessage());
        }
    }

    /**
     * 判断是否只有ID查询条件（用于确定是否为单条查询）
     * @param request 查询请求
     * @return 是否只有ID条件
     */
    private boolean isOnlyIdConditionForCrawler(CrawlerBloQueryRequest request) {
        return (request.getBloggerUid() == null) &&
               (request.getStatus() == null || request.getStatus().trim().isEmpty());
    }

    @Override
    public boolean createCrawlerBlo(CrawlerBloCreateRequest request) {
        try {
            Blo blo = new Blo();

            // 复制基本字段
            blo.setBloggerUid(request.getBloggerUid());
            blo.setBloggerNickname(request.getBloggerNickname());
            blo.setHomepageUrl(request.getHomepageUrl());
            blo.setAvatar(request.getAvatar());
            blo.setPhone(request.getPhone());
            blo.setTags(request.getTags());
            blo.setBloggerSignature(request.getBloggerSignature());
            blo.setType(request.getType());

            // 设置时间字段
            blo.setCreateTime(LocalDateTime.now());
            blo.setUpdateTime(LocalDateTime.now());

            // 设置默认值
            blo.setIsDelete("N");
            blo.setStatus("not_updated");
            blo.setIsEnter("N");
            blo.setFollowerCount(0L);
            blo.setFollowingCount(0L);
            blo.setWorkCount(0L);
            blo.setWorkRatio(0.0);
            blo.setIsPython(false);

            return this.save(blo);
        } catch (Exception e) {
            log.error("创建爬虫列表博主失败", e);
            throw new RuntimeException("创建失败：" + e.getMessage());
        }
    }

    @Override
    public boolean updateCrawlerBlo(CrawlerBloUpdateRequest request) {
        try {
            Blo blo = new Blo();
            blo.setId(request.getId());
            blo.setBloggerNickname(request.getBloggerNickname());
            blo.setAvatar(request.getAvatar());
            blo.setPhone(request.getPhone());
            blo.setTags(request.getTags());
            blo.setBloggerSignature(request.getBloggerSignature());
            blo.setType(request.getType());
            blo.setUpdateTime(LocalDateTime.now());

            return this.updateById(blo);
        } catch (Exception e) {
            log.error("更新爬虫列表博主信息失败，ID：{}", request.getId(), e);
            throw new RuntimeException("更新失败：" + e.getMessage());
        }
    }

    @Override
    public boolean deleteCrawlerBlo(Long id) {
        try {
            int result = bloMapper.softDeleteBlo(id);
            return result > 0;
        } catch (Exception e) {
            log.error("删除爬虫列表博主失败，ID：{}", id, e);
            throw new RuntimeException("删除失败：" + e.getMessage());
        }
    }
}
