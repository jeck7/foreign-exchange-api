package com.example.demo.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "fx")
public class FxProviderConfig {

    private String provider;
    private Provider exchangerate;
    private Provider fixer;
    private Provider currencylayer;

    public static class Provider {
        private String url;
        private String accessKey;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getAccessKey() {
            return accessKey;
        }

        public void setAccessKey(String accessKey) {
            this.accessKey = accessKey;
        }
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Provider getExchangerate() {
        return exchangerate;
    }

    public void setExchangerate(Provider exchangerate) {
        this.exchangerate = exchangerate;
    }

    public Provider getFixer() {
        return fixer;
    }

    public void setFixer(Provider fixer) {
        this.fixer = fixer;
    }

    public Provider getCurrencylayer() {
        return currencylayer;
    }

    public void setCurrencylayer(Provider currencylayer) {
        this.currencylayer = currencylayer;
    }
}
