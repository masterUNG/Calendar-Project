package keaononpho.pongsak.calendarproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
    private String idcardString, dateString, timeString, detailString;
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
        idcardString = loginStrings[3];
        idCardTextView.setText("ID Card  = " + loginStrings[3]);
        dateTextView.setText("Date = " + dateString);




    }//Main Method
    public void clickSaveDetailAdd(View view) {

        int intHour = timePicker.getCurrentHour();
        int intMinus = timePicker.getCurrentMinute();
        timeString = Integer.toString(intHour) + ":" + Integer.toString(intMinus);

        Log.d("9JuneV2", "intHour ==> " + intHour);
        Log.d("9JuneV2", "intMinus ==> " + intMinus);

        detailString = editText.getText().toString().trim();
        if (detailString.equals("")) {
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "มีช่องว่าง", "กรุณากรอก ทุกช่องคะ");
        } else {
            confirmAnUpload();
        }


        //finish();
    }   // clickSave

    private void confirmAnUpload() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.doremon48);
        builder.setCancelable(false);
        builder.setTitle("โปรตรวจสองข้อมูล");
        builder.setMessage("id Card = " + idcardString + "\n" +
        "Date = " + dateString + "\n" +
        "Time = " + timeString + "\n" +
        "Detail = " + detailString);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("Save to Server", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                uploadToServer();
                dialogInterface.dismiss();
            }
        });
        builder.show();


    }   // confirm

    private void uploadToServer() {

    }   // upload

}//Main Class
