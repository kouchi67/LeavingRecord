
package com.niyaty.leavingrecord;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class InputViewActivity extends Activity implements OnClickListener {

    Context context;

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
        // dateButton.setText(record.getDate());

        if (record.isNull() || record.getHoliday() == 1) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            String arrivalKey = getString(R.string.settingsArrivalPreference);
            String leavingKey = getString(R.string.settingsLeavingPreference);
            String resttimeKey = getString(R.string.settingsRestTimePreference);

            String arrival = preferences.getString(arrivalKey, "09:00");
            String leaving = preferences.getString(leavingKey, "18:00");
            String resttime = preferences.getString(resttimeKey, "01:00");

            arrivalButton.setText(arrival);
            leavingButton.setText(leaving);
            restTimeButton.setText(resttime);
            remarksEditText.setText(record.getRemarks());
        } else {
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
                                restTimeButton.setText(String
                                        .format("%02d:%02d", hourOfDay, minute));
                            }
                        },
                        Integer.parseInt(restTimeButton.getText().toString().substring(0, 2)),
                        Integer.parseInt(restTimeButton.getText().toString().substring(3)),
                        true);
                restTimePickerDialog.show();
                break;

            case R.id.inputViewGoButton:
                record.setArrival(arrivalButton.getText().toString());
                record.setLeaving(leavingButton.getText().toString());
                record.setRestTime(restTimeButton.getText().toString());
                record.setRemarks(remarksEditText.getText().toString());
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
