package com.myplace.myreview.place.dto;

import com.myplace.myreview.place.domain.Place;
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

    public static PlaceParam of(Place place) {
        return PlaceParam.builder()
            .id(place.getId())
            .name(place.getName())
            .address(place.getAddress())
            .review(place.getReview())
            .grade(place.getGrade())
            .build();
    }
}
