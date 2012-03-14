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

    public void close() {
        helper.close();
    }

    public void insertRecord(MyRecord record) {
        String sql =
            "insert into " + MyRecord.TABLE_NAME +
            " ( " +
            MyRecord.DATE + ", " +
            MyRecord.ARRIVAL + ", " +
            MyRecord.LEAVING + ", " +
            MyRecord.REST_TIME + ", " +
            MyRecord.REMARKS + ", " +
            MyRecord.HOLIDAY +
            " ) " +
            " values ( " +
            " '" + record.getDate() + "' , " +
            " '" + record.getArrival() + "' , " +
            " '" + record.getLeaving() + "' , " +
            " '" + record.getRestTime() + "' , " +
            " '" + record.getRemarks() + "' , " +
            " 0 " +
            ") ;";
        Log.d(null, sql);
        db.execSQL(sql);
    }

    public void updateRecord(MyRecord record) {
        String sql =
            "update " + MyRecord.TABLE_NAME + " set " +
            MyRecord.DATE + " = '" + record.getDate() + "', " +
            MyRecord.ARRIVAL + " = '" + record.getArrival() + "', " +
            MyRecord.LEAVING + " = '" + record.getLeaving() + "', " +
            MyRecord.REST_TIME + " = '" + record.getRestTime() + "', " +
            MyRecord.REMARKS + " = '" + record.getRemarks() + "', " +
            MyRecord.HOLIDAY + " = " + record.getHoliday() + " " +
            "where " + MyRecord.ID + " = " + record.getId() + ";";
        Log.d(null, sql);
        db.execSQL(sql);
    }

    public Cursor getRecords(String yearMonth) {
        String sql =
            "select * from " + MyRecord.TABLE_NAME +
            " where date like " + "'" + yearMonth + "%'" +
            " order by date asc;";
        Log.d(null, sql);
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

}
