package com.andy.moneywizparser.model;

public enum SourceType {
    C_1("現金"),
    C_2("台新 Richart悠遊御璽卡"),
    C_3("台新 RoseGiving 8601"),
    C_4("台新 Richart Fly GO"),
    C_5("永豐大戶卡"),
    C_6("永豐幣倍卡1703"),
    C_7("國泰KOKO iCash白金卡"),
    C_8("國泰costco 2219"),
    C_9("國泰CUBE卡 7371"),
    C_10("樂天信用卡"),
    C_11("富邦momo卡"),
    C_12("富邦數位生活白金卡 4806"),
    C_13("玉山Ubear卡"),
    C_14("玉山Only卡"),
    C_15("玉山Pi信用卡"),
    C_16("中信英雄聯盟卡"),
    C_17("花旗悠遊御璽卡（剪卡）"),
    C_18("花旗Pchome聯名御璽卡(剪卡)"),
    C_19("中國信託"),
    C_20("LineBank"),
    C_21("台新Richart"),
    C_22("台北富邦"),
    C_23("國泰世華"),
    C_24("國泰世華KOKO"),
    C_25("共同帳戶（華南）"),
    C_26("玉山銀行"),
    C_27("永豐大戶");
//    C_28("借貸");
//    C_29("股票張戶"),
//    C_30("股票");

    private String name;

    SourceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
