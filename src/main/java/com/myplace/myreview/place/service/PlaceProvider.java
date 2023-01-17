package com.myplace.myreview.place.service;

import com.myplace.myreview.global.exception.DataNotFoundException;
import com.myplace.myreview.place.domain.Place;
import com.myplace.myreview.place.dto.PlaceCond;
import com.myplace.myreview.place.dto.PlaceParam;
import com.myplace.myreview.place.repository.PlaceCustomRepositoryImpl;
import com.myplace.myreview.place.repository.PlaceRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PlaceProvider {

    private final PlaceRepository placeRepository;
    private final PlaceCustomRepositoryImpl placeCustomRepository;

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

    public Page<Place> findAll(PlaceCond placeCond) {

        Page<Place> placePage = placeCustomRepository.findAll(placeCond);

        if (placePage == null) {
            throw new DataNotFoundException("장소가 존재하지 않습니다.");
        }

        return placePage;
    }


}
