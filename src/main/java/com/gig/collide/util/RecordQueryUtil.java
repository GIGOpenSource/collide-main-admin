//package com.gig.collide.util;
//
//import com.gig.collide.mapper.RecordQueryMapper;
//import org.springframework.util.StringUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
///**
// * 记录查询工具类
// * 整合SQL构建、状态转换和分页查询功能，减少重复代码
// *
// * @author system
// * @since 2024-01-01
// */
//public class RecordQueryUtil {
//
//    /**
//     * 执行充值记录查询
//     */
//    public static PageResult<Map<String, Object>> queryRechargeRecords(
//            RecordQueryMapper recordQueryMapper, String packageName, Integer page, Integer size) {
//
//        try {
//            // 设置默认分页参数
//            if (page == null || page < 1) {
//                page = 1;
//            }
//            if (size == null || size < 1) {
//                size = 10;
//            }
//
//            // 计算偏移量
//            Integer offset = (page - 1) * size;
//
//            // 查询总数
//            Long total = recordQueryMapper.countRechargeRecords(packageName);
//            if (total == null || total == 0) {
//                return new PageResult<>(new ArrayList<>(), 0L, Long.valueOf(page), Long.valueOf(size));
//            }
//
//            // 查询数据
//            List<Map<String, Object>> records = recordQueryMapper.queryRechargeRecords(packageName, offset, size);
//
//            return new PageResult<>(records != null ? records : new ArrayList<>(), total, Long.valueOf(page), Long.valueOf(size));
//        } catch (Exception e) {
//            // 记录错误日志
//            System.err.println("查询充值记录失败: " + e.getMessage());
//            e.printStackTrace();
//            // 返回空结果
//            return new PageResult<>(new ArrayList<>(), 0L, Long.valueOf(page), Long.valueOf(size));
//        }
//    }
//
//    /**
//     * 执行消费记录查询
//     */
//    public static PageResult<Map<String, Object>> queryConsumptionRecords(
//            RecordQueryMapper recordQueryMapper, String packageName, Integer page, Integer size) {
//
//        try {
//            // 设置默认分页参数
//            if (page == null || page < 1) {
//                page = 1;
//            }
//            if (size == null || size < 1) {
//                size = 10;
//            }
//
//            // 计算偏移量
//            Integer offset = (page - 1) * size;
//
//            // 查询总数
//            Long total = recordQueryMapper.countConsumptionRecords(packageName);
//            if (total == null || total == 0) {
//                return new PageResult<>(new ArrayList<>(), 0L, Long.valueOf(page), Long.valueOf(size));
//            }
//
//            // 查询数据
//            List<Map<String, Object>> records = recordQueryMapper.queryConsumptionRecords(packageName, offset, size);
//
//            return new PageResult<>(records != null ? records : new ArrayList<>(), total, Long.valueOf(page), Long.valueOf(size));
//        } catch (Exception e) {
//            // 记录错误日志
//            System.err.println("查询消费记录失败: " + e.getMessage());
//            e.printStackTrace();
//            // 返回空结果
//            return new PageResult<>(new ArrayList<>(), 0L, Long.valueOf(page), Long.valueOf(size));
//        }
//    }
//}
