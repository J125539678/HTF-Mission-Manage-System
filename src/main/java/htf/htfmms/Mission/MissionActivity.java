package htf.htfmms.Mission;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import htf.htfmms.Database.CountManger;
import htf.htfmms.Database.Mission;
import htf.htfmms.Database.MissionCount;
import htf.htfmms.MainActivity;
import htf.htfmms.Database.MissionManger;
import htf.htfmms.R;

public class MissionActivity extends AppCompatActivity {
    Activity myseltContext;

    Mission Mis;
    MissionCount CountToday;

    TextView MissionName;
    TextView Author;
    TextView ShareType;
    TextView RepeatType;
    TextView BeginTime;
    TextView EndTime;
    TextView GainPoints;
    TextView LostPoints;
    TextView Contents;
    TextView Progress;
    TextView Record;
    TextView RemindWay;
    TextView RemindTime;

    Button bntEdit;
    Button bntDelete;
    Button bntUpdate;
    Button bntReturn;

    BmobUser user;

    AlertDialog alert = null;
    AlertDialog.Builder builder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission);
        myseltContext = this;
        CountToday = new MissionCount();
        Intent intent = this.getIntent();
        Mis = (Mission)intent.getSerializableExtra("Mission");
        user = BmobUser.getCurrentUser(MissionActivity.this);

        MissionName = (TextView)findViewById(R.id.MissionName);
        Author = (TextView)findViewById(R.id.Author);
        ShareType = (TextView)findViewById(R.id.ShareType);
        RepeatType = (TextView)findViewById(R.id.RepeatType);
        BeginTime = (TextView)findViewById(R.id.BeginTime);
        EndTime = (TextView)findViewById(R.id.EndTime);
        GainPoints = (TextView)findViewById(R.id.GainPoints);
        LostPoints = (TextView)findViewById(R.id.LostPoints);
        Contents = (TextView)findViewById(R.id.Contents);
        Progress = (TextView)findViewById(R.id.Progress);
        Record = (TextView)findViewById(R.id.Record);
        RemindWay = (TextView)findViewById(R.id.RemindWay);
        RemindTime = (TextView)findViewById(R.id.RemindTime);

        bntEdit = (Button)findViewById(R.id.btnEdit);
        bntDelete = (Button)findViewById(R.id.btnDelete);
        bntUpdate = (Button)findViewById(R.id.btnUpdate);
        bntReturn = (Button)findViewById(R.id.btnReturn);

        MissionName.setText(Mis.getName());
        Author.setText(Mis.getMissionSource());
        if (Mis.getIsPrivate().equals("0"))
            ShareType.setText("不共享");
        else if (Mis.getIsTeamWork().equals("1"))
            ShareType.setText("团队任务");
        else
            ShareType.setText("个人任务");

        if (Mis.getType().equals("每日"))
            RepeatType.setText(Mis.getType());
        else if (Mis.getType().equals("每周"))
            RepeatType.setText(Mis.getType());
        else
            RepeatType.setText(Mis.getType());
        String b = Mis.getBeginTime().substring(0,4) + "-" + Mis.getBeginTime().substring(4,6) + "-" +
                Mis.getBeginTime().substring(6,8) + " " + Mis.getBeginTime().substring(8,10) + ":" + Mis.getBeginTime().substring(10);
        String e = Mis.getEndTime().substring(0,4) + "-" + Mis.getEndTime().substring(4,6) + "-" +
                Mis.getEndTime().substring(6,8) + " " + Mis.getEndTime().substring(8,10) + ":" + Mis.getEndTime().substring(10);
        BeginTime.setText(b);
        EndTime.setText(e);
        GainPoints.setText(Mis.getGainPoint());
        LostPoints.setText(Mis.getLostPoint());
        Contents.setText(Mis.getContents());
        Record.setText(Mis.getMissionRecord());
        if (Mis.getRemindWay().equals("1"))
            RemindWay.setText("震动");
        else if (Mis.getRemindWay().equals("2"))
            RemindWay.setText("声音");
        else if (Mis.getRemindWay().equals("3"))
            RemindWay.setText("震动 + 声音");
        else if (Mis.getRemindWay().equals("0"))
            RemindWay.setText("不提醒");
        //Toast.makeText(MissionActivity.this, Mis.getRemindTime(), Toast.LENGTH_SHORT).show();
        if (Mis.getRemindTime() != null) {
            String r = Mis.getRemindTime().substring(0, 4) + "-" + Mis.getRemindTime().substring(4, 6) + "-" +
                    Mis.getRemindTime().substring(6, 8) + " " + Mis.getRemindTime().substring(8, 10) + ":" + Mis.getRemindTime().substring(10);
            RemindTime.setText(r);
        }
        else{
            RemindTime.setText(Mis.getRemindTime());
        }
        Progress.setText(Mis.getProcess());

        bntEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //进入编辑模式
                if (user.getUsername().equals(Mis.getMissionSource())) { // 是创建者
                    Intent intent = new Intent();
                    intent.setClass(MissionActivity.this, EditMission.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Mission", Mis);
                    intent.putExtras(bundle);
                    myseltContext.startActivity(intent);
                }
                else { // 不是任务创建者
                    Toast.makeText(MissionActivity.this, "你不是该任务创建者！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bntDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user.getUsername().equals(Mis.getMissionSource())) {
                    MissionManger.deleteMission(myseltContext, Mis.getMissionId());
                    Intent intent = new Intent();
                    intent.setClass(MissionActivity.this, MainActivity.class);
                    myseltContext.startActivity(intent);
                }
                else{
                    Toast.makeText(MissionActivity.this, "你不是该任务创建者！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bntUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText new_pro = new EditText(myseltContext);
                builder = new AlertDialog.Builder(myseltContext);
                alert = builder
                        .setTitle("更新进度")
                        .setView(new_pro)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
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
                                if (month.length() == 1) month = "0" + month;
                                if (day.length() == 1) day = "0" + day;
                                if (hour.length() == 1) hour = "0" + hour;
                                if (minute.length() == 1) minute = "0" + minute;
                                String nowTime = year + "-" + month + "-" + day + " " + hour + ":" + minute;
                                final String nowDay = year + month + day;
                                Mis.setProcess(new_pro.getText().toString()); // 更新进度
                                Mis.setMissionRecord(Mis.getMissionRecord()+nowTime+" "+user.getUsername()+" 完成至 "+Mis.getProcess()+"%\n"); // 更新任务完成记录
                                if (Mis.getProcess().equals("100")) { // 任务完成
                                    Mis.setRealFinishTime(nowDay);
                                }
                                // 更新所有同MissionId的任务
                                if (Mis.getIsTeamWork() != null && Mis.getIsTeamWork().equals("1"))
                                    MissionManger.deleteMission(myseltContext, Mis.getMissionId());
                                else
                                    MissionManger.deleteMissionOne(myseltContext, Mis.getMissionId(), user.getUsername());
                                //Toast.makeText(MissionActivity.this, Mis.getIsTeamWork(), Toast.LENGTH_SHORT).show();
                                //if (Mis.getIsTeamWork() != null && Mis.getIsTeamWork().equals("0"))
                                    //MissionManger.addMisByGroup(myseltContext, Mis, user.getEmail().substring(1));
                                // else
                                MissionManger.addMission(myseltContext, Mis);
                                Progress.setText(Mis.getProcess());
                                Record.setText(Mis.getMissionRecord());
                                /*if (Mis.getProcess().equals("100")) { // 任务完成
                                        BmobQuery<Mission> query = new BmobQuery<Mission>();
                                        query.addWhereEqualTo("Author", user);    // 查询当前用户的所有任务
                                        query.order("-createdAt");// 按照时间降序
                                        query.findObjects(MissionActivity.this, new FindListener<Mission>() {
                                            @Override
                                            public void onSuccess(List<Mission> object) { // 计算当日任务完成和积分情况
                                                // TODO Auto-generated method stub
                                                Toast.makeText(MissionActivity.this, "查询成功", Toast.LENGTH_SHORT).show();
                                                //MissionCount CountToday = new MissionCount();
                                                int total = 0;
                                                int completed = 0;
                                                int failed = 0;
                                                int count = 0;
                                                Toast.makeText(MissionActivity.this, Integer.toString(object.size()), Toast.LENGTH_SHORT).show();
                                                for (Mission Mis_: object){
                                                    if (Mis_.getProcess().equals("100") && (Mis_.getEndDay().equals(nowDay) || Mis_.getRealFinishTime().equals(nowDay))){
                                                        total = total + 1;
                                                        Toast.makeText(MissionActivity.this, Integer.toString(total) + ": " + Mis_.getName(), Toast.LENGTH_SHORT).show();
                                                        completed = completed + 1;
                                                        count = count + Integer.parseInt(Mis_.getGainPoint());
                                                    }
                                                    else if (Mis_.getEndDay().equals(nowDay) || Mis_.getRealFinishTime().equals(nowDay)){
                                                        total = total + 1;
                                                        Toast.makeText(MissionActivity.this, Integer.toString(total) + ": " + Mis_.getName(), Toast.LENGTH_SHORT).show();
                                                        failed = failed + 1;
                                                        count = count - Integer.parseInt(Mis_.getLostPoint());
                                                    }
                                                }
                                                CountToday.setDate(nowDay);
                                                CountToday.setAuthor(user);
                                                CountToday.setGainPointToday(Integer.toString(count));
                                                CountToday.setAccomplishToday(Integer.toString(completed));
                                                CountToday.setFailToday(Integer.toString(failed));
                                                CountManger.upLoadCount(myseltContext, CountToday);
                                                Toast.makeText(MissionActivity.this, Integer.toString(count), Toast.LENGTH_SHORT).show();
                                            }
                                            @Override
                                            public void onError(int code, String msg) {
                                                // TODO Auto-generated method stub
                                                Toast.makeText(MissionActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                }*/
                            }
                        })
                        .create();
                alert.show();
            }
        });

        bntReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //返回主界面
                Intent intent = new Intent();
                intent.setClass(MissionActivity.this, MainActivity.class);
                myseltContext.startActivity(intent);
            }
        });
    }
}

