package com.myplace.myreview.place.service;

import com.myplace.myreview.global.exception.DataNotFoundException;
import com.myplace.myreview.place.domain.Place;
import com.myplace.myreview.place.dto.SortOrder;
import com.myplace.myreview.place.dto.SortStandard;
import com.myplace.myreview.place.dto.PlaceCond;
import com.myplace.myreview.place.dto.PlaceParam;
import com.myplace.myreview.place.repository.PlaceRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PlaceProvider {

    private final PlaceRepository placeRepository;

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
        Pageable pageable = PageRequest.of(
            placeCond.getCurrentPage()
            , placeCond.getPostPerPage()
            , getSort(placeCond.getSortStandard(), placeCond.getOrder())
        );

        Page<Place> placePage = placeRepository.findAll(pageable);

        if (placePage.getTotalElements() == 0) {
            throw new DataNotFoundException("장소가 존재하지 않습니다.");
        }

        return placePage;
    }

    private Sort getSort(SortStandard standard, SortOrder order) {
        Sort sort = getSortByStandard(standard);

        if (order == null || order.equals(SortOrder.ASCENDING)) {
            return sort;
        }

        return sort.descending();

    }

    private Sort getSortByStandard(SortStandard standard) {
        if (standard == null) {
            return Sort.by("name").ascending();
        }

        return Sort.by(standard.getValue()).ascending();
    }
}
