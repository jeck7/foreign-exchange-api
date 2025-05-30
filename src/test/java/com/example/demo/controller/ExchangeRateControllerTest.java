package com.example.demo.controller;

import com.example.demo.model.CurrencyConversion;
import com.example.demo.service.ExchangeRateService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class ExchangeRateControllerTest {
    @Mock
    ExchangeRateService service;
    @InjectMocks
    ExchangeRateController exchangeRateController;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetRate() throws Exception {
        when(service.getExchangeRate(anyString(), anyString())).thenReturn(0d);

        double result = exchangeRateController.getRate("from", "to");
        Assert.assertEquals(0d, result);
    }

    @Test
    public void testConvert() throws Exception {
        when(service.convertCurrency(anyString(), anyString(), anyDouble())).thenReturn(new CurrencyConversion("id", "fromCurrency", "toCurrency", 0d, 0d, 0d, LocalDate.of(2025, Month.MAY, 30), LocalDateTime.of(2025, Month.MAY, 30, 10, 23, 14)));

        CurrencyConversion result = exchangeRateController.convert("from", "to", 0d);
        Assert.assertEquals(new CurrencyConversion("id", "fromCurrency", "toCurrency", 0d, 0d, 0d, LocalDate.of(2025, Month.MAY, 30), LocalDateTime.of(2025, Month.MAY, 30, 10, 23, 14)), result);
    }

    @Test
    public void testHistory() throws Exception {
        when(service.getConversions(any(Optional.class), any(Optional.class))).thenReturn(List.of(new CurrencyConversion("id", "fromCurrency", "toCurrency", 0d, 0d, 0d, LocalDate.of(2025, Month.MAY, 30), LocalDateTime.of(2025, Month.MAY, 30, 10, 23, 14))));

        List<CurrencyConversion> result = exchangeRateController.history("id", new GregorianCalendar(2025, Calendar.MAY, 30, 10, 23).getTime());
        Assert.assertEquals(List.of(new CurrencyConversion("id", "fromCurrency", "toCurrency", 0d, 0d, 0d, LocalDate.of(2025, Month.MAY, 30), LocalDateTime.of(2025, Month.MAY, 30, 10, 23, 14))), result);
    }

    @Test
    public void testGetConversionHistory() throws Exception {
        when(service.getHistory(anyString(), any(LocalDate.class), any(Pageable.class))).thenReturn(null);

        ResponseEntity<Page<CurrencyConversion>> result = exchangeRateController.getConversionHistory("transactionId", LocalDate.of(2025, Month.MAY, 30), 0, 0);
        Assert.assertEquals(new ResponseEntity<Page<CurrencyConversion>>(null, null, 0), result);
    }
}
