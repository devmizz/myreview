package com.myplace.myreview.place.repository;

import com.myplace.myreview.place.domain.Place;
import com.myplace.myreview.place.dto.PlaceCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlaceCustomRepository {

    Page<Place> findAll(PlaceCond placeCond);

}
