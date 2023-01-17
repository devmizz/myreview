package com.myplace.myreview.place.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PlaceCond {

    private SearchStandard searchStandard;
    private String searchWord;
    private int searchGrade;
    @Builder.Default
    private int currentPage = 0;
    @Builder.Default
    private int postPerPage = 10;
    @Builder.Default
    private OrderStandard orderStandard = OrderStandard.NAME;
    @Builder.Default
    private OrderDirect orderDirect = OrderDirect.ASCENDING;
}
