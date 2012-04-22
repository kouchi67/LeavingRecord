
package com.niyaty.leavingrecord;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

public class TimePickerPreference extends DialogPreference {

    private String preferenceValue;
    private TimePicker timePicker;

    public TimePickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimePickerPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected View onCreateDialogView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.timepicker_dialog, null);
        timePicker = (TimePicker) view.findViewById(R.id.timePickerDialog);
        timePicker.setIs24HourView(true);

        int hour = Integer.parseInt(preferenceValue.substring(0, 2));
        int minute = Integer.parseInt(preferenceValue.substring(3));
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minute);

        return view;
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            if (timePicker != null) {
                String newValue = String.format("%02d:%02d",
                        timePicker.getCurrentHour(),
                        timePicker.getCurrentMinute());
                if (callChangeListener(newValue)) {
                    preferenceValue = newValue;
                    persistString(newValue);
                }
            }
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        preferenceValue = (String) a.getText(index);
        return super.onGetDefaultValue(a, index);
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        if (restorePersistedValue) {
            preferenceValue = getPersistedString(preferenceValue);
        } else {
            preferenceValue = (String) defaultValue;
            persistString(preferenceValue);
        }
    }

}
