package keaononpho.pongsak.calendarproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {

    //Explicit
    private CalendarView calendarView;
    private String myDateString;
    private String[] loginStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        loginStrings = getIntent().getStringArrayExtra("Login");

        //Bind Widget
        calendarView = (CalendarView) findViewById(R.id.calendarView);

        calendarView.setShowWeekNumber(false);

        //Find Current Date
        findCurrentDate();

        //Select Date
        selectDate();



    }   // Main Method

    private void findCurrentDate() {

        Calendar calendar = Calendar.getInstance();
        int indDay = calendar.get(Calendar.DAY_OF_MONTH);
        int intMonth = calendar.get(Calendar.MONTH);
        int inYear = calendar.get(Calendar.YEAR);

        myDateString = Integer.toString(indDay) + "/"
                + Integer.toString(intMonth +1) + "/" + Integer.toString(inYear);


    }

    public void clickSelectDateCalendar(View view) {

        Log.d("9JuneV1", "myDate ==> " + myDateString);

    }   // clickSelect


    public void clickBackMyCalendar(View view) {
        finish();
    }

    public void clickDetail(View view) {
        Toast.makeText(this, myDateString, Toast.LENGTH_SHORT).show();
    }

    private void selectDate() {

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {

                myDateString = Integer.toString(day) + "/"
                        + Integer.toString(month +1) + "/" + Integer.toString(year);

            } // onSelected
        });

    }   // selectDate

}   // Main Class
