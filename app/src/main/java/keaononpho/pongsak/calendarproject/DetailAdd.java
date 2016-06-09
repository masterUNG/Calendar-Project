package keaononpho.pongsak.calendarproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class DetailAdd extends AppCompatActivity {

    //Expels
    private TextView idCardTextView, dateTextView;
    private String dateString;
    private String[] loginStrings;
    private EditText editText;
    private TimePicker timePicker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_add);

        //Bind
        idCardTextView = (TextView) findViewById(R.id.textView13);
        dateTextView = (TextView) findViewById(R.id.textView14);
        editText = (EditText) findViewById(R.id.edtDetail);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);


        //Showview
        loginStrings = getIntent().getStringArrayExtra("Login");
        dateString = getIntent().getStringExtra("Date");
        idCardTextView.setText("ID Card  = " + loginStrings[3]);
        dateTextView.setText("Date = " + dateString);




    }//Main Method
    public void clickSaveDetailAdd(View view) {

        int intHour = timePicker.getCurrentHour();
        int intMinus = timePicker.getCurrentMinute();

        Log.d("9JuneV2", "intHour ==> " + intHour);
        Log.d("9JuneV2", "intMinus ==> " + intMinus);


        //finish();
    }   // clickSave

}//Main Class
