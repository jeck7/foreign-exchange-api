package com.example.demo.service;

import com.example.demo.model.CurrencyConversion;
import com.example.demo.repository.CurrencyConversionRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.mockito.Mockito.*;

public class ExchangeRateServiceTest {
    @Mock
    CurrencyConversionRepository repository;

    @InjectMocks
    ExchangeRateService exchangeRateService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetExchangeRate() throws Exception {
        double result = exchangeRateService.getExchangeRate("from", "to");
        Assert.assertEquals(0d, result);
    }

    @Test
    public void testConvertCurrency() throws Exception {
        when(repository.save(any(CurrencyConversion.class))).thenReturn(new CurrencyConversion());

        CurrencyConversion result = exchangeRateService.convertCurrency("from", "to", 0d);
        Assert.assertEquals(new CurrencyConversion("id", "fromCurrency", "toCurrency", 0d, 0d, 0d, LocalDateTime.of(2025, Month.MAY, 30, 0, 4, 3), LocalDateTime.of(2025, Month.MAY, 30, 0, 4, 3)), result);
    }

    @Test
    public void testGetConversions() throws Exception {
        when(repository.findByTimestampBetween(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(List.of(new CurrencyConversion("id", "fromCurrency", "toCurrency", 0d, 0d, 0d, LocalDateTime.of(2025, Month.MAY, 30, 0, 4, 3), LocalDateTime.of(2025, Month.MAY, 30, 0, 4, 3))));
        when(repository.findById(any(String.class))).thenReturn(null);

        List<CurrencyConversion> result = exchangeRateService.getConversions(null, null);
        Assert.assertEquals(List.of(new CurrencyConversion("id", "fromCurrency", "toCurrency", 0d, 0d, 0d, LocalDateTime.of(2025, Month.MAY, 30, 0, 4, 3), LocalDateTime.of(2025, Month.MAY, 30, 0, 4, 3))), result);
    }

    @Test
    public void testGetAllConversions() throws Exception {
        when(repository.findAll()).thenReturn(List.of(new CurrencyConversion()));

        List<CurrencyConversion> result = exchangeRateService.getAllConversions();
        Assert.assertEquals(List.of(new CurrencyConversion("id", "fromCurrency", "toCurrency", 0d, 0d, 0d, LocalDateTime.of(2025, Month.MAY, 30, 0, 4, 3), LocalDateTime.of(2025, Month.MAY, 30, 0, 4, 3))), result);
    }

    @Test
    public void testGetHistory() throws Exception {
        when(repository.findById(anyString(), any(Pageable.class))).thenReturn(null);
        when(repository.findByTransactionDate(any(LocalDateTime.class), any(Pageable.class))).thenReturn(null);

        Page<CurrencyConversion> result = exchangeRateService.getHistory("id", LocalDateTime.of(2025, Month.MAY, 30, 0, 4, 3), null);
        Assert.assertEquals(null, result);
    }
}