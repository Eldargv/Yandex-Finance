package com.Test.test_app;

public class CompanyModel {
    private String Ticker, Name, LogoUrl, Currency;
    private Double CurrentPrice, Different;

    public CompanyModel() {
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public String getCurrency() {
        return Currency;
    }

    public double getCurrentPrice() {
        return CurrentPrice;
    }

    public double getDifferent() {
        return Different;
    }

    public String getLogoUrl() {
        return LogoUrl;
    }

    public String getName() {
        return Name;
    }

    public String getTicker() {
        return Ticker;
    }

    public void setCurrentPrice(double currentPrice) {
        CurrentPrice = currentPrice;
    }

    public void setDifferent(double different) {
        Different = different;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setTicker(String ticker) {
        Ticker = ticker;
    }

    public void setLogoUrl(String logoUrl) {
        LogoUrl = logoUrl;
    }
}
