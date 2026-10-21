package com.fxorder.exchangerate.repository;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.fxorder.exchangerate.entity.ExchangeRateHistory;
import com.fxorder.exchangerate.entity.QExchangeRateHistory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.fxorder.exchangerate.entity.QExchangeRateHistory.exchangeRateHistory;

@RequiredArgsConstructor
public class ExchangeRateHistoryRepositoryImpl implements ExchangeRateHistoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ExchangeRateHistory> findLatest() {
        QExchangeRateHistory sub = new QExchangeRateHistory("sub");

        return queryFactory
                .selectFrom(exchangeRateHistory)
                .where(exchangeRateHistory.dateTime.eq(
                        JPAExpressions
                                .select(sub.dateTime.max())
                                .from(sub)
                                .where(sub.currencyCode.eq(exchangeRateHistory.currencyCode))
                ))
                .fetch();
    }
}
