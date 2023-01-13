package com.myplace.myreview.place.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PlaceParam {

    private long id;
    private String name;
    private String address;
    private String review;
    private int grade;
}
