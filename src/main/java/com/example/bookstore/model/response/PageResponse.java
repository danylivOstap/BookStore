package com.example.bookstore.model.response;

import java.util.List;
import lombok.Data;

@Data
public class PageResponse<T> {
    private List<T> content;
    private int totalPages;
    private long totalElements;
    private boolean last;
    private boolean first;
    private int number;
    private int size;
    private int numberOfElements;
}
