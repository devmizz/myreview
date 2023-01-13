package com.myplace.myreview.place.dto;

public enum SortStandard {
    NAME("name"), GRADE("grade");

    private String value;

    private SortStandard(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
