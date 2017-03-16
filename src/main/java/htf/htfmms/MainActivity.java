package htf.htfmms;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import htf.htfmms.Account.AccountMenuActivity;
import htf.htfmms.Analysis.AnalysisActivity;
import htf.htfmms.Database.CountManger;
import htf.htfmms.Database.Mission;
import htf.htfmms.Database.MissionCount;
import htf.htfmms.Database.UserManger;
import htf.htfmms.Mission.AddMission;
import htf.htfmms.Mission.MissionActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    BmobQuery<Mission> query;
    BmobUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = BmobUser.getCurrentUser(MainActivity.this);
        Bmob.initialize(this,"9db978e06528708003c4fb5a5e5ede4b");
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //主界面显示任务
        //ArrayList<Mission> allMission = DbManger.getInstance(MainActivity.this).queryMissions();
       // GroupManger.createGroup(this,"ab");

        query = new BmobQuery<Mission>();
        //user = BmobUser.getCurrentUser(MainActivity.this);
        final RadioButton rbtn = (RadioButton)findViewById(R.id.rb_personal);
        if (rbtn.isChecked()){
            query.addWhereEqualTo("Author", user);
        }else {
            String Groupname;
            if (user==null) Groupname=null;
            else {
                Groupname = UserManger.getGroup(MainActivity.this).substring(1);
                query.addWhereEqualTo("GroupName", Groupname); //查询群组任务
            }
        }
        query.order("-createdAt");// 按照时间降序
        query.findObjects(MainActivity.this, new FindListener<Mission>() {

            @Override
            public void onSuccess(List<Mission> allMission) {
                // TODO Auto-generated method stub
                // 更新每日积分
                Time time = new Time("GMT+8");
                time.setToNow();
                String year = Integer.toString(time.year);
                String month = Integer.toString(time.month+1);
                String day_ = Integer.toString(time.monthDay);
                int h = time.hour + 8;
                if (h > 23)
                    h = h - 24;
                String hour = Integer.toString(h);
                String minute = Integer.toString(time.minute);
                if (month.length() == 1) month = "0" + month;
                if (day_.length() == 1) day_ = "0" + day_;
                if (hour.length() == 1) hour = "0" + hour;
                if (minute.length() == 1) minute = "0" + minute;
                String nowTime = month + day_ + hour + minute;
                //Toast.makeText(MainActivity.this, nowTime, Toast.LENGTH_SHORT).show();
                int int_now = Integer.parseInt(nowTime);
                //Toast.makeText(MainActivity.this, "查询成功", Toast.LENGTH_SHORT).show();
                final String nowDay = year + month + day_;
                MissionCount CountToday = new MissionCount();
                int total = 0;
                int completed = 0;
                int failed = 0;
                int count = 0;
                for (Mission Mis_: allMission){
                    int int_end = Integer.parseInt(Mis_.getEndTime().substring(4));
                    if (Mis_.getProcess().equals("100") && (Mis_.getEndDay().equals(nowDay) || Mis_.getRealFinishTime().equals(nowDay))){
                        total = total + 1;
                        completed = completed + 1;
                        count = count + Integer.parseInt(Mis_.getGainPoint());
                    }
                    else if ((Mis_.getEndDay().equals(nowDay) || Mis_.getRealFinishTime().equals(nowDay)) && int_now - int_end > 0){
                        total = total + 1;
                        failed = failed + 1;
                        count = count - Integer.parseInt(Mis_.getLostPoint());
                    }
                }
                BmobUser user = BmobUser.getCurrentUser(MainActivity.this);
                CountToday.setDate(nowDay);
                CountToday.setAuthor(user);
                CountToday.setGainPointToday(Integer.toString(count));
                CountToday.setAccomplishToday(Integer.toString(completed));
                CountToday.setFailToday(Integer.toString(failed));
                CountManger.upLoadCount(MainActivity.this, CountToday);

                //将结果显示在列表Missions中
               /* LostAdapter.clear();
                FoundAdapter.clear();
                if (losts == null || losts.size() == 0) {
                    showErrorView(0);
                    LostAdapter.notifyDataSetChanged();
                    return;
                }
                progress.setVisibility(View.GONE);
                LostAdapter.addAll(losts);
                listview.setAdapter(LostAdapter);*/
                //按日期排序
                Collections.sort(allMission, new Comparator<Mission>() {
                    @Override
                    public int compare(Mission lhs, Mission rhs) {
                        return lhs.getBeginTime().compareTo(rhs.getBeginTime());
                    }
                });
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                String day = formatter.format(curDate);
                List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
                for (Iterator it = allMission.iterator();it.hasNext();) {
                    Map<String, Object> showitem = new HashMap<String, Object>();
                    Mission tmp = (Mission) it.next();
                    if (tmp.getIsPrivate().equals("1") && tmp.getIsTeamWork().equals("0") && !tmp.getUserId().equals(user.getUsername()))
                        continue;
                    if (tmp.getIsPrivate().equals("1") && rbtn.isChecked())
                        continue;
                    if (!tmp.getBeginTime().substring(0,8).equals(day)) continue;
                    String bTime = tmp.getBeginTime().substring(8,10)+":"+tmp.getBeginTime().substring(10);
                    String eTime = tmp.getEndTime().substring(8,10)+":"+tmp.getEndTime().substring(10);
                    showitem.put("beginTime", bTime);
                    showitem.put("endTime", eTime);
                    showitem.put("name", tmp.getName());
                    showitem.put("progress",tmp.getProcess());
                    showitem.put("mission",tmp);
                    listitem.add(showitem);
                }
                SimpleAdapter myAdapter = new SimpleAdapter(getApplicationContext(), listitem,
                        R.layout.list_item, new String[]{"beginTime", "endTime", "name","progress"},
                        new int[]{R.id.begin_time, R.id.end_time, R.id.title, R.id.progress});
                final ListView missionList = (ListView)findViewById(R.id.mission_list);
                final LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                missionList.setAdapter(myAdapter);
                //监听
                missionList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                            long arg3) {
                        // TODO Auto-generated method stub
                        HashMap<String,Object> map = (HashMap<String,Object>)missionList.getItemAtPosition(arg2);
                        Mission choosedMission = (Mission)map.get("mission");
                        Intent toMission = new Intent(MainActivity.this,MissionActivity.class);
                        Bundle missionBundle = new Bundle();
                        missionBundle.putSerializable("Mission",choosedMission);
                        toMission.putExtras(missionBundle);
                        startActivity(toMission);
                    }

                });
            }

            @Override
            public void onError(int code, String arg0) {
                // TODO Auto-generated method stub
                //showErrorView(0);
            }
        });

        RadioGroup my_choice = (RadioGroup) findViewById(R.id.rg_tab_bar);
        my_choice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                query = new BmobQuery<Mission>();
                user = BmobUser.getCurrentUser(MainActivity.this);
                if (checkedId== R.id.rb_personal){
                    query.addWhereEqualTo("Author", user);
                }else {
                    String Groupname;
                    if (user==null) Groupname=null;
                    else {
                        Groupname = UserManger.getGroup(MainActivity.this).substring(1);
                        query.addWhereEqualTo("GroupName", Groupname); //查询群组任务
                    }
                }
                query.order("-createdAt");// 按照时间降序
                query.findObjects(MainActivity.this, new FindListener<Mission>() {

                    @Override
                    public void onSuccess(List<Mission> allMission) {
                        // TODO Auto-generated method stub
                        //将结果显示在列表Missions中
               /* LostAdapter.clear();
                FoundAdapter.clear();
                if (losts == null || losts.size() == 0) {
                    showErrorView(0);
                    LostAdapter.notifyDataSetChanged();
                    return;
                }
                progress.setVisibility(View.GONE);
                LostAdapter.addAll(losts);
                listview.setAdapter(LostAdapter);*/
                        //按日期排序
                        Collections.sort(allMission, new Comparator<Mission>() {
                            @Override
                            public int compare(Mission lhs, Mission rhs) {
                                return lhs.getBeginTime().compareTo(rhs.getBeginTime());
                            }
                        });
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                        String day = formatter.format(curDate);
                        List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
                        for (Iterator it = allMission.iterator();it.hasNext();) {
                            Map<String, Object> showitem = new HashMap<String, Object>();
                            Mission tmp = (Mission) it.next();
                            //Toast.makeText(MainActivity.this,tmp.getUserId() + "+" + user.getUsername(),Toast.LENGTH_LONG).show();
                            if (tmp.getIsPrivate().equals("1") && tmp.getIsTeamWork().equals("0") && !tmp.getUserId().equals(user.getUsername())) {
                                //Toast.makeText(MainActivity.this,tmp.getUserId() + "+" + user.getUsername(),Toast.LENGTH_LONG).show();
                                continue;
                            }
                            if (tmp.getIsPrivate().equals("1") && rbtn.isChecked())
                                continue;
                            if (!tmp.getBeginTime().substring(0,8).equals(day)) continue;
                            String bTime = tmp.getBeginTime().substring(8,10)+":"+tmp.getBeginTime().substring(10);
                            String eTime = tmp.getEndTime().substring(8,10)+":"+tmp.getEndTime().substring(10);
                            showitem.put("beginTime", bTime);
                            showitem.put("endTime", eTime);
                            showitem.put("name", tmp.getName());
                            showitem.put("progress",tmp.getProcess());
                            showitem.put("mission",tmp);
                            listitem.add(showitem);
                        }
                        SimpleAdapter myAdapter = new SimpleAdapter(getApplicationContext(), listitem,
                                R.layout.list_item, new String[]{"beginTime", "endTime", "name","progress"},
                                new int[]{R.id.begin_time, R.id.end_time, R.id.title, R.id.progress});
                        final ListView missionList = (ListView)findViewById(R.id.mission_list);
                        final LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                        missionList.setAdapter(myAdapter);
                        //监听
                        missionList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                            @Override
                            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                                    long arg3) {
                                // TODO Auto-generated method stub
                                HashMap<String,Object> map = (HashMap<String,Object>)missionList.getItemAtPosition(arg2);
                                Mission choosedMission = (Mission)map.get("mission");
                                Intent toMission = new Intent(MainActivity.this,MissionActivity.class);
                                Bundle missionBundle = new Bundle();
                                missionBundle.putSerializable("Mission",choosedMission);
                                toMission.putExtras(missionBundle);
                                startActivity(toMission);
                            }

                        });
                    }

                    @Override
                    public void onError(int code, String arg0) {
                        // TODO Auto-generated method stub
                        //showErrorView(0);
                    }
                });
            }
        });




//浮动按钮
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent toAddMission= new Intent(MainActivity.this,AddMission.class);
                    startActivity(toAddMission);
    //                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
    //                        .setAction("Action", null).show();
                }
            });
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);//设置菜单图标恢复本来的颜色
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
//设置部分
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.account_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
////        int id = item.getItemId();
////
////        //noinspection SimplifiableIfStatement
////        if (id == R.id.action_settings) {
////            //设置
////            return true;
////        }
//
//        return super.onOptionsItemSelected(item);
//    }
    //抽屉菜单部分
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        //主菜单点击响应
        int id = item.getItemId();

        if (id == R.id.nav_mission_manage) {
            // Handle the camera action
        } else if (id == R.id.nav_analysis) {
            Intent accountIntent = new Intent(MainActivity.this,AnalysisActivity.class);
            startActivity(accountIntent);
        } else if (id == R.id.nav_account) {
            Intent accountIntent = new Intent(MainActivity.this,AccountMenuActivity.class);
            startActivity(accountIntent);
        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_about) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
