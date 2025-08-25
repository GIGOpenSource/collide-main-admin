package com.gig.collide.service;

import com.gig.collide.dto.messageDto.*;
import com.gig.collide.util.PageResult;

import java.util.List;
import java.util.Map;

/**
 * 消息管理服务接口
 */
public interface MessageService {

    /**
     * 查询消息会话列表
     *
     * @param request 查询请求
     * @return 分页结果
     */
    PageResult<MessageSessionDTO> queryMessageSessions(MessageSessionQueryRequest request);

    /**
     * 根据会话ID查询消息详情
     *
     * @param sessionId 会话ID
     * @param page 页码
     * @param size 每页大小
     * @return 分页结果
     */
    PageResult<MessageDetailDTO> queryMessageDetails(Long sessionId, int page, int size);

    /**
     * 查询消息通知列表
     *
     * @param request 查询请求
     * @return 分页结果
     */
    PageResult<InformDTO> queryInforms(InformQueryRequest request);

    /**
     * 统计消息通知数量
     *
     * @param request 查询请求
     * @return 消息通知总数
     */
    Long countInforms(InformQueryRequest request);

    /**
     * 根据ID删除消息通知
     *
     * @param id 通知ID
     * @return 是否删除成功
     */
    boolean deleteInformById(Long id);

    /**
     * 根据ID查询单个通知
     *
     * @param id 通知ID
     * @return 通知信息，如果不存在返回null
     */
    InformDTO getInformById(Long id);

    /**
     * 根据ID查询单个消息详情
     *
     * @param id 消息ID
     * @return 消息详情，如果不存在返回null
     */
    MessageDetailDTO getMessageById(Long id);

    /**
     * 根据ID查询单个消息详情（支持分页）
     *
     * @param id 消息ID
     * @param page 页码
     * @param size 每页大小
     * @return 分页结果
     */
    PageResult<MessageDetailDTO> getMessageByIdWithPage(Long id, Integer page, Integer size);

    /**
     * 根据ID查询单个消息详情（包含对话数量）
     *
     * @param id 消息ID
     * @return 消息详情包含对话数量，如果不存在返回null
     */
    MessageDetailWithCountDTO getMessageByIdWithCount(Long id);

    /**
     * 查询消息简单信息列表（只包含id和status）
     *
     * @param request 查询请求
     * @return 分页结果
     */
    PageResult<MessageSimpleDTO> queryMessageSimpleList(MessageSimpleQueryRequest request);

    /**
     * 根据ID查询单个消息详情（支持分页，包含对话数量）
     *
     * @param id 消息ID
     * @param page 页码
     * @param size 每页大小
     * @return 分页结果
     */
    PageResult<MessageDetailWithCountDTO> getMessageByIdWithPageAndCount(Long id, Integer page, Integer size);

    /**
     * 查询对话消息数量
     *
     * @param sessionId 会话ID
     * @return 消息数量
     */
    Long getMessageCountBySessionId(Long sessionId);





    /**
     * 创建消息通知
     *
     * @param request 创建请求
     * @return 创建的通知信息
     */
    InformDTO createInform(InformCreateRequest request);

    /**
     * 批量删除消息通知
     *
     * @param ids 通知ID列表
     * @return 批量删除结果
     */
    Map<String, Object> batchDeleteInform(List<Long> ids);

    /**
     * 根据会话ID查询消息详情（支持时间范围查询）
     *
     * @param request 查询请求
     * @return 分页结果
     */
    PageResult<MessageDetailDTO> queryMessagesBySessionId(MessageSessionDetailQueryRequest request);

}
