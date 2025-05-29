package com.example.demo.wrappers;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CurrencyConversionWrapper {
    private String id;
    private String fromCurrency;
    private String toCurrency;
    private double amount;
    private double convertedAmount;
    private double rate;
    private LocalDateTime transactionDate;
    private LocalDateTime timestamp = LocalDateTime.now();
}
