package com.example.demo.controller;

import com.example.demo.model.CurrencyConversion;
import com.example.demo.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/exchange")
public class ExchangeRateController {

    @Autowired
    private ExchangeRateService service;

    @GetMapping("/rate")
    public double getRate(@RequestParam String from, @RequestParam String to) {
        return service.getExchangeRate(from, to);
    }

    @PostMapping("/convert")
    public CurrencyConversion convert(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam double amount) {
        return service.convertCurrency(from, to, amount);
    }

    @GetMapping("/history")
    public List<CurrencyConversion> history(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {

        return service.getConversions(Optional.ofNullable(id), Optional.ofNullable(date));
//        return service.getAllConversions();
    }

    @GetMapping("/historyPagable")
    public ResponseEntity<Page<CurrencyConversion>> getConversionHistory(
            @RequestParam(required = false) String transactionId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<CurrencyConversion> results = service.getHistory(transactionId, date, pageable);

        return ResponseEntity.ok(results);
    }
}
