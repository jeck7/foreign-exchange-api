package com.example.demo.service;

import com.example.demo.model.CurrencyConversion;
import com.example.demo.repository.CurrencyConversionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ExchangeRateService {

    @Autowired
    private CurrencyConversionRepository repository;

    @Value("${exchange.provider}")
    private String provider;

    @Value("${exchange.fixer.api-key}")
    private String fixerKey;

    @Value("${exchange.fixer.url}")
    private String fixerUrl;

    @Value("${exchange.currencylayer.api-key}")
    private String currencyLayerKey;

    @Value("${exchange.currencylayer.url}")
    private String currencyLayerUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public double getExchangeRate(String from, String to) {
        String url = buildUrl(from, to, 1);
        Map response = restTemplate.getForObject(url, Map.class);
        return extractRate(response);
    }

    public CurrencyConversion convertCurrency(String from, String to, double amount) {
        String url = buildUrl(from, to, amount);
        Map response = restTemplate.getForObject(url, Map.class);
        double rate = extractRate(response);
        double result = extractResult(response);

        CurrencyConversion conversion = new CurrencyConversion();
        conversion.setFromCurrency(from);
        conversion.setToCurrency(to);
        conversion.setAmount(amount);
        conversion.setConvertedAmount(result);
        conversion.setRate(rate);
        repository.save(conversion);
        return conversion;
    }

    public List<CurrencyConversion> getConversions(Optional<String> id, Optional<Date> date) {
        if (id.isPresent()) {
            return repository.findById(id.get()).map(List::of).orElse(List.of());
        } else if (date.isPresent()) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date.get());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            Date start = cal.getTime();
            cal.set(Calendar.HOUR_OF_DAY, 23);
            Date end = cal.getTime();
            return repository.findByTimestampBetween(
                    new java.sql.Timestamp(start.getTime()).toLocalDateTime(),
                    new java.sql.Timestamp(end.getTime()).toLocalDateTime());
        }
        return List.of();
    }

    public List<CurrencyConversion> getAllConversions() {
        return repository.findAll();
    }

    public Page<CurrencyConversion> getHistory(String id, LocalDateTime date, Pageable pageable) {
        if (id != null && !id.isEmpty()) {
            return repository.findById(id, pageable);
        } else if (date != null) {
            return repository.findByTransactionDate(date, pageable);
        } else {
            throw new IllegalArgumentException("Either transactionId or date must be provided");
        }
    }

    private String buildUrl(String from, String to, double amount) {
        switch (provider.toLowerCase()) {
            case "fixer":
                return String.format("%s/convert?access_key=%s&from=%s&to=%s&amount=%f", fixerUrl, fixerKey, from, to, amount);
            case "currencylayer":
                return String.format("%s/convert?access_key=%s&from=%s&to=%s&amount=%f", currencyLayerUrl, currencyLayerKey, from, to, amount);
            default:
                return String.format("https://api.exchangerate.host/convert?access_key=8ad7a73ff1299a61f1ca117940de99af&from=%s&to=%s&amount=%f", from, to, amount);
        }
    }

    private double extractRate(Map response) {
        if (provider.equalsIgnoreCase("fixer") || provider.equalsIgnoreCase("currencylayer")) {
            return ((Number) response.get("info") != null ? (Number) ((Map) response.get("info")).get("rate") : 1.0).doubleValue();
        }
        if (provider.equalsIgnoreCase("exchangeratehost")) {
            Map info = (Map) response.get("info");
            return (double) info.get("quote");
        }
        return (Double) ((Map) response.get("result")).get("rate");
    }

    private double extractResult(Map response) {
        return ((Number) response.get("result")).doubleValue();
    }
}
