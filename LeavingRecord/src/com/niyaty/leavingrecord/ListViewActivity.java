package com.niyaty.leavingrecord;

import java.util.ArrayList;

import com.viewpagerindicator.TitlePageIndicator;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.niyaty.leavingrecord.R;

public class ListViewActivity extends Activity {

    private LayoutInflater layoutInflater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ArrayList<View> arrView = new ArrayList<View>();

        Context context = getApplicationContext();
        ArrayAdapter<String> testAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        for (int i = 0; i < 31; i++) {
            testAdapter.add("test " + i);
        }

        layoutInflater = this.getLayoutInflater();

        View view = layoutInflater.inflate(R.layout.list_view, null);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(testAdapter);
        View listView2 = layoutInflater.inflate(R.layout.layout01, null);
        View listView3 = layoutInflater.inflate(R.layout.layout02, null);
        arrView.add(listView);
        arrView.add(listView2);
        arrView.add(listView3);
        String[] titles = new String[] {"ListView", "ListView2", "ListView3"};

        MyViewPagerAdapter adapter = new MyViewPagerAdapter(this, arrView);
        adapter.setTitleArr(titles);

        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        TitlePageIndicator indicator = (TitlePageIndicator)findViewById(R.id.indicator);
        pager.setAdapter(adapter);
        indicator.setViewPager(pager);

    }

}