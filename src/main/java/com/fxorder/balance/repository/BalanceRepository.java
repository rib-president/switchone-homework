package com.fxorder.balance.repository;

import com.fxorder.balance.entity.Balance;
import com.fxorder.enums.CurrencyCode;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface BalanceRepository extends JpaRepository<Balance, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Balance> findWithLockByCurrencyCode(CurrencyCode currencyCode);
}