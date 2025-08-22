package com.gig.collide.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 记录查询Mapper接口
 * 提供充值记录和消费记录的查询功能
 *
 * @author system
 * @since 2024-01-01
 * @version 1.0
 */
@Mapper
public interface RecordQueryMapper {

    /**
     * 查询充值记录总数
     *
     * @param packageName 包名
     * @param orderType 订单类型
     * @return 充值记录总数
     */
    Long countRechargeRecords(@Param("packageName") String packageName, 
                             @Param("orderType") String orderType);

    /**
     * 查询充值记录列表
     *
     * @param packageName 包名
     * @param orderType 订单类型
     * @param offset 偏移量
     * @param size 每页大小
     * @return 充值记录列表
     */
    List<Map<String, Object>> queryRechargeRecords(@Param("packageName") String packageName,
                                                   @Param("orderType") String orderType,
                                                   @Param("offset") Integer offset,
                                                   @Param("size") Integer size);

    /**
     * 查询消费记录总数
     *
     * @param packageName 包名
     * @param orderType 订单类型
     * @return 消费记录总数
     */
    Long countConsumptionRecords(@Param("packageName") String packageName, 
                                @Param("orderType") String orderType);

    /**
     * 查询消费记录列表
     *
     * @param packageName 包名
     * @param orderType 订单类型
     * @param offset 偏移量
     * @param size 每页大小
     * @return 消费记录列表
     */
    List<Map<String, Object>> queryConsumptionRecords(@Param("packageName") String packageName,
                                                      @Param("orderType") String orderType,
                                                      @Param("offset") Integer offset,
                                                      @Param("size") Integer size);
}

