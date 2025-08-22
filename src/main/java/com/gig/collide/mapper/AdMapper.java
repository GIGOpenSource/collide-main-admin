package com.gig.collide.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gig.collide.domain.ad.Ad;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdMapper extends BaseMapper<Ad> {
    // 基础CRUD操作由BaseMapper提供
    // 如果需要自定义查询方法，可以在这里添加
}
