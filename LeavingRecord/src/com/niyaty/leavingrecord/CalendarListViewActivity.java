
package com.niyaty.leavingrecord;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class CalendarListViewActivity extends Activity implements OnClickListener,
        android.content.DialogInterface.OnClickListener {

    int CONTEXT_MENU_1 = 0;
    int CONTEXT_MENU_2 = 1;
    int CONTEXT_MENU_3 = 2;

    private Context context;
    private Calendar calendar = Calendar.getInstance();
    private ArrayList<MyRecord> records;
    private CalendarListAdapter calendarAdapter;
    private MyDatabaseController db;

    private ListView listView;
    private TextView dateLabel;

    // 休日設定のdialog用
    private EditText dialogEditText;
    private MyRecord dialogRecord;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

        setContentView(R.layout.calendar_list_view);

        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_titlebar);
        dateLabel = (TextView) findViewById(R.id.titleBar_title);

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

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                registerForContextMenu(listView);
                return false;
            }
        });

        Button lastMonthButton = (Button) findViewById(R.id.calendarListViewLastMonthButton);
        lastMonthButton.setOnClickListener(this);

        Button nextMonthButton = (Button) findViewById(R.id.calendarListViewNextMonthButton);
        nextMonthButton.setOnClickListener(this);

        Button aggregateButton = (Button) findViewById(R.id.calendarListViewAggregateButton);
        aggregateButton.setOnClickListener(this);

        Button settingsButton = (Button) findViewById(R.id.calendarListViewSettingsButton);
        settingsButton.setOnClickListener(this);
    }

    private void updateView() {
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        dateLabel.setText(year + " 年 " + month + " 月 ");

        updateMyRecords();

        calendarAdapter = new CalendarListAdapter(context, R.layout.calendar_list_cell_view,
                records);
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
            String day = String.format("%1$02d", i + 1);
            MyRecord record = new MyRecord();

            // Cursorが空の場合、recordに日付のみ格納する
            // Cursorが空じゃない場合、recordに値を格納する

            if (isNullForCursor) {
                record.setArrival("");
                record.setLeaving("");
                record.setRestTime("");
                record.setDate(year + "/" + month + "/" + day);
                // Log.d(null, "day = " + day);
            } else {
                // Log.d("", "day = " + day + " , _id = " + cursor.getInt(0));
                String dateString = cursor.getString(cursor.getColumnIndex(MyRecord.DATE));
                if (dateString.substring(8).compareTo(day) == 0) {
                    // 当該日と、Cursorで指している日が同一であれば、recordに値を格納してCursorを進める
                    record.setId(cursor.getInt(cursor.getColumnIndex(MyRecord.ID)));
                    record.setDate(cursor.getString(cursor.getColumnIndex(MyRecord.DATE)));
                    record.setArrival(cursor.getString(cursor.getColumnIndex(MyRecord.ARRIVAL)));
                    record.setLeaving(cursor.getString(cursor.getColumnIndex(MyRecord.LEAVING)));
                    record.setRestTime(cursor.getString(cursor.getColumnIndex(MyRecord.REST_TIME)));
                    record.setRemarks(cursor.getString(cursor.getColumnIndex(MyRecord.REMARKS)));
                    record.setHoliday(cursor.getInt(cursor.getColumnIndex(MyRecord.HOLIDAY)));

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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        AdapterContextMenuInfo adapterInfo = (AdapterContextMenuInfo) menuInfo;
        int position = adapterInfo.position;

        // Menu.add(int groupId, int itemId, int order, CharSequence title)
        menu.add(0, CONTEXT_MENU_1, 0, "勤怠を入力する");
        menu.add(0, CONTEXT_MENU_2, 0, "休日に設定する");

        if (records.get(position).isNull() == false) {
            menu.add(0, CONTEXT_MENU_3, 0, "入力内容を削除");
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Log.d(null, item.getItemId() + " ---- " + item.getTitle());
        AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item.getMenuInfo();
        MyRecord record = records.get(menuInfo.position);

        // ContextMenuの上から順に id=0, id=1, id=2
        switch (item.getItemId()) {
            case 0: // 「勤怠を入力する」タップ
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
                break;
            case 1: // 「休日に設定する」タップ
                dialogEditText = new EditText(this);
                dialogEditText.setHint("有給休暇 etc ...");
                dialogRecord = record;
                final AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setTitle("備考")
                        .setView(dialogEditText)
                        .setPositiveButton("OK", this)
                        .setNegativeButton("キャンセル", this)
                        .create();
                alertDialog.show();
                break;
            case 2: // 「入力内容を削除」タップ
                MyDatabaseController db = new MyDatabaseController(getApplicationContext());
                db.setWritable();
                db.deleteRecord(record);
                db.close();
                updateView();
                break;
            default:
                break;
        }

        return super.onContextItemSelected(item);
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

    // ---- OnClickListener ----
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.calendarListViewLastMonthButton:
                calendar.add(Calendar.MONTH, -1);
                updateView();
                break;
            case R.id.calendarListViewNextMonthButton:
                calendar.add(Calendar.MONTH, +1);
                updateView();
                break;
            case R.id.calendarListViewAggregateButton:
                break;
            case R.id.calendarListViewSettingsButton:
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
                break;
            default:
                Log.d(null, "DialogInterface.OnClickListener");
                break;
        }

    }

    // ---- DialogInterface.OnClickListener ----
    // 休日設定時のダイアログ処理
    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                dialogRecord.setArrival("");
                dialogRecord.setLeaving("");
                dialogRecord.setRestTime("");
                dialogRecord.setHoliday(1);
                dialogRecord.setRemarks(dialogEditText.getText().toString());

                MyDatabaseController db = new MyDatabaseController(getApplicationContext());
                db.setWritable();
                if (dialogRecord.getId() == 0) {
                    db.insertRecord(dialogRecord);
                } else {
                    db.updateRecord(dialogRecord);
                }
                db.close();
                updateView();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                Log.d(null, "called onClick ---- NEGATIVE BUTTON");
                dialogEditText = null;
                dialogRecord = null;
                break;
            default:
                break;
        }
    }

}
