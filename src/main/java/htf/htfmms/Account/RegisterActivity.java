package htf.htfmms.Account;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import htf.htfmms.R;
import htf.htfmms.Database.UserManger;

public class RegisterActivity extends AppCompatActivity {
    EditText editTextUsername;
    EditText editTextKey;
    EditText certainKey;
    Button bRegister;
    //先采用sharedPreference方法存储数据
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editTextUsername = (EditText)findViewById(R.id.editTextUsername);
        editTextKey = (EditText)findViewById(R.id.editTextKey);
        certainKey = (EditText)findViewById(R.id.certainKey);
        bRegister = (Button)findViewById(R.id.btnRegister);

        //获取preferce实例
       // preferences = getSharedPreferences("account",MODE_PRIVATE);
       // editor = preferences.edit();
        //监听按钮
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = editTextUsername.getText().toString();
                String key = editTextKey.getText().toString();
                String cKey = certainKey.getText().toString();
                //检查合法性
                if (userName == "") {
                    Toast.makeText(RegisterActivity.this, "请输入用户名！", Toast.LENGTH_SHORT).show();
                    editTextUsername.setText("");
                    return;
                }
                String regex = "^[a-z0-9A-Z]+$";
                if (!userName.matches(regex)) {
                    Toast.makeText(RegisterActivity.this, "用户名必须由字母数字组成！", Toast.LENGTH_SHORT).show();
                    editTextUsername.setText("");
                    return;
                }
                if (userName.length()>10) {
                    Toast.makeText(RegisterActivity.this, "最多十位！", Toast.LENGTH_SHORT).show();
                    editTextUsername.setText("");
                    return;
                }
//                String existOrnot = preferences.getString(userName,"");
//                if (existOrnot != "") {
//                    Toast.makeText(RegisterActivity.this, "已被使用！", Toast.LENGTH_SHORT).show();
//                    editTextUsername.setText("");
//                    return;
//                }
                UserManger.checkUser(RegisterActivity.this,userName);
                if (key == "") {
                    Toast.makeText(RegisterActivity.this, "请输入密码！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!key.matches(regex)) {
                    Toast.makeText(RegisterActivity.this, "密码必须由字母数字组成！",
                            Toast.LENGTH_SHORT).show();
                    editTextKey.setText("");
                    return;
                }
                if (key.length()>10) {
                    Toast.makeText(RegisterActivity.this, "最多十位！", Toast.LENGTH_SHORT).show();
                    editTextKey.setText("");
                    return;
                }

                if (cKey==""){
                    Toast.makeText(RegisterActivity.this, "请再次输入密码！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!cKey.equals(key)){
                    Toast.makeText(RegisterActivity.this, "两次密码输入不匹配！", Toast.LENGTH_SHORT).show();
                    return;
                }

                UserManger.signIn(RegisterActivity.this,userName,key);
                //存储
//                editor.putString(userName,key);
//                editor.commit();
                //显示成功对话框
//                builder = new AlertDialog.Builder(RegisterActivity.this);
//                alert = builder.setTitle("提示")
//                        .setMessage("注册成功！")
//                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Intent return2login = new Intent(RegisterActivity.this,AccountActivity.class);
//                                startActivity(return2login);
//                            }
//                        })
//                        .create();
//                alert.show();
            }
        });
    }
}
