package com.gig.collide.mapper;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gig.collide.domain.tag.Tag;
import com.gig.collide.dto.tagDto.TagDTO;
import com.gig.collide.dto.tagDto.TagQueryRequest;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 标签Mapper接口
 * 
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 分页查询标签列表（带LIMIT）
     * @param request 查询条件
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 标签列表
     */
    List<TagDTO> selectTagListWithLimit(@Param("request") TagQueryRequest request,
                                        @Param("offset") long offset,
                                        @Param("limit") long limit);

    /**
     * 查询标签总数
     * @param request 查询条件
     * @return 标签总数
     */
    Long selectTagCount(@Param("request") TagQueryRequest request);


} 