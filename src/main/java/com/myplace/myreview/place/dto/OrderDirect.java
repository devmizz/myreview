package com.myplace.myreview.place.dto;

public enum OrderDirect {
    ASCENDING("ascending"), DESCENDING("descending");

    private String value;

    private OrderDirect(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
