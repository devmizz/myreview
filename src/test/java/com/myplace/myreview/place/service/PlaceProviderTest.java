package com.myplace.myreview.place.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.myplace.myreview.global.exception.DataNotFoundException;
import com.myplace.myreview.place.domain.Place;
import com.myplace.myreview.place.dto.PlaceCond;
import com.myplace.myreview.place.dto.PlaceParam;
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
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
class PlaceProviderTest {

    @Mock
    PlaceRepository placeRepository;

    @InjectMocks
    PlaceProvider placeProvider;

    private static List<Place> places = new ArrayList<>();

    @BeforeAll
    public static void setData() {
        for (int i = 0; i < 25; i++) {
            places.add(
                Place.builder()
                    .id(i + 1)
                    .name(i + "번째 장소")
                    .address("good")
                    .build()
            );
        }
    }

    @Test
    public void 전체조회() throws Exception {
        // given
        PlaceCond placeCond = PlaceCond.builder()
            .page(1)
            .postPerPage(10)
            .build();

        when(placeRepository.findAll(any(Pageable.class)))
            .thenReturn(new PageImpl<>(places, PageRequest.of(1, 10), 25));

        // when
        Page<Place> findPlacePage = placeProvider.findAll(placeCond);

        // then
        Assertions.assertThat(25).isEqualTo(findPlacePage.getTotalElements());
        Assertions.assertThat(3).isEqualTo(findPlacePage.getTotalPages());
        Assertions.assertThat(10).isEqualTo(findPlacePage.getSize());
        Assertions.assertThat(1).isEqualTo(findPlacePage.getNumber());
        Assertions.assertThat(25).isEqualTo(findPlacePage.getNumberOfElements());
    }

    @Test
    public void 데이터가_존재하지_않는_경우() throws Exception {
        // given
        PlaceCond placeCond = PlaceCond.builder()
            .page(1)
            .postPerPage(10)
            .build();

        when(placeRepository.findAll(any(Pageable.class)))
            .thenReturn(new PageImpl<>(new ArrayList<>()));

        // when, then
        assertThrows(DataNotFoundException.class, () -> placeProvider.findAll(placeCond));
    }

}