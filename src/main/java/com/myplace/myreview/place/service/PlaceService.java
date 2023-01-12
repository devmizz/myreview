package com.myplace.myreview.place.service;

import com.myplace.myreview.place.domain.Place;
import com.myplace.myreview.place.dto.PlaceForm;
import com.myplace.myreview.place.dto.PlaceParam;
import com.myplace.myreview.place.repository.PlaceRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PlaceService {

    private final PlaceRepository placeRepository;

    public long save(PlaceForm placeForm) {
        Place place = placeRepository.save(placeForm.toEntity());

        return place.getId();
    }

    public PlaceParam findOne(long id) {
        Place place = placeRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("존재하지 않는 장소입니다."));

        return PlaceParam.builder()
            .id(place.getId())
            .name(place.getName())
            .address(place.getAddress())
            .review(place.getReview())
            .grade(place.getGrade())
            .build();
    }
}
