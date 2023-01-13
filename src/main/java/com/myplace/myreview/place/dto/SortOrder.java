package com.myplace.myreview.place.dto;

public enum SortOrder {
    ASCENDING("ascending"), DESCENDING("descending");

    private String value;

    private SortOrder(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
