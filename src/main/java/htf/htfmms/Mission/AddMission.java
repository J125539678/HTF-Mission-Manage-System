package htf.htfmms.Mission;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import htf.htfmms.MainActivity;
import htf.htfmms.Database.Mission;
import htf.htfmms.Database.MissionManger;
import htf.htfmms.R;

public class AddMission extends AppCompatActivity {
    Activity myseltContext;

    EditText editTextMissionName;
    EditText editTextGainPoint;
    EditText editTextLostPoint;
    EditText editTextMissionContents;

    RadioGroup ShareType;
    RadioGroup RepeatType;

    int b1 = 0;
    int b2 = 0;
    int e1 = 0;
    int e2 = 0;
    int r1 = 0;
    int r2 = 0;

    CheckBox cbShake;
    CheckBox cbVoice;

    Button bntSubmit;
    Button bntCancel;

    Button beDay;
    Button beTime;
    Button enDay;
    Button enTime;
    Button reDay;
    Button reTime;

    BmobUser user;

    String b_year;
    String b_month;
    String b_day;
    String b_hour;
    String b_min;
    String e_year;
    String e_month;
    String e_day;
    String e_hour;
    String e_min;
    String r_year;
    String r_month;
    String r_day;
    String r_hour;
    String r_min;

    DatePickerFragment datePicker1 = new DatePickerFragment();
    DatePickerFragment datePicker2 = new DatePickerFragment();
    DatePickerFragment datePicker3 = new DatePickerFragment();
    TimePickerFragment timePicker1 = new TimePickerFragment();
    TimePickerFragment timePicker2 = new TimePickerFragment();
    TimePickerFragment timePicker3 = new TimePickerFragment();

    Mission Mis = new Mission();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mission);
        myseltContext = this;
        user = BmobUser.getCurrentUser(myseltContext);

        editTextMissionName = (EditText)findViewById(R.id.MissionName);
        ShareType = (RadioGroup)findViewById(R.id.ShareType);
        RepeatType = (RadioGroup)findViewById(R.id.RepeatType);
        editTextGainPoint = (EditText)findViewById(R.id.GainPoints);
        editTextLostPoint = (EditText)findViewById(R.id.LostPoints);
        editTextMissionContents = (EditText)findViewById(R.id.Contents);
        cbShake = (CheckBox)findViewById(R.id.ShakeWay);
        cbVoice = (CheckBox)findViewById(R.id.VoiceWay);

        bntSubmit = (Button)findViewById(R.id.btnSubmit);
        bntCancel = (Button)findViewById(R.id.btnDelete);

        beDay = (Button)findViewById(R.id.BgDay);
        beTime = (Button)findViewById(R.id.BgTime);
        enDay = (Button)findViewById(R.id.EnDay);
        enTime = (Button)findViewById(R.id.EnTime);
        reDay = (Button)findViewById(R.id.ReDay);
        reTime = (Button)findViewById(R.id.ReTime);

        Time time = new Time("GMT+8");
        time.setToNow();
        String year = Integer.toString(time.year);
        String month = Integer.toString(time.month+1);
        String day = Integer.toString(time.monthDay);
        int h = time.hour + 8;
        if (h > 23)
            h = h - 24;
        String hour = Integer.toString(h);
        String minute = Integer.toString(time.minute);
        String second = Integer.toString(time.second);
        if (month.length() == 1) month = "0" + month;
        if (day.length() == 1) day = "0" + day;
        if (hour.length() == 1) hour = "0" + hour;
        if (minute.length() == 1) minute = "0" + minute;
        if (second.length() == 1) second = "0" + second;
        Mis.setMissionId(year + month + day + hour + minute + second);

        b_year = e_year = r_year = year;
        b_month = e_month = r_month = month;
        b_day = e_day = r_day = day;
        b_hour = e_hour = r_hour = hour;
        b_min = e_min = r_min = minute;

        beDay.setText(b_year + "-" + b_month + "-" + b_day);
        beTime.setText(b_hour + ":" + b_min);
        enDay.setText(e_year + "-" + e_month + "-" + e_day);
        enTime.setText(e_hour + ":" + e_min);
        reDay.setText(r_year + "-" + r_month + "-" + r_day);
        reTime.setText(r_hour + ":" + r_min);

        beDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DatePickerFragment datePicker1 = new DatePickerFragment();
                datePicker1.show(getFragmentManager(), "datePicker");
                b1 = 1;
                if (b1 == 1) {
                    b_year = Integer.toString(datePicker1.year_);
                    b_month = Integer.toString(datePicker1.month_);
                    b_day = Integer.toString(datePicker1.day_);
                    if (b_month.length() == 1) b_month = "0" + b_month;
                    if (b_day.length() == 1) b_day = "0" + b_day;
                }
                if (b2 == 1) {
                    b_hour = Integer.toString(timePicker1.hour_);
                    b_min = Integer.toString(timePicker1.minute_);
                    if (b_hour.length() == 1) b_hour = "0" + b_hour;
                    if (b_min.length() == 1) b_min = "0" + b_min;
                }
                if (e1 == 1) {
                    e_year = Integer.toString(datePicker2.year_);
                    e_month = Integer.toString(datePicker2.month_);
                    e_day = Integer.toString(datePicker2.day_);
                    if (e_month.length() == 1) e_month = "0" + e_month;
                    if (e_day.length() == 1) e_day = "0" + e_day;
                }
                if (e2 == 1) {
                    e_hour = Integer.toString(timePicker2.hour_);
                    e_min = Integer.toString(timePicker2.minute_);
                    if (e_hour.length() == 1) e_hour = "0" + e_hour;
                    if (e_min.length() == 1) e_min = "0" + e_min;
                }
                if (r1 == 1) {
                    r_year = Integer.toString(datePicker3.year_);
                    r_month = Integer.toString(datePicker3.month_);
                    r_day = Integer.toString(datePicker3.day_);
                    if (r_month.length() == 1) r_month = "0" + r_month;
                    if (r_day.length() == 1) r_day = "0" + r_day;
                }
                if (r2 == 1) {
                    r_hour = Integer.toString(timePicker3.hour_);
                    r_min = Integer.toString(timePicker3.minute_);
                    if (r_hour.length() == 1) r_hour = "0" + r_hour;
                    if (r_min.length() == 1) r_min = "0" + r_min;
                }

                beDay.setText(b_year + "-" + b_month + "-" + b_day);
                beTime.setText(b_hour + ":" + b_min);
                enDay.setText(e_year + "-" + e_month + "-" + e_day);
                enTime.setText(e_hour + ":" + e_min);
                reDay.setText(r_year + "-" + r_month + "-" + r_day);
                reTime.setText(r_hour + ":" + r_min);
            }
        });

        beTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TimePickerFragment  timePicker = new TimePickerFragment();
                timePicker1.show(getFragmentManager(), "timePicker");
                b2 = 1;
                if (b1 == 1) {
                    b_year = Integer.toString(datePicker1.year_);
                    b_month = Integer.toString(datePicker1.month_);
                    b_day = Integer.toString(datePicker1.day_);
                    if (b_month.length() == 1) b_month = "0" + b_month;
                    if (b_day.length() == 1) b_day = "0" + b_day;
                }
                if (b2 == 1) {
                    b_hour = Integer.toString(timePicker1.hour_);
                    b_min = Integer.toString(timePicker1.minute_);
                    if (b_hour.length() == 1) b_hour = "0" + b_hour;
                    if (b_min.length() == 1) b_min = "0" + b_min;
                }
                if (e1 == 1) {
                    e_year = Integer.toString(datePicker2.year_);
                    e_month = Integer.toString(datePicker2.month_);
                    e_day = Integer.toString(datePicker2.day_);
                    if (e_month.length() == 1) e_month = "0" + e_month;
                    if (e_day.length() == 1) e_day = "0" + e_day;
                }
                if (e2 == 1) {
                    e_hour = Integer.toString(timePicker2.hour_);
                    e_min = Integer.toString(timePicker2.minute_);
                    if (e_hour.length() == 1) e_hour = "0" + e_hour;
                    if (e_min.length() == 1) e_min = "0" + e_min;
                }
                if (r1 == 1) {
                    r_year = Integer.toString(datePicker3.year_);
                    r_month = Integer.toString(datePicker3.month_);
                    r_day = Integer.toString(datePicker3.day_);
                    if (r_month.length() == 1) r_month = "0" + r_month;
                    if (r_day.length() == 1) r_day = "0" + r_day;
                }
                if (r2 == 1) {
                    r_hour = Integer.toString(timePicker3.hour_);
                    r_min = Integer.toString(timePicker3.minute_);
                    if (r_hour.length() == 1) r_hour = "0" + r_hour;
                    if (r_min.length() == 1) r_min = "0" + r_min;
                }

                beDay.setText(b_year + "-" + b_month + "-" + b_day);
                beTime.setText(b_hour + ":" + b_min);
                enDay.setText(e_year + "-" + e_month + "-" + e_day);
                enTime.setText(e_hour + ":" + e_min);
                reDay.setText(r_year + "-" + r_month + "-" + r_day);
                reTime.setText(r_hour + ":" + r_min);
            }
        });

        enDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DatePickerFragment datePicker = new DatePickerFragment();
                datePicker2.show(getFragmentManager(), "datePicker");
                e1 = 1;
                if (b1 == 1) {
                    b_year = Integer.toString(datePicker1.year_);
                    b_month = Integer.toString(datePicker1.month_);
                    b_day = Integer.toString(datePicker1.day_);
                    if (b_month.length() == 1) b_month = "0" + b_month;
                    if (b_day.length() == 1) b_day = "0" + b_day;
                }
                if (b2 == 1) {
                    b_hour = Integer.toString(timePicker1.hour_);
                    b_min = Integer.toString(timePicker1.minute_);
                    if (b_hour.length() == 1) b_hour = "0" + b_hour;
                    if (b_min.length() == 1) b_min = "0" + b_min;
                }
                if (e1 == 1) {
                    e_year = Integer.toString(datePicker2.year_);
                    e_month = Integer.toString(datePicker2.month_);
                    e_day = Integer.toString(datePicker2.day_);
                    if (e_month.length() == 1) e_month = "0" + e_month;
                    if (e_day.length() == 1) e_day = "0" + e_day;
                }
                if (e2 == 1) {
                    e_hour = Integer.toString(timePicker2.hour_);
                    e_min = Integer.toString(timePicker2.minute_);
                    if (e_hour.length() == 1) e_hour = "0" + e_hour;
                    if (e_min.length() == 1) e_min = "0" + e_min;
                }
                if (r1 == 1) {
                    r_year = Integer.toString(datePicker3.year_);
                    r_month = Integer.toString(datePicker3.month_);
                    r_day = Integer.toString(datePicker3.day_);
                    if (r_month.length() == 1) r_month = "0" + r_month;
                    if (r_day.length() == 1) r_day = "0" + r_day;
                }
                if (r2 == 1) {
                    r_hour = Integer.toString(timePicker3.hour_);
                    r_min = Integer.toString(timePicker3.minute_);
                    if (r_hour.length() == 1) r_hour = "0" + r_hour;
                    if (r_min.length() == 1) r_min = "0" + r_min;
                }

                beDay.setText(b_year + "-" + b_month + "-" + b_day);
                beTime.setText(b_hour + ":" + b_min);
                enDay.setText(e_year + "-" + e_month + "-" + e_day);
                enTime.setText(e_hour + ":" + e_min);
                reDay.setText(r_year + "-" + r_month + "-" + r_day);
                reTime.setText(r_hour + ":" + r_min);
            }
        });

        enTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TimePickerFragment  timePicker = new TimePickerFragment();
                timePicker2.show(getFragmentManager(), "timePicker");
                e2 = 1;
                if (b1 == 1) {
                    b_year = Integer.toString(datePicker1.year_);
                    b_month = Integer.toString(datePicker1.month_);
                    b_day = Integer.toString(datePicker1.day_);
                    if (b_month.length() == 1) b_month = "0" + b_month;
                    if (b_day.length() == 1) b_day = "0" + b_day;
                }
                if (b2 == 1) {
                    b_hour = Integer.toString(timePicker1.hour_);
                    b_min = Integer.toString(timePicker1.minute_);
                    if (b_hour.length() == 1) b_hour = "0" + b_hour;
                    if (b_min.length() == 1) b_min = "0" + b_min;
                }
                if (e1 == 1) {
                    e_year = Integer.toString(datePicker2.year_);
                    e_month = Integer.toString(datePicker2.month_);
                    e_day = Integer.toString(datePicker2.day_);
                    if (e_month.length() == 1) e_month = "0" + e_month;
                    if (e_day.length() == 1) e_day = "0" + e_day;
                }
                if (e2 == 1) {
                    e_hour = Integer.toString(timePicker2.hour_);
                    e_min = Integer.toString(timePicker2.minute_);
                    if (e_hour.length() == 1) e_hour = "0" + e_hour;
                    if (e_min.length() == 1) e_min = "0" + e_min;
                }
                if (r1 == 1) {
                    r_year = Integer.toString(datePicker3.year_);
                    r_month = Integer.toString(datePicker3.month_);
                    r_day = Integer.toString(datePicker3.day_);
                    if (r_month.length() == 1) r_month = "0" + r_month;
                    if (r_day.length() == 1) r_day = "0" + r_day;
                }
                if (r2 == 1) {
                    r_hour = Integer.toString(timePicker3.hour_);
                    r_min = Integer.toString(timePicker3.minute_);
                    if (r_hour.length() == 1) r_hour = "0" + r_hour;
                    if (r_min.length() == 1) r_min = "0" + r_min;
                }

                beDay.setText(b_year + "-" + b_month + "-" + b_day);
                beTime.setText(b_hour + ":" + b_min);
                enDay.setText(e_year + "-" + e_month + "-" + e_day);
                enTime.setText(e_hour + ":" + e_min);
                reDay.setText(r_year + "-" + r_month + "-" + r_day);
                reTime.setText(r_hour + ":" + r_min);
            }
        });

        reDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DatePickerFragment datePicker = new DatePickerFragment();
                datePicker3.show(getFragmentManager(), "datePicker");
                r1 = 1;
                if (b1 == 1) {
                    b_year = Integer.toString(datePicker1.year_);
                    b_month = Integer.toString(datePicker1.month_);
                    b_day = Integer.toString(datePicker1.day_);
                    if (b_month.length() == 1) b_month = "0" + b_month;
                    if (b_day.length() == 1) b_day = "0" + b_day;
                }
                if (b2 == 1) {
                    b_hour = Integer.toString(timePicker1.hour_);
                    b_min = Integer.toString(timePicker1.minute_);
                    if (b_hour.length() == 1) b_hour = "0" + b_hour;
                    if (b_min.length() == 1) b_min = "0" + b_min;
                }
                if (e1 == 1) {
                    e_year = Integer.toString(datePicker2.year_);
                    e_month = Integer.toString(datePicker2.month_);
                    e_day = Integer.toString(datePicker2.day_);
                    if (e_month.length() == 1) e_month = "0" + e_month;
                    if (e_day.length() == 1) e_day = "0" + e_day;
                }
                if (e2 == 1) {
                    e_hour = Integer.toString(timePicker2.hour_);
                    e_min = Integer.toString(timePicker2.minute_);
                    if (e_hour.length() == 1) e_hour = "0" + e_hour;
                    if (e_min.length() == 1) e_min = "0" + e_min;
                }
                if (r1 == 1) {
                    r_year = Integer.toString(datePicker3.year_);
                    r_month = Integer.toString(datePicker3.month_);
                    r_day = Integer.toString(datePicker3.day_);
                    if (r_month.length() == 1) r_month = "0" + r_month;
                    if (r_day.length() == 1) r_day = "0" + r_day;
                }
                if (r2 == 1) {
                    r_hour = Integer.toString(timePicker3.hour_);
                    r_min = Integer.toString(timePicker3.minute_);
                    if (r_hour.length() == 1) r_hour = "0" + r_hour;
                    if (r_min.length() == 1) r_min = "0" + r_min;
                }

                beDay.setText(b_year + "-" + b_month + "-" + b_day);
                beTime.setText(b_hour + ":" + b_min);
                enDay.setText(e_year + "-" + e_month + "-" + e_day);
                enTime.setText(e_hour + ":" + e_min);
                reDay.setText(r_year + "-" + r_month + "-" + r_day);
                reTime.setText(r_hour + ":" + r_min);
            }
        });

        reTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TimePickerFragment  timePicker = new TimePickerFragment();
                timePicker3.show(getFragmentManager(), "timePicker");
                r2 = 1;
                if (b1 == 1) {
                    b_year = Integer.toString(datePicker1.year_);
                    b_month = Integer.toString(datePicker1.month_);
                    b_day = Integer.toString(datePicker1.day_);
                    if (b_month.length() == 1) b_month = "0" + b_month;
                    if (b_day.length() == 1) b_day = "0" + b_day;
                }
                if (b2 == 1) {
                    b_hour = Integer.toString(timePicker1.hour_);
                    b_min = Integer.toString(timePicker1.minute_);
                    if (b_hour.length() == 1) b_hour = "0" + b_hour;
                    if (b_min.length() == 1) b_min = "0" + b_min;
                }
                if (e1 == 1) {
                    e_year = Integer.toString(datePicker2.year_);
                    e_month = Integer.toString(datePicker2.month_);
                    e_day = Integer.toString(datePicker2.day_);
                    if (e_month.length() == 1) e_month = "0" + e_month;
                    if (e_day.length() == 1) e_day = "0" + e_day;
                }
                if (e2 == 1) {
                    e_hour = Integer.toString(timePicker2.hour_);
                    e_min = Integer.toString(timePicker2.minute_);
                    if (e_hour.length() == 1) e_hour = "0" + e_hour;
                    if (e_min.length() == 1) e_min = "0" + e_min;
                }
                if (r1 == 1) {
                    r_year = Integer.toString(datePicker3.year_);
                    r_month = Integer.toString(datePicker3.month_);
                    r_day = Integer.toString(datePicker3.day_);
                    if (r_month.length() == 1) r_month = "0" + r_month;
                    if (r_day.length() == 1) r_day = "0" + r_day;
                }
                if (r2 == 1) {
                    r_hour = Integer.toString(timePicker3.hour_);
                    r_min = Integer.toString(timePicker3.minute_);
                    if (r_hour.length() == 1) r_hour = "0" + r_hour;
                    if (r_min.length() == 1) r_min = "0" + r_min;
                }

                beDay.setText(b_year + "-" + b_month + "-" + b_day);
                beTime.setText(b_hour + ":" + b_min);
                enDay.setText(e_year + "-" + e_month + "-" + e_day);
                enTime.setText(e_hour + ":" + e_min);
                reDay.setText(r_year + "-" + r_month + "-" + r_day);
                reTime.setText(r_hour + ":" + r_min);
            }
        });

        bntSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b1 == 1) {
                    b_year = Integer.toString(datePicker1.year_);
                    b_month = Integer.toString(datePicker1.month_);
                    b_day = Integer.toString(datePicker1.day_);
                    if (b_month.length() == 1) b_month = "0" + b_month;
                    if (b_day.length() == 1) b_day = "0" + b_day;
                }
                if (b2 == 1) {
                    b_hour = Integer.toString(timePicker1.hour_);
                    b_min = Integer.toString(timePicker1.minute_);
                    if (b_hour.length() == 1) b_hour = "0" + b_hour;
                    if (b_min.length() == 1) b_min = "0" + b_min;
                }
                if (e1 == 1) {
                    e_year = Integer.toString(datePicker2.year_);
                    e_month = Integer.toString(datePicker2.month_);
                    e_day = Integer.toString(datePicker2.day_);
                    if (e_month.length() == 1) e_month = "0" + e_month;
                    if (e_day.length() == 1) e_day = "0" + e_day;
                }
                if (e2 == 1) {
                    e_hour = Integer.toString(timePicker2.hour_);
                    e_min = Integer.toString(timePicker2.minute_);
                    if (e_hour.length() == 1) e_hour = "0" + e_hour;
                    if (e_min.length() == 1) e_min = "0" + e_min;
                }
                if (r1 == 1) {
                    r_year = Integer.toString(datePicker3.year_);
                    r_month = Integer.toString(datePicker3.month_);
                    r_day = Integer.toString(datePicker3.day_);
                    if (r_month.length() == 1) r_month = "0" + r_month;
                    if (r_day.length() == 1) r_day = "0" + r_day;
                }
                if (r2 == 1) {
                    r_hour = Integer.toString(timePicker3.hour_);
                    r_min = Integer.toString(timePicker3.minute_);
                    if (r_hour.length() == 1) r_hour = "0" + r_hour;
                    if (r_min.length() == 1) r_min = "0" + r_min;
                }

                beDay.setText(b_year + "-" + b_month + "-" + b_day);
                beTime.setText(b_hour + ":" + b_min);
                enDay.setText(e_year + "-" + e_month + "-" + e_day);
                enTime.setText(e_hour + ":" + e_min);
                reDay.setText(r_year + "-" + r_month + "-" + r_day);
                reTime.setText(r_hour + ":" + r_min);

                Mis.setName(editTextMissionName.getText().toString());
                Mis.setBeginTime(b_year + b_month + b_day + b_hour + b_min);
                Mis.setEndTime(e_year + e_month + e_day + e_hour + e_min);
                Mis.setEndDay(e_year + e_month + e_day);
                Mis.setGainPoint(editTextGainPoint.getText().toString());
                Mis.setLostPoint(editTextLostPoint.getText().toString());
                Mis.setContents(editTextMissionContents.getText().toString());
                Mis.setRemindTime(r_year + r_month + r_day + r_hour + r_min);
                Mis.setRealFinishTime("000000000000");
                Mis.setMissionRecord("");
                Mis.setAuthor(user);
                Mis.setUserId(user.getUsername());
                Mis.setMissionSource(user.getUsername());
                //Toast.makeText(AddMission.this, Mis.getRemindTime(), Toast.LENGTH_SHORT).show();
                Mis.setProcess("0");

                for (int i=0; i < ShareType.getChildCount(); ++i){
                    RadioButton rd = (RadioButton)ShareType.getChildAt(i);
                    if (rd.isChecked()){
                        if (rd.getText().toString().equals("不共享")){
                            Mis.setIsPrivate("0");
                            Mis.setIsTeamWork("-1");
                            Mis.setGroupName("");
                            //Toast.makeText(AddMission.this, Mis.getIsTeamWork(), Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Mis.setIsPrivate("1");
                            Mis.setGroupName(user.getEmail().substring(1));
                            if (user.getEmail() == null || user.getEmail().equals("nnn@what.com")) {
                                Toast.makeText(AddMission.this, "你还未加入任何群组！", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Mis.setGroupName(user.getEmail().substring(1));
                            if (rd.getText().toString().equals("个人任务"))
                                Mis.setIsTeamWork("0");
                            else
                                Mis.setIsTeamWork("1");
                        }
                    }
                }

                for (int i = 0; i < RepeatType.getChildCount(); ++i){
                    RadioButton rd = (RadioButton)RepeatType.getChildAt(i);
                    if (rd.isChecked()){
                        Mis.setType(rd.getText().toString());
                        break;
                    }
                }

                if (cbShake.isChecked() && !cbVoice.isChecked()) {
                    Mis.setRemindWay("1");
                }
                else if (!cbShake.isChecked() && cbVoice.isChecked()) {
                    Mis.setRemindWay("2");
                }
                else if (cbShake.isChecked() && cbVoice.isChecked()) {
                    Mis.setRemindWay("3");
                }
                else {
                    Mis.setRemindWay("0");
                }



                // 检查合法性
                if (Mis.getName().equals("")) {
                    Toast.makeText(AddMission.this, "请输入任务名称！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Mis.getBeginTime().equals("")) {
                    Toast.makeText(AddMission.this, "请输入任务开始时间！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Mis.getEndTime().equals("")) {
                    Toast.makeText(AddMission.this, "请输入任务结束时间！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Mis.getGainPoint().equals("")) {
                    Toast.makeText(AddMission.this, "请输入奖励积分！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Mis.getLostPoint().equals("")) {
                    Toast.makeText(AddMission.this, "请输入惩罚积分！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Mis.getRemindWay().equals("") && Mis.getRemindWay().equals("0")){
                    Toast.makeText(AddMission.this, "请选择提醒方式！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Mis.getRemindWay().equals("") && Mis.getRemindWay().equals("0")){
                    Toast.makeText(AddMission.this, "请输入提醒时间！", Toast.LENGTH_SHORT).show();
                    return;
                }

                //存储
                if (Mis.getIsTeamWork().equals("0"))
                    MissionManger.addMisByGroup(myseltContext, Mis, user.getEmail().substring(1));
                else
                    MissionManger.addMission(myseltContext,Mis);

                //返回任务详细信息界面
                Intent intent = new Intent();
                intent.setClass(AddMission.this, MissionActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Mission", Mis);
                intent.putExtras(bundle);
                myseltContext.startActivity(intent);
            }
        });

        bntCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //返回主界面
                Intent intent = new Intent();
                intent.setClass(AddMission.this, MainActivity.class);
                myseltContext.startActivity(intent);
            }
        });
    }

}
