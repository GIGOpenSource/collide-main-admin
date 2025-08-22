package com.gig.collide.constant;

/**
 * 内容操作类型常量
 * 定义统一内容接口支持的各种操作类型
 * 根据实际页面输入需求设计
 * 
 * @author why
 * @since 2025-08-11
 * @version 1.0
 */
public class ContentOperationConstant {
    
    // ========== 创建操作 ==========
    
    /**
     * 创建文章/帖子
     * 输入：主标题、正文、标签、媒体文件URL
     * 操作表：t_content
     */
    public static final String CREATE_ARTICLE = "CREATE_ARTICLE";
    
    /**
     * 创建小说/动漫/漫画
     * 输入：封面URL、名称、作者、简介、标签、分集信息
     * 操作表：t_content + t_content_chapter
     */
    public static final String CREATE_STORY = "CREATE_STORY";
    
    /**
     * 为现有内容添加分集/章节
     * 操作表：t_content_chapter
     */
    public static final String CREATE_CHAPTERS = "CREATE_CHAPTERS";
    
    // ========== 更新操作 ==========
    
    /**
     * 更新内容
     * 操作表：t_content
     */
    public static final String UPDATE_CONTENT = "UPDATE_CONTENT";
    
    /**
     * 更新分集/章节
     * 操作表：t_content_chapter
     */
    public static final String UPDATE_CHAPTERS = "UPDATE_CHAPTERS";
    
    /**
     * 批量更新分集/章节
     * 操作表：t_content_chapter
     */
    public static final String BATCH_UPDATE_CHAPTERS = "BATCH_UPDATE_CHAPTERS";
    
    // ========== 删除操作 ==========
    
    /**
     * 删除内容（同时删除相关分集/章节）
     * 操作表：t_content + t_content_chapter
     */
    public static final String DELETE_CONTENT = "DELETE_CONTENT";
    
    /**
     * 删除指定分集/章节
     * 操作表：t_content_chapter
     */
    public static final String DELETE_CHAPTERS = "DELETE_CHAPTERS";
    
    /**
     * 批量删除分集/章节
     * 操作表：t_content_chapter
     */
    public static final String BATCH_DELETE_CHAPTERS = "BATCH_DELETE_CHAPTERS";
    
    // ========== 查询操作 ==========
    
    /**
     * 查询内容详情
     * 操作表：t_content
     */
    public static final String QUERY_CONTENT = "QUERY_CONTENT";
    
    /**
     * 查询分集/章节列表
     * 操作表：t_content_chapter
     */
    public static final String QUERY_CHAPTERS = "QUERY_CHAPTERS";
    
    /**
     * 查询内容和分集/章节
     * 操作表：t_content + t_content_chapter
     */
    public static final String QUERY_CONTENT_WITH_CHAPTERS = "QUERY_CONTENT_WITH_CHAPTERS";
    
    // ========== 状态操作 ==========
    
    /**
     * 发布内容
     * 操作表：t_content
     */
    public static final String PUBLISH_CONTENT = "PUBLISH_CONTENT";
    
    /**
     * 发布分集/章节
     * 操作表：t_content_chapter
     */
    public static final String PUBLISH_CHAPTERS = "PUBLISH_CHAPTERS";
    
    /**
     * 下架内容
     * 操作表：t_content
     */
    public static final String OFFLINE_CONTENT = "OFFLINE_CONTENT";
    
    /**
     * 下架分集/章节
     * 操作表：t_content_chapter
     */
    public static final String OFFLINE_CHAPTERS = "OFFLINE_CHAPTERS";
    
    // ========== 统计操作 ==========
    
    /**
     * 更新内容统计信息
     * 操作表：t_content
     */
    public static final String UPDATE_CONTENT_STATS = "UPDATE_CONTENT_STATS";
    
    /**
     * 更新分集/章节统计信息
     * 操作表：t_content_chapter
     */
    public static final String UPDATE_CHAPTER_STATS = "UPDATE_CHAPTER_STATS";
    
    /**
     * 验证操作类型是否有效
     */
    public static boolean isValidOperationType(String operationType) {
        if (operationType == null) {
            return false;
        }
        
        return CREATE_ARTICLE.equals(operationType) ||
               CREATE_STORY.equals(operationType) ||
               CREATE_CHAPTERS.equals(operationType) ||
               UPDATE_CONTENT.equals(operationType) ||
               UPDATE_CHAPTERS.equals(operationType) ||
               BATCH_UPDATE_CHAPTERS.equals(operationType) ||
               DELETE_CONTENT.equals(operationType) ||
               DELETE_CHAPTERS.equals(operationType) ||
               BATCH_DELETE_CHAPTERS.equals(operationType) ||
               QUERY_CONTENT.equals(operationType) ||
               QUERY_CHAPTERS.equals(operationType) ||
               QUERY_CONTENT_WITH_CHAPTERS.equals(operationType) ||
               PUBLISH_CONTENT.equals(operationType) ||
               PUBLISH_CHAPTERS.equals(operationType) ||
               OFFLINE_CONTENT.equals(operationType) ||
               OFFLINE_CHAPTERS.equals(operationType) ||
               UPDATE_CONTENT_STATS.equals(operationType) ||
               UPDATE_CHAPTER_STATS.equals(operationType);
    }
    
    /**
     * 获取操作类型描述
     */
    public static String getOperationDescription(String operationType) {
        if (operationType == null) {
            return "未知操作";
        }
        
        switch (operationType) {
            case CREATE_ARTICLE:
                return "创建文章/帖子";
            case CREATE_STORY:
                return "创建小说/动漫/漫画";
            case CREATE_CHAPTERS:
                return "添加分集/章节";
            case UPDATE_CONTENT:
                return "更新内容";
            case UPDATE_CHAPTERS:
                return "更新分集/章节";
            case BATCH_UPDATE_CHAPTERS:
                return "批量更新分集/章节";
            case DELETE_CONTENT:
                return "删除内容";
            case DELETE_CHAPTERS:
                return "删除分集/章节";
            case BATCH_DELETE_CHAPTERS:
                return "批量删除分集/章节";
            case QUERY_CONTENT:
                return "查询内容";
            case QUERY_CHAPTERS:
                return "查询分集/章节";
            case QUERY_CONTENT_WITH_CHAPTERS:
                return "查询内容和分集/章节";
            case PUBLISH_CONTENT:
                return "发布内容";
            case PUBLISH_CHAPTERS:
                return "发布分集/章节";
            case OFFLINE_CONTENT:
                return "下架内容";
            case OFFLINE_CHAPTERS:
                return "下架分集/章节";
            case UPDATE_CONTENT_STATS:
                return "更新内容统计";
            case UPDATE_CHAPTER_STATS:
                return "更新分集/章节统计";
            default:
                return "未知操作";
        }
    }
}
