package com.learning.springboot.dto;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 通用分页结果封装
 * <p>
 * 字段: total / page / size / totalPages / records
 */
public class PageResult<T> {

    private long total;
    private int page;
    private int size;
    private int totalPages;
    private List<T> records;

    public PageResult() {}

    public static <T> PageResult<T> of(Page<T> p) {
        PageResult<T> r = new PageResult<>();
        r.total = p.getTotalElements();
        r.page = p.getNumber() + 1;  // Spring Data 从 0 开始
        r.size = p.getSize();
        r.totalPages = p.getTotalPages();
        r.records = p.getContent();
        return r;
    }

    public static <T> PageResult<T> of(int page, int size, long total, List<T> records) {
        PageResult<T> r = new PageResult<>();
        r.total = total;
        r.page = page;
        r.size = size;
        r.totalPages = (int) Math.ceil(total * 1.0 / Math.max(size, 1));
        r.records = records;
        return r;
    }

    public long getTotal() { return total; }
    public void setTotal(long total) { this.total = total; }
    public int getPage() { return page; }
    public void setPage(int page) { this.page = page; }
    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }
    public int getTotalPages() { return totalPages; }
    public void setTotalPages(int totalPages) { this.totalPages = totalPages; }
    public List<T> getRecords() { return records; }
    public void setRecords(List<T> records) { this.records = records; }
}