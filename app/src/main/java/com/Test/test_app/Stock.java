package com.Test.test_app;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class Stock {
    private String Ticker, Name, Currency;
    private Double CurrentPrice, Different;
    private Bitmap Logo;
    private int star = R.drawable.star_unselected;

    public Stock() {
    }

    public void setLogo(Bitmap logo) {
        Logo = logo;
    }

    public Bitmap getLogo() {
        return Logo;
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

    public void setStarMode(int resource) {
        star = resource;
    }

    public int getStarMode() {
        return star;
    }
}
