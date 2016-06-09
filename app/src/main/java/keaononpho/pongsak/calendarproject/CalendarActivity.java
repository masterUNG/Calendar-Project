package keaononpho.pongsak.calendarproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.doremon48);
        builder.setCancelable(false);
        builder.setTitle("Confirm Date ?");
        builder.setMessage("คุณต้องการกำหนด รายละเอียด ของวันที่ " + myDateString + " จริงๆ หรือ ?");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(CalendarActivity.this, DetailListView.class);
                intent.putExtra("Login", loginStrings);
                intent.putExtra("Date", myDateString);
                startActivity(intent);

                dialogInterface.dismiss();
            }
        });
        builder.show();

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
