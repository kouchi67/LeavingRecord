package com.niyaty.leavingrecord;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import com.viewpagerindicator.TitlePageIndicator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class CalendarListViewActivity extends Activity implements OnClickListener {

    private Context context;
    private Calendar calendar = Calendar.getInstance();
    private ArrayList<MyRecord> records;
    private CalendarListAdapter calendarAdapter;
    private MyDatabaseController db;

    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_list_view);

        context = getApplicationContext();

        db = new MyDatabaseController(context);
        listView = (ListView) findViewById(R.id.calendarListView);

        Button lastMonthButton = (Button) findViewById(R.id.button1);
        Button nextMonthButton = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        lastMonthButton.setOnClickListener(this);
        nextMonthButton.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button4.setText("Next Activty");

        updateView();

    }



    private void updateView() {
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        int dayOfMonthMaximum = calendar.getActualMaximum(Calendar.DATE);

        updateMyRecords();
        calendarAdapter = new CalendarListAdapter(context, R.layout.record_list_cell_view, records);

        listView.setAdapter(calendarAdapter);

    }

    private void updateMyRecords() {
        ArrayList<MyRecord> items = new ArrayList<MyRecord>();
        db.setReadable();
        Cursor cursor = db.getRecords();
        if (cursor.moveToFirst()) {
            do {
                MyRecord record = new MyRecord();
                record.setArrival(cursor.getString(cursor.getColumnIndex(MyRecord.ARRIVAL)));
                record.setLeaving(cursor.getString(cursor.getColumnIndex(MyRecord.LEAVING)));
                record.setRemarks(cursor.getString(cursor.getColumnIndex(MyRecord.REMARKS)));
                items.add(record);
            } while (cursor.moveToNext());
        }

        records = items;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.button1:
            calendar.add(Calendar.YEAR, +1);
            updateView();
            break;
        case R.id.button2:
            calendar.add(Calendar.YEAR, -1);
            updateView();
            break;
        case R.id.button3:
            break;
        case R.id.button4:
            Intent intent = new Intent(getApplicationContext(), InputViewActivity.class);
            startActivity(intent);
            break;
        default:
            break;
        }

    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    @Override
    protected void onPause() {
        super.onPause();
    }



    @Override
    protected void onResume() {
        super.onResume();
        updateView();
//        calendarAdapter.notifyDataSetChanged();
   }

}