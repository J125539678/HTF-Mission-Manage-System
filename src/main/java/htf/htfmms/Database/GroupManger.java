package htf.htfmms.Database;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Lin on 2016/6/20.
 */
public class GroupManger {
    //检查群组是否已经存在
    public static void checkGroup(final Context context, final String Groupname) {
        BmobQuery<Group> query = new BmobQuery<Group>();
        query.addWhereEqualTo("Name", Groupname);
        query.findObjects(context, new FindListener<Group>() {
            @Override
            public void onSuccess(List<Group> object) {
                UserManger.joinGroup(context,Groupname);
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
                builder = new AlertDialog.Builder(context);
                alert = builder.setTitle("提示")
                        .setMessage("群组不存在！")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .create();
                alert.show();
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
    //创建群组
    public static void createGroup(final Context context, String Groupname) {
        Group bu = new Group();
        bu.setName(Groupname);
        bu.setNumber("0");
        bu.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                //Toast.makeText(context,"注册！",Toast.LENGTH_SHORT).show();
                // toast("注册成功:");
                //通过BmobUser.getCurrentUser(context)方法获取登录成功后的本地用户信息

                AlertDialog alert = null;
                AlertDialog.Builder builder = null;
                builder = new AlertDialog.Builder(context);
                alert = builder.setTitle("提示")
                        .setMessage("创建成功！")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .create();
                alert.show();
            }

            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
                //  toast("创建失败:"+msg);
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}