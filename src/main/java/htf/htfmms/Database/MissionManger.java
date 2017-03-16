package htf.htfmms.Database;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.BmobUser;
import htf.htfmms.Database.Mission;

/**
 * Created by Lin on 2016/6/1.
 */
public class MissionManger {
    public static  void addMission(final Context context,Mission mission){
        final Mission myMission = new Mission();
        BmobUser user = BmobUser.getCurrentUser(context);
        myMission.setUserId(mission.getUserId());
        myMission.setMissionId(mission.getMissionId());
        myMission.setName(mission.getName());
        myMission.setContents(mission.getContents());
        myMission.setBeginTime(mission.getBeginTime());
        myMission.setEndTime(mission.getEndTime());
        myMission.setGainPoint(mission.getGainPoint());
        myMission.setLostPoint(mission.getLostPoint());
        myMission.setRemindTime(mission.getRemindTime());
        myMission.setProcess(mission.getProcess());
        myMission.setRemindWay(mission.getRemindWay());
        myMission.setType(mission.getType());
        myMission.setUpdateTime(mission.getUpdateTime());
        myMission.setAuthor(user);
        myMission.setGroupName(mission.getGroupName());
        myMission.setIsPrivate(mission.getIsPrivate());
        myMission.setIsTeamWork(mission.getIsTeamWork());
        myMission.setRealFinishTime(mission.getRealFinishTime());
        myMission.setMissionSource(mission.getMissionSource());
        myMission.setMissionRecord(mission.getMissionRecord());
        myMission.setEndDay(mission.getEndDay());
        myMission.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                //toast("添加数据成功，返回objectId为："+myMission.getObjectId() + ",数据在服务端的创建时间为：" + myMission.getCreatedAt());
            }
            @Override
            public void onFailure(int code, String arg0) {
                // 添加失败
            }
        });
    }
    public static  void queryMisssions(final Context context){
        //showView();
        BmobUser user = BmobUser.getCurrentUser(context);
        BmobQuery<Mission> query = new BmobQuery<Mission>();
        query.addWhereEqualTo("Author", user);    // 查询当前用户的所有任务
        query.order("-createdAt");// 按照时间降序
        query.findObjects(context, new FindListener<Mission>() {

            @Override
            public void onSuccess(List<Mission> Missions) {
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
            }

            @Override
            public void onError(int code, String arg0) {
                // TODO Auto-generated method stub
                //showErrorView(0);
            }
        });
    }

    //根据实际完成时间或计划完成时间查询任务
    public static  void queryBydate(final Context context, String time){
        BmobQuery<Mission> eq1 = new BmobQuery<Mission>();
        eq1.addWhereEqualTo("EndDay", time);
        BmobQuery<Mission> eq2 = new BmobQuery<Mission>();
        eq2.addWhereEqualTo("RealFinishTime", time);
        List<BmobQuery<Mission>> queries = new ArrayList<BmobQuery<Mission>>();
        queries.add(eq1);
        queries.add(eq2);
        BmobQuery<Mission> mainQuery = new BmobQuery<Mission>();
        mainQuery.or(queries);
        mainQuery.order("-createdAt");// 按照时间降序
        mainQuery.findObjects(context, new FindListener<Mission>() {
            @Override
            public void onSuccess(List<Mission> object) {
                // TODO Auto-generated method stub
                //将结果显示在列表object中
            }
            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub

            }
        });
    }

    //根据群组名查询任务
    public static  void queryByGroup(final Context context, String Groupname){
        BmobQuery<Mission> query = new BmobQuery<Mission>();
        query.addWhereEqualTo("GroupName", Groupname);
        query.order("-createdAt");// 按照时间降序
        query.findObjects(context, new FindListener<Mission>() {

            @Override
            public void onSuccess(List<Mission> Missions) {
                // TODO Auto-generated method stub
                //将结果显示在列表Missions中
            }

            @Override
            public void onError(int code, String arg0) {
                // TODO Auto-generated method stub
                //showErrorView(0);
            }
        });
    }
    public static  void deleteMission(final Context context, String MissionId) {
        BmobQuery<Mission> query = new BmobQuery<Mission>();
        query.addWhereEqualTo("MissionId", MissionId);
//执行查询方法
        query.findObjects(context, new FindListener<Mission>() {
            @Override
            public void onSuccess(List<Mission> object) {
                // TODO Auto-generated method stub
                //toast("查询成功：共"+object.size()+"条数据。");
                for (Mission myMission : object) {
                    myMission.delete(context, new DeleteListener() {
                        @Override
                        public void onSuccess() {
                            // TODO Auto-generated method stub
                            //LostAdapter.remove(position);
                            //重新显示
                        }

                        @Override
                        public void onFailure(int code, String arg0) {
                            // TODO Auto-generated method stub

                        }
                    });
                }
            }
            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
                //toast("查询失败："+msg);
            }
        });
    }

    public static  void deleteMissionOne(final Context context, String MissionId, final String username) {
        BmobQuery<Mission> query = new BmobQuery<Mission>();
        query.addWhereEqualTo("MissionId", MissionId);
//执行查询方法
        query.findObjects(context, new FindListener<Mission>() {
            @Override
            public void onSuccess(List<Mission> object) {
                // TODO Auto-generated method stub
                //toast("查询成功：共"+object.size()+"条数据。");
                for (Mission myMission : object) {
                    if (myMission.getUserId().equals(username))
                        myMission.delete(context, new DeleteListener() {
                            @Override
                            public void onSuccess() {
                            // TODO Auto-generated method stub
                            //LostAdapter.remove(position);
                            //重新显示
                            }

                            @Override
                            public void onFailure(int code, String arg0) {
                                // TODO Auto-generated method stub

                            }
                        });
                }
            }
            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
                //toast("查询失败："+msg);
            }
        });
    }

    public static  void addMisByGroup(final Context context,final Mission mission,String Groupname){
        for (int i=0; i<10; ++i){
            String tmp = Integer.toString(i);
            BmobQuery<BmobUser> query = new BmobQuery<BmobUser>();
            query.addWhereEqualTo("email", tmp + Groupname);    // 查询群组的所有用户
            query.order("-createdAt");// 按照时间降序
            query.findObjects(context, new FindListener<BmobUser>() {
                @Override
                public void onSuccess(List<BmobUser> users) {
                    // TODO Auto-generated method stub
                    for (BmobUser user : users){  //为每个群组内用户添加任务
                        final Mission myMission = new Mission();
                        myMission.setUserId(user.getUsername());
                        myMission.setMissionId(mission.getMissionId());
                        myMission.setName(mission.getName());
                        myMission.setContents(mission.getContents());
                        myMission.setBeginTime(mission.getBeginTime());
                        myMission.setEndTime(mission.getEndTime());
                        myMission.setGainPoint(mission.getGainPoint());
                        myMission.setLostPoint(mission.getLostPoint());
                        myMission.setRemindTime(mission.getRemindTime());
                        myMission.setProcess(mission.getProcess());
                        myMission.setRemindWay(mission.getRemindWay());
                        myMission.setType(mission.getType());
                        myMission.setUpdateTime(mission.getUpdateTime());
                        myMission.setAuthor(user);
                        myMission.setGroupName(mission.getGroupName());
                        myMission.setIsPrivate(mission.getIsPrivate());
                        myMission.setIsTeamWork(mission.getIsTeamWork());
                        myMission.setRealFinishTime(mission.getRealFinishTime());
                        myMission.setMissionSource(mission.getMissionSource());
                        myMission.setMissionRecord(mission.getMissionRecord());
                        myMission.setEndDay(mission.getEndDay());
                        myMission.save(context, new SaveListener() {
                            @Override
                            public void onSuccess() {
                                //toast("添加数据成功，返回objectId为："+myMission.getObjectId() + ",数据在服务端的创建时间为：" + myMission.getCreatedAt());
                            }
                            @Override
                            public void onFailure(int code, String arg0) {
                                // 添加失败
                            }
                        });
                    }
                }
                @Override
                public void onError(int code, String arg0) {
                    // TODO Auto-generated method stub
                    //showErrorView(0);
                }
            });
        }
    }


    public static  void updateMission(final Context context, Mission oldmission,Mission newmission) {
        Mission myMission = new Mission();
        myMission.setUserId(newmission.getUserId());
        myMission.setMissionId(newmission.getMissionId());
        myMission.setName(newmission.getName());
        myMission.setContents(newmission.getContents());
        myMission.setBeginTime(newmission.getBeginTime());
        myMission.setEndTime(newmission.getEndTime());
        myMission.setGainPoint(newmission.getGainPoint());
        myMission.setLostPoint(newmission.getLostPoint());
        myMission.setRemindWay(newmission.getRemindWay());
        myMission.setProcess(newmission.getProcess());
        myMission.setRemindWay(newmission.getRemindWay());
        myMission.setType(newmission.getType());
        myMission.setUpdateTime(newmission.getUpdateTime());
        myMission.setObjectId(newmission.getObjectId());
        myMission.update(context, oldmission.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                Log.i("bmob","更新成功：");
            }

            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
                Log.i("bmob","更新失败："+msg);
            }
        });
    }
}
