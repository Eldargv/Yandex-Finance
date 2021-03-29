package com.Test.test_app.db;

public class Constants {

    public static final  String TABLE_NAME = "my_table";
    public static final  String _ID = "_id";
    public static final  String TITLE = "title";
    public static final  String DESC = "desc";
    public static final  String DB_NAME = "my_db.db";
    public static final  int DB_VERSION = 1;

    public static final  String TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY, " + TITLE + " TEXT," +
            DESC + " TEXT)";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

}
