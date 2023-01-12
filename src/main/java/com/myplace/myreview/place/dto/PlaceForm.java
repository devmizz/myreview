package com.myplace.myreview.place.dto;

import com.myplace.myreview.place.domain.Place;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlaceForm {

    private String name;
    private String address;
    private String review;
    private int grade;

    @Builder
    public PlaceForm(String name, String address, String review, int grade) {
        this.name = name;
        this.address = address;
        this.review = review;
        this.grade = grade;
    }

    public Place toEntity() {
        return Place.builder()
            .name(name)
            .address(address)
            .review(review)
            .grade(grade)
            .build();
    }
}
