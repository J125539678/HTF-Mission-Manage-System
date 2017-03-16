package htf.htfmms.Account;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import htf.htfmms.Database.Group;
import htf.htfmms.Database.GroupManger;
import htf.htfmms.Database.UserManger;
import htf.htfmms.MainActivity;
import htf.htfmms.R;

public class AccountMenuActivity extends AppCompatActivity {
    TextView loginState;
    BmobUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("账号管理");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//返回箭头
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMain = new Intent(AccountMenuActivity.this,MainActivity.class);
                startActivity(toMain);
            }
        });


        loginState = (TextView)findViewById(R.id.login_status);
        currentUser = BmobUser.getCurrentUser(AccountMenuActivity.this);
        if (currentUser != null) loginState.setText(currentUser.getUsername());
        else loginState.setText("请先登录！");

        ListView accountMenuList = (ListView)findViewById(R.id.account_menu_list);
        String [] menuStr = {"登录","修改密码","注销","创建群组","加入群组","退出群组"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,menuStr);
        accountMenuList.setAdapter(arrayAdapter);
        accountMenuList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                switch (arg2){
                    case 0:{
                        if (currentUser!=null){
                            AlertDialog alert = null;
                            AlertDialog.Builder builder = null;
                            builder = new AlertDialog.Builder(AccountMenuActivity.this);
                            alert = builder.setTitle("提示")
                                    .setMessage("已经登录！")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    })
                                    .create();
                            alert.show();
                        }else {
                            Intent toAccount = new Intent(AccountMenuActivity.this, AccountActivity.class);
                            startActivity(toAccount);
                        }
                        break;
                    }
                    case 1:{
                        if (currentUser==null){
                            AlertDialog alert = null;
                            AlertDialog.Builder builder = null;
                            builder = new AlertDialog.Builder(AccountMenuActivity.this);
                            alert = builder.setTitle("提示")
                                    .setMessage("请先登录！")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent toAccount = new Intent(AccountMenuActivity.this,AccountActivity.class);
                                            startActivity(toAccount);
                                        }
                                    })
                                    .create();
                            alert.show();
                        }else {
                            Intent toChange = new Intent(AccountMenuActivity.this,ChangeKeyActivity.class);
                            startActivity(toChange);
                        }
                        break;
                    }
                    case 2:{
                        if (currentUser==null){
                            AlertDialog alert = null;
                            AlertDialog.Builder builder = null;
                            builder = new AlertDialog.Builder(AccountMenuActivity.this);
                            alert = builder.setTitle("提示")
                                    .setMessage("尚未登录！")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    })
                                    .create();
                            alert.show();
                        }else {
                            UserManger.loginOut(AccountMenuActivity.this);
                            currentUser = BmobUser.getCurrentUser(AccountMenuActivity.this);
                            if (currentUser == null) loginState.setText("请先登录！");
                        }
                        break;
                    }
                    case 3:{
                        final EditText createName = new EditText(AccountMenuActivity.this);
                        AlertDialog alert = null;
                        AlertDialog.Builder builder = null;
                        builder = new AlertDialog.Builder(AccountMenuActivity.this);
                        alert = builder.setTitle("提示")
                                .setMessage("输入群组号！")
                                .setView(createName)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        final String name = createName.getText().toString()+"@qq.com";
                                        BmobQuery<Group> query = new BmobQuery<Group>();
                                        query.addWhereEqualTo("Name", name);
                                        query.findObjects(AccountMenuActivity.this, new FindListener<Group>() {
                                            @Override
                                            public void onSuccess(List<Group> object) {
                                                GroupManger.createGroup(AccountMenuActivity.this,name);
                                                // TODO Auto-generated method stub
                                                //  toast("查询用户成功："+object.size());
                                                //Toast.makeText(context, "加入成功！"+Groupname, Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onError(int code, String msg) {
                                                // TODO Auto-generated method stub
                                                //   toast("查询用户失败："+msg);

                                                AlertDialog alert = null;
                                                AlertDialog.Builder builder = null;
                                                builder = new AlertDialog.Builder(AccountMenuActivity.this);
                                                alert = builder.setTitle("提示")
                                                        .setMessage("群组不存在！")
                                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                            }
                                                        })
                                                        .create();
                                                alert.show();
                                                Toast.makeText(AccountMenuActivity.this, msg, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                })
                                .create();
                        alert.show();
                        break;
                    }
                    case 4:{
                        String currGroup = UserManger.getGroup(AccountMenuActivity.this);
                        final EditText groupName = new EditText(AccountMenuActivity.this);
                        if (!currGroup.equals("nnn@what.com")){
                            //Toast.makeText(AccountMenuActivity.this, currGroup, Toast.LENGTH_SHORT).show();
                            AlertDialog alert = null;
                            AlertDialog.Builder builder = null;
                            builder = new AlertDialog.Builder(AccountMenuActivity.this);
                            alert = builder.setTitle("提示")
                                    .setMessage("请先退出当前群组！")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    })
                                    .create();
                            alert.show();
                        }
                        else{
                            AlertDialog alert = null;
                            AlertDialog.Builder builder = null;
                            builder = new AlertDialog.Builder(AccountMenuActivity.this);
                            alert = builder.setTitle("提示")
                                    .setMessage("输入群组号！")
                                    .setView(groupName)
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            final String name = "0"+groupName.getText().toString()+"@qq.com";
                                            BmobQuery<Group> query = new BmobQuery<Group>();
                                            query.addWhereEqualTo("Name", name.substring(1));
                                            query.findObjects(AccountMenuActivity.this, new FindListener<Group>() {
                                                @Override
                                                public void onSuccess(List<Group> object) {
                                                    UserManger.joinGroup(AccountMenuActivity.this,name);
                                                    // TODO Auto-generated method stub
                                                    //  toast("查询用户成功："+object.size());
                                                    //Toast.makeText(context, "加入成功！"+Groupname, Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onError(int code, String msg) {
                                                    // TODO Auto-generated method stub
                                                    //   toast("查询用户失败："+msg);

                                                    AlertDialog alert = null;
                                                    AlertDialog.Builder builder = null;
                                                    builder = new AlertDialog.Builder(AccountMenuActivity.this);
                                                    alert = builder.setTitle("提示")
                                                            .setMessage("群组不存在！")
                                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                }
                                                            })
                                                            .create();
                                                    alert.show();
                                                    Toast.makeText(AccountMenuActivity.this, msg, Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    })
                                    .create();
                            alert.show();
                        }
                        break;
                    }
                    case 5:{
                        String currGroup = UserManger.getGroup(AccountMenuActivity.this);
                        //Toast.makeText(AccountMenuActivity.this, currGroup+"45", Toast.LENGTH_SHORT).show();
                        if (!currGroup.equals("nnn@what.com")){
                            UserManger.quitGroup(AccountMenuActivity.this,currGroup);
                        }
                        else{
                            AlertDialog alert = null;
                            AlertDialog.Builder builder = null;
                            builder = new AlertDialog.Builder(AccountMenuActivity.this);
                            alert = builder.setTitle("提示")
                                    .setMessage("没有加入任何群组！")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    })
                                    .create();
                            alert.show();
                        }
                        break;
                    }
                }

            }

        });


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

}
