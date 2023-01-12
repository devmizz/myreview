package com.myplace.myreview.stub;

import com.myplace.myreview.place.domain.Place;
import java.util.HashMap;

public class StubPlaceRepository {

    private static final HashMap<Long, Place> placeMemory = new HashMap<>();
    private static long id = 1;

    public Place save(Place place) {
        Place savePlace = Place.builder()
            .id(id)
            .name(place.getName())
            .address(place.getAddress())
            .review(place.getReview())
            .grade(place.getGrade())
            .build();
        placeMemory.put(id, savePlace);

        return savePlace;
    }

    public Place find(long id) {
        return placeMemory.get(id);
    }
}
