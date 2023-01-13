package com.myplace.myreview.place.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.myplace.myreview.place.domain.Place;
import com.myplace.myreview.place.dto.PlaceCond;
import com.myplace.myreview.place.dto.SortOrder;
import com.myplace.myreview.place.dto.SortStandard;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class PlaceProviderSpringTest {

    @Autowired
    PlaceProvider placeProvider;

    @Autowired
    PlaceRepository placeRepository;

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
    public void 전체조회() throws Exception {
        // given
        PlaceCond placeCond = PlaceCond.builder()
            .currentPage(1)
            .postPerPage(10)
            .build();

        // when
        Page<Place> findPlacePage = placeProvider.findAll(placeCond);

        // then
        Assertions.assertThat(findPlacePage.getTotalElements()).isEqualTo(25);
        Assertions.assertThat(findPlacePage.getTotalPages()).isEqualTo(3);
        Assertions.assertThat(findPlacePage.getSize()).isEqualTo(10);
        Assertions.assertThat(findPlacePage.getNumber()).isEqualTo(1);
        Assertions.assertThat(findPlacePage.getNumberOfElements()).isEqualTo(10);
    }

    @Test
    public void 가나다순_정렬() throws Exception {
        // given
        PlaceCond placeCond = PlaceCond.builder()
            .currentPage(0)
            .postPerPage(10)
            .sortStandard(SortStandard.NAME)
            .build();

        // when
        Page<Place> findPlacePage = placeProvider.findAll(placeCond);

        // then
        Assertions.assertThat(findPlacePage.getContent().get(0).getName()).isEqualTo("A");
        Assertions.assertThat(findPlacePage.getContent().get(1).getName()).isEqualTo("A");
    }

    @Test
    public void 평점순_정렬() throws Exception {
        // given
        PlaceCond placeCond = PlaceCond.builder()
            .currentPage(0)
            .postPerPage(10)
            .sortStandard(SortStandard.GRADE)
            .build();

        // when
        Page<Place> findPlacePage = placeProvider.findAll(placeCond);

        // then
        Assertions.assertThat(findPlacePage.getContent().get(0).getGrade()).isEqualTo(1);
        Assertions.assertThat(findPlacePage.getContent().get(9).getGrade())
            .isGreaterThanOrEqualTo(1);
    }

    @Transactional
    @Test
    public void 가나다순_정렬_내림차순() throws Exception {
        // given
        PlaceCond placeCond = PlaceCond.builder()
            .currentPage(0)
            .postPerPage(10)
            .sortStandard(SortStandard.GRADE)
            .order(SortOrder.DESCENDING)
            .build();

        // when
        Page<Place> findPlacePage = placeProvider.findAll(placeCond);

        // then
        Assertions.assertThat(findPlacePage.getContent().get(0).getName()).isEqualTo("B");
        Assertions.assertThat(findPlacePage.getContent().get(1).getName()).isEqualTo("B");
    }

    @Test
    public void 평점순_정렬_내림차순() throws Exception {
        // given
        PlaceCond placeCond = PlaceCond.builder()
            .currentPage(0)
            .postPerPage(10)
            .sortStandard(SortStandard.GRADE)
            .order(SortOrder.DESCENDING)
            .build();

        // when
        Page<Place> findPlacePage = placeProvider.findAll(placeCond);

        // then
        Assertions.assertThat(findPlacePage.getContent().get(0).getGrade()).isEqualTo(5);
        Assertions.assertThat(findPlacePage.getContent().get(1).getGrade()).isEqualTo(5);
    }
}
