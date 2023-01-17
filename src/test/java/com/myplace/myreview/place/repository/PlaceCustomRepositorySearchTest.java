package com.myplace.myreview.place.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.myplace.myreview.place.domain.Place;
import com.myplace.myreview.place.dto.PlaceCond;
import com.myplace.myreview.place.dto.SearchStandard;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class PlaceCustomRepositorySearchTest {

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    PlaceCustomRepositoryImpl placeCustomRepository;

    private static List<Place> places = new ArrayList<>();

    @BeforeAll
    public static void setData() {
        int num = 0;
        String str;
        String address;
        for (int i = 1; i <= 25; i++) {
            if (i % 3 == 0) {
                num = i - 2;
                str = "A";
                address = "서울특별시";
            } else {
                str = "B";
                address = "부산광역시";
            }
            places.add(
                Place.builder()
                    .id(i)
                    .name(str)
                    .address(address)
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
            .searchWord("A")
            .build();

        // when
        Page<Place> placePage = placeCustomRepository.findAll(placeCond);

        // then
        for(Place p : placePage.getContent()) {
            Assertions.assertThat(p.getName()).isEqualTo("A");
        }
    }

    @Test
    public void 주소_검색() throws Exception {
        // given
        PlaceCond placeCond = PlaceCond.builder()
            .searchStandard(SearchStandard.ADDRESS)
            .searchWord("부산")
            .build();

        // when
        Page<Place> placePage = placeCustomRepository.findAll(placeCond);

        // then
        for(Place p : placePage.getContent()) {
            Assertions.assertThat(p.getAddress()).isEqualTo("부산광역시");
        }
    }

    @Test
    public void 평점_검색() throws Exception {
        // given
        PlaceCond placeCond = PlaceCond.builder()
            .searchStandard(SearchStandard.GRADE)
            .searchGrade(3)
            .build();

        // when
        Page<Place> placePage = placeCustomRepository.findAll(placeCond);

        // then
        for(Place p : placePage.getContent()) {
            Assertions.assertThat(p.getGrade()).isEqualTo(3);
        }
    }

    @Test
    public void 평점_기준만_있는_경우() throws Exception {
        // given
        PlaceCond placeCond = PlaceCond.builder()
            .searchStandard(SearchStandard.GRADE)
            .build();

        // when
        Page<Place> placePage = placeCustomRepository.findAll(placeCond);

        // then
        assertThat(placePage.getTotalElements()).isEqualTo(25);
        assertThat(placePage.getTotalPages()).isEqualTo(3);
        assertThat(placePage.getSize()).isEqualTo(10);
        assertThat(placePage.getNumber()).isEqualTo(0);
        assertThat(placePage.getNumberOfElements()).isEqualTo(10);
    }

    @Test
    public void 장소명_기준만_있는_경우() throws Exception {
        // given
        PlaceCond placeCond = PlaceCond.builder()
            .searchStandard(SearchStandard.NAME)
            .build();

        // when
        Page<Place> placePage = placeCustomRepository.findAll(placeCond);

        // then
        assertThat(placePage.getTotalElements()).isEqualTo(25);
        assertThat(placePage.getTotalPages()).isEqualTo(3);
        assertThat(placePage.getSize()).isEqualTo(10);
        assertThat(placePage.getNumber()).isEqualTo(0);
        assertThat(placePage.getNumberOfElements()).isEqualTo(10);
    }

    @Test
    public void 주소_기준만_있는_경우() throws Exception {
        // given
        PlaceCond placeCond = PlaceCond.builder()
            .searchStandard(SearchStandard.ADDRESS)
            .build();

        // when
        Page<Place> placePage = placeCustomRepository.findAll(placeCond);

        // then
        assertThat(placePage.getTotalElements()).isEqualTo(25);
        assertThat(placePage.getTotalPages()).isEqualTo(3);
        assertThat(placePage.getSize()).isEqualTo(10);
        assertThat(placePage.getNumber()).isEqualTo(0);
        assertThat(placePage.getNumberOfElements()).isEqualTo(10);
    }

    @Test
    public void 기준이_없는_경우_장소명이_기준() throws Exception {
        // given
        PlaceCond placeCond = PlaceCond.builder()
            .searchWord("A")
            .build();

        // when
        Page<Place> placePage = placeCustomRepository.findAll(placeCond);

        // then
        placePage.getContent().get(0).getName().equals("A");
    }
}
