package com.myplace.myreview.place.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.myplace.myreview.place.domain.Place;
import com.myplace.myreview.place.dto.PlaceForm;
import com.myplace.myreview.place.repository.PlaceRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PlaceServiceTest {

    @Mock
    PlaceRepository placeRepository;

    @InjectMocks
    PlaceService placeService;

    @Test
    public void 저장_테스트() throws Exception {
        // given
        Place place = Place.builder()
            .id(1)
            .name("A")
            .address("서울특별시")
            .review("good")
            .grade(3)
            .build();

        when(placeRepository.save(any())).thenReturn(place);

        // when
        PlaceForm placeForm = PlaceForm.builder()
            .name("A")
            .address("서울특별시")
            .review("good")
            .grade(3)
            .build();

        // then
        assertThat(placeService.save(placeForm)).isEqualTo(1);
    }
}