package com.niyaty.leavingrecord;

import java.util.ArrayList;

import com.viewpagerindicator.TitlePageIndicator;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

public class ListViewActivity extends Activity {

    private LayoutInflater layoutInflater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ArrayList<View> arrView = new ArrayList<View>();

        Context context = getApplicationContext();
        ArrayList<String> items = new ArrayList<String>();
        for (int i = 1; i < 31; i++) {
            items.add(String.format("%1$02d day", i));
        }
        CalendarAdapter calendarAdapter = new CalendarAdapter(context, R.layout.record_list_cell_view, items);

        layoutInflater = this.getLayoutInflater();

        View view = layoutInflater.inflate(R.layout.list_view, null);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(calendarAdapter);
        View listView2 = layoutInflater.inflate(R.layout.layout01, null);
        View listView3 = layoutInflater.inflate(R.layout.layout02, null);
        arrView.add(listView);
        arrView.add(listView2);
        arrView.add(listView3);
        String[] titles = new String[] {"ListView", "ListView2", "ListView3"};

        MyViewPagerAdapter pagerAdapter = new MyViewPagerAdapter(this, arrView);
        pagerAdapter.setTitleArr(titles);

        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        TitlePageIndicator indicator = (TitlePageIndicator)findViewById(R.id.indicator);
        pager.setAdapter(pagerAdapter);
        indicator.setViewPager(pager);

    }

}