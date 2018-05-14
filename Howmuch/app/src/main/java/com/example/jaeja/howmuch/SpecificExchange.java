package com.example.jaeja.howmuch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by jaeja on 2018-01-10.
 */

public class SpecificExchange extends AppCompatActivity {

    private Spinner baseSpinner, exchangeSpinner;
    private EditText amountEntered;
    private TextView exchangedView;
    double enteredValue;
    String selectedCurrency, originCurrency;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.specific_change);

        baseSpinner = findViewById(R.id.base_spinner);
        exchangeSpinner = findViewById(R.id.exchang_spinner);
        amountEntered = findViewById(R.id.editText);
        exchangedView = findViewById(R.id.exchanged_specific);




        amountEntered.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                enteredValue = Double.parseDouble(amountEntered.getText().toString());
                networkingProcess(selectedCurrency, originCurrency);

                return false;
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array2, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        baseSpinner.setAdapter(adapter);
        exchangeSpinner.setAdapter(adapter);

        baseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                originCurrency = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        exchangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCurrency = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void networkingProcess(final String selectedCurrency, final String originCurrency){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(MainActivity.BASE_URL +originCurrency, new JsonHttpResponseHandler(){


            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                Log.d("How_much", response.toString());
                DataHandler dataHandler = DataHandler.fromJson(response, selectedCurrency);
                double temp = Double.parseDouble(dataHandler.getValue()) * enteredValue;

                exchangedView.setText(String.valueOf(temp));
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
