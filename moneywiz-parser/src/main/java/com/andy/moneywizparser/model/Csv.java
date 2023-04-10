package com.andy.moneywizparser.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Csv {
    private String source;
    private String transfers;
    private String description;
    private String category;
    private String date;
    private String time;
    private String note;
    private Long amount;
}
