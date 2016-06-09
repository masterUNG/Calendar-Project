package keaononpho.pongsak.calendarproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class DetailAdd extends AppCompatActivity {

    //Expels
    private TextView idCardTextView, dateTextView;
    private String dateString;
    private String[] loginStrings;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_add);

        //Bind
        idCardTextView = (TextView) findViewById(R.id.textView13);
        dateTextView = (TextView) findViewById(R.id.textView14);


        //Showview
        loginStrings = getIntent().getStringArrayExtra("Login");
        dateString = getIntent().getStringExtra("Date");
        idCardTextView.setText("ID Card  = " + loginStrings[3]);
        dateTextView.setText("Date = " + dateString);




    }//Main Method
    public void clickSaveDetailAdd(View view) {
        finish();
    }

}//Main Class
