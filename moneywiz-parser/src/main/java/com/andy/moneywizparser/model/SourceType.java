package com.andy.moneywizparser.model;

public enum SourceType {


    S1("現金"),
    S2("國泰世華"),
    S10("中國信託"),
    S13("台新Richart"),
    S18("台新 Richart悠遊御璽卡"),
    S21("台新 RoseGiving 8601"),
    S22("台新 Richart Fly GO"),
    S23("永豐大戶卡"),
    S24("永豐幣倍卡1703"),
    S25("國泰KOKO iCash白金卡"),
    S27("國泰costco 2219"),
    S28("國泰CUBE卡 7371"),
    S29("樂天信用卡"),
    S30("富邦momo卡"),
    S31("富邦數位生活白金卡 4806"),
    S32("台北富邦"),
    S33("玉山Ubear卡"),
    S34("玉山Only卡"),
    S35("玉山Pi信用卡"),
    S36("中信英雄聯盟卡"),
    S37("花旗悠遊御璽卡（剪卡）"),
    S38("花旗Pchome聯名御璽卡(剪卡)"),
    S39("LineBank"),
    S40("永豐大戶"),
    S41("台新銀行_美金"),
    S43("國泰世華KOKO"),
    S44("玉山銀行"),

    S45("台新銀行_人民幣"),
    S46("台新銀行_澳幣"),
    S48("共同帳戶（華南）");

    private String name;

    SourceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
