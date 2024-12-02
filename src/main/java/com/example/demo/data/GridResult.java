package com.example.demo.data;

import lombok.Data;

@Data
public class GridResult {
    private Object data;
    private int draw;
    private int recordsTotal;
    private int recordsFiltered;
    private String query;
    private Object additionalData;
}
