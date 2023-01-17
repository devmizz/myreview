package com.myplace.myreview.place.repository;

import static com.myplace.myreview.place.domain.QPlace.place;

import com.myplace.myreview.global.exception.DataNotFoundException;
import com.myplace.myreview.place.domain.Place;
import com.myplace.myreview.place.dto.OrderDirect;
import com.myplace.myreview.place.dto.OrderStandard;
import com.myplace.myreview.place.dto.PlaceCond;
import com.myplace.myreview.place.dto.SearchStandard;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PlaceCustomRepositoryImpl implements PlaceCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Place> findAll(PlaceCond placeCond) {

        List<Place> places = jpaQueryFactory.selectFrom(place)
            .where(getWhereExpression(placeCond.getSearchStandard(), placeCond.getSearchWord()))
            .offset(placeCond.getCurrentPage() * placeCond.getPostPerPage())
            .limit(placeCond.getPostPerPage())
            .orderBy(getOrderExpression(placeCond.getOrderStandard(), placeCond.getOrderDirect()))
            .fetch();

        if (places.size() == 0) {
            throw new DataNotFoundException("결과가 존재하지 않습니다.");
        }

        Pageable pageable = PageRequest.of(placeCond.getCurrentPage(), placeCond.getPostPerPage(),
            Sort.by(placeCond.getOrderDirect().equals(OrderDirect.DESCENDING) ? Direction.DESC
                : Direction.ASC, placeCond.getOrderDirect().getValue()));

        long count = jpaQueryFactory.select(place.count()).from(place).fetchOne();

        return new PageImpl<>(places, pageable, count);
    }

    private Predicate getWhereExpression(SearchStandard searchStandard, String searchWord) {
        if (searchWord == null) {
            return place.id.eq(place.id);
        }

        ComparableExpressionBase columnPath = getColumnPath(searchStandard.getValue());

        return columnPath.eq(searchWord);
    }

    private OrderSpecifier getOrderExpression(OrderStandard orderStandard, OrderDirect orderDirect) {
        ComparableExpressionBase columnPath = getColumnPath(orderStandard.getValue());

        if (orderDirect.equals(OrderDirect.DESCENDING)) {
            return columnPath.desc();
        }

        return columnPath.asc();
    }

    private ComparableExpressionBase getColumnPath(String columnName) {
        switch (columnName) {
            case "id" :
                return place.id;
            case "name" :
                return place.name;
            case "address" :
                return place.address;
            case "review" :
                return place.review;
            case "grade" :
                return place.grade;
        }

        return null;
    }
}
