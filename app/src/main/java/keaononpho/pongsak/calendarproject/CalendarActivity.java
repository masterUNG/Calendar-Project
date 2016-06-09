package keaononpho.pongsak.calendarproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

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

        //Select Date
        selectDate();


    }   // Main Method

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

                Intent intent = new Intent(CalendarActivity.this, DetailListView.class);
                intent.putExtra("ID_Card",getIntent().getStringExtra("ID_Card") );
                intent.putExtra("Date", myDateString);
                intent.putExtra("Login", loginStrings);
                startActivity(intent);

            } // onSelected
        });

    }   // selectDate

}   // Main Class
