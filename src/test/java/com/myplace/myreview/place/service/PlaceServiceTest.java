package com.myplace.myreview.place.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.myplace.myreview.place.domain.Place;
import com.myplace.myreview.place.dto.PlaceForm;
import com.myplace.myreview.place.repository.PlaceRepository;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PlaceServiceTest {

    @Mock
    private PlaceRepository placeRepository;

    @InjectMocks
    PlaceService placeService;

    @InjectMocks
    PlaceProvider placeProvider;

    @Test
    public void 장소_등록_및_조회() throws Exception {
      // given
        PlaceForm placeForm = PlaceForm.builder()
            .name("장소A")
            .address("서울특별시 동작구 상도동 12")
            .review("맛있어요")
            .grade(3)
            .build();

        Place place = Place.builder()
            .id(1L)
            .build();

        Optional<Place> optPlace = Optional.of(place);

        when(placeRepository.save(any(Place.class))).thenReturn(place);
        when(placeRepository.findById(1L)).thenReturn(optPlace);

      // when
        long savedId = placeService.save(placeForm);

      // then
        Assertions.assertThat(savedId).isEqualTo(placeProvider.findOne(savedId).getId());
    }
}