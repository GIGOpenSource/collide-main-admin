package com.gig.collide.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gig.collide.domain.message.Message;
import com.gig.collide.dto.messageDto.InformDTO;
import com.gig.collide.dto.messageDto.InformQueryRequest;
import com.gig.collide.dto.messageDto.MessageDetailDTO;
import com.gig.collide.dto.messageDto.MessageDetailWithCountDTO;
import com.gig.collide.dto.messageDto.MessageSessionDTO;
import com.gig.collide.dto.messageDto.MessageSessionQueryRequest;
import com.gig.collide.dto.messageDto.MessageSimpleDTO;
import com.gig.collide.dto.messageDto.MessageSimpleQueryRequest;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 消息Mapper接口
 *
 * @author system
 * @since 2025-01-27
 * @version 1.0
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {

    /**
     * 查询消息会话列表（支持ID模糊查询）
     *
     * @param request 查询请求
     * @return 消息会话列表
     */
    List<MessageSessionDTO> queryMessageSessions(MessageSessionQueryRequest request);

    /**
     * 根据会话ID查询消息详情
     *
     * @param sessionId 会话ID
     * @return 消息详情列表
     */
    List<MessageDetailDTO> queryMessageDetails(@Param("sessionId") Long sessionId);

    /**
     * 根据ID查询单个消息详情
     *
     * @param id 消息ID
     * @return 消息详情
     */
    MessageDetailDTO getMessageById(@Param("id") Long id);

//    /**
//     * 根据ID查询单个消息详情（支持分页）
//     *
//     * @param id 消息ID
//     * @param size 每页大小
//     * @param offset 偏移量
//     * @return 消息详情列表
//     */
//    List<MessageDetailDTO> getMessageByIdWithPage(@Param("id") Long id, @Param("size") Integer size, @Param("offset") Integer offset);

    /**
     * 根据ID查询单个消息详情（包含对话数量）
     *
     * @param id 消息ID
     * @return 消息详情包含对话数量
     */
    MessageDetailWithCountDTO getMessageByIdWithCount(@Param("id") Long id);

    /**
     * 查询消息简单信息列表（只包含id和status）
     *
     * @param request 查询请求
     * @return 消息简单信息列表
     */
    List<MessageSimpleDTO> queryMessageSimpleList(MessageSimpleQueryRequest request);

    /**
     * 查询消息简单信息总数
     *
     * @param request 查询请求
     * @return 总数
     */
    Long queryMessageSimpleCount(MessageSimpleQueryRequest request);

    /**
     * 根据ID查询单个消息详情（支持分页，包含对话数量）
     *
     * @param id 消息ID
     * @param size 每页大小
     * @param offset 偏移量
     * @return 消息详情列表包含对话数量
     */
    List<MessageDetailWithCountDTO> getMessageByIdWithPageAndCount(@Param("id") Long id, @Param("size") Integer size, @Param("offset") Integer offset);

    /**
     * 查询对话消息数量
     *
     * @param sessionId 会话ID
     * @return 消息数量
     */
    Long getMessageCountBySessionId(@Param("sessionId") Long sessionId);



    /**
     * 查询消息通知列表
     *
     * @param request 查询请求
     * @return 消息通知列表
     */
    List<InformDTO> queryInforms(InformQueryRequest request);

    /**
     * 统计消息通知数量（排除已逻辑删除的记录）
     *
     * @param request 查询请求
     * @return 消息通知总数
     */
    Long countInforms(InformQueryRequest request);


    /**
     * 根据ID查询单个通知
     *
     * @param id 通知ID
     * @return 通知信息
     */
    InformDTO getInformById(@Param("id") Long id);

    /**
     * 根据ID删除消息通知（逻辑删除）
     *
     * @param id 通知ID
     * @return 影响行数
     */
    int deleteInformById(@Param("id") Long id);

    /**
     * 创建消息详情
     *
     * @param message 消息实体
     * @return 影响行数
     */
    int insertMessage(Message message);

    /**
     * 更新消息详情
     *
     * @param message 消息实体
     * @return 影响行数
     */
    int updateMessage(Message message);

//    /**
//     * 根据ID删除消息详情
//     *
//     * @param id 消息ID
//     * @return 影响行数
//     */
//    int deleteMessageById(@Param("id") Long id);

    /**
     * 创建消息通知
     *
     * @param inform 通知DTO
     * @return 影响行数
     */
    int insertInform(InformDTO inform);

    /**
     * 根据会话ID查询消息详情（支持时间范围查询）
     *
     * @param sessionId 会话ID
     * @param startTime 开始时间（可选）
     * @param endTime 结束时间（可选）
     * @param size 每页大小
     * @param offset 偏移量
     * @return 消息详情列表
     */
    List<MessageDetailDTO> queryMessagesBySessionIdWithTimeRange(@Param("sessionId") Long sessionId,
                                                                 @Param("startTime") String startTime,
                                                                 @Param("endTime") String endTime,
                                                                 @Param("size") Integer size,
                                                                 @Param("offset") Integer offset);

    /**
     * 根据会话ID统计消息数量（支持时间范围查询）
     *
     * @param sessionId 会话ID
     * @param startTime 开始时间（可选）
     * @param endTime 结束时间（可选）
     * @return 消息数量
     */
    Long countMessagesBySessionIdWithTimeRange(@Param("sessionId") Long sessionId,
                                               @Param("startTime") String startTime,
                                               @Param("endTime") String endTime);

    List<MessageDetailDTO> getMessageByIdWithPage(@Param("id") Long id, @Param("size") Integer size, @Param("offset") Integer offset);

}

