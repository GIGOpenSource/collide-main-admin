package com.gig.collide.domain.blo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Python爬虫博主信息实体类
 */
@Data
@TableName("t_blo_python")
public class BloPython {

    /**
     * 序号（自增）
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 博主UID（前端输入，不允许为空）
     */
    private Long bloggerUid;

    /**
     * 博主昵称
     */
    private String bloggerNickname;

    /**
     * 主页地址（前端输入，不允许为空）
     */
    private String homepageUrl;

    /**
     * 状态：updated-已更新、not_updated-未更新
     */
    private String status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
