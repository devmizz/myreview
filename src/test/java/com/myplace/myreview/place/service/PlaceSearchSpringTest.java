package com.myplace.myreview.place.service;

import com.myplace.myreview.place.domain.Place;
import com.myplace.myreview.place.dto.PlaceCond;
import com.myplace.myreview.place.dto.SearchStandard;
import com.myplace.myreview.place.repository.PlaceCustomRepositoryImpl;
import com.myplace.myreview.place.repository.PlaceRepository;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

@SpringBootTest
public class PlaceSearchSpringTest {

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    PlaceCustomRepositoryImpl placeCustomRepository;

    @Autowired
    PlaceProvider placeProvider;

    private static List<Place> places = new ArrayList<>();

    @BeforeAll
    public static void setData() {
        int num = 0;
        String str;
        for (int i = 1; i <= 25; i++) {
            if (i % 3 == 0) {
                num = i - 2;
                str = "A";
            } else {
                str = "B";
            }
            places.add(
                Place.builder()
                    .id(i)
                    .name(str)
                    .address("서울특별시")
                    .grade((num % 5) + 1)
                    .build()
            );
        }
    }

    @BeforeEach
    public void savePlaces() {
        placeRepository.saveAll(places);
    }

    @Test
    public void 장소명_검색() throws Exception {
        // given
        PlaceCond placeCond = PlaceCond.builder()
            .searchStandard(SearchStandard.NAME)
            .searchWord("B")
            .build();

        // when
        Page<Place> placePage = placeCustomRepository.findAll(placeCond);

        // then
        for(Place p : placePage.getContent()) {
            Assertions.assertThat(p.getName()).isEqualTo("B");
        }
    }
}
