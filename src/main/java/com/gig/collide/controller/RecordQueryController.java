package com.gig.collide.controller;

import com.gig.collide.constant.OrderTypeConstant;
import com.gig.collide.dto.BaseQueryRequest;
import com.gig.collide.service.RecordQueryService;
import com.gig.collide.util.PageResult;
import com.gig.collide.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 记录查询控制器
 * 提供充值记录查询和消费记录查询功能，支持多条件筛选和分页查询
 *
 * @author system
 * @since 2024-01-01
 * @version 1.0
 */
@RestController
@RequestMapping("/api/admin/record-query")
public class RecordQueryController {

    @Autowired
    private RecordQueryService recordQueryService;

    /**
     * 功能：充值记录查询
     * 描述：查询用户用现金购买金币的记录，支持按包名、订单类型等条件筛选，支持分页查询
     * 使用场景：财务统计、充值分析、用户消费行为分析、对账管理、运营数据分析
     *
     * @param request 充值记录查询请求对象，包含查询条件和分页参数
     * @return 充值记录分页响应
     *
     * 订单类型说明：
     * - coin: 金币充值（推荐使用，对应充值记录）
     * 
     * 请求报文：
     * POST /api/admin/record-query/recharge-records
     * {
     *   "packageName": "com.example.app",
     *   "orderType": "coin",
     *   "page": 1,
     *   "size": 10
     * }
     *
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": [
     *       {
     *         "id": 1,
     *         "userId": 1001,
     *         "username": "testuser",
     *         "nickname": "测试用户",
     *         "packageName": "com.example.app",
     *         "orderType": "RECHARGE",
     *         "orderNo": "RC202501270001",
     *         "amount": 99.99,
     *         "coins": 1000,
     *         "paymentMethod": "ALIPAY",
     *         "status": "SUCCESS",
     *         "createTime": "2025-01-27T10:00:00",
     *         "payTime": "2025-01-27T10:05:00",
     *         "remark": "充值1000金币"
     *       }
     *     ],
     *     "total": 100,
     *     "current": 1,
     *     "size": 10,
     *     "message": "查询成功"
     * }
     */
    @PostMapping("/recharge-records")
    public Map<String, Object> queryRechargeRecords(
            @RequestBody RechargeRecordQueryRequest request) {

        try {
            if (request == null) {
                return ResponseUtil.error("请求参数不能为空");
            }

            // 验证订单类型参数
            if (StringUtils.hasText(request.getOrderType()) && 
                !OrderTypeConstant.isValidRechargeOrderType(request.getOrderType())) {
                return ResponseUtil.error("无效的订单类型，充值记录支持的类型：" + OrderTypeConstant.COIN_RECHARGE);
            }

            // 设置默认分页参数
            if (request.getPage() == null || request.getPage() <= 0) {
                request.setPage(1);
            }
            if (request.getSize() == null || request.getSize() <= 0) {
                request.setSize(10);
            }

            PageResult<Map<String, Object>> result = recordQueryService.queryRechargeRecords(
                    request.getPackageName(),
                    request.getOrderType(),
                    request.getPage(),
                    request.getSize()
            );

            // 确保返回所有信息
            if (result != null) {
                return ResponseUtil.success(result, "查询成功");
            } else {
                return ResponseUtil.error("查询结果为空");
            }
        } catch (Exception e) {
            return ResponseUtil.error("查询充值记录失败：" + e.getMessage());
        }
    }

    /**
     * 功能：消费记录查询
     * 描述：查询用户消费平台服务的记录，包括现金购买商品/VIP/内容，以及金币购买任何商品的记录
     * 支持按包名、订单类型等条件筛选，支持分页查询
     * 使用场景：消费统计、用户行为分析、商品销售分析、VIP服务统计、内容消费分析、运营决策支持
     *
     * @param request 消费记录查询请求对象，包含查询条件和分页参数
     * @return 消费记录分页响应
     *
     * 订单类型说明：
     * - goods: 商品购买（购买平台商品）
     * - subscription: VIP订阅（购买VIP会员服务）
     * - content: 内容购买（购买付费内容，如小说、漫画等）
     * 
     * 请求报文：
     * POST /api/admin/record-query/consumption-records
     * {
     *   "packageName": "com.example.app",
     *   "orderType": "goods",
     *   "page": 1,
     *   "size": 10
     * }
     *
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": [
     *       {
     *         "id": 1,
     *         "userId": 1001,
     *         "username": "testuser",
     *         "nickname": "测试用户",
     *         "packageName": "com.example.app",
     *         "orderType": "GOODS_PURCHASE",
     *         "orderNo": "CP202501270001",
     *         "goodsId": 101,
     *         "goodsName": "VIP会员月卡",
     *         "goodsType": "VIP",
     *         "amount": 29.99,
     *         "paymentMethod": "WECHAT",
     *         "paymentType": "CASH",
     *         "status": "SUCCESS",
     *         "createTime": "2025-01-27T10:00:00",
     *         "payTime": "2025-01-27T10:05:00",
     *         "remark": "购买VIP会员月卡"
     *       },
     *       {
     *         "id": 2,
     *         "userId": 1002,
     *         "username": "user2",
     *         "nickname": "用户2",
     *         "packageName": "com.example.app",
     *         "orderType": "CONTENT_PURCHASE",
     *         "orderNo": "CP202501270002",
     *         "contentId": 201,
     *         "contentTitle": "精品小说",
     *         "contentType": "NOVEL",
     *         "amount": 0,
     *         "coins": 100,
     *         "paymentMethod": "COINS",
     *         "paymentType": "COINS",
     *         "status": "SUCCESS",
     *         "createTime": "2025-01-27T11:00:00",
     *         "payTime": "2025-01-27T11:00:00",
     *         "remark": "使用金币购买小说"
     *       }
     *     ],
     *     "total": 150,
     *     "current": 1,
     *     "size": 10,
     *     "message": "查询成功"
     * }
     */
    @PostMapping("/consumption-records")
    public Map<String, Object> queryConsumptionRecords(
            @RequestBody ConsumptionRecordQueryRequest request) {

        try {
            if (request == null) {
                return ResponseUtil.error("请求参数不能为空");
            }

            // 验证订单类型参数
            if (StringUtils.hasText(request.getOrderType()) && 
                !OrderTypeConstant.isValidConsumptionOrderType(request.getOrderType())) {
                return ResponseUtil.error("无效的订单类型，消费记录支持的类型：goods, subscription, content");
            }

            // 设置默认分页参数
            if (request.getPage() == null || request.getPage() <= 0) {
                request.setPage(1);
            }
            if (request.getSize() == null || request.getSize() <= 0) {
                request.setSize(10);
            }

            PageResult<Map<String, Object>> result = recordQueryService.queryConsumptionRecords(
                    request.getPackageName(),
                    request.getOrderType(),
                    request.getPage(),
                    request.getSize()
            );

            // 确保返回所有信息
            if (result != null) {
                return ResponseUtil.success(result, "查询成功");
            } else {
                return ResponseUtil.error("查询结果为空");
            }
        } catch (Exception e) {
            return ResponseUtil.error("查询消费记录失败：" + e.getMessage());
        }
    }

    /**
     * 充值记录查询请求参数
     * 继承BaseQueryRequest获取分页功能，扩展包名和订单类型筛选条件
     *
     * @author system
     * @since 2024-01-01
     * @version 1.0
     */
    public static class RechargeRecordQueryRequest extends BaseQueryRequest {

        /**
         * 包名（应用包名）
         * 用于筛选特定应用的充值记录
         */
        private String packageName;

        /**
         * 订单类型
         * 用于筛选特定类型的充值订单，如：RECHARGE（充值）、REFUND（退款）等
         */
        private String orderType;

        // Getters and Setters
        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        @Override
        public String toString() {
            return "RechargeRecordQueryRequest{" +
                    "packageName='" + packageName + '\'' +
                    ", orderType='" + orderType + '\'' +
                    ", page=" + getPage() +
                    ", size=" + getSize() +
                    '}';
        }
    }

    /**
     * 消费记录查询请求参数
     * 继承BaseQueryRequest获取分页功能，扩展包名和订单类型筛选条件
     *
     * @author system
     * @since 2024-01-01
     * @version 1.0
     */
    public static class ConsumptionRecordQueryRequest extends BaseQueryRequest {

        /**
         * 包名（应用包名）
         * 用于筛选特定应用的消费记录
         */
        private String packageName;

        /**
         * 订单类型
         * 用于筛选特定类型的消费订单，如：GOODS_PURCHASE（商品购买）、
         * CONTENT_PURCHASE（内容购买）、VIP_PURCHASE（VIP购买）等
         */
        private String orderType;

        // Getters and Setters
        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        @Override
        public String toString() {
            return "ConsumptionRecordQueryRequest{" +
                    "packageName='" + packageName + '\'' +
                    ", orderType='" + orderType + '\'' +
                    ", page=" + getPage() +
                    ", size=" + getSize() +
                    '}';
        }
    }
}
