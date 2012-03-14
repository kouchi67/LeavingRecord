package com.niyaty.leavingrecord;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class CalendarListViewActivity extends Activity implements OnClickListener {

    private Context context;
    private Calendar calendar = Calendar.getInstance();
    private ArrayList<MyRecord> records;
    private CalendarListAdapter calendarAdapter;
    private MyDatabaseController db;

    private ListView listView;
    private TextView dateLabel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_list_view);

        context = getApplicationContext();

        db = new MyDatabaseController(context);
        listView = (ListView) findViewById(R.id.calendarListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), InputViewActivity.class);
                intent.putExtra("record", records.get(position));
                startActivity(intent);
            }
        });

        dateLabel = (TextView) findViewById(R.id.calendarListViewDateLabel);

        Button lastMonthButton = (Button) findViewById(R.id.button1);
        lastMonthButton.setOnClickListener(this);

        Button nextMonthButton = (Button) findViewById(R.id.button2);
        nextMonthButton.setOnClickListener(this);

        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(this);

        Button button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(this);
    }



    private void updateView() {
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        dateLabel.setText(year + " 年 " + month + " 月 ");

        updateMyRecords();
        calendarAdapter = new CalendarListAdapter(context, R.layout.record_list_cell_view, records);

        listView.setAdapter(calendarAdapter);

    }

    private void updateMyRecords() {
        String month = String.format("%1$02d", calendar.get(Calendar.MONTH) + 1);
        String year = calendar.get(Calendar.YEAR) + "";

        ArrayList<MyRecord> items = new ArrayList<MyRecord>();
        db.setReadable();
        Cursor cursor = db.getRecords(year + "/" + month);
        int dayOfMonthMaximum = calendar.getActualMaximum(Calendar.DATE);

        boolean isNullForCursor = false;
        if (cursor.moveToFirst() == false) {
            isNullForCursor = true;
        }

        // CalendarList作成、1日〜月末までループ
        for (int i = 0; i < dayOfMonthMaximum; i++) {
            String day = String.format("%1$02d", i+1);
            MyRecord record = new MyRecord();

            // Cursorが空の場合、recordに日付のみ格納する
            // Cursorが空じゃない場合、recordに値を格納する

            if (isNullForCursor) {
                record.setArrival("");
                record.setLeaving("");
                record.setDate(year + "/" + month + "/" + day);
//                Log.d(null, "day = " + day);
            } else {
//                Log.d("", "day = " + day + " , _id = " + cursor.getInt(0));
                String dateString = cursor.getString(cursor.getColumnIndex(MyRecord.DATE));
                if (dateString.substring(8).compareTo(day) == 0) {
                    // 当該日と、Cursorで指している日が同一であれば、recordに値を格納してCursorを進める
                    record.setId(cursor.getInt(cursor.getColumnIndex(MyRecord.ID)));
                    record.setDate(cursor.getString(cursor.getColumnIndex(MyRecord.DATE)));
                    record.setArrival(cursor.getString(cursor.getColumnIndex(MyRecord.ARRIVAL)));
                    record.setLeaving(cursor.getString(cursor.getColumnIndex(MyRecord.LEAVING)));
                    record.setRestTime(cursor.getString(cursor.getColumnIndex(MyRecord.REST_TIME)));
                    record.setRemarks(cursor.getString(cursor.getColumnIndex(MyRecord.REMARKS)));

                    if (cursor.moveToNext()) {
                        isNullForCursor = false;
                    } else {
                        isNullForCursor = true;
                    }

                } else {
                    record.setArrival("");
                    record.setLeaving("");
                    record.setDate(year + "/" + month + "/" + day);
                }
            }
            items.add(record);
        }

        records = items;
        db.close();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.button1:
            calendar.add(Calendar.MONTH, -1);
            updateView();
            break;
        case R.id.button2:
            calendar.add(Calendar.MONTH, +1);
            updateView();
            break;
        case R.id.button3:
            break;
        case R.id.button4:
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
   }

}