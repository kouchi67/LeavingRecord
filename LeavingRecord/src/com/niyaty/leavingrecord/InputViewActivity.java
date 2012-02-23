package com.niyaty.leavingrecord;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class InputViewActivity extends Activity implements OnClickListener {

    EditText editText;
    Button goButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_view);

        editText = (EditText) findViewById(R.id.inputViewRemarks);
        goButton = (Button) findViewById(R.id.inputViewGoButton);
        goButton.setOnClickListener(this);

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
        case R.id.inputViewGoButton:
            MyRecord rec = new MyRecord();
            rec.setRemarks(editText.getText().toString());
            MyDatabaseController db = new MyDatabaseController(getApplicationContext());
            db.setWritable();
            db.insertRecord(rec);
            break;

        default:
            break;
        }
    }


}
