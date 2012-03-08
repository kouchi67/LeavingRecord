package com.niyaty.leavingrecord;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MyDatabaseController {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "mydb.db";

    private SQLiteDatabase db;
    private MyDatabaseHelper helper;

    public MyDatabaseController(Context context) {
        this.helper = new MyDatabaseHelper(context, DB_NAME, DB_VERSION);
    }

    public void setWritable() {
        try {
            db = helper.getWritableDatabase();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void setReadable() {
        try {
            db = helper.getReadableDatabase();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void insertRecord(MyRecord record) {
        ContentValues cv = new ContentValues();
        cv.put(MyRecord.DATE, record.getDate());
        cv.put(MyRecord.ARRIVAL, record.getArrival());
        cv.put(MyRecord.LEAVING, record.getLeaving());
        cv.put(MyRecord.REST_TIME, record.getRestTime());
        cv.put(MyRecord.REMARKS, record.getRemarks());
        cv.put(MyRecord.HOLIDAY, record.getHoliday());

        String sql = "insert into " + MyRecord.TABLE_NAME +
        " ( " + MyRecord.REMARKS + " ) " +
        " values ( '" + record.getRemarks() + "' ) ;";
        Log.d(null, sql);
        db.execSQL(sql);
    }

    public Cursor getRecords() {
        String sql = "select * from " + MyRecord.TABLE_NAME + " order by date asc;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

}
