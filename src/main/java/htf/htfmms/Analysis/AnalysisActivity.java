package htf.htfmms.Analysis;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.format.Time;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import htf.htfmms.Database.Mission;
import htf.htfmms.MainActivity;
import htf.htfmms.Mission.MissionActivity;
import htf.htfmms.R;


public class AnalysisActivity extends AppCompatActivity {
    Activity myseltContext;


    TableLayout t;
    TableRow row;
    StringBuffer noww = new StringBuffer();
    int week;
    List<Mission> listMission = new ArrayList<>();
    List<Mission> dayMission = new ArrayList<>();
    List<Mission> weekMission = new ArrayList<>();
    Mission mislist[] = new Mission[20];//最多20个

    private Button button1,button2,button3,button4;

    TextView textView1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        myseltContext = this;

        BmobUser user = BmobUser.getCurrentUser(AnalysisActivity.this);
        BmobQuery<Mission> query = new BmobQuery<Mission>();
        query.addWhereEqualTo("Author", user);    // 查询当前用户的所有任务
        query.order("-createdAt");// 按照时间降序
        query.findObjects(AnalysisActivity.this, new FindListener<Mission>() {

            @Override
            public void onSuccess(List<Mission> Missions) {
                // TODO Auto-generated method stub
                listMission = Missions;

                int scores[] = initScore(listMission, noww.toString());


                showText(scores);


                showMission(listMission);

            }

            @Override
            public void onError(int code, String arg0) {
                // TODO Auto-generated method stub
                //showErrorView(0);
            }
        });

        button1 = (Button) findViewById(R.id.button1);//通过id获取按钮1
        button2 = (Button) findViewById(R.id.button2);//通过id获取按钮2
        button3 = (Button) findViewById(R.id.button3);//通过id获取按钮3
        button4 = (Button) findViewById(R.id.button4);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//返回箭头
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMain = new Intent(AnalysisActivity.this,MainActivity.class);
                startActivity(toMain);
            }
        });

        View.OnClickListener mylistener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //这里可以判断点击的是哪个对象，通过v对象的getId()方法获取当前点击对象的id
                switch (v.getId()) {
                    case R.id.button1:
                        //
                        dayMission.clear();
                        for (int i=0;i<listMission.size();++i)
                        {
                            if (listMission.get(i).getEndTime().substring(0,8).equals(noww.substring(0,8)))
                                dayMission.add(listMission.get(i));
                        }

                        int scores[] = initScore(dayMission, noww.toString());

                        showText(scores);
                        showMission(dayMission);
                        break;
                    case R.id.button2:
                        //
                        weekMission.clear();
                        for (int i=0;i<listMission.size();++i)
                        {
                            if (listMission.get(i).getEndTime().substring(0,6).equals(noww.substring(0,6)))
                            {
                                int a = Integer.parseInt(listMission.get(i).getEndTime().substring(6,8));
                                int b = Integer.parseInt(noww.substring(6,8));
                                if (a>=b-week && a<=b+(6-week))
                                    weekMission.add(listMission.get(i));
                            }
                        }

                        int scores2[] = initScore(weekMission, noww.toString());

                        showText(scores2);
                        showMission(weekMission);
                        break;
                    case R.id.button3:
                        //
                        int scores3[] = initScore(listMission, noww.toString());

                        showText(scores3);
                        showMission(listMission);
                        break;
                    case R.id.button4:

                        Intent intent = new Intent();
                        intent.setClass(AnalysisActivity.this, Chart.class);
                        myseltContext.startActivity(intent);
                        break;

                    default:
                        break;
                }
            }
        };
        button1.setOnClickListener(mylistener);
        button2.setOnClickListener(mylistener);
        button3.setOnClickListener(mylistener);
        button4.setOnClickListener(mylistener);



        Time ti=new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
        ti.setToNow(); // 取得系统时间。
        String year = Integer.toString(ti.year);
        String month = Integer.toString(ti.month+1);
        String date = Integer.toString(ti.monthDay);
        String hour = Integer.toString(ti.hour); // 0-23
        String minute = Integer.toString(ti.minute);
        String second = Integer.toString(ti.second);
        week = ti.weekDay;
        if (month.length()==1) month="0"+month;
        if (date.length()==1) date="0"+date;
        if (hour.length()==1) hour="0"+hour;
        if (minute.length()==1) minute="0"+minute;
        if (second.length()==1) second="0"+second;


        noww.append(year).append(month).append(date).append(hour).append(minute).append(second);

        //改成这个==========================================
        // listMission = DbManger.getInstance(AnalysisActivity.this).queryMissions();




    }

    private void showText(int scores[]) {
        int score=scores[0];
        int taskFinished=scores[1];
        int taskTotal=scores[2];
        textView1=(TextView)findViewById(R.id.textView1);
        double judge;
        if (taskTotal != 0)
            judge = (double)taskFinished/(double)taskTotal;
        else
            judge = 0;
        String judgeStr = getJudge(judge);
        SpannableString msp = null;
        msp =new SpannableString("积分："+score+"    完成任务："+taskFinished+"/"+taskTotal+ "\n" +judgeStr);
        String mspStr = "积分："+score+"    完成任务："+taskFinished+"/"+taskTotal+ "\n" +judgeStr;
        msp.setSpan(new ForegroundColorSpan(Color.BLUE), mspStr.indexOf("：")+1 , mspStr.indexOf("完"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        msp.setSpan(new ForegroundColorSpan(Color.GREEN), mspStr.indexOf("务")+2 , mspStr.indexOf("/"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        msp.setSpan(new ForegroundColorSpan(Color.RED), mspStr.indexOf("/")+1 , mspStr.indexOf("\n"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView1.setText(msp);
        textView1.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private String getJudge(double judge) {
        if (0<=judge && judge <=0.33) return "最近懒了啊~";
        else if (0.33<judge && judge <=0.66) return "还得加油啊~";
        else if (0.66<judge && judge <=1) return "干的不错哦~";
        else return "---";

    }

    private int[] initScore(List<Mission> listMission, String noww) {
        int total=0;
        int finished=0;
        for (int i=0;i<listMission.size();++i) {
            Mission m=listMission.get(i);
            String t=m.getEndTime();
            if (m.getProcess().equals("100"))
            {
                total += Integer.parseInt(m.getGainPoint());
                finished++;
            }
            else if (t.compareTo(noww)<0)//未完成,且过时
                total-=Integer.parseInt(m.getLostPoint());
        }
        int[] a={total,finished,listMission.size()};
        return a;
    }


    public void showMission(List<Mission> thisMission) {
        List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();

        for (Iterator it = thisMission.iterator(); it.hasNext();) {
            Map<String, Object> showitem = new HashMap<String, Object>();
            Mission tmp = (Mission) it.next();

            showitem.put("name", tmp.getName());
            showitem.put("progress",tmp.getProcess());
            showitem.put("mission",tmp);
            listitem.add(showitem);
        }
        SimpleAdapter myAdapter = new SimpleAdapter(getApplicationContext(), listitem,
                R.layout.list_item, new String[]{ "name","progress"},
                new int[]{ R.id.title, R.id.progress});
        final ListView missionList = (ListView)findViewById(R.id.mission_list);
        final LayoutInflater inflater = LayoutInflater.from(AnalysisActivity.this);
        missionList.setAdapter(myAdapter);
        //监听
        missionList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                HashMap<String,Object> map = (HashMap<String,Object>)missionList.getItemAtPosition(arg2);
                Mission choosedMission = (Mission)map.get("mission");
                Intent toMission = new Intent(AnalysisActivity.this,MissionActivity.class);
                Bundle missionBundle = new Bundle();
                missionBundle.putSerializable("Mission",choosedMission);
                toMission.putExtras(missionBundle);
                startActivity(toMission);
            }

        });

        /*t = (TableLayout) findViewById(R.id.t);

        int j = t.getChildCount();
        if(j>=1) {
            for (int i = j; i >= 0; i--) {
                t.removeView(t.getChildAt(i));//必须从后面减去子元素
            }
        }
        Comparator comp = new SortComparator();
        Collections.sort(thisMission,comp);

        List<View> v = new ArrayList<View>();

        String a[] = new String[thisMission.size()];//任务名称和完成度
        String b[] = new String[thisMission.size()];
        for (int i=0;i<thisMission.size();++i)
        {
            a[i]=thisMission.get(i).getName();
            mislist[i]=thisMission.get(i);
            b[i]=thisMission.get(i).getProcess();
        }

        for (int x = 0; x < a.length; x++) {
            row = new TableRow(this);

            TextView t1 = new TextView(this);
            t1.setText(a[x]+"     完成度："+b[x]+"           ");
            t1.setTextColor(Color.BLACK);
            t1.setGravity(Gravity.LEFT);
            // t1.setLayoutParams();
            row.addView(t1);
            Button b1 = new Button(this);
            b1.setText("查看");
            b1.setId(x+10);
            b1.setGravity(Gravity.CENTER);
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //调用！！！！！！！！！！！===========================================================
                    //在这里进入具体按钮界面
                    //showMission(weekMission);
                    //String name = namelist[v.getId()-10];
                    Mission mis = mislist[v.getId()-10];
                    Intent intent = new Intent();
                    intent.setClass(AnalysisActivity.this, MissionActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Mission",mis);
                    intent.putExtras(bundle);
                    myseltContext.startActivity(intent);
                }
            });
            row.addView(b1);

            t.addView(row);
        }*/
    }
}