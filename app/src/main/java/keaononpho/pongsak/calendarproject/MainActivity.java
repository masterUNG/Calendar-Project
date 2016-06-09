package keaononpho.pongsak.calendarproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.Voice;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    //Explici
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;
    private MyManage myManage;
    public static ArrayList<String> listValue;
    private int dayAnInt, monthAnInt, yearAnInt, hrAnInt, minusAnInt;
    private int month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind Widget
        bindWidget();

        listValue = new ArrayList<String>();

        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int hr = calendar.get(Calendar.HOUR_OF_DAY);
        int minius = calendar.get(Calendar.MINUTE) + 1;

        //setupDateAnTimeforAlarm(day, month, year, hr, minius);

        //alarmByMyData();


        //Request SQLite
        myManage = new MyManage(this);

        //Test add User
        //myManage.addUser("user", "password", "idcard", "email", "sex");

        //Delete All SQLite
        deleteAllSQLite();


        Button btnRegister;


        // การทำปุ่มไป หน้า อื่น
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent go = new Intent(getApplicationContext(), Register.class);
                startActivity(go);

                // การทำเสียง Effect คลิก
                MediaPlayer buttonMediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.btnck1);
                buttonMediaPlayer.start();

            }
        });


        //Syn JSON to SQLite
        synJSONtoSQLite();

    }   // Main Method

    private void alarmByMyData(String resultString) {

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("ID_Card", resultString)
                .build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url("http://swiftcodingthai.com/aee/get_detail_where.php").post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

                String strJSON = response.body().string();
                Log.d("9JuneV5", "strJSON ==> " + strJSON);
                try {

                    JSONArray jsonArray = new JSONArray(strJSON);

                    for (int i=0;i<jsonArray.length();i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String[] myDate = jsonObject.getString("Date").split("/");
                        String[] myTime = jsonObject.getString("Time").split(":");
                        int myMonth = Integer.parseInt(myDate[1]) - 1;
                        int myDay = Integer.parseInt(myDate[0]);
                        yearAnInt = Integer.parseInt(myDate[2]);
                        hrAnInt = Integer.parseInt(myTime[0]);
                        minusAnInt = Integer.parseInt(myTime[1]);

                        if (myMonth == month) {
                            if (myDay >= day) {
                                dayAnInt = myDay;
                                monthAnInt = myMonth;
                                setupDateAnTimeforAlarm(dayAnInt, monthAnInt,
                                        yearAnInt, hrAnInt, minusAnInt);
                            }
                        } else if (myMonth > month) {
                            dayAnInt = myDay;
                            monthAnInt = myMonth;
                            setupDateAnTimeforAlarm(dayAnInt, monthAnInt,
                                    yearAnInt, hrAnInt, minusAnInt);
                        }


                    } // for


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });



    }   // alarm

    private void setupDateAnTimeforAlarm(int intDay,
                                         int intMonth,
                                         int intYear,
                                         int intHr,
                                         int intMinis) {

        Calendar calendar = Calendar.getInstance();

        Log.d("9JuneV4", "calendar ตั่งต้น ==> " + calendar.getTime().toString());

        calendar.set(Calendar.DAY_OF_MONTH, intDay);
        calendar.set(Calendar.MONTH, intMonth);
        calendar.set(Calendar.YEAR, intYear);
        calendar.set(Calendar.HOUR_OF_DAY, intHr);
        calendar.set(Calendar.MINUTE, intMinis);
        calendar.set(Calendar.SECOND, 0);

        Log.d("9JuneV4", "calendar ตัวส่ง ==> " + calendar.getTime().toString());

        setAlarm(calendar);


    }


    private void setAlarm(Calendar targetCal){

        listValue.add(targetCal.getTime()+"");



        final int _id = (int) System.currentTimeMillis();

        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), _id, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);

    }   // setAlarm

    @Override
    protected void onResume() {
        super.onResume();

    }   // onResume


    @Override
    protected void onRestart() {
        super.onRestart();
        deleteAllSQLite();
        synJSONtoSQLite();
    }

    private void synJSONtoSQLite() {
        MyConnected myConnected = new MyConnected();
        myConnected.execute();
    }

    public class MyConnected extends AsyncTask<Void, Voice, String> {

        @Override
        protected String doInBackground(Void... voids) {

            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url("http://swiftcodingthai.com/aee/php_get_user.php").build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                Log.d("test", "Error Connected ==> " + e.toString());
                return null;
            }

        }   // doInBack

        @Override
        protected void onPostExecute(String strJSON) {
            super.onPostExecute(strJSON);

            Log.d("7April", "strJSON ==> " + strJSON);

            try {

                JSONArray jsonArray = new JSONArray(strJSON);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String strUser = jsonObject.getString(MyManage.column_User);
                    String strPassword = jsonObject.getString(MyManage.column_Password);
                    String strIDcard = jsonObject.getString(MyManage.column_ID_card);
                    String strEmail = jsonObject.getString(MyManage.column_Email);
                    String strSex = jsonObject.getString(MyManage.column_Sex);

                    myManage.addUser(strUser, strPassword, strIDcard, strEmail, strSex);

                }   // for

            } catch (Exception e) {
                Log.d("test", "JSON Connected ==> " + e.toString());
            }

        }   // onPost

    }   // MyConnected Class



    private void deleteAllSQLite() {
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        sqLiteDatabase.delete(MyManage.user_table, null, null);
    }

    public void clickLoginMain(View view) {

        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        //Check Space
        if (userString.equals("") || passwordString.equals("")) {
            //Have Space
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "มีช่องว่าง",
                    "กรุณากรอกทุกช่อง");
        } else {
            //No Space
            checkUser();


        } // if

    }    // clickLogin

    private void checkUser() {

        try {

            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                    MODE_PRIVATE, null);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM userTABLE WHERE User = " + "'" + userString + "'", null);
            cursor.moveToFirst();
            String[] resultStrings = new String[cursor.getColumnCount()];
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                resultStrings[i] = cursor.getString(i);
            }   // for
            cursor.close();

            //Check Password
            if (passwordString.equals(resultStrings[2])) {
                //Password True
                Toast.makeText(this, "ยินดีต้อนรับ [24] Calendar",
                        Toast.LENGTH_SHORT).show();

                alarmByMyData(resultStrings[3]);

                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                intent.putExtra("ID_Card", resultStrings[3]);
                intent.putExtra("Login", resultStrings);
                startActivity(intent);

               // finish();

            } else {
                //Password False
                MyAlert myAlert = new MyAlert();
                myAlert.myDialog(this, "Password ผิด",
                        "กรุณาพิมพ์ Password ใหม่ Password ผิด");

            }

        } catch (Exception e) {
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "ไม่มี User นี้"  ,
                    "ไม่มี " + userString + " ในฐานข้อมูลของเรา");
        }

    }   // checkUser


    private void bindWidget() {

        userEditText = (EditText) findViewById(R.id.txtIdLogin);
        passwordEditText = (EditText) findViewById(R.id.txtPassLogin);

    }

}// MAIN CLASS
