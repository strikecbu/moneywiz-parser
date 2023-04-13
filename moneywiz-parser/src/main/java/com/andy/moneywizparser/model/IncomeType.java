package com.andy.moneywizparser.model;

public enum IncomeType {

    S3("還錢給款"),
    S4("其他"),
    S8("社會保險"),
    S12("人情往來"),
    S14("租賃"),
    S15("薪水與工資"),
    S42("投資");

    private String name;

    IncomeType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
