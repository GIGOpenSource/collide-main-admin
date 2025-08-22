package com.gig.collide.service;

import com.gig.collide.util.PageResult;

import java.util.Map;

/**
 * 记录查询服务接口
 * 
 * @author system
 * @since 2024-01-01
 */
public interface RecordQueryService {

    /**
     * 查询充值记录
     * 
     * @param packageName 包名
     * @param orderType 订单类型
     * @param page 页码
     * @param size 每页大小
     * @return 充值记录分页结果
     */
    PageResult<Map<String, Object>> queryRechargeRecords(String packageName, String orderType, 
                                                        Integer page, Integer size);

    /**
     * 查询消费记录
     * 
     * @param packageName 包名
     * @param orderType 订单类型
     * @param page 页码
     * @param size 每页大小
     * @return 消费记录分页结果
     */
    PageResult<Map<String, Object>> queryConsumptionRecords(String packageName, String orderType, 
                                                           Integer page, Integer size);
}
