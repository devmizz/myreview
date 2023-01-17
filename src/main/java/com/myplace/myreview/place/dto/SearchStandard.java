package com.myplace.myreview.place.dto;

public enum SearchStandard {
    NAME("name"), ADDRESS("address"), GRADE("grade");

    private String value;

    private SearchStandard(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
