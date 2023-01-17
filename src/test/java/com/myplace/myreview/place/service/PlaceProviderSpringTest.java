package com.myplace.myreview.place.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.myplace.myreview.global.exception.DataNotFoundException;
import com.myplace.myreview.place.domain.Place;
import com.myplace.myreview.place.dto.OrderDirect;
import com.myplace.myreview.place.dto.OrderStandard;
import com.myplace.myreview.place.dto.PlaceCond;
import com.myplace.myreview.place.repository.PlaceRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
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

    @Transactional
    @Rollback
    @Test
    public void 전체조회() throws Exception {
        // given
        placeRepository.saveAll(places);

        PlaceCond placeCond = PlaceCond.builder()
            .currentPage(0)
            .postPerPage(10)
            .build();

        // when
        Page<Place> findPlacePage = placeProvider.findAll(placeCond);

        // then
        assertThat(findPlacePage.getTotalElements()).isEqualTo(25);
        assertThat(findPlacePage.getTotalPages()).isEqualTo(3);
        assertThat(findPlacePage.getSize()).isEqualTo(10);
        assertThat(findPlacePage.getNumber()).isEqualTo(0);
        assertThat(findPlacePage.getNumberOfElements()).isEqualTo(10);
    }

    @Transactional
    @Rollback
    @Test
    public void 데이터가_존재하지_않는_경우() throws Exception {
        // given
        PlaceCond placeCond = PlaceCond.builder()
            .currentPage(0)
            .postPerPage(10)
            .build();

        // when
        // then
        assertThrows(DataNotFoundException.class,
            () -> placeProvider.findAll(placeCond));
    }

    @Transactional
    @Rollback
    @Test
    public void 가나다순_정렬() throws Exception {
        // given
        placeRepository.saveAll(places);

        PlaceCond placeCond = PlaceCond.builder()
            .currentPage(0)
            .postPerPage(10)
            .orderStandard(OrderStandard.NAME)
            .build();

        // when
        Page<Place> findPlacePage = placeProvider.findAll(placeCond);

        // then
        assertThat(findPlacePage.getContent().get(0).getName()).isEqualTo("A");
        assertThat(findPlacePage.getContent().get(1).getName()).isEqualTo("A");
    }

    @Transactional
    @Rollback
    @Test
    public void 평점순_정렬() throws Exception {
        // given
        placeRepository.saveAll(places);

        PlaceCond placeCond = PlaceCond.builder()
            .currentPage(0)
            .postPerPage(10)
            .orderStandard(OrderStandard.GRADE)
            .build();

        // when
        Page<Place> findPlacePage = placeProvider.findAll(placeCond);

        // then
        assertThat(findPlacePage.getContent().get(0).getGrade()).isEqualTo(1);
        assertThat(findPlacePage.getContent().get(9).getGrade())
            .isGreaterThanOrEqualTo(1);
    }

    @Transactional
    @Rollback
    @Test
    public void 가나다순_정렬_내림차순() throws Exception {
        // given
        placeRepository.saveAll(places);

        PlaceCond placeCond = PlaceCond.builder()
            .currentPage(0)
            .postPerPage(10)
            .orderStandard(OrderStandard.NAME)
            .orderDirect(OrderDirect.DESCENDING)
            .build();

        // when
        Page<Place> findPlacePage = placeProvider.findAll(placeCond);

        // then
        assertThat(findPlacePage.getContent().get(0).getName()).isEqualTo("B");
        assertThat(findPlacePage.getContent().get(1).getName()).isEqualTo("B");
    }

    @Transactional
    @Rollback
    @Test
    public void 평점순_정렬_내림차순() throws Exception {
        // given
        placeRepository.saveAll(places);

        PlaceCond placeCond = PlaceCond.builder()
            .currentPage(0)
            .postPerPage(10)
            .orderStandard(OrderStandard.GRADE)
            .orderDirect(OrderDirect.DESCENDING)
            .build();

        // when
        Page<Place> findPlacePage = placeProvider.findAll(placeCond);

        // then
        assertThat(findPlacePage.getContent().get(0).getGrade()).isEqualTo(5);
        assertThat(findPlacePage.getContent().get(1).getGrade()).isEqualTo(5);
    }
}
