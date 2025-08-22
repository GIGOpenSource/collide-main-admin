package com.gig.collide.constant;

/**
 * 内容状态常量类
 * 以数据库定义为准，统一管理内容相关状态值
 * 
 * @author system
 * @since 2025-01-27
 * @version 1.0
 */
public class ContentStatusConstant {
    
    // ========== 内容状态常量 (t_content.status) ==========
    /**
     * 草稿状态
     */
    public static final String DRAFT = "DRAFT";
    
    /**
     * 已发布状态
     */
    public static final String PUBLISHED = "PUBLISHED";
    
    /**
     * 下线状态
     */
    public static final String OFFLINE = "OFFLINE";
    
    // ========== 内容章节状态常量 (t_content_chapter.status) ==========
    /**
     * 章节草稿状态
     */
    public static final String CHAPTER_DRAFT = "DRAFT";
    
    /**
     * 章节已发布状态
     */
    public static final String CHAPTER_PUBLISHED = "PUBLISHED";
    
    // ========== 内容审核状态常量 (t_content.review_status) ==========
    /**
     * 待审核状态
     */
    public static final String REVIEW_PENDING = "PENDING";
    
    /**
     * 审核通过状态
     */
    public static final String REVIEW_APPROVED = "APPROVED";
    
    /**
     * 审核拒绝状态
     */
    public static final String REVIEW_REJECTED = "REJECTED";
    
    // ========== 内容付费状态常量 (t_content_payment.status) ==========
    /**
     * 付费配置启用状态
     */
    public static final String PAYMENT_ACTIVE = "ACTIVE";
    
    /**
     * 付费配置禁用状态
     */
    public static final String PAYMENT_INACTIVE = "INACTIVE";
    
    /**
     * 获取内容状态显示名称
     * 
     * @param status 内容状态值
     * @return 显示名称
     */
    public static String getContentStatusDisplayName(String status) {
        switch (status) {
            case DRAFT:
                return "草稿";
            case PUBLISHED:
                return "已发布";
            case OFFLINE:
                return "已下线";
            default:
                return status;
        }
    }
    
    /**
     * 获取章节状态显示名称
     * 
     * @param status 章节状态值
     * @return 显示名称
     */
    public static String getChapterStatusDisplayName(String status) {
        switch (status) {
            case CHAPTER_DRAFT:
                return "草稿";
            case CHAPTER_PUBLISHED:
                return "已发布";
            default:
                return status;
        }
    }
    
    /**
     * 获取审核状态显示名称
     * 
     * @param status 审核状态值
     * @return 显示名称
     */
    public static String getReviewStatusDisplayName(String status) {
        switch (status) {
            case REVIEW_PENDING:
                return "待审核";
            case REVIEW_APPROVED:
                return "审核通过";
            case REVIEW_REJECTED:
                return "审核拒绝";
            default:
                return status;
        }
    }
    
    /**
     * 验证内容状态值是否有效
     * 
     * @param status 内容状态值
     * @return 是否有效
     */
    public static boolean isValidContentStatus(String status) {
        return DRAFT.equals(status) || PUBLISHED.equals(status) || OFFLINE.equals(status);
    }
    
    /**
     * 验证章节状态值是否有效
     * 
     * @param status 章节状态值
     * @return 是否有效
     */
    public static boolean isValidChapterStatus(String status) {
        return CHAPTER_DRAFT.equals(status) || CHAPTER_PUBLISHED.equals(status);
    }
    
    /**
     * 验证审核状态值是否有效
     * 
     * @param status 审核状态值
     * @return 是否有效
     */
    public static boolean isValidReviewStatus(String status) {
        return REVIEW_PENDING.equals(status) || REVIEW_APPROVED.equals(status) || REVIEW_REJECTED.equals(status);
    }
    
    // ========== 内容类型常量 ==========
    /**
     * 小说类型
     */
    public static final String NOVEL = "NOVEL";
    
    /**
     * 漫画类型
     */
    public static final String COMIC = "COMIC";
    
    /**
     * 视频类型
     */
    public static final String VIDEO = "VIDEO";
    
    /**
     * 文章类型
     */
    public static final String ARTICLE = "ARTICLE";
    
    /**
     * 音频类型
     */
    public static final String AUDIO = "AUDIO";
    
    /**
     * 验证内容类型值是否有效
     * 
     * @param contentType 内容类型值
     * @return 是否有效
     */
    public static boolean isValidContentType(String contentType) {
        return NOVEL.equals(contentType) || COMIC.equals(contentType) || 
               VIDEO.equals(contentType) || ARTICLE.equals(contentType) || 
               AUDIO.equals(contentType);
    }
}
