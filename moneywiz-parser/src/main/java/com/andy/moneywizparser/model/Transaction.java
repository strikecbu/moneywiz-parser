package com.andy.moneywizparser.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Transaction {
    private String source;
    private String destination;
    private String description;
    private String category;
    private String date;
    private Long amount;
    private String note;
}
