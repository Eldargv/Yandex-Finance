package com.Test.test_app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class Manager {

    private Context context;
    private DBHelper dbhelper;
    private SQLiteDatabase db;

    public Manager(Context context) {
        this.context = context;
        dbhelper = new DBHelper(context);
    }

    public void OpenDB() {
        db = dbhelper.getWritableDatabase();
    }

    public void insertIN(String title, String desc) {
        ContentValues cval = new ContentValues();
        cval.put(Constants.TITLE, title);
        cval.put(Constants.DESC, desc);
        db.insert(Constants.TABLE_NAME, null, cval);
    }

    public List<String> Read() {
        List<String> tempList = new ArrayList<>();
        Cursor cursor = db.query(
                Constants.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex(Constants.TITLE));
            tempList.add(title);
        }
        cursor.close();
        return tempList;
    }

    public void closeDB() {
        dbhelper.close();
    }
}
