package com.gig.collide.dto.contentDto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 内容DTO
 * 
 * @author why
 * @since 2025-08-06
 * @version 1.0
 */
@Data
public class ContentDTO {
    
    /**
     * 内容数据项（分集信息）
     */
    public static class ContentDataItem {
        /**
         * 内容ID
         */
        @com.fasterxml.jackson.annotation.JsonProperty("contentId")
        private Long contentId;
        
        /**
         * 章节号
         */
        @com.fasterxml.jackson.annotation.JsonProperty("chapterNum")
        private Integer chapterNum;
        
        /**
         * 章节标题
         */
        @com.fasterxml.jackson.annotation.JsonProperty("title")
        private String title;
        
        /**
         * 文件URL
         */
        @com.fasterxml.jackson.annotation.JsonProperty("fileUrl")
        private String fileUrl;
        
        /**
         * 章节状态
         */
        @com.fasterxml.jackson.annotation.JsonProperty("status")
        private String status;
        
        // 构造函数
        public ContentDataItem() {}
        
        public ContentDataItem(Long contentId, Integer chapterNum, String title, String fileUrl, String status) {
            this.contentId = contentId;
            this.chapterNum = chapterNum;
            this.title = title;
            this.fileUrl = fileUrl;
            this.status = status;
        }
        
        // Getter方法
        public Long getContentId() {
            return contentId;
        }
        
        public Integer getChapterNum() {
            return chapterNum;
        }
        
        public String getTitle() {
            return title;
        }
        
        public String getFileUrl() {
            return fileUrl;
        }
        
        public String getStatus() {
            return status;
        }
        
        // Setter方法
        public void setContentId(Long contentId) {
            this.contentId = contentId;
        }
        
        public void setChapterNum(Integer chapterNum) {
            this.chapterNum = chapterNum;
        }
        
        public void setTitle(String title) {
            this.title = title;
        }
        
        public void setFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
        }
        
        public void setStatus(String status) {
            this.status = status;
        }
        
        @Override
        public String toString() {
            return "ContentDataItem{" +
                    "contentId=" + contentId +
                    ", chapterNum=" + chapterNum +
                    ", title='" + title + '\'' +
                    ", fileUrl='" + fileUrl + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ContentDataItem that = (ContentDataItem) o;
            return Objects.equals(contentId, that.contentId) &&
                    Objects.equals(chapterNum, that.chapterNum) &&
                    Objects.equals(title, that.title) &&
                    Objects.equals(fileUrl, that.fileUrl) &&
                    Objects.equals(status, that.status);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(contentId, chapterNum, title, fileUrl, status);
        }
    }
    
    /**
     * 内容ID
     */
    private Long id;
    
    /**
     * 内容标题
     */
    private String title;
    
    /**
     * 内容简介
     */
    private String description;
    
    /**
     * 内容类型：NOVEL、ARTICLE、POEM等
     */
    private String contentType;
    
    /**
     * 状态：DRAFT、PUBLISHED
     */
    private String status;
    
    /**
     * 审核状态：PENDING、APPROVED、REJECTED
     */
    private String reviewStatus;
    
    /**
     * 作者ID
     */
    private Long authorId;
    
    /**
     * 作者昵称
     */
    private String authorNickname;
    
    /**
     * 作者头像URL
     */
    private String authorAvatar;
    
    /**
     * 封面图片URL
     */
    private String coverUrl;
    
    /**
     * 分类ID
     */
    private Long categoryId;
    
    /**
     * 分类名称
     */
    private String categoryName;
    
    /**
     * 标签，数组格式
     */
    private List<String> tags;
    
    /**
     * 内容数据，对象数组格式
     */
    @com.fasterxml.jackson.annotation.JsonProperty("contentData")
    private List<ContentDataItem> contentData;
    
    // Getters and Setters for contentData and tags
    public List<ContentDataItem> getContentData() {
        return contentData;
    }
    
    public void setContentData(List<ContentDataItem> contentData) {
        this.contentData = contentData;
    }
    
    public List<String> getTags() {
        return tags;
    }
    
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    
    @Override
    public String toString() {
        return "ContentDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", contentType='" + contentType + '\'' +
                ", status='" + status + '\'' +
                ", reviewStatus='" + reviewStatus + '\'' +
                ", authorId=" + authorId +
                ", authorNickname='" + authorNickname + '\'' +
                ", authorAvatar='" + authorAvatar + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", tags=" + tags +
                ", contentData=" + contentData +
                ", viewCount=" + viewCount +
                ", likeCount=" + likeCount +
                ", commentCount=" + commentCount +
                ", favoriteCount=" + favoriteCount +
                ", scoreCount=" + scoreCount +
                ", scoreTotal=" + scoreTotal +
                ", publishTime=" + publishTime +
                ", totalWordCount=" + totalWordCount +
                ", chapterCount=" + chapterCount +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
    
    /**
     * 查看数
     */
    private Long viewCount;
    
    /**
     * 点赞数
     */
    private Long likeCount;
    
    /**
     * 评论数
     */
    private Long commentCount;
    
    /**
     * 收藏数
     */
    private Long favoriteCount;
    
    /**
     * 评分数
     */
    private Long scoreCount;
    
    /**
     * 总评分
     */
    private Long scoreTotal;
    
    /**
     * 发布时间
     */
    private LocalDateTime publishTime;
    
    /**
     * 总字数
     */
    private Integer totalWordCount;
    
    /**
     * 章节数量
     */
    private Integer chapterCount;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
