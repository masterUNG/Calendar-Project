package keaononpho.pongsak.calendarproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

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

public class DetailListView extends AppCompatActivity {

    //Expils
    private TextView idCardTextView, dateTextView;
    private ListView listView;
    private String idCardString, dateString;
    private String[] loginStrings;
    private static final String urlJSON = "http://swiftcodingthai.com/aee/get_detail_where.php";

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

        //Create ListView
        createListView();

    }//Main Method

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.d("8JuneV3", "onRestart Work");
        createListView();

    }

    private void createListView() {

        try {

            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("isAdd", "true")
                    .add("ID_Card", idCardString)
                    .build();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(urlJSON).post(requestBody).build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    Log.d("9JuneV3", "Error call ==> " + e.toString());
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    String strJSON = response.body().string();
                    Log.d("9JuneV3", "strJSON ==> " + strJSON);

                    try {

                        JSONArray jsonArray = new JSONArray(strJSON);

                        String[] timeStrings = new String[jsonArray.length()];
                        String[] detailStrings = new String[jsonArray.length()];

                        for (int i=0;i<jsonArray.length();i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            timeStrings[i] = jsonObject.getString("Time");
                            detailStrings[i] = jsonObject.getString("Detail");

                        }   // for
                        MyAdapter myAdapter = new MyAdapter(DetailListView.this,
                                timeStrings, detailStrings);
                        listView.setAdapter(myAdapter);


                    } catch (Exception e) {
                        Log.d("9JuneV3", "Error JSONarray ==> " + e.toString());
                    }


                }   // onResponse
            });


        } catch (Exception e) {
            Log.d("9JuneV3", "Error From ListView ==> " + e.toString());
        }


    }   // createListView


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
