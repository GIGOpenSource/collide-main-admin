package com.gig.collide.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gig.collide.dto.contentDto.ContentDTO;
import com.gig.collide.dto.contentDto.ContentDTO.ContentDataItem;
import com.gig.collide.config.ContentDataTypeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * ContentData调试工具类
 * 用于诊断contentData序列化问题
 * 
 * @author why
 * @since 2025-01-27
 */
public class ContentDataDebugUtil {
    
    private static final Logger log = LoggerFactory.getLogger(ContentDataDebugUtil.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final ContentDataTypeHandler contentDataTypeHandler = new ContentDataTypeHandler();
    
    /**
     * 调试ContentDTO的contentData字段
     */
    public static void debugContentDTO(ContentDTO contentDTO) {
        if (contentDTO == null) {
            log.error("ContentDTO is null");
            return;
        }
        
        log.info("=== ContentDTO Debug Info ===");
        log.info("ID: {}", contentDTO.getId());
        log.info("Title: {}", contentDTO.getTitle());
        log.info("ContentType: {}", contentDTO.getContentType());
        
        List<ContentDataItem> contentData = contentDTO.getContentData();
        log.info("ContentData field: {}", contentData);
        
        if (contentData != null) {
            log.info("ContentData size: {}", contentData.size());
            for (int i = 0; i < contentData.size(); i++) {
                ContentDataItem item = contentData.get(i);
                log.info("  Item[{}]: {}", i, item);
                if (item != null) {
                    log.info("    - contentId: {}", item.getContentId());
                    log.info("    - chapterNum: {}", item.getChapterNum());
                    log.info("    - title: {}", item.getTitle());
                    log.info("    - fileUrl: {}", item.getFileUrl());
                    log.info("    - status: {}", item.getStatus());
                } else {
                    log.warn("    - Item is null!");
                }
            }
        } else {
            log.warn("ContentData is null!");
        }
        
        // 尝试序列化
        try {
            String jsonString = objectMapper.writeValueAsString(contentDTO);
            log.info("Serialized JSON: {}", jsonString);
            
            // 检查JSON中是否包含contentData
            if (jsonString.contains("contentData")) {
                log.info("✅ JSON contains 'contentData' field");
            } else {
                log.error("❌ JSON missing 'contentData' field");
            }
            
            // 检查JSON中是否包含ContentDataItem的字段
            if (jsonString.contains("contentId")) {
                log.info("✅ JSON contains 'contentId' field");
            } else {
                log.error("❌ JSON missing 'contentId' field");
            }
            
            if (jsonString.contains("chapterNum")) {
                log.info("✅ JSON contains 'chapterNum' field");
            } else {
                log.error("❌ JSON missing 'chapterNum' field");
            }
            
            if (jsonString.contains("title")) {
                log.info("✅ JSON contains 'title' field");
            } else {
                log.error("❌ JSON missing 'title' field");
            }
            
            if (jsonString.contains("fileUrl")) {
                log.info("✅ JSON contains 'fileUrl' field");
            } else {
                log.error("❌ JSON missing 'fileUrl' field");
            }
            
            if (jsonString.contains("status")) {
                log.info("✅ JSON contains 'status' field");
            } else {
                log.error("❌ JSON missing 'status' field");
            }
            
        } catch (Exception e) {
            log.error("Failed to serialize ContentDTO", e);
        }
        
        log.info("=== End Debug Info ===");
    }
    
    /**
     * 调试数据库JSON字符串
     */
    public static void debugDatabaseJson(String databaseJson) {
        log.info("=== Database JSON Debug Info ===");
        log.info("Raw database JSON: {}", databaseJson);
        
        if (databaseJson == null || databaseJson.trim().isEmpty()) {
            log.warn("Database JSON is null or empty");
            return;
        }
        
        try {
            List<ContentDataItem> parsedItems = contentDataTypeHandler.parseContentData(databaseJson);
            log.info("Parsed ContentDataItem list: {}", parsedItems);
            log.info("Parsed list size: {}", parsedItems != null ? parsedItems.size() : "null");
            
            if (parsedItems != null) {
                for (int i = 0; i < parsedItems.size(); i++) {
                    ContentDataItem item = parsedItems.get(i);
                    log.info("  Parsed Item[{}]: {}", i, item);
                    if (item != null) {
                        log.info("    - contentId: {}", item.getContentId());
                        log.info("    - chapterNum: {}", item.getChapterNum());
                        log.info("    - title: {}", item.getTitle());
                        log.info("    - fileUrl: {}", item.getFileUrl());
                        log.info("    - status: {}", item.getStatus());
                    } else {
                        log.warn("    - Parsed item is null!");
                    }
                }
            }
            
        } catch (Exception e) {
            log.error("Failed to parse database JSON", e);
        }
        
        log.info("=== End Database JSON Debug Info ===");
    }
}
