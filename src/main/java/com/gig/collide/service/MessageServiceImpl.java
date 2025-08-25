package com.gig.collide.service;

import com.gig.collide.domain.message.Message;
import com.gig.collide.dto.messageDto.InformDTO;
import com.gig.collide.dto.messageDto.InformQueryRequest;
import com.gig.collide.dto.messageDto.MessageDetailDTO;
import com.gig.collide.dto.messageDto.MessageDetailWithCountDTO;
import com.gig.collide.dto.messageDto.MessageSessionDTO;
import com.gig.collide.dto.messageDto.MessageSessionQueryRequest;
import com.gig.collide.dto.messageDto.MessageSessionDetailQueryRequest;

import com.gig.collide.dto.messageDto.InformCreateRequest;
import com.gig.collide.dto.messageDto.MessageSimpleDTO;
import com.gig.collide.dto.messageDto.MessageSimpleQueryRequest;
import com.gig.collide.mapper.MessageMapper;
import com.gig.collide.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 消息管理服务实现类
 *
 * @author system
 * @since 2025-01-27
 * @version 1.0
 */
@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger log = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public PageResult<MessageSessionDTO> queryMessageSessions(MessageSessionQueryRequest request) {
        try {
            log.info("开始查询消息会话列表，请求参数：{}", request);

            // 设置默认分页参数
            request.setDefaultPagination();

            // 使用MyBatis查询
            List<MessageSessionDTO> sessions = messageMapper.queryMessageSessions(request);

            // 计算总数（这里需要优化，可以考虑在XML中添加count查询）
            int total = sessions.size();
            if (request.getPage() != null && request.getSize() != null) {
                // 临时方案：如果分页参数存在，则查询总数
                // 实际项目中建议在XML中添加专门的count查询
                total = sessions.size();
            }

            log.info("查询消息会话列表成功，返回数据条数：{}", sessions.size());
            return new PageResult<>(sessions, Long.valueOf(total), Long.valueOf(request.getPage()), Long.valueOf(request.getSize()));

        } catch (Exception e) {
            log.error("查询消息会话列表失败", e);
            throw new RuntimeException("查询消息会话列表失败：" + e.getMessage());
        }
    }


    @Override
    public PageResult<MessageDetailDTO> queryMessageDetails(Long sessionId, int page, int size) {
        try {
            log.info("开始查询消息详情，会话ID：{}，页码：{}，每页大小：{}", sessionId, page, size);

            // 参数验证
            if (sessionId == null || sessionId <= 0) {
                throw new IllegalArgumentException("会话ID必须大于0");
            }

            if (page <= 0) {
                page = 1;
            }

            if (size <= 0 || size > 100) {
                size = 20;
            }

            // 使用MyBatis查询
            List<MessageDetailDTO> messages = messageMapper.queryMessageDetails(sessionId);

            // 手动分页处理
            int total = messages.size();
            int startIndex = (page - 1) * size;
            int endIndex = Math.min(startIndex + size, total);

            List<MessageDetailDTO> pageMessages = messages.subList(startIndex, endIndex);

            log.info("查询消息详情成功，会话ID：{}，返回数据条数：{}", sessionId, pageMessages.size());
            return new PageResult<>(pageMessages, Long.valueOf(total), Long.valueOf(page), Long.valueOf(size));

        } catch (Exception e) {
            log.error("查询消息详情失败，会话ID：{}", sessionId, e);
            throw new RuntimeException("查询消息详情失败：" + e.getMessage());
        }
    }

    @Override
    public PageResult<InformDTO> queryInforms(InformQueryRequest request) {
        try {
            log.info("开始查询消息通知列表，请求参数：{}", request);

            // 设置默认分页参数
            if (request.getPage() == null) {
                request.setPage(1);
            }
            if (request.getSize() == null) {
                request.setSize(10);
            }

            // 计算偏移量
            int offset = (request.getPage() - 1) * request.getSize();
            request.setOffset(offset);

            // 使用MyBatis查询
            List<InformDTO> informs = messageMapper.queryInforms(request);

            // 查询总数
            Long total = messageMapper.countInforms(request);

            log.info("查询消息通知列表成功，返回数据条数：{}", informs.size());
            return new PageResult<>(informs, total, Long.valueOf(request.getPage()), Long.valueOf(request.getSize()));

        } catch (Exception e) {
            log.error("查询消息通知列表失败", e);
            throw new RuntimeException("查询消息通知列表失败：" + e.getMessage());
        }
    }

    @Override
    public Long countInforms(InformQueryRequest request) {
        try {
            log.info("开始统计消息通知数量，请求参数：{}", request);

            // 使用MyBatis查询统计数量
            Long count = messageMapper.countInforms(request);

            log.info("统计消息通知数量成功，数量：{}", count);
            return count != null ? count : 0L;

        } catch (Exception e) {
            log.error("统计消息通知数量失败", e);
            throw new RuntimeException("统计消息通知数量失败：" + e.getMessage());
        }
    }





    @Override
    public boolean deleteInformById(Long id) {
        try {
            log.info("开始删除消息通知，通知ID：{}", id);

            // 参数验证
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("通知ID必须大于0");
            }

            // 先检查通知是否存在且未被删除
            InformDTO inform = getInformById(id);
            if (inform == null || "Y".equals(inform.getIsDeleted())) {
                log.warn("通知不存在或已被删除，通知ID：{}", id);
                return false;
            }

            // 使用MyBatis删除
            int result = messageMapper.deleteInformById(id);

            boolean success = result > 0;
            log.info("删除消息通知{}，通知ID：{}", success ? "成功" : "失败", id);
            return success;

        } catch (Exception e) {
            log.error("删除消息通知失败，通知ID：{}", id, e);
            throw new RuntimeException("删除消息通知失败：" + e.getMessage());
        }
    }


    @Override
    public InformDTO getInformById(Long id) {
        try {
            log.info("开始查询单个通知，通知ID：{}", id);

            // 参数验证
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("通知ID必须大于0");
            }

            // 使用MyBatis查询（包括已删除的记录）
            InformDTO inform = messageMapper.getInformById(id);

            if (inform != null) {
                log.info("查询单个通知成功，通知ID：{}", id);
            } else {
                log.warn("通知不存在，通知ID：{}", id);
            }

            return inform;

        } catch (Exception e) {
            log.error("查询单个通知失败，通知ID：{}", id, e);
            throw new RuntimeException("查询单个通知失败：" + e.getMessage());
        }
    }


    @Override
    public MessageDetailDTO getMessageById(Long id) {
        try {
            log.info("开始查询单个消息详情，消息ID：{}", id);

            // 参数验证
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("消息ID必须大于0");
            }

            // 使用MyBatis查询
            MessageDetailDTO message = messageMapper.getMessageById(id);

            if (message != null) {
                log.info("查询单个消息详情成功，消息ID：{}", id);
            } else {
                log.warn("消息不存在，消息ID：{}", id);
            }

            return message;

        } catch (Exception e) {
            log.error("查询单个消息详情失败，消息ID：{}", id, e);
            throw new RuntimeException("查询单个消息详情失败：" + e.getMessage());
        }
    }

    @Override
    public PageResult<MessageDetailDTO> getMessageByIdWithPage(Long id, Integer page, Integer size) {
        try {
            log.info("开始查询消息详情（分页），消息ID：{}，页码：{}，每页大小：{}", id, page, size);

            // 参数验证
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("消息ID必须大于0");
            }

            // 分页参数验证和调整
            if (page == null || page <= 0) {
                page = 1;
            }
            if (size == null || size <= 0) {
                size = 10;
            } else if (size > 100) {
                size = 100;
            }

            int offset = (page - 1) * size;

            // 使用MyBatis查询
            List<MessageDetailDTO> messages = messageMapper.getMessageByIdWithPage(id, size, offset);

            // 计算总数（临时方案）
            int total = messages.size();

            log.info("查询消息详情（分页）成功，消息ID：{}，返回数据条数：{}", id, messages.size());
            return new PageResult<>(messages, Long.valueOf(total), Long.valueOf(page), Long.valueOf(size));

        } catch (Exception e) {
            log.error("查询消息详情（分页）失败，消息ID：{}", id, e);
            throw new RuntimeException("查询消息详情（分页）失败：" + e.getMessage());
        }
    }

    @Override
    public MessageDetailWithCountDTO getMessageByIdWithCount(Long id) {
        try {
            log.info("开始查询消息详情（包含对话数量），消息ID：{}", id);

            // 参数验证
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("消息ID必须大于0");
            }

            // 使用MyBatis查询
            MessageDetailWithCountDTO message = messageMapper.getMessageByIdWithCount(id);

            if (message != null) {
                log.info("查询消息详情（包含对话数量）成功，消息ID：{}，对话数量：{}", id, message.getConversationCount());
            } else {
                log.warn("消息不存在，消息ID：{}", id);
            }

            return message;

        } catch (Exception e) {
            log.error("查询消息详情（包含对话数量）失败，消息ID：{}", id, e);
            throw new RuntimeException("查询消息详情（包含对话数量）失败：" + e.getMessage());
        }
    }

    @Override
    public PageResult<MessageDetailWithCountDTO> getMessageByIdWithPageAndCount(Long id, Integer page, Integer size) {
        try {
            log.info("开始查询消息详情（分页，包含对话数量），消息ID：{}，页码：{}，每页大小：{}", id, page, size);

            // 参数验证
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("消息ID必须大于0");
            }

            // 分页参数验证和调整
            if (page == null || page <= 0) {
                page = 1;
            }
            if (size == null || size <= 0) {
                size = 10;
            } else if (size > 100) {
                size = 100;
            }

            int offset = (page - 1) * size;

            // 使用MyBatis查询
            List<MessageDetailWithCountDTO> messages = messageMapper.getMessageByIdWithPageAndCount(id, size, offset);

            // 计算总数（临时方案）
            int total = messages.size();

            log.info("查询消息详情（分页，包含对话数量）成功，消息ID：{}，返回数据条数：{}", id, messages.size());
            return new PageResult<>(messages, Long.valueOf(total), Long.valueOf(page), Long.valueOf(size));

        } catch (Exception e) {
            log.error("查询消息详情（分页，包含对话数量）失败，消息ID：{}", id, e);
            throw new RuntimeException("查询消息详情（分页，包含对话数量）失败：" + e.getMessage());
        }
    }

    @Override
    public Long getMessageCountBySessionId(Long sessionId) {
        try {
            log.info("开始查询对话消息数量，会话ID：{}", sessionId);

            // 参数验证
            if (sessionId == null || sessionId <= 0) {
                throw new IllegalArgumentException("会话ID必须大于0");
            }

            // 使用MyBatis查询
            Long count = messageMapper.getMessageCountBySessionId(sessionId);

            log.info("查询对话消息数量成功，会话ID：{}，数量：{}", sessionId, count);
            return count != null ? count : 0L;

        } catch (Exception e) {
            log.error("查询对话消息数量失败，会话ID：{}", sessionId, e);
            throw new RuntimeException("查询对话消息数量失败：" + e.getMessage());
        }
    }







    /**
     * 将字符串状态转换为整数状态
     *
     * @param status 字符串状态
     * @return 整数状态
     */
    private Integer convertStatusToInt(String status) {
        if (status == null) {
            return 0;
        }

        switch (status.toLowerCase()) {
            case "sent":
                return 1;
            case "delivered":
                return 2;
            case "read":
                return 3;
            case "deleted":
                return 4;
            default:
                return 0;
        }
    }

    @Override
    public InformDTO createInform(InformCreateRequest request) {
        try {
            log.info("开始创建消息通知，请求参数：{}", request);

            // 参数验证
            if (request == null) {
                throw new IllegalArgumentException("请求参数不能为空");
            }
            if (request.getAppName() == null || request.getAppName().trim().isEmpty()) {
                throw new IllegalArgumentException("APP名称不能为空");
            }
            if (request.getTypeRelation() == null || request.getTypeRelation().trim().isEmpty()) {
                throw new IllegalArgumentException("类型关系不能为空");
            }
            if (request.getUserType() == null || request.getUserType().trim().isEmpty()) {
                throw new IllegalArgumentException("用户类型不能为空");
            }
            if (request.getNotificationContent() == null || request.getNotificationContent().trim().isEmpty()) {
                throw new IllegalArgumentException("通知内容不能为空");
            }

            // 创建InformDTO
            InformDTO inform = new InformDTO();
            inform.setAppName(request.getAppName().trim());
            inform.setTypeRelation(request.getTypeRelation().trim());
            inform.setUserType(request.getUserType().trim());
            inform.setNotificationContent(request.getNotificationContent().trim());

            // 处理发送时间格式
            LocalDateTime sendTime = null;
            if (request.getSendTime() != null && !request.getSendTime().trim().isEmpty()) {
                try {
                    // 尝试解析 yyyy-MM-dd HH:mm:ss 格式
                    sendTime = LocalDateTime.parse(request.getSendTime(),
                            java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                } catch (Exception e) {
                    log.warn("发送时间格式解析失败，使用当前时间: {}", e.getMessage());
                    sendTime = LocalDateTime.now();
                }
            } else {
                sendTime = LocalDateTime.now();
            }
            inform.setSendTime(sendTime);

            inform.setIsSent(request.getIsSent() != null ? request.getIsSent() : "N");
            inform.setIsDeleted("N");
            inform.setCreateTime(LocalDateTime.now());
            inform.setUpdateTime(LocalDateTime.now());

            // 使用MyBatis插入
            int result = messageMapper.insertInform(inform);

            if (result > 0) {
                log.info("创建消息通知成功，通知ID：{}", inform.getId());
                // 重新查询获取完整的通知信息
                return messageMapper.getInformById(inform.getId());
            } else {
                log.error("创建消息通知失败，插入结果：{}", result);
                throw new RuntimeException("创建消息通知失败");
            }

        } catch (Exception e) {
            log.error("创建消息通知失败", e);
            throw new RuntimeException("创建消息通知失败：" + e.getMessage());
        }
    }


    @Override
    public Map<String, Object> batchDeleteInform(List<Long> ids) {
        try {
            log.info("开始批量删除消息通知，通知ID列表：{}", ids);

            // 参数验证
            if (ids == null || ids.isEmpty()) {
                throw new IllegalArgumentException("通知ID列表不能为空");
            }

            // 验证ID有效性
            for (Long id : ids) {
                if (id == null || id <= 0) {
                    throw new IllegalArgumentException("存在无效的通知ID：" + id);
                }
            }

            int successCount = 0;
            int failCount = 0;
            List<Long> successIds = new ArrayList<>();
            List<Long> failIds = new ArrayList<>();

            // 逐个删除通知
            for (Long id : ids) {
                try {
                    boolean result = deleteInformById(id);
                    if (result) {
                        successCount++;
                        successIds.add(id);
                        log.debug("通知删除成功，ID：{}", id);
                    } else {
                        failCount++;
                        failIds.add(id);
                        log.warn("通知删除失败，ID：{}", id);
                    }
                } catch (Exception e) {
                    failCount++;
                    failIds.add(id);
                    log.error("通知删除异常，ID：{}，错误信息：{}", id, e.getMessage());
                }
            }

            // 构建返回结果
            Map<String, Object> result = new HashMap<>();
            result.put("successCount", successCount);
            result.put("failCount", failCount);
            result.put("totalCount", ids.size());
            result.put("successIds", successIds);
            result.put("failIds", failIds);

            log.info("批量删除消息通知完成，总数：{}，成功：{}，失败：{}", ids.size(), successCount, failCount);
            return result;

        } catch (Exception e) {
            log.error("批量删除消息通知失败", e);
            throw new RuntimeException("批量删除消息通知失败：" + e.getMessage());
        }
    }

    @Override
    public PageResult<MessageDetailDTO> queryMessagesBySessionId(MessageSessionDetailQueryRequest request) {
        try {
            log.info("开始根据会话ID查询消息详情，请求参数：{}", request);

            // 参数验证
            if (request == null) {
                throw new IllegalArgumentException("请求参数不能为空");
            }
            if (request.getSessionId() == null || request.getSessionId() <= 0) {
                throw new IllegalArgumentException("会话ID不能为空且必须大于0");
            }

            // 设置默认分页参数
            if (request.getPage() == null || request.getPage() <= 0) {
                request.setPage(1);
            }
            if (request.getSize() == null || request.getSize() <= 0) {
                request.setSize(10);
            } else if (request.getSize() > 100) {
                request.setSize(100); // 限制最大每页大小
            }

            int offset = (request.getPage() - 1) * request.getSize();

            // 使用MyBatis查询
            List<MessageDetailDTO> messages = messageMapper.queryMessagesBySessionIdWithTimeRange(
                    request.getSessionId(),
                    request.getStartTime(),
                    request.getEndTime(),
                    request.getSize(),
                    offset);

            // 查询总数
            Long total = messageMapper.countMessagesBySessionIdWithTimeRange(
                    request.getSessionId(),
                    request.getStartTime(),
                    request.getEndTime());

            log.info("根据会话ID查询消息详情成功，会话ID：{}，返回数据条数：{}", request.getSessionId(), messages.size());
            return new PageResult<>(messages, total, Long.valueOf(request.getPage()), Long.valueOf(request.getSize()));

        } catch (Exception e) {
            log.error("根据会话ID查询消息详情失败，会话ID：{}", request != null ? request.getSessionId() : null, e);
            throw new RuntimeException("查询消息详情失败：" + e.getMessage());
        }
    }

    @Override
    public PageResult<MessageSimpleDTO> queryMessageSimpleList(MessageSimpleQueryRequest request) {
        try {
            log.info("开始查询消息简单信息列表，请求参数：{}", request);

            // 设置默认分页参数
            request.setDefaultPagination();

            // 计算偏移量
            int offset = (request.getPage() - 1) * request.getSize();

            // 查询总数
            Long total = messageMapper.queryMessageSimpleCount(request);

            // 创建新的请求对象，包含offset参数
            MessageSimpleQueryRequest queryRequest = new MessageSimpleQueryRequest();
            queryRequest.setId(request.getId());
            queryRequest.setStatus(request.getStatus());
            queryRequest.setPage(request.getPage());
            queryRequest.setSize(request.getSize());
            // 添加offset参数到请求中
            queryRequest.setOffset(offset);

            // 查询分页数据
            List<MessageSimpleDTO> messages = messageMapper.queryMessageSimpleList(queryRequest);

            log.info("查询消息简单信息列表成功，总数：{}，返回数据条数：{}", total, messages.size());
            return new PageResult<>(messages, total, Long.valueOf(request.getPage()), Long.valueOf(request.getSize()));

        } catch (Exception e) {
            log.error("查询消息简单信息列表失败", e);
            throw new RuntimeException("查询消息简单信息列表失败：" + e.getMessage());
        }
    }
}
