package com.gig.collide.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gig.collide.domain.content.Comment;
import com.gig.collide.constant.CommonStatusConstant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 评论Mapper接口
 * 
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    
    /**
     * 根据评论ID查询评论详情
     * 
     * @param commentId 评论ID
     * @return 评论详情
     */
    Comment selectCommentById(@Param("commentId") Long commentId);
    
    /**
     * 删除评论（软删除，将状态设置为DELETED）
     * 
     * @param commentId 评论ID
     * @return 影响行数
     */
    @Update("UPDATE t_comment SET status = '" + CommonStatusConstant.COMMENT_DELETED + "', update_time = NOW() WHERE id = #{commentId} AND status != '" + CommonStatusConstant.COMMENT_DELETED + "'")
    int deleteCommentById(@Param("commentId") Long commentId);
    
    /**
     * 检查评论是否存在且未被删除
     * 
     * @param commentId 评论ID
     * @return 评论数量
     */
    int checkCommentExists(@Param("commentId") Long commentId);
}
