package com.gig.collide.domain.content;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 评论实体类
 * 
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
@Data
@TableName("t_comment")
public class Comment {
    
    /**
     * 评论ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 评论类型：CONTENT、DYNAMIC
     */
    private String commentType;
    
    /**
     * 目标对象ID
     */
    private Long targetId;
    
    /**
     * 父评论ID，0表示根评论
     */
    private Long parentCommentId;
    
    /**
     * 评论内容
     */
    private String content;
    
    /**
     * 评论用户ID
     */
    private Long userId;
    
    /**
     * 用户昵称（冗余）
     */
    private String userNickname;
    
    /**
     * 用户头像（冗余）
     */
    private String userAvatar;
    
    /**
     * 回复目标用户ID
     */
    private Long replyToUserId;
    
    /**
     * 回复目标用户昵称（冗余）
     */
    private String replyToUserNickname;
    
    /**
     * 回复目标用户头像（冗余）
     */
    private String replyToUserAvatar;
    
    /**
     * 状态：NORMAL、HIDDEN、DELETED
     */
    private String status;
    
    /**
     * 点赞数（冗余统计）
     */
    private Integer likeCount;
    
    /**
     * 回复数（冗余统计）
     */
    private Integer replyCount;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
