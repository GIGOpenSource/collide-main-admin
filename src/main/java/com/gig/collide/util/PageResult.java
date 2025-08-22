package com.gig.collide.util;

import lombok.Data;

import java.util.List;

/**
 * 分页结果类
 * 用于封装分页查询的结果数据
 * 
 * @param <T> 数据类型
 * @author why
 * @since 2025-01-27
 */
@Data
public class PageResult<T> {

    /**
     * 数据列表
     */
    private List<T> data;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 当前页码
     */
    private Long current;

    /**
     * 每页大小
     */
    private Long size;

    /**
     * 总页数
     */
    private Long pages;

    /**
     * 构造函数
     */
    public PageResult() {
    }

    /**
     * 构造函数
     * 接受int类型参数
     * @param data 数据列表
     * @param total 总记录数
     * @param page 当前页码
     * @param size 每页大小
     */
    public PageResult(List<T> data, int total, int page, int size) {
        this.data = data;
        this.total = (long) total;
        this.current = (long) page;
        this.size = (long) size;
        this.pages = calculatePages(this.total, this.size);
    }

    /**
     * 构造函数
     * 接受Long类型参数
     * @param data 数据列表
     * @param total 总记录数
     * @param current 当前页码
     * @param size 每页大小
     */
    public PageResult(List<T> data, Long total, Long current, Long size) {
        this.data = data;
        this.total = total;
        this.current = current;
        this.size = size;
        this.pages = calculatePages(total, size);
    }

    /**
     * 计算总页数
     * 
     * @param total 总记录数
     * @param size 每页大小
     * @return 总页数
     */
    private Long calculatePages(Long total, Long size) {
        if (total == null || size == null || size == 0) {
            return 0L;
        }
        return (total + size - 1) / size;
    }


}
