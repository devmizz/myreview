package com.myplace.myreview.place.repository;

import com.myplace.myreview.place.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}
