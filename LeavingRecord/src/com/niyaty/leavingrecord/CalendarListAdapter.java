
package com.niyaty.leavingrecord;

import java.util.ArrayList;
import java.util.Calendar;

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

    public void setRecords(ArrayList<MyRecord> records) {
        this.records = records;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.calendar_list_cell_view, null);

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
        calendar.set(year, month - 1, day);
        if (isNationalHoliday()) {
            holder.cell.setBackgroundColor(Color.rgb(220, 96, 96));
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            holder.cell.setBackgroundColor(Color.rgb(96, 96, 220));
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            holder.cell.setBackgroundColor(Color.rgb(220, 96, 96));
        } else {
            if (position % 2 == 0) {
                holder.cell.setBackgroundColor(Color.rgb(99, 99, 99));
            } else {
                holder.cell.setBackgroundColor(Color.rgb(66, 66, 66));
            }
        }

        return view;
    }

    private boolean isNationalHoliday() {
        String date = String.format("%04d/%02d/%02d",
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH));

        String[] holidayList2012 = {
                "2012/01/01",
                "2012/01/02",
                "2012/01/09",
                "2012/02/11",
                "2012/03/20",
                "2012/04/29",
                "2012/04/30",
                "2012/05/03",
                "2012/05/04",
                "2012/05/05",
                "2012/07/16",
                "2012/09/17",
                "2012/09/22",
                "2012/10/08",
                "2012/11/03",
                "2012/11/23",
                "2012/12/23",
                "2012/12/24",
        };

        String[] holidayList2013 = {
                "2013/01/01",
                "2013/01/14",
                "2013/02/11",
                "2013/03/20",
                "2013/04/29",
                "2013/04/30",
                "2013/05/03",
                "2013/05/04",
                "2013/05/05",
                "2013/05/06",
                "2013/07/15",
                "2013/09/16",
                "2013/09/23",
                "2013/10/14",
                "2013/11/03",
                "2013/11/04",
                "2013/11/23",
                "2013/12/23",
        };

        for (String holiday : holidayList2012) {
            if (date.compareTo(holiday) == 0) {
                return true;
            }
        }

        for (String holiday : holidayList2013) {
            if (date.compareTo(holiday) == 0) {
                return true;
            }
        }

        return false;
    }

}
