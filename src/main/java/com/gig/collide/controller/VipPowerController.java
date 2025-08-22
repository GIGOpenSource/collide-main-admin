package com.gig.collide.controller;

import com.gig.collide.dto.vipDto.VipPowerCreateRequest;
import com.gig.collide.dto.vipDto.VipPowerDTO;
import com.gig.collide.dto.vipDto.VipPowerQueryRequest;
import com.gig.collide.dto.vipDto.VipPowerUpdateRequest;
import com.gig.collide.service.VipPowerService;
import com.gig.collide.util.PageResult;
import com.gig.collide.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Map;

/**
 * VIP特权配置管理Controller
 * 提供VIP特权配置的CRUD操作，包括特权配置查询、创建、更新、删除等功能
 * 
 * @author system
 * @since 2025-01-27
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/admin/vip-power")
@CrossOrigin(origins = "*")
public class VipPowerController {

    @Autowired
    private VipPowerService vipPowerService;

    /**
     * 功能：查询VIP特权配置列表
     * 描述：支持多条件筛选的VIP特权配置查询，包括策略ID、特权文案名称、所属VIP名称、状态等条件
     * 使用场景：VIP特权管理、特权配置筛选、管理后台特权列表展示、特权详情查询
     * 
     * @param request VIP特权查询请求对象
     * @return VIP特权配置列表响应
     */
    @PostMapping("/query")
    public Map<String, Object> queryVipPower(@RequestBody VipPowerQueryRequest request) {
        try {
            log.debug("接收到VIP特权查询请求：{}", request);
            
            PageResult<VipPowerDTO> result = vipPowerService.queryVipPower(request);
            
            log.debug("VIP特权查询成功，返回数据量：{}", result.getData().size());
            return ResponseUtil.success(result);
            
        } catch (Exception e) {
            log.error("VIP特权查询失败", e);
            return ResponseUtil.error("VIP特权查询失败：" + e.getMessage());
        }
    }

    /**
     * 功能：创建VIP特权配置
     * 描述：新增VIP特权配置信息，包括特权文案名称、附件、所属VIP名称、备注、优先级等
     * 使用场景：VIP特权管理、新特权配置添加、管理后台特权创建
     * 
     * @param request VIP特权创建请求对象
     * @return 创建结果响应
     */
    @PostMapping("/create")
    public Map<String, Object> createVipPower(@Valid @RequestBody VipPowerCreateRequest request) {
        try {
            log.debug("接收到VIP特权创建请求：{}", request);
            
            boolean result = vipPowerService.createVipPower(request);
            
            if (result) {
                log.debug("VIP特权创建成功");
                return ResponseUtil.success("VIP特权创建成功");
            } else {
                log.error("VIP特权创建失败");
                return ResponseUtil.error("VIP特权创建失败");
            }
            
        } catch (Exception e) {
            log.error("VIP特权创建失败", e);
            return ResponseUtil.error("VIP特权创建失败：" + e.getMessage());
        }
    }

    /**
     * 功能：编辑VIP特权配置
     * 描述：修改现有VIP特权配置信息，支持编辑特权文案名称、附件、所属VIP名称、备注、优先级
     * 使用场景：VIP特权管理、特权配置修改、管理后台特权编辑
     * 
     * @param request VIP特权更新请求对象
     * @return 更新结果响应
     */
    @PostMapping("/update")
    public Map<String, Object> updateVipPower(@Valid @RequestBody VipPowerUpdateRequest request) {
        try {
            log.debug("接收到VIP特权更新请求：{}", request);
            
            boolean result = vipPowerService.updateVipPower(request);
            
            if (result) {
                log.debug("VIP特权更新成功，策略ID：{}", request.getId());
                return ResponseUtil.success("VIP特权更新成功");
            } else {
                log.error("VIP特权更新失败，策略ID：{}", request.getId());
                return ResponseUtil.error("VIP特权更新失败");
            }
            
        } catch (Exception e) {
            log.error("VIP特权更新失败", e);
            return ResponseUtil.error("VIP特权更新失败：" + e.getMessage());
        }
    }

    /**
     * 功能：删除VIP特权配置
     * 描述：根据策略ID物理删除VIP特权配置信息，删除后不可恢复
     * 使用场景：VIP特权管理、特权配置删除、管理后台特权移除
     * 
     * @param id 策略ID（VIP特权配置的唯一标识）
     * @return 删除结果响应
     */
    @DeleteMapping("/delete/{id}")
    public Map<String, Object> deleteVipPower(@PathVariable Long id) {
        try {
            log.debug("接收到VIP特权删除请求，策略ID：{}", id);
            
            boolean result = vipPowerService.deleteVipPower(id);
            
            if (result) {
                log.debug("VIP特权删除成功，策略ID：{}", id);
                return ResponseUtil.success("VIP特权删除成功");
            } else {
                log.error("VIP特权删除失败，策略ID：{}", id);
                return ResponseUtil.error("VIP特权删除失败");
            }
            
        } catch (Exception e) {
            log.error("VIP特权删除失败", e);
            return ResponseUtil.error("VIP特权删除失败：" + e.getMessage());
        }
    }
}
