package com.gig.collide.service;

import com.gig.collide.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RecordQueryServiceImpl implements RecordQueryService {

    @Autowired
    private com.gig.collide.mapper.RecordQueryMapper recordQueryMapper;

    @Override
    public PageResult<Map<String, Object>> queryRechargeRecords(String packageName, String orderType,
                                                                Integer page, Integer size) {
        try {
            // 设置默认分页参数
            if (page == null || page < 1) {
                page = 1;
            }
            if (size == null || size < 1) {
                size = 10;
            }

            // 计算偏移量
            Integer offset = (page - 1) * size;

            // 查询总数
            Long total = recordQueryMapper.countRechargeRecords(packageName, orderType);
            if (total == null || total == 0) {
                return new PageResult<>(new ArrayList<>(), 0L, Long.valueOf(page), Long.valueOf(size));
            }

            // 查询数据
            List<Map<String, Object>> records = recordQueryMapper.queryRechargeRecords(packageName, orderType, offset, size);

            return new PageResult<>(records != null ? records : new ArrayList<>(), total, Long.valueOf(page), Long.valueOf(size));
        } catch (Exception e) {
            // 记录错误日志
            System.err.println("查询充值记录失败: " + e.getMessage());
            e.printStackTrace();
            // 返回空结果
            return new PageResult<>(new ArrayList<>(), 0L, Long.valueOf(page), Long.valueOf(size));
        }
    }

    @Override
    public PageResult<Map<String, Object>> queryConsumptionRecords(String packageName, String orderType,
                                                                   Integer page, Integer size) {
        try {
            // 设置默认分页参数
            if (page == null || page < 1) {
                page = 1;
            }
            if (size == null || size < 1) {
                size = 10;
            }

            // 计算偏移量
            Integer offset = (page - 1) * size;

            // 查询总数
            Long total = recordQueryMapper.countConsumptionRecords(packageName, orderType);
            if (total == null || total == 0) {
                return new PageResult<>(new ArrayList<>(), 0L, Long.valueOf(page), Long.valueOf(size));
            }

            // 查询数据
            List<Map<String, Object>> records = recordQueryMapper.queryConsumptionRecords(packageName, orderType, offset, size);

            return new PageResult<>(records != null ? records : new ArrayList<>(), total, Long.valueOf(page), Long.valueOf(size));
        } catch (Exception e) {
            // 记录错误日志
            System.err.println("查询消费记录失败: " + e.getMessage());
            e.printStackTrace();
            // 返回空结果
            return new PageResult<>(new ArrayList<>(), 0L, Long.valueOf(page), Long.valueOf(size));
        }
    }
}
