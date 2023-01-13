package com.myplace.myreview.place.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PlaceCond {

    private int currentPage;
    private int postPerPage;
    private SortStandard sortStandard;
    private SortOrder order;
}
