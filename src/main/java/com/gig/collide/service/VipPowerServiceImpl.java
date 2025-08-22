package com.gig.collide.service;

import com.gig.collide.domain.vip.VipPower;
import com.gig.collide.dto.vipDto.VipPowerCreateRequest;
import com.gig.collide.dto.vipDto.VipPowerDTO;
import com.gig.collide.dto.vipDto.VipPowerQueryRequest;
import com.gig.collide.dto.vipDto.VipPowerUpdateRequest;
import com.gig.collide.mapper.VipPowerMapper;
import com.gig.collide.service.VipPowerService;
import com.gig.collide.util.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import com.gig.collide.util.PageUtil;

/**
 * VIP特权服务实现类
 * 
 * @author system
 * @since 2025-01-27
 * @version 1.0
 */
@Slf4j
@Service
public class VipPowerServiceImpl implements VipPowerService {

    @Autowired
    private VipPowerMapper vipPowerMapper;

    @Override
    public PageResult<VipPowerDTO> queryVipPower(VipPowerQueryRequest request) {
        log.debug("查询VIP特权，请求参数：{}", request);
        
        try {
            // 设置默认分页参数
            request.setDefaultPagination();
            
            // 使用PageUtil计算偏移量
            long offset = PageUtil.calculateOffset(request.getPage(), request.getSize());
            
            // 查询数据
            List<VipPowerDTO> list = vipPowerMapper.selectVipPowerList(request, offset, request.getSize());
            Long total = vipPowerMapper.selectVipPowerCount(request);
            
            // 构建分页结果
            PageResult<VipPowerDTO> result = new PageResult<>(list, total, Long.valueOf(request.getPage()), Long.valueOf(request.getSize()));
            
            log.debug("VIP特权查询完成，返回{}条数据", list.size());
            return result;
            
        } catch (Exception e) {
            log.error("查询VIP特权失败", e);
            throw new RuntimeException("查询VIP特权失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createVipPower(VipPowerCreateRequest request) {
        log.debug("创建VIP特权，请求参数：{}", request);
        
        try {
            // 检查特权文案名称是否已存在
            int existCount = vipPowerMapper.checkPowerNameExists(request.getPowerName(), request.getVipName(), null);
            if (existCount > 0) {
                throw new RuntimeException("该VIP名称下已存在相同的特权文案名称");
            }
            
            // 创建VIP特权实体
            VipPower vipPower = new VipPower();
            BeanUtils.copyProperties(request, vipPower);
            vipPower.setCreateTime(LocalDateTime.now());
            vipPower.setUpdateTime(LocalDateTime.now());
            
            // 如果状态为空，设置默认值
            if (vipPower.getStatus() == null || vipPower.getStatus().isEmpty()) {
                vipPower.setStatus("active");
            }
            
            int result = vipPowerMapper.insert(vipPower);
            
            log.debug("VIP特权创建完成，影响行数：{}", result);
            return result > 0;
            
        } catch (Exception e) {
            log.error("创建VIP特权失败", e);
            throw new RuntimeException("创建VIP特权失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateVipPower(VipPowerUpdateRequest request) {
        log.debug("更新VIP特权，请求参数：{}", request);
        
        try {
            // 检查VIP特权是否存在
            int existCount = vipPowerMapper.checkVipPowerExists(request.getId());
            if (existCount == 0) {
                throw new RuntimeException("VIP特权不存在");
            }
            
            // 检查特权文案名称是否已存在（排除当前记录）
            int duplicateCount = vipPowerMapper.checkPowerNameExists(request.getPowerName(), request.getVipName(), request.getId());
            if (duplicateCount > 0) {
                throw new RuntimeException("该VIP名称下已存在相同的特权文案名称");
            }
            
            // 更新VIP特权实体
            VipPower vipPower = new VipPower();
            BeanUtils.copyProperties(request, vipPower);
            vipPower.setUpdateTime(LocalDateTime.now());
            
            int result = vipPowerMapper.updateById(vipPower);
            
            log.debug("VIP特权更新完成，影响行数：{}", result);
            return result > 0;
            
        } catch (Exception e) {
            log.error("更新VIP特权失败", e);
            throw new RuntimeException("更新VIP特权失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteVipPower(Long id) {
        log.debug("删除VIP特权，策略ID：{}", id);
        
        try {
            // 检查VIP特权是否存在
            int existCount = vipPowerMapper.checkVipPowerExists(id);
            if (existCount == 0) {
                throw new RuntimeException("VIP特权不存在");
            }
            
            // 物理删除VIP特权
            int result = vipPowerMapper.deleteById(id);
            
            log.debug("VIP特权删除完成，影响行数：{}", result);
            return result > 0;
            
        } catch (Exception e) {
            log.error("删除VIP特权失败", e);
            throw new RuntimeException("删除VIP特权失败：" + e.getMessage());
        }
    }
}
