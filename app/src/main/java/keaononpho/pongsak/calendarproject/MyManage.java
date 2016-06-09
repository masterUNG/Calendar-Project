package keaononpho.pongsak.calendarproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrator on 7/4/2559.
 */
public class MyManage {

    //Explicit
    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase sqLiteDatabase;

    public static final String user_table = "userTABLE";
    public static final String column_id = "_id";
    public static final String column_User = "User";
    public static final String column_Password = "Password";
    public static final String column_ID_card = "ID_card";
    public static final String column_Email = "Email";
    public static final String column_Sex = "Sex";


    public MyManage(Context context) {

        //create SqLite
        myOpenHelper = new MyOpenHelper(context);
        sqLiteDatabase = myOpenHelper.getWritableDatabase();


    } //Constructor

    public long addUser(String strUser,
                        String strPassword,
                        String strIDcard,
                        String strEmail,
                        String strSex) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(column_User,strUser);
        contentValues.put(column_Password,strPassword);
        contentValues.put(column_ID_card,strIDcard);
        contentValues.put(column_Email,strEmail);
        contentValues.put(column_Sex,strSex);



        return sqLiteDatabase.insert(user_table,null, contentValues);
    }


}//MAIN Class
