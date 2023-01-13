package com.myplace.myreview.place.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.myplace.myreview.global.exception.DataNotFoundException;
import com.myplace.myreview.place.domain.Place;
import com.myplace.myreview.place.dto.PlaceCond;
import com.myplace.myreview.place.repository.PlaceRepository;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class PlaceProviderMockTest {

    @Mock
    PlaceRepository placeRepository;

    @InjectMocks
    PlaceProvider placeProvider;

    private static List<Place> places = new ArrayList<>();

    @BeforeAll
    public static void setData() {
        int num;
        for (int i = 1; i <= 25; i++) {
            num = (i % 3 == 0) ? (i + 1) : (i - 2);
            places.add(
                Place.builder()
                    .id(num)
                    .name((num) + "번째 장소")
                    .address("서울특별시")
                    .grade((num / 5) + 1)
                    .build()
            );
        }
    }

    @Test
    public void 전체조회() throws Exception {
        // given
        PlaceCond placeCond = PlaceCond.builder()
            .currentPage(1)
            .postPerPage(10)
            .build();

        when(placeRepository.findAll(any(Pageable.class)))
            .thenReturn(new PageImpl<>(places, PageRequest.of(1, 10), places.size()));

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
    public void 데이터가_존재하지_않는_경우() throws Exception {
        // given
        PlaceCond placeCond = PlaceCond.builder()
            .currentPage(1)
            .postPerPage(10)
            .build();

        when(placeRepository.findAll(any(Pageable.class)))
            .thenReturn(new PageImpl<>(new ArrayList<>()));

        // when, then
        assertThrows(DataNotFoundException.class, () -> placeProvider.findAll(placeCond));
    }
}