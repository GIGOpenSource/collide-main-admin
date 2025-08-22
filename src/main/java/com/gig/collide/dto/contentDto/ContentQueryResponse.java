package com.gig.collide.dto.contentDto;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 内容查询响应DTO
 * 响应报文格式与入参报文保持一致
 * 如果创建时入参报文是数组类型，响应报文响应的内容也要是数组类型
 * 
 * @author why
 * @since 2025-08-11
 * @version 1.0
 */
@Data
public class ContentQueryResponse {
    
    /**
     * 操作类型
     */
    private String operationType;
    
    /**
     * 内容数据（简化结构）
     */
    private ContentData contentData;
    
    /**
     * 章节数据列表
     */
    private List<ChapterData> chapterDataList;
    
    /**
     * 内容数据内部类
     */
    @Data
    public static class ContentData {
        /**
         * 封面图片URL
         */
        private String coverUrl;
        
        /**
         * 内容标题
         */
        private String title;
        
        /**
         * 内容描述
         */
        private String description;
        
        /**
         * 作者昵称
         */
        private String authorNickname;
        
        /**
         * 标签列表
         */
        private List<String> tags;
        
        /**
         * 内容类型
         */
        private String contentType;
    }
    
    /**
     * 章节数据内部类
     */
    @Data
    public static class ChapterData {
        /**
         * 章节标题
         */
        private String title;
        
        /**
         * 文件URL
         */
        private String fileUrl;
        
        /**
         * 状态
         */
        private String status;
        
        /**
         * 章节号
         */
        private Integer chapterNum;
    }
}
