package com.myplace.myreview.place.dto;

import com.myplace.myreview.place.domain.Place;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PlaceForm {

    private String name;
    private String address;
    private String review;
    private int grade;

    public Place toEntity() {
        return Place.builder()
            .name(name)
            .address(address)
            .review(review)
            .grade(grade)
            .build();
    }
}
