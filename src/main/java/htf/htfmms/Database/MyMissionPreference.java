package htf.htfmms.Database;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import cn.bmob.v3.BmobUser;

/**
 * Created by Lin on 2016/5/29.
 */
public class MyMissionPreference {
    private static final String TAG = MyMissionPreference.class.getSimpleName();

    private Context context;
    private SharedPreferences spf = null;
    private static MyMissionPreference instance;

    private MyMissionPreference() {
    }

    private MyMissionPreference(Context ctx) {
        this.context = ctx;
    }

    public synchronized static MyMissionPreference getInstance(Context ctx) {
        if (instance == null) {
            instance = new MyMissionPreference(ctx);
        }
        return instance;
    }
    public final static String MYPLAN_NAME = "htf.htfmms.mymissionpreference";

    public String getUserId() {
        String userID = "";
        try {
            BmobUser bmobUser = BmobUser.getCurrentUser(context);
            if(bmobUser != null){
                // 允许用户使用应用
                userID=bmobUser.getObjectId();

            }else{
                //缓存用户对象为空时， 可打开用户注册界面…
            }

            Object obj = readObject(context, userID);
            UserInfo userinfo = new UserInfo();

            if (obj != null) {
                userinfo = (UserInfo) obj;
                if (userinfo.objectId.contains(userID) &&
                        !TextUtils.isEmpty(userinfo.objectId)) {
                    userID = userinfo.objectId;
                }
            }
        } catch (Exception ex) {

        }
        return userID;
    }

    public String getUsername() {
        String userName = "";

        try {
            String userId = getUserId();
            Object obj = readObject(context, userId);
            UserInfo userinfo = new UserInfo();

            if (obj != null) {
                userinfo = (UserInfo) obj;
                if (userinfo.objectId.contains(userId) &&
                        !TextUtils.isEmpty(userinfo.userName)) {
                    userName = userinfo.userName;
                }
            }
        } catch (Exception ex) {

        }
        return userName;
    }

    public void setUsername(String username) {
        try {
            String userId = getUserId();
            Object obj = readObject(context, userId);
            UserInfo userinfo = new UserInfo();

            if (obj != null) {
                userinfo = (UserInfo) obj;
                if (userinfo.objectId.contains(userId)) {
                    userinfo.userName=username;
                    saveObject(context,userId,obj);
                }
            }
        }catch (Exception ex){

        }
    }

    public String getUpdatetime() {
        String updateTime = "";

        try {
            String userId = getUserId();
            Object obj = readObject(context, userId);
            UserInfo userinfo = new UserInfo();

            if (obj != null) {
                userinfo = (UserInfo) obj;
                if (userinfo.objectId.contains(userId) &&
                        !TextUtils.isEmpty(userinfo.updatedTime)) {
                    updateTime = userinfo.updatedTime;
                }
            }
        } catch (Exception ex) {

        }
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        try {
            String userId = getUserId();
            Object obj = readObject(context, userId);
            UserInfo userinfo = new UserInfo();

            if (obj != null) {
                userinfo = (UserInfo) obj;
                if (userinfo.objectId.contains(userId)) {
                    userinfo.updatedTime=updateTime;
                    saveObject(context,userId,obj);
                }
            }
        }catch (Exception ex){

        }

    }

    public void removeObject(Context context,String key){

        try {
            // 保存对象
            SharedPreferences.Editor sharedata = context.getSharedPreferences(MYPLAN_NAME, 0).edit();
            //先将序列化结果写到byte缓存中，其实就分配一个内存空间
            sharedata.remove(key);
            sharedata.commit();
        } catch (Exception e) {
            e.printStackTrace();
//			Log.e("", "保存obj失败");
        }
    }

    public void saveObject(Context context,String key ,Object obj){
        try {
            // 保存对象
            SharedPreferences.Editor sharedata = context.getSharedPreferences(MYPLAN_NAME, 0).edit();
            //先将序列化结果写到byte缓存中，其实就分配一个内存空间
            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            ObjectOutputStream os=new ObjectOutputStream(bos);
            //将对象序列化写入byte缓存
            os.writeObject(obj);
            //将序列化的数据转为16进制保存
            String bytesToHexString = bytesToHexString(bos.toByteArray());
            //保存该16进制数组
            sharedata.putString(key, bytesToHexString);
            sharedata.commit();
        } catch (IOException e) {
            e.printStackTrace();
//			Log.e("", "保存obj失败");
        }
    }

    private String bytesToHexString(byte[] bArray) {
        if(bArray == null){
            return null;
        }
        if(bArray.length == 0){
            return "";
        }
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    private Object readObject(Context context,String key ){
        try {
            SharedPreferences sharedata = context.getSharedPreferences(MYPLAN_NAME, 0);
            if (sharedata.contains(key)) {
                String string = sharedata.getString(key, "");
                if(TextUtils.isEmpty(string)){
                    return null;
                }else{
                    //将16进制的数据转为数组，准备反序列化
                    byte[] stringToBytes = StringToBytes(string);
                    ByteArrayInputStream bis=new ByteArrayInputStream(stringToBytes);
                    ObjectInputStream is=new ObjectInputStream(bis);
                    //返回反序列化得到的对象
                    Object readObject = is.readObject();
                    return readObject;
                }
            }
        } catch (StreamCorruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //所有异常返回null
        return null;

    }

    private byte[] StringToBytes(String data){
        String hexString=data.toUpperCase().trim();
        if (hexString.length()%2!=0) {
            return null;
        }
        byte[] retData=new byte[hexString.length()/2];
        for(int i=0;i<hexString.length();i++)
        {
            int int_ch;  // 两位16进制数转化后的10进制数
            char hex_char1 = hexString.charAt(i); ////两位16进制数中的第一位(高位*16)
            int int_ch1;
            if(hex_char1 >= '0' && hex_char1 <='9')
                int_ch1 = (hex_char1-48)*16;   //// 0 的Ascll - 48
            else if(hex_char1 >= 'A' && hex_char1 <='F')
                int_ch1 = (hex_char1-55)*16; //// A 的Ascll - 65
            else
                return null;
            i++;
            char hex_char2 = hexString.charAt(i); ///两位16进制数中的第二位(低位)
            int int_ch2;
            if(hex_char2 >= '0' && hex_char2 <='9')
                int_ch2 = (hex_char2-48); //// 0 的Ascll - 48
            else if(hex_char2 >= 'A' && hex_char2 <='F')
                int_ch2 = hex_char2-55; //// A 的Ascll - 65
            else
                return null;
            int_ch = int_ch1+int_ch2;
            retData[i/2]=(byte) int_ch;//将转化后的数放入Byte里
        }
        return retData;
    }
}
