package com.gig.collide.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gig.collide.domain.goods.Goods;
import com.gig.collide.dto.goodsDto.GoodsCreateRequest;
import com.gig.collide.dto.goodsDto.GoodsDTO;
import com.gig.collide.dto.goodsDto.GoodsQueryRequest;
import com.gig.collide.dto.goodsDto.GoodsUpdateRequest;
import com.gig.collide.mapper.GoodsMapper;
import com.gig.collide.util.PageUtil;
import com.gig.collide.util.ValidationUtil;
import com.gig.collide.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 商品信息服务实现类
 * 
 * @author system
 * @since 2025-01-27
 * @version 1.0
 */
@Slf4j
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public IPage<GoodsDTO> queryGoods(GoodsQueryRequest request) {
        try {
            log.debug("开始查询商品信息，请求参数：{}", request);
            
            // 判断是否为单条ID查询（不分页）
            boolean isSingleQuery = request.isSingleQuery() && isOnlyIdCondition(request);
            
            if (isSingleQuery) {
                // 单条ID查询，不分页，直接返回详情
                GoodsDTO singleResult = goodsMapper.selectGoodsById(request.getId());
                if (singleResult != null) {
                    log.debug("单条ID查询成功，商品ID：{}", request.getId());
                    Page<GoodsDTO> page = new Page<>(1, 1);
                    page.setRecords(List.of(singleResult));
                    page.setTotal(1);
                    return page;
                } else {
                    log.debug("单条ID查询无结果，商品ID：{}", request.getId());
                    Page<GoodsDTO> page = new Page<>(1, 1);
                    page.setRecords(List.of());
                    page.setTotal(0);
                    return page;
                }
            }
            
            // 设置默认分页参数
            request.setDefaultPagination();
            
            // 使用PageUtil计算偏移量
            long offset = PageUtil.calculateOffset(request.getPage(), request.getSize());
            
            // 查询数据
            List<GoodsDTO> goods = goodsMapper.selectGoodsWithAllFields(request, offset, request.getSize());
            
            // 查询总数
            Long total = goodsMapper.countGoodsWithConditions(request);
            
            // 构建分页结果
            Page<GoodsDTO> page = new Page<>(request.getPage(), request.getSize());
            page.setRecords(goods);
            page.setTotal(total);
            
            return page;
            
        } catch (Exception e) {
            throw ExceptionUtil.handleBusinessException(e, "查询商品信息");
        }
    }

    @Override
    public boolean createGoods(GoodsCreateRequest request) {
        try {
            log.debug("开始创建商品，请求参数：{}", request);
            
            Goods goods = new Goods();
            BeanUtils.copyProperties(request, goods);
            goods.setCreateTime(LocalDateTime.now());
            goods.setUpdateTime(LocalDateTime.now());
            
            // 设置默认值
            if (goods.getCategoryId() == null) {
                goods.setCategoryId(2L); // 默认分类ID为002
            }
            if (goods.getIsOnline() == null) {
                goods.setIsOnline("N");
            }
            if (goods.getSortOrder() == null) {
                goods.setSortOrder(0);
            }
            if (goods.getSalesCount() == null) {
                goods.setSalesCount(0L);
            }
            if (goods.getViewCount() == null) {
                goods.setViewCount(0L);
            }
            
            boolean result = save(goods);
            
            if (result) {
                log.debug("创建商品成功，商品ID：{}", goods.getId());
            } else {
                log.error("创建商品失败");
            }
            
            return result;
            
        } catch (Exception e) {
            throw ExceptionUtil.handleBusinessException(e, "创建商品");
        }
    }

    @Override
    public boolean updateGoods(GoodsUpdateRequest request) {
        try {
            log.debug("开始更新商品，请求参数：{}", request);
            
            // 使用工具类验证
            ValidationUtil.validateRequest(request, "商品更新请求");
            ValidationUtil.validateIdPositive(request.getId(), "商品");
            
            // 查询现有商品
            Goods existingGoods = getById(request.getId());
            ValidationUtil.validateEntityExists(existingGoods, "商品", request.getId());
            
            // 只更新允许编辑的字段
            Goods goods = new Goods();
            goods.setId(request.getId());
            goods.setUpdateTime(LocalDateTime.now());
            
            // 策略名称场景
            if (request.getStrategyScene() != null) {
                goods.setStrategyScene(request.getStrategyScene());
            }
            // 用户浏览器标签
            if (request.getBrowserTag() != null) {
                goods.setBrowserTag(request.getBrowserTag());
            }
            // 促销文案
            if (request.getDescription() != null) {
                goods.setDescription(request.getDescription());
            }
            // 价格
            if (request.getPrice() != null) {
                goods.setPrice(request.getPrice());
            }
            // 商品名称
            if (request.getName() != null) {
                goods.setName(request.getName());
            }
            // 包名
            if (request.getPackageName() != null) {
                goods.setPackageName(request.getPackageName());
            }
            // 优先级
            if (request.getSortOrder() != null) {
                goods.setSortOrder(request.getSortOrder());
            }
            // 状态
            if (request.getStatus() != null) {
                goods.setStatus(request.getStatus());
            }
            
            boolean result = updateById(goods);
            
            if (result) {
                log.debug("更新商品成功，商品ID：{}", request.getId());
            } else {
                log.error("更新商品失败，商品ID：{}", request.getId());
            }
            
            return result;
            
        } catch (Exception e) {
            throw ExceptionUtil.handleBusinessException(e, "更新商品");
        }
    }

    @Override
    public boolean deleteGoods(Long id) {
        try {
            log.debug("开始删除商品，商品ID：{}", id);
            
            ValidationUtil.validateIdPositive(id, "商品");
            
            // 检查商品是否存在
            Goods existingGoods = getById(id);
            ValidationUtil.validateEntityExists(existingGoods, "商品", id);
            
            boolean result = removeById(id);
            
            if (result) {
                log.debug("删除商品成功，商品ID：{}", id);
            } else {
                log.error("删除商品失败，商品ID：{}", id);
            }
            
            return result;
            
        } catch (Exception e) {
            throw ExceptionUtil.handleBusinessException(e, "删除商品");
        }
    }

    @Override
    public String updateOnlineStatusWithCheck(Long id, String isOnline) {
        try {
            log.debug("开始更新商品上线状态（带检查），商品ID：{}，状态：{}", id, isOnline);
            
            ValidationUtil.validateIdPositive(id, "商品");
            ValidationUtil.validateOnlineStatus(isOnline, "商品上线状态");
            
            // 查询现有商品及其状态
            Goods existingGoods = getById(id);
            ValidationUtil.validateEntityExists(existingGoods, "商品", id);
            
            String currentStatus = existingGoods.getIsOnline();
            
            // 检查当前状态是否与目标状态相同
            if (isOnline.equals(currentStatus)) {
                if ("Y".equals(isOnline)) {
                    log.debug("商品已是上线状态，无需更新，商品ID：{}", id);
                    return "ALREADY_ONLINE";
                } else {
                    log.debug("商品已是下线状态，无需更新，商品ID：{}", id);
                    return "ALREADY_OFFLINE";
                }
            }
            
            // 执行状态更新
            int result = goodsMapper.updateOnlineStatus(id, isOnline);
            
            if (result > 0) {
                log.debug("更新商品上线状态成功，商品ID：{}，状态：{}", id, isOnline);
                return "SUCCESS";
            } else {
                log.error("更新商品上线状态失败，商品ID：{}，状态：{}", id, isOnline);
                return "FAILED";
            }
            
        } catch (Exception e) {
            throw ExceptionUtil.handleBusinessException(e, "更新商品上线状态");
        }
    }

    /**
     * 判断是否只有ID条件（用于单条查询判断）
     */
    private boolean isOnlyIdCondition(GoodsQueryRequest request) {
        return request.getId() != null
                && (request.getPackageName() == null || request.getPackageName().trim().isEmpty())
                && (request.getStatus() == null || request.getStatus().trim().isEmpty())
                && (request.getStrategyScene() == null || request.getStrategyScene().trim().isEmpty())
                && (request.getIsOnline() == null || request.getIsOnline().trim().isEmpty());
    }
}
