package keaononpho.pongsak.calendarproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class Register extends AppCompatActivity {

    //Explicit

    private EditText userEditText,
            passwordEditText,
            idCardEditText, emailEditText;
    private RadioGroup sexRadioGroup;
    private RadioButton maleRadioButton, femaleRadioButton;
    private String userString, passwordString, idCardString, emailString, sexString;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Bind Widget
        bindWidget();

        //ChooseSex
        chooseSex();


    }//MAIN METHOD

    private void chooseSex() {


        sexString = "ชาย";
        sexRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) {

                    case R.id.radioButton:
                        sexString = "ชาย";
                        break;
                    case R.id.radioButton2:
                        sexString = "หญิง";
                        break;
                } //switch

            }// OnChecked
        });
    }//ChooseSex

    public void clickOKRegis(View view) {

        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();
        idCardString = idCardEditText.getText().toString().trim();
        emailString = emailEditText.getText().toString().trim();


        if (checkSpace()) {
            // Have Space
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "มีช่องว่าง",
                    "กรุณากรอกให้ครบทุกช่อง ");


        } else {
            //No Space

            comfirmDialog();
        }// if


    } // clickOK

    private void comfirmDialog() {
        String strSpace = "\n";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.adbook);
        builder.setCancelable(false);
        builder.setTitle("โปรดตรวจสอบข้อมูล");
        builder.setMessage("User = " + userString + strSpace +
                "Password = " + passwordString + strSpace +
                "ID-Card = " + idCardString + strSpace +
                "E-mail = " + emailString + strSpace +
                "Sex = " + sexString);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                updateToServer();

            }//OnClick
        });
        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();

    }

    private void updateToServer() {

        //connected http
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy
                .Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        try {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("isAdd", "true"));
            nameValuePairs.add(new BasicNameValuePair("User", userString));
            nameValuePairs.add(new BasicNameValuePair("Password", passwordString));
            nameValuePairs.add(new BasicNameValuePair("ID_card", idCardString));
            nameValuePairs.add(new BasicNameValuePair("Email", emailString));
            nameValuePairs.add(new BasicNameValuePair("Sex", sexString));

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://swiftcodingthai.com/aee/php_add_user_master.php");
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            httpClient.execute(httpPost);



            Toast.makeText(this, "อัพเดตข้อมูลเรียบร้อย ", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "ไม่สามารถเข้าถึง Server ได้ ", Toast.LENGTH_SHORT).show();
        }


    }//update

    private boolean checkSpace() {

        boolean bolResult = true;

        bolResult = userString.equals("") || passwordString.equals("") ||
                idCardString.equals("") || emailString.equals("");


        return bolResult;
    }


    public void clickBackRegis(View view) {
        finish();
    }


    private void bindWidget() {

        userEditText = (EditText) findViewById(R.id.txtId);
        passwordEditText = (EditText) findViewById(R.id.txtPass);
        idCardEditText = (EditText) findViewById(R.id.txtIdNum);
        emailEditText = (EditText) findViewById(R.id.txtEmail);
        sexRadioGroup = (RadioGroup) findViewById((R.id.ragSex));
        maleRadioButton = (RadioButton) findViewById(R.id.radioButton);
        femaleRadioButton = (RadioButton) findViewById(R.id.radioButton2);


    }//bindwiget
} //MAIN CLASS
