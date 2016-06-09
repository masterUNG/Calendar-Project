package keaononpho.pongsak.calendarproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class DetailListView extends AppCompatActivity {

    //Expils
    private TextView idCardTextView, dateTextView;
    private ListView listView;
    private String idCardString, dateString;
    private String[] loginStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list_view);

        //Bind
        idCardTextView = (TextView) findViewById(R.id.textView11);
        dateTextView = (TextView) findViewById(R.id.textView12);
        listView = (ListView) findViewById(R.id.listView);


        //Showview
        loginStrings = getIntent().getStringArrayExtra("Login");
        idCardString = loginStrings[3];
        dateString = getIntent().getStringExtra("Date");
        idCardTextView.setText("ID Card  = " +idCardString);
        dateTextView.setText("Date = " + dateString);




    }//Main Method


    public void clickAddDetail(View view) {
        Intent intent = new Intent(DetailListView.this, DetailAdd.class);
        intent.putExtra("Login", loginStrings);
        intent.putExtra("Date", dateString);
        startActivity(intent);

    }

    public void clickBalckDetailList(View   view) {
        finish();
    }
}//Main class
