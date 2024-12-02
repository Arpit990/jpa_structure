package com.example.demo.data;

import lombok.Data;
import lombok.Generated;

@Data
public class GridSearch {
    private String id;
    private int start;
    private int length;
    private int draw;
    private String search;
    private String order;
    private String orderDir;
    private String searchColumn;
    private String searchType;
    private String searchValue;
}
