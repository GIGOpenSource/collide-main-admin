package com.gig.collide.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gig.collide.dto.contentDto.ContentDTO.ContentDataItem;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * MyBatis类型处理器
 * 用于处理contentData字段的JSON字符串与List<ContentDataItem>之间的转换
 * 使用Jackson进行JSON处理
 * 
 * @author why
 * @since 2025-01-27
 */
@MappedTypes(List.class)
public class ContentDataTypeHandler extends BaseTypeHandler<List<ContentDataItem>> {

    private static final Logger log = LoggerFactory.getLogger(ContentDataTypeHandler.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<ContentDataItem> parameter, JdbcType jdbcType) throws SQLException {
        if (parameter != null) {
            try {
                String jsonString = objectMapper.writeValueAsString(parameter);
                log.debug("序列化ContentDataItem列表到JSON: {}", jsonString);
                ps.setString(i, jsonString);
            } catch (JsonProcessingException e) {
                log.error("序列化ContentDataItem列表到JSON失败", e);
                throw new SQLException("Error serializing ContentDataItem list to JSON", e);
            }
        } else {
            log.debug("设置null值到数据库");
            ps.setString(i, null);
        }
    }

    @Override
    public List<ContentDataItem> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        log.debug("从数据库读取contentData字段[{}]: {}", columnName, value);
        return parseContentData(value);
    }

    @Override
    public List<ContentDataItem> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        log.debug("从数据库读取contentData字段[索引{}]: {}", columnIndex, value);
        return parseContentData(value);
    }

    @Override
    public List<ContentDataItem> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        log.debug("从CallableStatement读取contentData字段[索引{}]: {}", columnIndex, value);
        return parseContentData(value);
    }

    /**
     * 解析contentData字段的JSON字符串
     * 支持多种格式的兼容性处理
     * 
     * @param value JSON字符串
     * @return ContentDataItem列表
     */
    public List<ContentDataItem> parseContentData(String value) {
        log.info("开始解析contentData: {}", value);
        
        if (value == null || value.trim().isEmpty()) {
            log.info("contentData为空或null，返回空列表");
            return new ArrayList<>();
        }

        try {
            // 尝试直接解析为ContentDataItem列表
            log.debug("尝试直接解析为ContentDataItem列表");
            List<ContentDataItem> result = objectMapper.readValue(value, new TypeReference<List<ContentDataItem>>() {});
            log.info("✅ 直接解析成功，得到{}个ContentDataItem", result.size());
            
            // 验证解析结果
            for (int i = 0; i < result.size(); i++) {
                ContentDataItem item = result.get(i);
                if (item != null) {
                    log.debug("  解析结果[{}]: contentId={}, chapterNum={}, title={}, fileUrl={}, status={}", 
                        i, item.getContentId(), item.getChapterNum(), item.getTitle(), item.getFileUrl(), item.getStatus());
                } else {
                    log.warn("  解析结果[{}]是null!", i);
                }
            }
            
            return result;
            
        } catch (Exception e1) {
            log.warn("直接解析失败，尝试兼容性解析: {}", e1.getMessage());
            
            try {
                // 如果直接解析失败，尝试解析为Object列表，然后转换
                log.debug("尝试解析为Object列表");
                List<Object> objList = objectMapper.readValue(value, new TypeReference<List<Object>>() {});
                log.debug("解析为Object列表成功，大小: {}", objList.size());
                
                List<ContentDataItem> result = new ArrayList<>();
                
                for (int i = 0; i < objList.size(); i++) {
                    Object obj = objList.get(i);
                    log.debug("处理Object[{}]: {}", i, obj);
                    
                    if (obj instanceof String) {
                        // 如果是字符串，创建默认的ContentDataItem
                        log.debug("Object[{}]是String类型: {}", i, obj);
                        ContentDataItem item = new ContentDataItem();
                        item.setTitle((String) obj);  // 使用title字段存储字符串内容
                        result.add(item);
                    } else if (obj instanceof java.util.Map) {
                        // 如果是Map，转换为ContentDataItem
                        log.debug("Object[{}]是Map类型: {}", i, obj);
                        ContentDataItem item = objectMapper.convertValue(obj, ContentDataItem.class);
                        result.add(item);
                    } else {
                        // 其他类型，创建默认的ContentDataItem
                        log.debug("Object[{}]是其他类型: {}", i, obj.getClass().getSimpleName());
                        ContentDataItem item = new ContentDataItem();
                        item.setTitle(obj != null ? obj.toString() : "");  // 使用title字段存储内容
                        result.add(item);
                    }
                }
                
                log.info("✅ 兼容性解析成功，得到{}个ContentDataItem", result.size());
                return result;
                
            } catch (Exception e2) {
                // 如果所有解析都失败，返回空列表
                log.error("❌ 所有解析方法都失败", e2);
                log.error("原始值: {}", value);
                log.error("错误1: {}", e1.getMessage());
                log.error("错误2: {}", e2.getMessage());
                return new ArrayList<>();
            }
        }
    }
}
