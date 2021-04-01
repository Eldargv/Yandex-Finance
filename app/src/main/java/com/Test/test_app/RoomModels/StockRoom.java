package com.Test.test_app.RoomModels;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class StockRoom implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "ticker")
    public String ticker;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "currency")
    public String currency;

    @ColumnInfo(name = "logo")
    public int logo;

    public StockRoom(){
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockRoom stockRoom = (StockRoom) o;
        return id == stockRoom.id &&
                logo == stockRoom.logo &&
                Objects.equals(ticker, stockRoom.ticker) &&
                Objects.equals(name, stockRoom.name) &&
                Objects.equals(currency, stockRoom.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ticker, name, currency, logo);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(ticker);
        dest.writeString(name);
        dest.writeString(currency);
        dest.writeInt(logo);
    }

    protected StockRoom(Parcel in) {
        id = in.readInt();
        ticker = in.readString();
        name = in.readString();
        currency = in.readString();
        logo = in.readInt();
    }

    public static final Creator<StockRoom> CREATOR = new Creator<StockRoom>() {
        @Override
        public StockRoom createFromParcel(Parcel in) {
            return new StockRoom(in);
        }

        @Override
        public StockRoom[] newArray(int size) {
            return new StockRoom[size];
        }
    };
}
