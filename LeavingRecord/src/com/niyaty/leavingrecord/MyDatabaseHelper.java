package com.niyaty.leavingrecord;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public MyDatabaseHelper(Context context, String dbName, int version) {
        super(context, dbName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + MyRecord.TABLE_NAME + "(" +
                MyRecord.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MyRecord.ARRIVAL + " TEXT , " +
                MyRecord.LEAVING + " TEXT , " +
                MyRecord.REST_TIME + " TEXT , " +
                MyRecord.DATE + " TEXT , " +
                MyRecord.REMARKS + " TEXT , " +
                MyRecord.HOLIDAY + " INTEGER " +
                ");"
                );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
