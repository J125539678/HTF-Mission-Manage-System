package htf.htfmms.Account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import htf.htfmms.MainActivity;
import htf.htfmms.R;
import htf.htfmms.Database.UserManger;

public class AccountActivity extends AppCompatActivity {
    EditText editTextUsername;
    EditText editTextKey;
    Button btnLogin;
    TextView btnRegister;
    //先采用sharedPreference方法存储数据
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toolbar mytoolbar = (Toolbar) findViewById(R.id.my_toolbar);
        editTextUsername = (EditText)findViewById(R.id.editTextUsername);
        editTextKey = (EditText)findViewById(R.id.editTextKey);
        btnLogin = (Button)findViewById(R.id.buttonLogin);
        btnRegister = (TextView)findViewById(R.id.buttonRegister);

        setSupportActionBar(mytoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//返回箭头
        mytoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMain = new Intent(AccountActivity.this,MainActivity.class);
                startActivity(toMain);
            }
        });
        //获取preferce实例
      //  preferences = getSharedPreferences("account",MODE_PRIVATE);
      //  editor = preferences.edit();
        //监听按钮
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toRegister = new Intent(AccountActivity.this,RegisterActivity.class);
                startActivity(toRegister);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String userName = editTextUsername.getText().toString();
                String keyIn = editTextKey.getText().toString();
                if (userName.equals("")) {
                    Toast.makeText(AccountActivity.this, "请输入用户名！", Toast.LENGTH_SHORT).show();
                    editTextUsername.setText("");
                    return;
                }
                String regex = "^[a-z0-9A-Z]+$";
                if (!userName.matches(regex)) {
                    Toast.makeText(AccountActivity.this, "用户名必须由字母数字组成！", Toast.LENGTH_SHORT).show();
                    editTextUsername.setText("");
                    return;
                }
                if (userName.length()>10) {
                    Toast.makeText(AccountActivity.this, "最多十位！", Toast.LENGTH_SHORT).show();
                    editTextUsername.setText("");
                    return;
                }

                UserManger.loginIn(AccountActivity.this,userName,keyIn);
//                String  keyRight = preferences.getString(userName,"");
//                if (!keyRight.equals(keyIn)){
//                    Toast.makeText(AccountActivity.this,"用户名与密码不匹配！",Toast.LENGTH_SHORT).show();
//                    return;
//                }else{
//                    Toast.makeText(AccountActivity.this,"登陆成功！",Toast.LENGTH_SHORT).show();
//                    Intent toMain = new Intent(AccountActivity.this,MainActivity.class);
//                    startActivity(toMain);
//                }

            }
        });
    }
}
