package com.myplace.myreview.place.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.myplace.myreview.global.exception.DataNotFoundException;
import com.myplace.myreview.place.domain.Place;
import com.myplace.myreview.place.dto.PlaceCond;
import com.myplace.myreview.place.dto.PlaceParam;
import com.myplace.myreview.place.repository.PlaceCustomRepositoryImpl;
import com.myplace.myreview.place.repository.PlaceRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class PlaceProviderTest {

    @Mock
    PlaceRepository placeRepository;

    @Mock
    PlaceCustomRepositoryImpl placeCustomRepository;
    
    @InjectMocks
    PlaceProvider placeProvider;

    private static List<Place> places = new ArrayList<>();

    @BeforeAll
    public static void setData() {
        for (int i = 1; i <= 25; i++) {
            places.add(
                Place.builder()
                    .id(i)
                    .name("안녕")
                    .address("서울특별시")
                    .grade(3)
                    .build()
            );
        }
    }
    
    @Test
    public void 존재하지_않는_장소_id_조회() throws Exception {
        // given
        Optional<Place> emptyPlace = Optional.empty();
        when(placeRepository.findById(any())).thenReturn(emptyPlace);
        
        // when
        // then
        assertThrows(NoSuchElementException.class, () -> placeProvider.findOne(1));
    }

    @Test
    public void 장소_id_조회() throws Exception {
        // given
        Optional<Place> optPlace = Optional.of(Place.builder()
            .id(1)
            .name("A")
            .address("서울특별시")
            .review("아")
            .grade(3)
            .build());

        when(placeRepository.findById(any())).thenReturn(optPlace);

        // when
        // then
        assertThat(placeProvider.findOne(1).getId()).isEqualTo(1);
    }
    
    @Test
    public void 데이터가_없는_경우_전체_조회_예외() throws Exception {
        // given
        when(placeCustomRepository.findAll(any(PlaceCond.class))).thenThrow(new DataNotFoundException("결과가 존재하지 않습니다."));

        PlaceCond placeCond = PlaceCond.builder().build();
        
        // when
        // then
        assertThrows(DataNotFoundException.class, () -> placeProvider.findAll(placeCond));
    }
    
    @Test
    public void 조회_된_데이터는_PlaceParam으로_변환() throws Exception {
        // given
        Page<Place> placePage = new PageImpl<>(places, PageRequest.of(0, 10), 25);
        when(placeCustomRepository.findAll(any(PlaceCond.class))).thenReturn(placePage);
        
        // when
        Page<PlaceParam> placeParamPage = placeProvider.findAll(PlaceCond.builder().build());
        
        // then
        assertThat(placeParamPage.getTotalElements()).isEqualTo(25);
    }
}