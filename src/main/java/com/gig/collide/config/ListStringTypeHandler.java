package com.gig.collide.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * MyBatis类型处理器
 * 用于处理List<String>类型与数据库JSON字符串之间的转换
 * 使用Jackson进行JSON处理
 * 
 * @author why
 * @since 2025-01-27
 */
@MappedTypes(List.class)
public class ListStringTypeHandler extends BaseTypeHandler<List<String>> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
        if (parameter != null) {
            try {
                ps.setString(i, objectMapper.writeValueAsString(parameter));
            } catch (JsonProcessingException e) {
                throw new SQLException("Error serializing string list to JSON", e);
            }
        } else {
            ps.setString(i, null);
        }
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return parseStringList(value);
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return parseStringList(value);
    }

    @Override
    public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return parseStringList(value);
    }

    /**
     * 解析JSON字符串为字符串列表
     * 
     * @param value JSON字符串
     * @return 字符串列表
     */
    private List<String> parseStringList(String value) {
        if (value == null || value.trim().isEmpty()) {
            return new ArrayList<>();
        }

        try {
            return objectMapper.readValue(value, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            // 如果解析失败，返回空列表
            System.out.println("Warning: Failed to parse string list: " + value);
            System.out.println("Error: " + e.getMessage());
            return new ArrayList<>();
        }
    }
} 