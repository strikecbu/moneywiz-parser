package com.andy.moneywizparser.model;

public enum CategoryType {

    G1("人情往來"),
    G2("房屋"),
    G3("生活"),
    G4("餐飲"),
    G5("休閒"),
    G6("汽機車"),
    G7("養身保健"),
    G8("數碼"),
    G9("其他"),
    G10("帳單"),
    G11("服飾");


    private String name;

    CategoryType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
