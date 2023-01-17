package com.myplace.myreview.place.dto;

public enum OrderStandard {
    NAME("name"), GRADE("grade");

    private String value;

    private OrderStandard(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
