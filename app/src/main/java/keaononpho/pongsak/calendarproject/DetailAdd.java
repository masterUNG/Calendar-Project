package keaononpho.pongsak.calendarproject;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

public class DetailAdd extends AppCompatActivity {

    //Expels
    private TextView idCardTextView, dateTextView;
    private String idCardString, dateString;

    TimePicker myTimePicker;
    Button buttonstartSetDialog;
    private ListView listAlarm;
    public static ArrayList<String> listValue;

    TimePickerDialog timePickerDialog;

    final static int RQS_1 = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_add);

        //Bind
        idCardTextView = (TextView) findViewById(R.id.textView13);
        dateTextView = (TextView) findViewById(R.id.textView14);


        //Showview
        idCardString = getIntent().getStringExtra("ID_Card");
        dateString = getIntent().getStringExtra("Date");
        idCardTextView.setText("ID Card  = " + idCardString);
        dateTextView.setText("Date = " + dateString);


        listAlarm = (ListView)findViewById(R.id.listView1);
        listValue = new ArrayList<String>();

        buttonstartSetDialog = (Button)findViewById(R.id.startSetDialog);
        buttonstartSetDialog.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //openTimePickerDialog(true);
                showTimePickerDialog(buttonstartSetDialog);
            }});


    }//Main Method
    public void clickBalckDetailList1(View view) {
        finish();
    }
    public void clickSetCalendar(View view) {
        startActivity(new Intent(DetailAdd.this, SetCalendarActivity.class));
    }
    public void clickCalendar(View view) {

        String tag = "18April";

        Calendar calendar = Calendar.getInstance();
        int intDay = calendar.get(Calendar.DAY_OF_MONTH);
        int intMonth = calendar.get(Calendar.MONTH);
        int intMinus = calendar.get(Calendar.MINUTE);

        Calendar myCalendar1 = (Calendar) calendar.clone();

        Log.d(tag, "intDay ==> " + intDay);
        Log.d(tag, "intMonth ==> " + intMonth);

        intDay += 1;
        intMinus += 2;

        myCalendar1.set(Calendar.DAY_OF_MONTH, intDay);
        myCalendar1.set(Calendar.MINUTE, intMinus);

        Log.d(tag, "myCalendar1 ==> " + myCalendar1.toString());

        setAlarm(myCalendar1);

    }   // clickCalendar


    public void showTimePickerDialog(View v) {
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }   // showTimePickerDialog

    class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        int callCount = 0;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            if(callCount==0){
                // Do something with the time chosen by the user
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal.set(Calendar.MINUTE, minute);

                Log.d("18April", "cal ==> " + cal.toString());

                setAlarm(cal);
            }
            callCount++;
        }
    }

    private void setAlarm(Calendar targetCal){

        listValue.add(targetCal.getTime()+"");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listValue);
        listAlarm.setAdapter(adapter);

        final int _id = (int) System.currentTimeMillis();

        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), _id, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);

    }   // setAlarm

    @Override
    protected void onResume() {
        super.onResume();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listValue);
        listAlarm.setAdapter(adapter);
    }   // onResume


}//Main Class
