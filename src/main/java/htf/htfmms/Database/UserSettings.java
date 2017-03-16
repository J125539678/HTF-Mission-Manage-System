package htf.htfmms.Database;

import cn.bmob.v3.BmobObject;

/**
 * Created by Lin on 2016/5/29.
 */
public class UserSettings extends BmobObject {//只能继承BmobObject，否则无法查询数据
    public String userObjectId;
    public String isAutoSync;
    public String createdTime;
    public String updatedTime;
    public String syncTime;//
}
