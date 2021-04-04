package com.Test.test_app;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "stock_table")
public class Stock implements Parcelable {
    @PrimaryKey
    @NonNull
    private String Ticker;

    private String Name;

    private String LogoUrl;

    private String Currency;

    private Double CurrentPrice;

    private Double Different;

    private int StarMode = R.drawable.star_unselected;

    public Stock() {
        Ticker = null;
    }

    public String getTicker() {
        return Ticker;
    }

    public String getName() {
        return Name;
    }

    public String getLogoUrl() {
        return LogoUrl;
    }

    public String getCurrency() {
        return Currency;
    }

    public Double getCurrentPrice() {
        return CurrentPrice;
    }

    public Double getDifferent() {
        return Different;
    }

    public int getStarMode() {
        return StarMode;
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

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public void setCurrentPrice(Double currentPrice) {
        CurrentPrice = currentPrice;
    }

    public void setDifferent(Double different) {
        Different = different;
    }

    public void setStarMode(int starMode) {
        StarMode = starMode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return StarMode == stock.StarMode &&
                Ticker.equals(stock.Ticker) &&
                Objects.equals(Name, stock.Name) &&
                Objects.equals(LogoUrl, stock.LogoUrl) &&
                Objects.equals(Currency, stock.Currency) &&
                Objects.equals(CurrentPrice, stock.CurrentPrice) &&
                Objects.equals(Different, stock.Different);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Ticker, Name, LogoUrl, Currency, CurrentPrice, Different, StarMode);
    }

    protected Stock(Parcel in) {
        Ticker = in.readString();
        Name = in.readString();
        LogoUrl = in.readString();
        Currency = in.readString();
        if (in.readByte() == 0) {
            CurrentPrice = null;
        } else {
            CurrentPrice = in.readDouble();
        }
        if (in.readByte() == 0) {
            Different = null;
        } else {
            Different = in.readDouble();
        }
        StarMode = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Ticker);
        dest.writeString(Name);
        dest.writeString(LogoUrl);
        dest.writeString(Currency);
        if (CurrentPrice == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(CurrentPrice);
        }
        if (Different == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(Different);
        }
        dest.writeInt(StarMode);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Stock> CREATOR = new Creator<Stock>() {
        @Override
        public Stock createFromParcel(Parcel in) {
            return new Stock(in);
        }

        @Override
        public Stock[] newArray(int size) {
            return new Stock[size];
        }
    };

}
