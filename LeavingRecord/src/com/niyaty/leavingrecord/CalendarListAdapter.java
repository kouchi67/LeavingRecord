package com.niyaty.leavingrecord;

import java.util.ArrayList;
import java.util.Calendar;

import android.R.integer;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CalendarListAdapter extends ArrayAdapter<MyRecord> {

    static class ViewHolder {
        View cell;
        TextView day;
        TextView time;
        TextView remarks;
    }

    private ArrayList<MyRecord> records;
    private LayoutInflater inflater;
    Calendar calendar = Calendar.getInstance();

    public CalendarListAdapter(Context context, int textViewResourceId, ArrayList<MyRecord> records) {
        super(context, textViewResourceId, records);
        this.records = records;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.record_list_cell_view, null);

            View cell = (View) view.findViewById(R.id.linearLayoutCell);
            TextView day = (TextView) view.findViewById(R.id.recordCellDayLabel);
            TextView time = (TextView) view.findViewById(R.id.recordCellTimeLabel);
            TextView remarks = (TextView) view.findViewById(R.id.recordCellRemarksLabel);

            holder = new ViewHolder();
            holder.cell = cell;
            holder.day = day;
            holder.time = time;
            holder.remarks = remarks;

            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        MyRecord record = records.get(position);
        String dateString = records.get(position).getDate();
        String recordDay = dateString.substring(8);
        holder.day.setText(recordDay + " 日");
        holder.time.setText(record.getArrival() + " - " + record.getLeaving());
        holder.remarks.setText(record.getRemarks());

        int year = Integer.parseInt(dateString.substring(0, 4));
        int month = Integer.parseInt(dateString.substring(5, 7));
        int day = Integer.parseInt(recordDay);
        calendar.set(year, month-1, day);
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            holder.cell.setBackgroundColor(Color.rgb(96, 96, 220));
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            holder.cell.setBackgroundColor(Color.rgb(220,  96,  96));
        } else {
            if (position % 2 == 0) {
                holder.cell.setBackgroundColor(Color.rgb(99, 99, 99));
            } else {
                holder.cell.setBackgroundColor(Color.rgb(66, 66, 66));
            }
        }

        return view;
    }
}
