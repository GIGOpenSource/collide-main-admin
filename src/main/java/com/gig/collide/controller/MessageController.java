package com.gig.collide.controller;

import com.gig.collide.dto.messageDto.InformDTO;
import com.gig.collide.dto.messageDto.InformQueryRequest;
import com.gig.collide.dto.messageDto.MessageDetailDTO;
import com.gig.collide.dto.messageDto.MessageSessionDTO;
import com.gig.collide.dto.messageDto.MessageSessionQueryRequest;
import com.gig.collide.dto.messageDto.InformCreateRequest;
import com.gig.collide.dto.messageDto.MessageSessionDetailQueryRequest;
import com.gig.collide.dto.messageDto.MessageSimpleDTO;
import com.gig.collide.dto.messageDto.MessageSimpleQueryRequest;
import com.gig.collide.service.MessageService;
import com.gig.collide.util.PageResult;
import com.gig.collide.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息管理Controller
 * 提供消息会话查询、消息详情查询、消息通知管理等功能
 *
 * @author system
 * @since 2025-01-27
 * @version 1.0
 */
@RestController
@RequestMapping("/api/admin/message")
public class MessageController {

    private static final Logger log = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;

    /**
     * 功能：消息会话列表查询
     * 描述：分页查询消息会话列表，支持多种条件筛选
     * 使用场景：消息管理、用户对话列表、消息统计
     *
     * @param request 查询请求参数
     * @return 消息会话分页响应
     */
    @PostMapping("/sessions")
    public Map<String, Object> queryMessageSessions(@RequestBody MessageSessionQueryRequest request) {
        try {
            // 设置默认分页参数
            if (request.getPage() == null) {
                request.setPage(1);
            }
            if (request.getSize() == null) {
                request.setSize(10);
            }

            PageResult<MessageSessionDTO> result = messageService.queryMessageSessions(request);
            return ResponseUtil.success(result, "查询成功");
        } catch (Exception e) {
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 功能：根据会话ID查询消息详情（支持时间范围查询）
     * 描述：根据会话ID获取发送用户ID、消息类型、消息内容和发送时间，支持时间范围筛选
     * 使用场景：私聊管理、消息查询、时间范围筛选
     *
     * @param request 查询请求
     * @return 消息详情分页响应
     *
     * 请求报文：
     * POST /api/admin/message/session/details
     * {
     *   "sessionId": 1,
     *   "startTime": "2025-01-01 00:00:00",
     *   "endTime": "2025-01-31 23:59:59",
     *   "page": 1,
     *   "size": 10
     * }
     *
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": {
     *     "data": [
     *       {
     *         "id": 1,
     *         "messageSequence": 1,
     *         "senderId": 10001,
     *         "receiverId": 10002,
     *         "messageType": "text",
     *         "content": "你好！看到你发布的Python数据分析文章很有收获",
     *         "status": "read",
     *         "isRead": 1,
     *         "readTime": "2024-08-19T14:30:25",
     *         "createTime": "2024-08-19T14:30:25"
     *       }
     *     ],
     *     "total": 50,
     *     "current": 1,
     *     "size": 10,
     *     "pages": 5
     *   },
     *   "message": "查询成功"
     * }
     */
    @PostMapping("/session/details")
    public Map<String, Object> querySessionMessageDetails(@RequestBody MessageSessionDetailQueryRequest request) {
        try {
            log.info("接收到查询会话消息详情请求，请求参数：{}", request);

            // 参数验证
            if (request == null) {
                log.warn("查询会话消息详情参数错误，请求参数为空");
                return ResponseUtil.error("参数错误：请求参数不能为空");
            }

            // 验证sessionId是否为空
            if (request.getSessionId() == null || request.getSessionId() <= 0) {
                log.warn("查询会话消息详情参数错误，sessionId不能为空或小于等于0");
                return ResponseUtil.error("参数错误：sessionId不能为空且必须大于0");
            }

            // 设置默认分页参数
            if (request.getPage() == null) {
                request.setPage(1);
            }
            if (request.getSize() == null) {
                request.setSize(10);
            }

            PageResult<MessageDetailDTO> result = messageService.queryMessagesBySessionId(request);
            return ResponseUtil.pageSuccessWithRecords(
                    result.getData(),
                    result.getTotal(),
                    result.getCurrent(),
                    result.getSize(),
                    result.getPages(),
                    "查询成功"
            );

        } catch (IllegalArgumentException e) {
            log.warn("查询会话消息详情参数错误，错误信息：{}", e.getMessage());
            return ResponseUtil.error("参数错误：" + e.getMessage());
        } catch (RuntimeException e) {
            log.error("查询会话消息详情时发生运行时异常，错误信息：{}", e.getMessage(), e);
            return ResponseUtil.error("查询失败：" + e.getMessage());
        } catch (Exception e) {
            log.error("查询会话消息详情时发生未知异常", e);
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 功能：查询消息通知列表
     * 描述：分页查询消息通知，支持多种条件筛选
     * 使用场景：消息通知管理、通知统计、通知查询
     *
     * @param request 查询请求参数
     * @return 消息通知分页响应
     */
    @PostMapping("/informs")
    public Map<String, Object> queryInforms(@RequestBody InformQueryRequest request) {
        try {
            // 设置默认分页参数
            if (request.getPage() == null) {
                request.setPage(1);
            }
            if (request.getSize() == null) {
                request.setSize(10);
            }

            PageResult<InformDTO> result = messageService.queryInforms(request);
            return ResponseUtil.success(result, "查询成功");
        } catch (Exception e) {
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 功能：根据ID查询单个通知
     * 描述：根据通知ID查询通知的详细信息
     * 使用场景：通知详情查看、通知编辑、调试验证
     *
     * @param id 通知ID
     * @return 通知详细信息
     */
    @GetMapping("/inform/{id}")
    public Map<String, Object> getInformById(@PathVariable Long id) {
        try {
            log.info("接收到查询通知请求，通知ID：{}", id);

            // 参数验证
            if (id == null || id <= 0) {
                log.warn("查询通知参数错误，ID：{}", id);
                return ResponseUtil.error("参数错误：通知ID必须大于0");
            }

            InformDTO inform = messageService.getInformById(id);
            if (inform != null) {
                log.info("通知查询成功，ID：{}", id);
                return ResponseUtil.success(inform, "查询成功");
            } else {
                log.warn("通知不存在，ID：{}", id);
                return ResponseUtil.error("查询失败：通知不存在");
            }
        } catch (Exception e) {
            log.error("查询通知时发生异常，ID：{}", id, e);
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 功能：根据ID删除消息通知
     * 描述：逻辑删除指定的消息通知（将is_deleted设置为Y）
     * 使用场景：消息通知管理、通知清理
     *
     * @param id 通知ID
     * @return 删除操作结果
     */
    @DeleteMapping("/inform/{id}")
    public Map<String, Object> deleteInform(@PathVariable Long id) {
        try {
            log.info("接收到删除通知请求，通知ID：{}", id);

            // 参数验证
            if (id == null || id <= 0) {
                log.warn("删除通知参数错误，ID：{}", id);
                return ResponseUtil.error("参数错误：通知ID必须大于0");
            }

            boolean result = messageService.deleteInformById(id);
            if (result) {
                log.info("通知删除成功，ID：{}", id);
                return ResponseUtil.success(true, "删除成功");
            } else {
                log.warn("通知删除失败，ID：{}", id);
                return ResponseUtil.error("删除失败：通知不存在或已被删除");
            }
        } catch (Exception e) {
            log.error("删除通知时发生异常，ID：{}", id, e);
            return ResponseUtil.error("删除失败：" + e.getMessage());
        }
    }

    /**
     * 功能：批量删除消息通知
     * 描述：根据ID列表批量逻辑删除消息通知
     * 使用场景：批量通知管理、批量清理
     *
     * @param request 包含ID列表的请求
     * @return 批量删除结果
     */
    @DeleteMapping("/inform/batch")
    public Map<String, Object> batchDeleteInform(@RequestBody Map<String, Object> request) {
        try {
            log.info("接收到批量删除通知请求，请求参数：{}", request);

            // 参数验证
            if (request == null || !request.containsKey("ids")) {
                log.warn("批量删除通知参数错误，缺少ids字段");
                return ResponseUtil.error("参数错误：缺少ids字段");
            }

            @SuppressWarnings("unchecked")
            List<Long> ids = (List<Long>) request.get("ids");
            if (ids == null || ids.isEmpty()) {
                log.warn("批量删除通知参数错误，ids列表为空");
                return ResponseUtil.error("参数错误：ids列表不能为空");
            }

            // 验证ID有效性
            for (Long id : ids) {
                if (id == null || id <= 0) {
                    log.warn("批量删除通知参数错误，存在无效ID：{}", id);
                    return ResponseUtil.error("参数错误：存在无效的通知ID");
                }
            }

            // 调用Service进行批量删除
            Map<String, Object> result = messageService.batchDeleteInform(ids);

            int successCount = (Integer) result.get("successCount");
            int failCount = (Integer) result.get("failCount");
            int totalCount = (Integer) result.get("totalCount");

            log.info("批量删除通知完成，总数：{}，成功：{}，失败：{}", totalCount, successCount, failCount);
            return ResponseUtil.success(result, String.format("批量删除完成，成功%d条，失败%d条", successCount, failCount));

        } catch (IllegalArgumentException e) {
            log.warn("批量删除通知参数错误，错误信息：{}", e.getMessage());
            return ResponseUtil.error("参数错误：" + e.getMessage());
        } catch (RuntimeException e) {
            log.error("批量删除通知时发生运行时异常，错误信息：{}", e.getMessage(), e);
            return ResponseUtil.error("删除失败：" + e.getMessage());
        } catch (Exception e) {
            log.error("批量删除通知时发生未知异常", e);
            return ResponseUtil.error("删除失败：" + e.getMessage());
        }
    }

    /**
     * 功能：创建消息通知
     * 描述：创建新的消息通知，包含APP名称、类型关系、用户类型、通知内容等
     * 使用场景：系统通知、用户通知、消息通知管理
     *
     * @param request 创建请求
     * @return 创建结果
     */
    @PostMapping("/inform/create")
    public Map<String, Object> createInform(@RequestBody InformCreateRequest request) {
        try {
            log.info("接收到创建消息通知请求，请求参数：{}", request);

            // 参数验证
            if (request == null) {
                log.warn("创建消息通知参数错误，请求参数为空");
                return ResponseUtil.error("参数错误：请求参数不能为空");
            }

            InformDTO inform = messageService.createInform(request);

            log.info("创建消息通知成功，通知ID：{}", inform.getId());
            return ResponseUtil.success(inform, "创建成功");

        } catch (IllegalArgumentException e) {
            log.warn("创建消息通知参数错误，错误信息：{}", e.getMessage());
            return ResponseUtil.error("参数错误：" + e.getMessage());
        } catch (RuntimeException e) {
            log.error("创建消息通知时发生运行时异常，错误信息：{}", e.getMessage(), e);
            return ResponseUtil.error("创建失败：" + e.getMessage());
        } catch (Exception e) {
            log.error("创建消息通知时发生未知异常", e);
            return ResponseUtil.error("创建失败：" + e.getMessage());
        }
    }

    /**
     * 功能：查询消息简单信息列表
     * 描述：分页查询消息的id和status字段，支持按id和status筛选
     * 使用场景：消息状态查询、消息管理、状态统计
     *
     * @param request 查询请求参数
     * @return 消息简单信息分页响应
     *
     * 请求报文：
     * POST /api/admin/message/simple/list
     * {
     *   "id": 1,
     *   "status": "sent",
     *   "page": 1,
     *   "size": 10
     * }
     *
     * 响应报文：
     * {
     *   "code": 0,
     *   "data": {
     *     "data": [
     *       {
     *         "id": 1,
     *         "status": "sent"
     *       },
     *       {
     *         "id": 2,
     *         "status": "read"
     *       }
     *     ],
     *     "total": 100,
     *     "size": 10,
     *     "current": 1,
     *     "pages": 10
     *   },
     *   "message": "查询成功"
     * }
     */
    @PostMapping("/simple/list")
    public Map<String, Object> queryMessageSimpleList(@RequestBody MessageSimpleQueryRequest request) {
        try {
            log.info("接收到查询消息简单信息列表请求，请求参数：{}", request);

            // 参数验证
            if (request == null) {
                log.warn("查询消息简单信息列表参数错误，请求参数为空");
                return ResponseUtil.error("参数错误：请求参数不能为空");
            }

            // 调用Service进行查询
            PageResult<MessageSimpleDTO> result = messageService.queryMessageSimpleList(request);

            log.info("查询消息简单信息列表成功，总数：{}，返回数据条数：{}", result.getTotal(), result.getData().size());
            return ResponseUtil.pageSuccessWithRecords(
                    result.getData(),
                    result.getTotal(),
                    result.getCurrent(),
                    result.getSize(),
                    result.getPages(),
                    "查询成功"
            );

        } catch (IllegalArgumentException e) {
            log.warn("查询消息简单信息列表参数错误，错误信息：{}", e.getMessage());
            return ResponseUtil.error("参数错误：" + e.getMessage());
        } catch (RuntimeException e) {
            log.error("查询消息简单信息列表时发生运行时异常，错误信息：{}", e.getMessage(), e);
            return ResponseUtil.error("查询失败：" + e.getMessage());
        } catch (Exception e) {
            log.error("查询消息简单信息列表时发生未知异常", e);
            return ResponseUtil.error("查询失败：" + e.getMessage());
        }
    }
}
