package htf.htfmms.Database;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import htf.htfmms.Account.AccountActivity;
import htf.htfmms.Account.AccountMenuActivity;
import htf.htfmms.MainActivity;


/**
 * Created by Lin on 2016/5/23.
 */
public class UserManger {

    //检查是否已经注册
    public static void checkUser(final Context context, String username){
        /*BmobQuery<BmobUser> query = new BmobQuery<BmobUser>();
        query.addWhereEqualTo("username", username);
        query.findObjects(context, listener);*/
        BmobQuery<BmobUser> query = new BmobQuery<BmobUser>();
        query.addWhereEqualTo("username", username);
        query.findObjects(context, new FindListener<BmobUser>() {
            @Override
            public void onSuccess(List<BmobUser> object) {
                // TODO Auto-generated method stub
                //  toast("查询用户成功："+object.size());
                // Toast.makeText(context, "查询成功！", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
                //   toast("查询用户失败："+msg);
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //注册
    public static void signIn(final Context context, String username, String password){
        /*BmobUser bu = new BmobUser();
        bu.setUsername(username);
        bu.setPassword(password);
        bu.setEmail(username);
        //注意：不能用save方法进行注册
        bu.signUp(context, listener);*/

        BmobUser bu = new BmobUser();
        bu.setUsername(username);
        bu.setPassword(password);
        bu.setEmail("nnn@what.com");
        //bu.setEmail("sendi@163.com");
        //注意：不能用save方法进行注册
        bu.signUp(context, new SaveListener() {
            @Override
            public void onSuccess() {
                //Toast.makeText(context,"注册！",Toast.LENGTH_SHORT).show();
                // toast("注册成功:");
                //通过BmobUser.getCurrentUser(context)方法获取登录成功后的本地用户信息

                AlertDialog alert = null;
                AlertDialog.Builder builder = null;
                builder = new AlertDialog.Builder(context);
                alert = builder.setTitle("提示")
                        .setMessage("注册成功！")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent return2login = new Intent(context,AccountActivity.class);
                                context.startActivity(return2login);
                            }
                        })
                        .create();
                alert.show();
            }
            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
                //  toast("注册失败:"+msg);
                Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void checkUserSettingInfo(Context context,String userObjectId,FindListener<UserSettings> listener){
        BmobQuery<UserSettings> query = new BmobQuery<UserSettings>();
        //查询
        query.addWhereEqualTo("userObjectId", userObjectId);
        //执行查询方法
        query.findObjects(context, listener);
    }

    //登录

    public static void loginIn(final Context context, String username, String password){
        /*BmobUser bu2 = new BmobUser();
        bu2.setUsername(username);
        bu2.setPassword(password);
        bu2.login(context, listener);*/
        BmobUser bu2 = new BmobUser();
        bu2.setUsername(username);
        bu2.setPassword(password);
        bu2.login(context, new SaveListener() {
            @Override
            public void onSuccess() {
                //  toast("登录成功:");
                //通过BmobUser user = BmobUser.getCurrentUser(context)获取登录成功后的本地用户信息
                //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(context,MyUser.class)获取自定义用户信息

                //登陆成功则更新任务
                final String userId= BmobUser.getCurrentUser(context).getObjectId().toString();
                UserInfo userInfo=new UserInfo();
                userInfo.objectId=userId;
                userInfo.userName= BmobUser.getCurrentUser(context).getUsername().toString();
                MyMissionPreference.getInstance(context).saveObject(context,userId,userInfo);
                //new UpDataUtils().upAllPlanDate(context);

                Toast.makeText(context,"登陆成功！",Toast.LENGTH_SHORT).show();
                Intent toMain = new Intent(context,MainActivity.class);
                context.startActivity(toMain);
            }
            @Override
            public void onFailure(int code, String msg) {
                // toast("登录失败:"+msg);
                Toast.makeText(context,"登录失败："+msg,Toast.LENGTH_SHORT).show();
            }
        });
    }
    //退出登陆
    public static void loginOut(Context context){
        /*BmobUser bmobUser=BmobUser.getCurrentUser(context);
        if(bmobUser!=null){
            MyPlanPreference.getInstance(context).removeObject(context,bmobUser.getObjectId());
        }*/
        BmobUser.logOut(context);   //清除缓存用户对象
        BmobUser currentUser = BmobUser.getCurrentUser(context); // 现在的currentUser是null了
    }

    public static void updatePlanDate(final Context context, ArrayList<BmobObject> listPlan, UpdateListener listener){
        try{
            new BmobObject().updateBatch(context, listPlan, listener);
        }catch (Exception ex){
            //ToastUtil.showLongToast(context, "更新失败");
        }
    }

    public  static void changePassword(final Context context, String oldPassword, String newPassword){
        BmobUser.updateCurrentUserPassword(context, oldPassword, newPassword, new UpdateListener() {
            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                //Log.i("smile", "密码修改成功，可以用新密码进行登录啦");
                AlertDialog alert = null;
                AlertDialog.Builder builder = null;
                builder = new AlertDialog.Builder(context);
                alert = builder.setTitle("提示")
                        .setMessage("修改密码成功！")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent return2account = new Intent(context,AccountMenuActivity.class);
                                context.startActivity(return2account);
                            }
                        })
                        .create();
                alert.show();
            }

            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
                Toast.makeText(context,"密码修改失败："+msg,Toast.LENGTH_SHORT).show();
                Log.i("smile", "密码修改失败："+msg+"("+code+")");
            }
        });
    }

    //加入群组，使用这个函数前先查询群组是否存在
    public static  void joinGroup(final Context context,final String Groupname){
        BmobUser user = BmobUser.getCurrentUser(context);
        user.setEmail(Groupname);
        user.update(context, user.getObjectId(),new UpdateListener() {
            @Override
            public void onSuccess() {
                BmobQuery<Group> query = new BmobQuery<Group>();
                query.addWhereEqualTo("Name", Groupname.substring(1));
                query.findObjects(context, new FindListener<Group>() {
                    @Override
                    public void onSuccess(List<Group> object) {
                        // TODO Auto-generated method stub
                        for(Group g:object){
                            g.setNumber(Integer.toString(Integer.parseInt(g.getNumber())+1));
                            g.update(context, g.getObjectId(), new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    // TODO Auto-generated method stub
                                    Toast.makeText(context,"加入成功",Toast.LENGTH_SHORT).show();
                                    Log.i("bmob","更新成功：");
                                }

                                @Override
                                public void onFailure(int code, String msg) {
                                    // TODO Auto-generated method stub
                                    Toast.makeText(context,"加入失败："+msg,Toast.LENGTH_SHORT).show();
                                    Log.i("bmob","更新败："+msg);
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(int code, String msg) {
                        // TODO Auto-generated method stub
                        //   toast("查询用户失败："+msg);
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                });
                //toast("添加数据成功，返回objectId为："+myMission.getObjectId() + ",数据在服务端的创建时间为：" + myMission.getCreatedAt());
            }
            @Override
            public void onFailure(int code, String arg0) {
                // 添加失败
                String tmpname = Integer.toString((Integer.parseInt(Groupname.substring(0,1))+1))+Groupname.substring(1);
                joinGroup(context,tmpname);
                //Toast.makeText(context,"操作失败" +"："+arg0,Toast.LENGTH_LONG).show();
            }
        });
    }

    //退出群组，使用这个函数前先查询群组是否存在
    public static  void quitGroup(final Context context,final String Groupname){
        BmobUser user = BmobUser.getCurrentUser(context);
        user.setEmail("nnn@what.com");
        user.update(context,user.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {

                Toast.makeText(context,"退出成功",Toast.LENGTH_SHORT).show();
                //Toast.makeText(context,BmobUser.getCurrentUser(context).getEmail(),Toast.LENGTH_SHORT).show();

                //toast("添加数据成功，返回objectId为："+myMission.getObjectId() + ",数据在服务端的创建时间为：" + myMission.getCreatedAt());
            }
            @Override
            public void onFailure(int code, String arg0) {
                Toast.makeText(context,"退出失败："+arg0,Toast.LENGTH_SHORT).show();
                // 添加失败
            }
        });
        BmobQuery<Group> query = new BmobQuery<Group>();
        BmobUser user2 = BmobUser.getCurrentUser(context);
        if (user2.getEmail().equals("nnn@what.com")) {
            query.addWhereEqualTo("Name", Groupname);
            query.findObjects(context, new FindListener<Group>() {
                @Override
                public void onSuccess(List<Group> object) {
                    //Toast.makeText(context,Integer.toString(object.size())+"555",Toast.LENGTH_SHORT).show();
                    // TODO Auto-generated method stub
                    for (Group g : object) {
                        g.setNumber(Integer.toString(Integer.parseInt(g.getNumber()) - 1));

                        //Toast.makeText(context,Groupname+"555",Toast.LENGTH_SHORT).show();
                        g.update(context, g.getObjectId(), new UpdateListener() {
                            @Override
                            public void onSuccess() {
                                // TODO Auto-generated method stub
                                Toast.makeText(context, "退出群组成功", Toast.LENGTH_SHORT).show();
                                Log.i("bmob", "更新成功：");
                            }

                            @Override
                            public void onFailure(int code, String msg) {
                                // TODO Auto-generated method stub
                                Toast.makeText(context, "退出失败：" + msg, Toast.LENGTH_SHORT).show();
                                Log.i("bmob", "更新失败：" + msg);
                            }
                        });
                    }
                }

                @Override
                public void onError(int code, String msg) {
                    // TODO Auto-generated method stub
                    //   toast("查询用户失败："+msg);
                    //Toast.makeText(context, "操作失败：" + msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //获取用户所属群组名,若无则返回null
    public static  String getGroup(final Context context) {
        BmobUser user = BmobUser.getCurrentUser(context);
        return user.getEmail();
    }

    //根据群组名查询用户
    public static  void queryUser(final Context context,final String Groupname){
        BmobQuery<BmobUser> query = new BmobQuery<BmobUser>();
        query.addWhereEqualTo("email", Groupname);    // 查询群组的所有用户
        query.order("-createdAt");// 按照时间降序
        query.findObjects(context, new FindListener<BmobUser>() {
            @Override
            public void onSuccess(List<BmobUser> users) {
                // TODO Auto-generated method stub
                //将结果显示在列表Missions中
                // for (BmobUser user : users){
                //
                // }
            }

            @Override
            public void onError(int code, String arg0) {
                // TODO Auto-generated method stub
                //showErrorView(0);
            }
        });
    }
}
