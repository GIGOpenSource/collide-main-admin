package com.gig.collide.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gig.collide.domain.message.MessageSetting;
import org.apache.ibatis.annotations.Mapper;

/**
 * 消息设置Mapper接口
 * 
 * @author system
 * @since 2025-01-27
 * @version 1.0
 */
@Mapper
public interface MessageSettingMapper extends BaseMapper<MessageSetting> {
}
