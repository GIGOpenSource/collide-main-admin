package com.gig.collide.domain.content;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 内容章节实体类
 * 
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
@Data
@TableName("t_content_chapter")
public class ContentChapter {
    
    /**
     * 章节ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 内容ID
     */
    private Long contentId;
    
    /**
     * 章节号
     */
    private Integer chapterNum;
    
    /**
     * 章节标题
     */
    private String title;
    
    /**
     * 章节内容
     */
    private String content;
    
    /**
     * 章节文件URL（可选）
     */
    private String fileUrl;
    
    /**
     * 字数
     */
    private Integer wordCount;
    
    /**
     * 状态：DRAFT、PUBLISHED
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