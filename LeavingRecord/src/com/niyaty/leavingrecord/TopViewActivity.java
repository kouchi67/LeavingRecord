package com.niyaty.leavingrecord;

import java.util.ArrayList;

import com.viewpagerindicator.TitlePageIndicator;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

public class TopViewActivity extends Activity {

    private LayoutInflater layoutInflater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ArrayList<View> arrView = new ArrayList<View>();

        layoutInflater = this.getLayoutInflater();
        View viewOne = layoutInflater.inflate(R.layout.layout01, null);
        View viewTwo = layoutInflater.inflate(R.layout.layout02, null);
        arrView.add(viewOne);
        arrView.add(viewTwo);
        String[] titles = new String[] {
                "Page1","Page2"
        };

        MyViewPagerAdapter adapter = new MyViewPagerAdapter(this, arrView);
        adapter.setTitleArr(titles);

        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        TitlePageIndicator indicator = (TitlePageIndicator)findViewById(R.id.indicator);
        pager.setAdapter(adapter);
        indicator.setViewPager(pager);

    }

}