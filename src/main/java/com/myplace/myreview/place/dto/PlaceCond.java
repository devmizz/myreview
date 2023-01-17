package com.myplace.myreview.place.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PlaceCond {

    @Builder.Default
    private SearchStandard searchStandard = SearchStandard.NAME;

    private String searchWord;

    @Builder.Default
    private int searchGrade = 0;

    @Builder.Default
    private int currentPage = 0;

    @Builder.Default
    private int postPerPage = 10;

    @Builder.Default
    private OrderStandard orderStandard = OrderStandard.NAME;

    @Builder.Default
    private OrderDirect orderDirect = OrderDirect.ASCENDING;
}
