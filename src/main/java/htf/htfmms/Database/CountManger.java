package htf.htfmms.Database;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Lin on 2016/6/10.
 */
public class CountManger {
    public static  void upLoadCount(final Context context, final MissionCount countToday){
        //Toast.makeText(context, "开始上传数据", Toast.LENGTH_SHORT).show();
        BmobUser user = BmobUser.getCurrentUser(context);
        BmobQuery<MissionCount> query = new BmobQuery<MissionCount>();
        query.addWhereEqualTo("Author", user);    // 查询当前用户的所有任务
        query.addWhereEqualTo("Date", countToday.getDate());
        //query.setLimit(30);//最多返回30条数据
        query.findObjects(context, new FindListener<MissionCount>() {

            @Override
            public void onSuccess(List<MissionCount> Counts) {
                // TODO Auto-generated method stub
                if(Counts.size()!=0)
                for (MissionCount oldCount : Counts){
                    final MissionCount myCount = new MissionCount();
                    BmobUser user = BmobUser.getCurrentUser(context);
                    myCount.setAuthor(user);
                    myCount.setAccomplishToday(countToday.getAccomplishToday());
                    myCount.setDate(countToday.getDate());
                    myCount.setFailToday(countToday.getFailToday());
                    myCount.setGainPointToday(countToday.getGainPointToday());
                    myCount.update(context, oldCount.getObjectId(), new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            // TODO Auto-generated method stub
                            Log.i("bmob","更新成功：");
                            //Toast.makeText(context, "更新成功", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(int code, String msg) {
                            // TODO Auto-generated method stub
                            Log.i("bmob","更新失败："+msg);
                            //Toast.makeText(context, "更新失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    final MissionCount myCount = new MissionCount();
                    BmobUser user = BmobUser.getCurrentUser(context);
                    myCount.setAuthor(user);
                    myCount.setAccomplishToday(countToday.getAccomplishToday());
                    myCount.setDate(countToday.getDate());
                    myCount.setFailToday(countToday.getFailToday());
                    myCount.setGainPointToday(countToday.getGainPointToday());
                    myCount.save(context, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            //Toast.makeText(context, "创建成功", Toast.LENGTH_SHORT).show();
                            //toast("添加数据成功，返回objectId为："+myMission.getObjectId() + ",数据在服务端的创建时间为：" + myMission.getCreatedAt());
                        }
                        @Override
                        public void onFailure(int code, String arg0) {
                            // 添加失败
                            //Toast.makeText(context, "创建失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onError(int code, String arg0) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "失败", Toast.LENGTH_SHORT).show();
                final MissionCount myCount = new MissionCount();
                BmobUser user = BmobUser.getCurrentUser(context);
                myCount.setAuthor(user);
                myCount.setAccomplishToday(countToday.getAccomplishToday());
                myCount.setDate(countToday.getDate());
                myCount.setFailToday(countToday.getFailToday());
                myCount.setGainPointToday(countToday.getGainPointToday());
                myCount.save(context, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(context, "创建成功", Toast.LENGTH_SHORT).show();
                        //toast("添加数据成功，返回objectId为："+myMission.getObjectId() + ",数据在服务端的创建时间为：" + myMission.getCreatedAt());
                    }
                    @Override
                    public void onFailure(int code, String arg0) {
                        // 添加失败
                        Toast.makeText(context, "创建失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    public static  void queryCount(final Context context){
        //showView();
        BmobUser user = BmobUser.getCurrentUser(context);
        BmobQuery<MissionCount> query = new BmobQuery<MissionCount>();
        query.addWhereEqualTo("Author", user);    // 查询当前用户的所有任务统计
        query.order("-Date");// 按照时间降序
        //query.setLimit(30);//最多返回30条数据

        query.findObjects(context, new FindListener<MissionCount>() {

            @Override
            public void onSuccess(List<MissionCount> Counts) {
                // TODO Auto-generated method stub
                //将结果显示在列表Counts中
                //此处改写
            }

            @Override
            public void onError(int code, String arg0) {
                // TODO Auto-generated method stub
                //showErrorView(0);
            }
        });
    }
}
