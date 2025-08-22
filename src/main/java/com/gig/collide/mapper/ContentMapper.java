package com.gig.collide.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gig.collide.domain.content.Content;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 内容Mapper接口
 * 
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
@Mapper
public interface ContentMapper extends BaseMapper<Content> {
    
    /**
     * 根据内容ID查询内容标题
     * 
     * @param contentId 内容ID
     * @return 内容标题
     */
    @Select("SELECT title FROM t_content WHERE id = #{contentId}")
    String selectContentTitleById(@Param("contentId") Long contentId);
}
