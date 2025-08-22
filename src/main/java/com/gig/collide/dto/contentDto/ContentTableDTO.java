package com.gig.collide.dto.contentDto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.gig.collide.domain.content.Content;
import com.gig.collide.domain.content.ContentChapter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 内容表格数据传输对象
 * 用于前端表格展示，包含内容和章节的统计信息
 * 使用Jackson进行JSON处理
 * 
 * @author why
 * @since 2025-01-27
 */
public class ContentTableDTO {
    
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    // 内容基本信息
    private Long id;
    private String title;
    private String description;
    private String contentType;
    private String status;
    private String reviewStatus;
    private Long authorId;
    private String authorNickname;
    private String authorAvatar;
    private String coverUrl;
    private Long categoryId;
    private String categoryName;
    
    // 文章正文内容（用于文章、帖子等）
    private String content;
    
    // 统计信息
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;
    private Integer favoriteCount;
    private Integer scoreCount;
    private Integer scoreTotal;
    private LocalDateTime publishTime;
    private Integer totalWordCount;
    private Integer chapterCount;
    
    // 时间信息
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    
    // 标签（JSON字符串形式，用于数据库存储）
    private String tagsString;
    
    // 标签（对象形式，用于前端展示）
    private List<String> tags;
    
    // 内容数据（JSON字符串形式，用于数据库存储）
    private String contentDataString;
    
    // 内容数据（对象形式，用于前端展示）
    private List<Map<String, Object>> contentData;
    
    // 构造函数
    public ContentTableDTO() {}
    
    public ContentTableDTO(Content content) {
        BeanUtils.copyProperties(content, this);
        
        // 处理标签
        if (content.getTags() != null && !content.getTags().trim().isEmpty()) {
            this.tagsString = content.getTags();
            try {
                this.tags = objectMapper.readValue(content.getTags(), 
                    objectMapper.getTypeFactory().constructCollectionType(List.class, String.class));
            } catch (Exception e) {
                this.tags = new ArrayList<>();
            }
        } else {
            this.tags = new ArrayList<>();
        }
        
        // 处理内容数据
        if (content.getContentData() != null && !content.getContentData().trim().isEmpty()) {
            this.contentDataString = content.getContentData();
            try {
                this.contentData = objectMapper.readValue(content.getContentData(), 
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class));
            } catch (Exception e) {
                this.contentData = new ArrayList<>();
            }
        } else {
            this.contentData = new ArrayList<>();
        }
    }
    
    // 从内容和章节列表构建
    public static ContentTableDTO fromContentAndChapters(Content content, List<ContentChapter> chapters) {
        ContentTableDTO dto = new ContentTableDTO(content);
        
        // 计算总字数
        if (chapters != null && !chapters.isEmpty()) {
            int totalWords = chapters.stream()
                .mapToInt(chapter -> chapter.getWordCount() != null ? chapter.getWordCount() : 0)
                .sum();
            dto.setTotalWordCount(totalWords);
            dto.setChapterCount(chapters.size());
        }
        
        return dto;
    }
    
    // 获取标签列表
    public List<String> getTags() {
        if (this.tags == null && this.tagsString != null && !this.tagsString.trim().isEmpty()) {
            try {
                this.tags = objectMapper.readValue(this.tagsString, 
                    objectMapper.getTypeFactory().constructCollectionType(List.class, String.class));
            } catch (Exception e) {
                this.tags = new ArrayList<>();
            }
        }
        return this.tags != null ? this.tags : new ArrayList<>();
    }
    
    // 设置标签列表
    public void setTags(List<String> tags) {
        this.tags = tags;
        try {
            this.tagsString = objectMapper.writeValueAsString(tags);
        } catch (JsonProcessingException e) {
            this.tagsString = "[]";
        }
    }
    
    // 获取内容数据
    public List<Map<String, Object>> getContentData() {
        if (this.contentData == null && this.contentDataString != null && !this.contentDataString.trim().isEmpty()) {
            try {
                this.contentData = objectMapper.readValue(this.contentDataString, 
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class));
            } catch (Exception e) {
                this.contentData = new ArrayList<>();
            }
        }
        return this.contentData != null ? this.contentData : new ArrayList<>();
    }
    
    // 设置内容数据
    public void setContentData(List<Map<String, Object>> contentData) {
        this.contentData = contentData;
        try {
            this.contentDataString = objectMapper.writeValueAsString(contentData);
        } catch (JsonProcessingException e) {
            this.contentDataString = "[]";
        }
    }
    
    // 获取标签字符串（用于数据库存储）
    public String getTagsString() {
        if (this.tagsString == null && this.tags != null) {
            try {
                this.tagsString = objectMapper.writeValueAsString(this.tags);
            } catch (JsonProcessingException e) {
                this.tagsString = "[]";
            }
        }
        return this.tagsString;
    }
    
    // 获取内容数据字符串（用于数据库存储）
    public String getContentDataString() {
        if (this.contentDataString == null && this.contentData != null) {
            try {
                this.contentDataString = objectMapper.writeValueAsString(this.contentData);
            } catch (JsonProcessingException e) {
                this.contentDataString = "[]";
            }
        }
        return this.contentDataString;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getReviewStatus() { return reviewStatus; }
    public void setReviewStatus(String reviewStatus) { this.reviewStatus = reviewStatus; }
    
    public Long getAuthorId() { return authorId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }
    
    public String getAuthorNickname() { return authorNickname; }
    public void setAuthorNickname(String authorNickname) { this.authorNickname = authorNickname; }
    
    public String getAuthorAvatar() { return authorAvatar; }
    public void setAuthorAvatar(String authorAvatar) { this.authorAvatar = authorAvatar; }
    
    public String getCoverUrl() { return coverUrl; }
    public void setCoverUrl(String coverUrl) { this.coverUrl = coverUrl; }
    
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public Integer getViewCount() { return viewCount; }
    public void setViewCount(Integer viewCount) { this.viewCount = viewCount; }
    
    public Integer getLikeCount() { return likeCount; }
    public void setLikeCount(Integer likeCount) { this.likeCount = likeCount; }
    
    public Integer getCommentCount() { return commentCount; }
    public void setCommentCount(Integer commentCount) { this.commentCount = commentCount; }
    
    public Integer getFavoriteCount() { return favoriteCount; }
    public void setFavoriteCount(Integer favoriteCount) { this.favoriteCount = favoriteCount; }
    
    public Integer getScoreCount() { return scoreCount; }
    public void setScoreCount(Integer scoreCount) { this.scoreCount = scoreCount; }
    
    public Integer getScoreTotal() { return scoreTotal; }
    public void setScoreTotal(Integer scoreTotal) { this.scoreTotal = scoreTotal; }
    
    public LocalDateTime getPublishTime() { return publishTime; }
    public void setPublishTime(LocalDateTime publishTime) { this.publishTime = publishTime; }
    
    public Integer getTotalWordCount() { return totalWordCount; }
    public void setTotalWordCount(Integer totalWordCount) { this.totalWordCount = totalWordCount; }
    
    public Integer getChapterCount() { return chapterCount; }
    public void setChapterCount(Integer chapterCount) { this.chapterCount = chapterCount; }
    
    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
    
    public LocalDateTime getUpdateTime() { return updateTime; }
    public void setUpdateTime(LocalDateTime updateTime) { this.updateTime = updateTime; }
    
    public void setTagsString(String tagsString) { this.tagsString = tagsString; }
    public void setContentDataString(String contentDataString) { this.contentDataString = contentDataString; }
}
