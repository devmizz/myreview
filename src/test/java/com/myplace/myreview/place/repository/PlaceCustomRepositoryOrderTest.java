package com.myplace.myreview.place.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.myplace.myreview.place.domain.Place;
import com.myplace.myreview.place.dto.OrderDirect;
import com.myplace.myreview.place.dto.OrderStandard;
import com.myplace.myreview.place.dto.PlaceCond;
import com.myplace.myreview.place.service.PlaceProvider;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class PlaceCustomRepositoryOrderTest {

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
