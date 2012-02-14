package com.niyaty.leavingrecord;

import java.util.List;

import com.viewpagerindicator.TitleProvider;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

public class MyViewPagerAdapter extends PagerAdapter implements TitleProvider {

    private String[] titles = new String[] {};
    private final Context context;
    private List<View> mView;

    public MyViewPagerAdapter( Context context, List<View> view) {
        this.context = context;
        this.mView = view;
    }

    public View getItem(int position) {
        return mView.get(position);
    }

    public void setTitleArr(String[] title) {
        this.titles = title;
    }
    @Override
    public String getTitle( int position ) {
        return titles[ position ];
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem( View pager, int position ) {
        ViewGroup parent = (ViewGroup)mView.get(position).getParent();
        if (parent != null) {
            parent.removeView(mView.get(position));
        }
        View view = mView.get(position);
        ((ViewPager)pager).addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem( View pager, int position, Object view ) {
        ( (ViewPager) pager ).removeView( (View) view );
    }
    @Override
    public boolean isViewFromObject( View view, Object object ) {
        return view.equals( object );
    }

    @Override
    public void finishUpdate( View view ) {}

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate( View view ) {}

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

}
