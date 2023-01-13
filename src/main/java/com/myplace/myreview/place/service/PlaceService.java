package com.myplace.myreview.place.service;

import com.myplace.myreview.place.domain.Place;
import com.myplace.myreview.place.dto.PlaceForm;
import com.myplace.myreview.place.dto.PlaceParam;
import com.myplace.myreview.place.repository.PlaceRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class PlaceService {

    private final PlaceRepository placeRepository;

    public long save(PlaceForm placeForm) {
        Place place = placeRepository.save(placeForm.toEntity());

        return place.getId();
    }
}
