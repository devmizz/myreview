package com.myplace.myreview.place.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceParam {

    private long id;
    private String name;
    private String address;
    private String review;
    private int grade;

    @Builder
    public PlaceParam(long id, String name, String address, String review, int grade) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.review = review;
        this.grade = grade;
    }
}
