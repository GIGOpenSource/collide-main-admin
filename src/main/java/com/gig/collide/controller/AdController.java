package com.gig.collide.controller;

import com.gig.collide.dto.adDto.AdCreateRequest;
import com.gig.collide.dto.adDto.AdDTO;
import com.gig.collide.dto.adDto.AdQueryRequest;
import com.gig.collide.dto.adDto.AdUpdateRequest;
import com.gig.collide.service.AdService;
import com.gig.collide.util.PageResult;
import com.gig.collide.util.ResponseUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * 广告管理控制器
 * 提供广告的增删改查功能
 *
 * @author system
 * @since 2025-01-27
 * @version 1.0
 */
@RestController
@RequestMapping("/api/admin/ad")
@CrossOrigin
public class AdController {

    private static final Logger log = LoggerFactory.getLogger(AdController.class);

    @Autowired
    private AdService adService;

    /**
     * 查询所有广告
     *
     * @return 广告列表
     */
    @GetMapping("/all")
    public Map<String, Object> queryAllAds() {
        try {
            log.info("接收到查询所有广告请求");

            List<AdDTO> ads = adService.queryAllAds();

            log.info("查询所有广告成功，共{}条", ads.size());
            return ResponseUtil.success(ads, "查询成功");

        } catch (Exception e) {
            log.error("查询所有广告失败", e);
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 根据ID查询广告
     *
     * @param id 广告ID
     * @return 广告信息
     */
    @GetMapping("/{id}")
    public Map<String, Object> queryAdById(@PathVariable Long id) {
        try {
            log.info("接收到根据ID查询广告请求，ID：{}", id);

            if (id == null || id <= 0) {
                return ResponseUtil.error("广告ID无效");
            }

            AdDTO ad = adService.queryAdById(id);
            if (ad != null) {
                log.info("查询广告成功，ID：{}", id);
                return ResponseUtil.success(ad, "查询成功");
            } else {
                log.warn("广告不存在，ID：{}", id);
                return ResponseUtil.error("广告不存在");
            }

        } catch (Exception e) {
            log.error("查询广告失败，ID：{}", id, e);
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 分页查询广告
     *
     * @param request 查询请求
     * @return 分页结果
     */
    @PostMapping("/page")
    public Map<String, Object> queryAdsByPage(@RequestBody AdQueryRequest request) {
        try {
            log.info("接收到分页查询广告请求，请求参数：{}", request);

            PageResult<AdDTO> result = adService.queryAdsByPage(request);

            log.info("分页查询广告成功，总数：{}，当前页：{}，每页大小：{}",
                    result.getTotal(), result.getCurrent(), result.getSize());
            return ResponseUtil.success(result, "查询成功");

        } catch (Exception e) {
            log.error("分页查询广告失败", e);
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 新增广告
     *
     * @param request 创建请求
     * @return 操作结果
     */
    @PostMapping("/create")
    public Map<String, Object> createAd(@Valid @RequestBody AdCreateRequest request) {
        try {
            log.info("接收到创建广告请求，请求参数：{}", request);

            boolean result = adService.createAd(request);

            if (result) {
                log.info("创建广告成功");
                return ResponseUtil.success(true, "创建成功");
            } else {
                log.warn("创建广告失败");
                return ResponseUtil.error("创建失败");
            }

        } catch (Exception e) {
            log.error("创建广告失败", e);
            return ResponseUtil.error("创建失败：" + e.getMessage());
        }
    }

    /**
     * 修改广告
     *
     * @param request 更新请求
     * @return 操作结果
     */
    @PutMapping("/update")
    public Map<String, Object> updateAd(@Valid @RequestBody AdUpdateRequest request) {
        try {
            log.info("接收到更新广告请求，请求参数：{}", request);

            boolean result = adService.updateAd(request);

            if (result) {
                log.info("更新广告成功，ID：{}", request.getId());
                return ResponseUtil.success(true, "更新成功");
            } else {
                log.warn("更新广告失败，ID：{}", request.getId());
                return ResponseUtil.error("更新失败：广告不存在或更新失败");
            }

        } catch (Exception e) {
            log.error("更新广告失败", e);
            return ResponseUtil.error("更新失败：" + e.getMessage());
        }
    }

    /**
     * 删除广告
     *
     * @param id 广告ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteAd(@PathVariable Long id) {
        try {
            log.info("接收到删除广告请求，ID：{}", id);

            if (id == null || id <= 0) {
                return ResponseUtil.error("广告ID无效");
            }

            boolean result = adService.deleteAd(id);

            if (result) {
                log.info("删除广告成功，ID：{}", id);
                return ResponseUtil.success(true, "删除成功");
            } else {
                log.warn("删除广告失败，ID：{}", id);
                return ResponseUtil.error("删除失败：广告不存在或删除失败");
            }

        } catch (Exception e) {
            log.error("删除广告失败，ID：{}", id, e);
            return ResponseUtil.error("删除失败：" + e.getMessage());
        }
    }

    /**
     * 批量删除广告
     *
     * @param ids 广告ID列表
     * @return 操作结果
     */
    @DeleteMapping("/batch")
    public Map<String, Object> batchDeleteAds(@RequestBody List<Long> ids) {
        try {
            log.info("接收到批量删除广告请求，ID列表：{}", ids);

            if (ids == null || ids.isEmpty()) {
                return ResponseUtil.error("广告ID列表不能为空");
            }

            boolean result = adService.batchDeleteAds(ids);

            if (result) {
                log.info("批量删除广告成功，ID列表：{}", ids);
                return ResponseUtil.success(true, "批量删除成功");
            } else {
                log.warn("批量删除广告失败，ID列表：{}", ids);
                return ResponseUtil.error("批量删除失败");
            }

        } catch (Exception e) {
            log.error("批量删除广告失败", e);
            return ResponseUtil.error("批量删除失败：" + e.getMessage());
        }
    }

    /**
     * 更新广告状态
     *
     * @param id 广告ID
     * @param isActive 是否启用
     * @return 操作结果
     */
    @PutMapping("/{id}/status")
    public Map<String, Object> updateAdStatus(@PathVariable Long id, @RequestParam Boolean isActive) {
        try {
            log.info("接收到更新广告状态请求，ID：{}，状态：{}", id, isActive);

            if (id == null || id <= 0) {
                return ResponseUtil.error("广告ID无效");
            }

            if (isActive == null) {
                return ResponseUtil.error("广告状态不能为空");
            }

            boolean result = adService.updateAdStatus(id, isActive);

            if (result) {
                log.info("更新广告状态成功，ID：{}，状态：{}", id, isActive);
                return ResponseUtil.success(true, "状态更新成功");
            } else {
                log.warn("更新广告状态失败，ID：{}", id);
                return ResponseUtil.error("状态更新失败：广告不存在或更新失败");
            }

        } catch (Exception e) {
            log.error("更新广告状态失败，ID：{}", id, e);
            return ResponseUtil.error("状态更新失败：" + e.getMessage());
        }
    }
}
