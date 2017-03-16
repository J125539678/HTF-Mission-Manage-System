package htf.htfmms.Database;
import android.database.sqlite.SQLiteDatabase;
/**
 * Created by Lin on 2016/5/16.
 */
public interface HTF_Table {
    void onCreate(SQLiteDatabase db);
    void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion);
}
