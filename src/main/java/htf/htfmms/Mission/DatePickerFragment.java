package htf.htfmms.Mission;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements
        DatePickerDialog.OnDateSetListener {

    public int year_;
    public int month_;
    public int day_;

    Button beDay;
    Button beTime;
    Button enDay;
    Button enTime;
    Button reDay;
    Button reTime;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Log.d("OnDateSet", "select year:"+year+";month:"+month+";day:"+day);
        year_ = year;
        month_ = month + 1;
        day_ = day;
    }
}
