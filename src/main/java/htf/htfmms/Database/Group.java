package htf.htfmms.Database;

import cn.bmob.v3.BmobObject;

/**
 * Created by Lin on 2016/6/20.
 */
public class Group extends BmobObject {
    private String Name;//任务名称
    private String Number;//任务创建者
    public String getName(){
        return Name;
    }
    public String getNumber(){
        return Number;
    }

    public void setName(String Name){
        this.Name = Name;
    }
    public void setNumber(String Number){
        this.Number = Number;
    }
}
