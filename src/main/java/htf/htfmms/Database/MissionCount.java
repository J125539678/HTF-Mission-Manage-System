package htf.htfmms.Database;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * Created by Lin on 2016/6/10.
 */
public class MissionCount extends BmobObject {
    private String Date;//日期
    private BmobUser Author;//任务创建者
    private String GainPointToday;//今日获得积分
    private String AccomplishToday;//今日完成任务数
    private String FailToday;//今日失败任务数

    public String getDate(){
        return Date;
    }
    public String getGainPointToday(){
        return GainPointToday;
    }
    public String getAccomplishToday(){
        return AccomplishToday;
    }
    public String getFailToday(){
        return FailToday;
    }
    public BmobUser getAuthor(){
        return Author;
    }

    public void setDate(String Date){
        this.Date = Date;
    }
    public void setGainPointToday(String GainPointToday){
        this.GainPointToday = GainPointToday;
    }
    public void setAccomplishToday(String AccomplishToday){
        this.AccomplishToday = AccomplishToday;
    }
    public void setFailToday(String FailToday){
        this.FailToday = FailToday;
    }
    public void setAuthor(BmobUser Author){
        this.Author = Author;
    }
}
