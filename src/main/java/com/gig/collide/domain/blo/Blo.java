package com.gig.collide.domain.blo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 博主信息实体类
 */
@Data
@TableName("t_blo")
public class Blo {

    /**
     * 序号（ID）
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 博主Uid
     */
    private Long bloggerUid;

    /**
     * 主页地址
     */
    private String homepageUrl;

    /**
     * 博主昵称
     */
    private String bloggerNickname;

    /**
     * 博主签名
     */
    private String bloggerSignature;

    /**
     * 头像（字符串存储）
     */
    private String avatar;

    /**
     * 标签
     */
    private String tags;

    /**
     * 粉丝数
     */
    private Long followerCount;

    /**
     * 关注数
     */
    private Long followingCount;

    /**
     * 状态：not_updated-未更新，updating-已更新，success-更新成功，failed-更新失败
     */
    private String status;

    /**
     * 是否删除：N-未删除，Y-已删除
     */
    @TableLogic(value = "N", delval = "Y")
    private String isDelete;

    /**
     * 是否入驻：N-未入驻，Y-已入驻
     */
    private String isEnter;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 作品数
     */
    private Long workCount;

    /**
     * 作品比例
     */
    private Double workRatio;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 账户名
     */
    private String account;

    /**
     * 博主类型
     */
    private String type;

    /**
     * 是否Python开发者
     */
    private Boolean isPython;
}
