package htf.htfmms.Account;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import htf.htfmms.R;
import htf.htfmms.Database.UserManger;

public class ChangeKeyActivity extends AppCompatActivity {
    EditText oldKey;
    EditText newKey;
    Button btnChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_key);

        oldKey= (EditText)findViewById(R.id.old_key);
        newKey = (EditText)findViewById(R.id.new_key);
        btnChange = (Button)findViewById(R.id.button_change);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = newKey.getText().toString();
                String regex = "^[a-z0-9A-Z]+$";
                if (key == "") {
                    Toast.makeText(ChangeKeyActivity.this, "请输入密码！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!key.matches(regex)) {
                    Toast.makeText(ChangeKeyActivity.this, "密码必须由字母数字组成！",
                            Toast.LENGTH_SHORT).show();
                    newKey.setText("");
                    return;
                }
                if (key.length()>10) {
                    Toast.makeText(ChangeKeyActivity.this, "最多十位！", Toast.LENGTH_SHORT).show();
                    newKey.setText("");
                    return;
                }
                UserManger.changePassword(ChangeKeyActivity.this,oldKey.getText().toString(),key);
            }

        });
    }
}
