package com.niyaty.leavingrecord;

import java.util.ArrayList;
import java.util.Calendar;

import com.viewpagerindicator.TitlePageIndicator;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class CalendarListViewActivity extends Activity implements OnClickListener {

    private LayoutInflater layoutInflater;
    private Calendar calendar = Calendar.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        updateView();

        Button lastMonthButton = (Button) findViewById(R.id.button1);
        Button nextMonthButton = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        lastMonthButton.setOnClickListener(this);
        nextMonthButton.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button4.setText("create database");
    }



    private void updateView() {
        Context context = getApplicationContext();

        ArrayList<String> titles = new ArrayList<String>();
        ArrayList<View> arrView = new ArrayList<View>();
        layoutInflater = this.getLayoutInflater();

        // ----
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        ArrayList<String> items = new ArrayList<String>();
        int dayOfMonthMaximum = calendar.getActualMaximum(Calendar.DATE);
        for (int i = 0; i < dayOfMonthMaximum; i++) {
            int day = i+1;
            items.add(day + " 日");
        }
        // ----

        CalendarListAdapter calendarAdapter = new CalendarListAdapter(context, R.layout.record_list_cell_view, items);
        titles.add("View1");
        titles.add(year + " 年 " + month + " 月");
        titles.add("View2");

        View view = layoutInflater.inflate(R.layout.list_view, null);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(calendarAdapter);

        view = layoutInflater.inflate(R.layout.aggregate_view, null);
        arrView.add(view);
        arrView.add(listView);
        view = layoutInflater.inflate(R.layout.input_view, null);
        arrView.add(view);

        CalendarListViewPagerAdapter pagerAdapter = new CalendarListViewPagerAdapter(this, arrView);
        pagerAdapter.setTitleArr(titles);

        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        TitlePageIndicator indicator = (TitlePageIndicator)findViewById(R.id.indicator);
        pager.setAdapter(pagerAdapter);
        indicator.setViewPager(pager);
        indicator.setCurrentItem(1);
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
            MyDatabaseHelper helper = new MyDatabaseHelper(getApplicationContext(), "mydb.db", 1);
            try {
                helper.getWritableDatabase();
            } catch (Exception e) {
                // TODO: handle exception
            }
            break;
        default:
            break;
        }

    }

}