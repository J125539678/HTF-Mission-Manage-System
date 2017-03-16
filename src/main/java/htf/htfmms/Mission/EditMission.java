package htf.htfmms.Mission;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class EditMission extends AppCompatActivity {
    Activity myseltContext;

    Mission Mis;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mission);
        Intent intent = this.getIntent();
        Mis = (Mission)intent.getSerializableExtra("Mission");
        myseltContext = this;

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

        b_year = Mis.getBeginTime().substring(0,4);
        b_month = Mis.getBeginTime().substring(4,6);
        b_day = Mis.getBeginTime().substring(6,8);
        b_hour = Mis.getBeginTime().substring(8,10);
        b_min = Mis.getBeginTime().substring(10,12);
        e_year = Mis.getEndTime().substring(0,4);
        e_month = Mis.getEndTime().substring(4,6);
        e_day = Mis.getEndTime().substring(6,8);
        e_hour = Mis.getEndTime().substring(8,10);
        e_min = Mis.getEndTime().substring(10,12);
        r_year = Mis.getRemindTime().substring(0,4);
        r_month = Mis.getRemindTime().substring(4,6);
        r_day = Mis.getRemindTime().substring(6,8);
        r_hour = Mis.getRemindTime().substring(8,10);
        r_min = Mis.getRemindTime().substring(10,12);

        beDay.setText(b_year + "-" + b_month + "-" + b_day);
        beTime.setText(b_hour + ":" + b_min);
        enDay.setText(e_year + "-" + e_month + "-" + e_day);
        enTime.setText(e_hour + ":" + e_min);
        reDay.setText(r_year + "-" + r_month + "-" + r_day);
        reTime.setText(r_hour + ":" + r_min);

        editTextMissionName.setText(Mis.getName());
        RadioButton rd;
        if (Mis.getIsPrivate().equals("0"))
            rd = (RadioButton)RepeatType.getChildAt(0);
        else if (Mis.getIsTeamWork().equals("0"))
            rd = (RadioButton)RepeatType.getChildAt(1);
        else
            rd = (RadioButton)RepeatType.getChildAt(2);
        rd.performClick();
        if (Mis.getType().equals("每日"))
            rd = (RadioButton)RepeatType.getChildAt(0);
        else if (Mis.getType().equals("每周"))
            rd = (RadioButton)RepeatType.getChildAt(1);
        else
            rd = (RadioButton)RepeatType.getChildAt(2);
        rd.performClick();
        editTextGainPoint.setText(Mis.getGainPoint());
        editTextLostPoint.setText(Mis.getLostPoint());
        editTextMissionContents.setText(Mis.getContents());
        if (Mis.getRemindWay().equals("1"))
            cbShake.performClick();
        else if (Mis.getRemindWay().equals("2"))
            cbVoice.performClick();
        else if (Mis.getRemindWay().equals("3")){
            cbShake.performClick();
            cbVoice.performClick();
        }

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

                Mis.setName(editTextMissionName.getText().toString());
                Mis.setBeginTime(b_year + b_month + b_day + b_hour + b_min);
                Mis.setEndTime(e_year + e_month + e_day + e_hour + e_min);
                Mis.setEndDay(e_year + e_month + e_day);
                Mis.setGainPoint(editTextGainPoint.getText().toString());
                Mis.setLostPoint(editTextLostPoint.getText().toString());
                Mis.setContents(editTextMissionContents.getText().toString());
                Mis.setRemindTime(r_year + r_month + r_day + r_hour + r_min);

                for (int i=0; i < ShareType.getChildCount(); ++i){
                    RadioButton rd = (RadioButton)ShareType.getChildAt(i);
                    if (rd.isChecked()){
                        if (rd.getText().toString().equals("不共享")){
                            Mis.setIsPrivate("0");
                            Mis.setGroupName("");
                            Mis.setIsTeamWork("-1");
                        }
                        else {
                            Mis.setIsPrivate("1");
                            if (user.getEmail() == null || user.getEmail().equals("nnn@what.com")) {
                                Toast.makeText(EditMission.this, "你还未加入任何群组！", Toast.LENGTH_SHORT).show();
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

                if (Mis.getName().equals("")) {
                    Toast.makeText(EditMission.this, "请输入任务名称！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Mis.getBeginTime().equals("")) {
                    Toast.makeText(EditMission.this, "请输入任务开始时间！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Mis.getEndTime().equals("")) {
                    Toast.makeText(EditMission.this, "请输入任务结束时间！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Mis.getGainPoint().equals("")) {
                    Toast.makeText(EditMission.this, "请输入奖励积分！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Mis.getLostPoint().equals("")) {
                    Toast.makeText(EditMission.this, "请输入惩罚积分！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Mis.getRemindWay().equals("") && Mis.getRemindWay().equals("0")){
                    Toast.makeText(EditMission.this, "请选择提醒方式！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Mis.getRemindWay().equals("") && Mis.getRemindWay().equals("0")){
                    Toast.makeText(EditMission.this, "请输入提醒时间！", Toast.LENGTH_SHORT).show();
                    return;
                }

                //存储
                //DbManger.getInstance(myseltContext).updateMission(Mis);
                //DbManger.getInstance(myseltContext).deleteMission(Mis.MissionId);
                //DbManger.getInstance(myseltContext).addMission(Mis);
                MissionManger.deleteMission(myseltContext, Mis.getMissionId());
                if (Mis.getIsTeamWork().equals("0"))
                    MissionManger.addMisByGroup(myseltContext, Mis, user.getEmail().substring(1));
                else
                    MissionManger.addMission(myseltContext,Mis);

                //返回任务详细信息界面
                Intent intent = new Intent();
                intent.setClass(EditMission.this, MissionActivity.class);
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
                intent.setClass(EditMission.this, MainActivity.class);
                myseltContext.startActivity(intent);
            }
        });
    }
}
