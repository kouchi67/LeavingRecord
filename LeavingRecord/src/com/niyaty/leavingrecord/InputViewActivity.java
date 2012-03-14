package com.niyaty.leavingrecord;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class InputViewActivity extends Activity implements OnClickListener {

    Context context;

//    Button dateButton;
    TextView dateLabel;
    Button arrivalButton;
    Button leavingButton;
    Button restTimeButton;
    EditText remarksEditText;
    Button goButton;
    MyRecord record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_input_view);

        context = getApplicationContext();

//        dateButton = (Button) findViewById(R.id.inputViewDateButton);
//        dateButton.setOnClickListener(this);
        dateLabel = (TextView) findViewById(R.id.inputViewDateLabel);
        arrivalButton = (Button) findViewById(R.id.inputViewArrivalButton);
        arrivalButton.setOnClickListener(this);
        leavingButton = (Button) findViewById(R.id.inputViewLeavingButton);
        leavingButton.setOnClickListener(this);
        restTimeButton = (Button) findViewById(R.id.inputViewRestTimeButton);
        restTimeButton.setOnClickListener(this);
        remarksEditText = (EditText) findViewById(R.id.inputViewRemarksEditText);
        goButton = (Button) findViewById(R.id.inputViewGoButton);
        goButton.setOnClickListener(this);

        Intent intent = getIntent();
        record = (MyRecord) intent.getSerializableExtra("record");
        dateLabel.setText(record.getDate());
//        dateButton.setText(record.getDate());

        if (record.isNull() == false) {
            arrivalButton.setText(record.getArrival());
            leavingButton.setText(record.getLeaving());
            restTimeButton.setText(record.getRestTime());
            remarksEditText.setText(record.getRemarks());
        }

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.inputViewArrivalButton:
            TimePickerDialog arrivalTimePickerDialog = new TimePickerDialog(
                    this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            arrivalButton.setText(String.format("%02d:%02d", hourOfDay, minute));
                        }
                    },
                    Integer.parseInt(arrivalButton.getText().toString().substring(0, 2)),
                    Integer.parseInt(arrivalButton.getText().toString().substring(3)),
                    true);
            arrivalTimePickerDialog.show();
            break;
        case R.id.inputViewLeavingButton:
            TimePickerDialog leavingTimePickerDialog = new TimePickerDialog(
                    this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            leavingButton.setText(String.format("%02d:%02d", hourOfDay, minute));
                        }
                    },
                    Integer.parseInt(leavingButton.getText().toString().substring(0, 2)),
                    Integer.parseInt(leavingButton.getText().toString().substring(3)),
                    true);
            leavingTimePickerDialog.show();
            break;

        case R.id.inputViewRestTimeButton:
            TimePickerDialog restTimePickerDialog = new TimePickerDialog(
                    this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            restTimeButton.setText(String.format("%02d:%02d", hourOfDay, minute));
                        }
                    },
                    Integer.parseInt(restTimeButton.getText().toString().substring(0, 2)),
                    Integer.parseInt(restTimeButton.getText().toString().substring(3)),
                    true);
            restTimePickerDialog.show();
            break;

//        case R.id.inputViewDateButton:
//            DatePickerDialog datePickerDialog = new DatePickerDialog(
//                    this,
//                    new DatePickerDialog.OnDateSetListener() {
//                        @Override
//                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                            dateButton.setText(String.format("%04d/%02d/%02d", year, monthOfYear+1,dayOfMonth));
//                        }
//                    },
//                    Integer.parseInt(dateButton.getText().toString().substring(0,4)),
//                    Integer.parseInt(dateButton.getText().toString().substring(5,7))-1,
//                    Integer.parseInt(dateButton.getText().toString().substring(8))
//                    );
//            datePickerDialog.show();
//            break;

        case R.id.inputViewGoButton:
//            MyRecord record = new MyRecord();
//            record.setId(record.getId());
//            record.setDate(dateButton.getText().toString());
            record.setArrival(arrivalButton.getText().toString());
            record.setLeaving(leavingButton.getText().toString());
            record.setRestTime(restTimeButton.getText().toString());
            record.setRemarks(remarksEditText.getText().toString());
//            String year = String.format("%1$04d", dateButton.getYear());
//            String month = String.format("%1$02d", dateButton.getMonth()+1);
//            String day = String.format("%1$02d", dateButton.getDayOfMonth());
//            String date = year + "/" + month + "/" + day;
            MyDatabaseController db = new MyDatabaseController(getApplicationContext());
            db.setWritable();
            if (record.getId() == 0) {
                db.insertRecord(record);
            } else {
                db.updateRecord(record);
            }
            db.close();

            finish();
            break;

        default:
            break;
        }
    }


}
