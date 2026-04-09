package com.banking.common.dto;

import java.util.Collections;
import java.util.List;

/**
 * Pagination request DTO with sorting support.
 */
public class PageRequest {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 20;
    private static final int MAX_SIZE = 100;

    private int page;
    private int size;
    private List<SortField> sortFields;

    public PageRequest() {
        this.page = DEFAULT_PAGE;
        this.size = DEFAULT_SIZE;
        this.sortFields = Collections.emptyList();
    }

    public PageRequest(int page, int size) {
        this.page = Math.max(page, 0);
        this.size = Math.min(Math.max(size, 1), MAX_SIZE);
        this.sortFields = Collections.emptyList();
    }

    public PageRequest(int page, int size, List<SortField> sortFields) {
        this.page = Math.max(page, 0);
        this.size = Math.min(Math.max(size, 1), MAX_SIZE);
        this.sortFields = sortFields != null ? sortFields : Collections.emptyList();
    }

    public static PageRequest of(int page, int size) {
        return new PageRequest(page, size);
    }

    public static PageRequest of(int page, int size, String sortBy, SortField.Direction direction) {
        return new PageRequest(page, size, List.of(new SortField(sortBy, direction)));
    }

    public static PageRequest firstPage(int size) {
        return new PageRequest(0, size);
    }

    /**
     * Calculate the offset for database queries.
     */
    public long getOffset() {
        return (long) page * size;
    }

    // ===================== Getters and Setters =====================

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = Math.max(page, 0);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = Math.min(Math.max(size, 1), MAX_SIZE);
    }

    public List<SortField> getSortFields() {
        return sortFields;
    }

    public void setSortFields(List<SortField> sortFields) {
        this.sortFields = sortFields != null ? sortFields : Collections.emptyList();
    }
}
