package com.parkway.model;

import lombok.Data;

import java.util.List;

@Data
public class PageResponse<T> {
    private int currentPage;
    private int totalPages;
    private long totalItems;
    private List<T> data;
}
