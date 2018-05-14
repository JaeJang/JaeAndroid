package com.example.jaeja.howmuch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpRequest;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    final static String BASE_URL = "https://api.fixer.io/latest?base=";
    Spinner spinner;
    TextView exchanged;
    ImageButton changeLayoutToSpecific;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner);
        exchanged = findViewById(R.id.exchange);
        changeLayoutToSpecific = findViewById(R.id.change_layout);

        changeLayoutToSpecific.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent specificLayout = new Intent(MainActivity.this, SpecificExchange.class);
                if(specificLayout != null)
                    Log.d("How_much", "specificLayout intent is not null");
                startActivity(specificLayout);
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array1, R.layout.spinner_item);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCurrency = parent.getItemAtPosition(position).toString();
                networkingProcess(selectedCurrency ,"KRW");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void networkingProcess(String selectedCurrency, final String originCurrency){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(BASE_URL +  selectedCurrency, new JsonHttpResponseHandler(){


           public void onSuccess(int statusCode, Header[] headers, JSONObject response){
               Log.d("How_much", response.toString());
                DataHandler dataHandler = DataHandler.fromJson(response, originCurrency);
                exchanged.setText(dataHandler.getValue());
                //Log.d("How_much", dataHandler.getKrw());
           }

           public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response){
               // called when response HTTP status is "4XX" (eg. 401, 403, 404)
               Log.d("ticker", "Request fail! Status code: " + statusCode);
               Log.d("ticker", "Fail response: " + response);
               Log.e("ERROR", e.toString());
               Toast.makeText(getApplicationContext(), "Request Failed", Toast.LENGTH_SHORT).show();
           }
        });
    }
}
