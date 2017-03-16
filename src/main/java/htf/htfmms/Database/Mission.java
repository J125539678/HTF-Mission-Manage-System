package htf.htfmms.Database;

import org.apache.http.auth.AUTH;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
/**
 * Created by Lin on 2016/5/17.
 */
public class Mission extends BmobObject  {
    private String UserId;//对应的userId
    private String MissionId;// 表键
    private String Name;//任务名称l
    private String Contents;// 内容
    private String BeginTime;// 开始时间
    private String EndTime;// 完成时间
    private String GainPoint;//奖励积分
    private String LostPoint;//惩罚积分
    private String RemindWay;//通知方式
    private String Process;// 进度
    private String RemindTime;// 通知时间
    private String Type;// 任务类型
    private String UpdateTime;//更新时间
    private BmobUser Author;//任务创建者
    private String GroupName;//所属群组名
    private String IsPrivate;//0私人任务、1群组任务
    private String IsTeamWork;//0独自完成，1共同完成
    private String RealFinishTime;//实际完成时间
    private String EndDay; // 记录了任务截止的日期
    private String MissionSource;//任务发布者
    private String MissionRecord; //任务完成记录

    public String getEndDay() {return EndDay;}
    public String getUserId(){
        return UserId;
    }
    public String getMissionId(){
        return MissionId;
    }
    public String getName(){
        return Name;
    }
    public String getContents(){
        return Contents;
    }
    public String getBeginTime(){
        return BeginTime;
    }
    public String getEndTime(){
        return EndTime;
    }
    public String getGainPoint(){
        return GainPoint;
    }
    public String getLostPoint(){
        return LostPoint;
    }
    public String getRemindWay(){
        return RemindWay;
    }
    public String getProcess(){ return Process;}
    public String getRemindTime(){
        return RemindTime;
    }
    public String getType(){
        return Type;
    }
    public String getUpdateTime(){
        return UpdateTime;
    }
    public String getGroupName(){
        return GroupName;
    }
    public String getIsPrivate(){
        return IsPrivate;
    }
    public String getIsTeamWork(){
        return IsTeamWork;
    }
    public String getRealFinishTime(){
        return RealFinishTime;
    }
    public String getMissionSource(){
        return MissionSource;
    }
    public String getMissionRecord() { return MissionRecord;}
    public BmobUser getAuthor(){
        return Author;
    }

    public void setEndDay(String EndDay) {this.EndDay = EndDay;}
    public void setUserId(String UserId){
        this.UserId = UserId;
    }
    public void setMissionId(String MissionId){
        this.MissionId = MissionId;
    }
    public void setName(String Name){
        this.Name = Name;
    }
    public void setContents(String Contents){
        this.Contents = Contents;
    }
    public void setBeginTime(String BeginTime){
        this.BeginTime = BeginTime;
    }
    public void setEndTime(String EndTime){
        this.EndTime = EndTime;
    }
    public void setGainPoint(String GainPoint){
        this.GainPoint = GainPoint;
    }
    public void setLostPoint(String LostPoint){
        this.LostPoint = LostPoint;
    }
    public void setRemindWay(String RemindWay){
        this.RemindWay = RemindWay;
    }
    public void setProcess(String Process){
        this.Process = Process;
    }
    public void setRemindTime(String RemindTime){
        this.RemindTime = RemindTime;
    }
    public void setType(String Type){
        this.Type = Type;
    }
    public void setUpdateTime(String UpdateTime){
        this.UpdateTime = UpdateTime;
    }
    public void setGroupName(String GroupName){
        this.GroupName = GroupName;
    }
    public void setIsPrivate(String IsPrivate){
        this.IsPrivate = IsPrivate;
    }
    public void setIsTeamWork(String IsTeamWork){
        this.IsTeamWork = IsTeamWork;
    }
    public void setRealFinishTime(String RealFinishTime){
        this.RealFinishTime = RealFinishTime;
    }
    public void setMissionSource(String MissionSource){
        this.MissionSource = MissionSource;
    }
    public void setAuthor(BmobUser Author){
        this.Author = Author;
    }
    public void setMissionRecord(String MissionRecord) { this.MissionRecord = MissionRecord;}
}
