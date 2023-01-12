package com.myplace.myreview.place.domain;

import com.myplace.myreview.place.dto.PlaceForm;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Place {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(length = 3000)
    private String review;

    private int grade;

    @Builder
    private Place(long id, String name, String address, String review, int grade) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.review = review;
        this.grade = grade;
    }
}
