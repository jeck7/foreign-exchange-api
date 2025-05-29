package com.example.demo.repository;

import com.example.demo.model.CurrencyConversion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CurrencyConversionRepository extends JpaRepository<CurrencyConversion, String> {
    List<CurrencyConversion> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
    Page<CurrencyConversion> findById(String id, Pageable pageable);
    Page<CurrencyConversion> findByTransactionDate(LocalDateTime transactionDate, Pageable pageable);
}
