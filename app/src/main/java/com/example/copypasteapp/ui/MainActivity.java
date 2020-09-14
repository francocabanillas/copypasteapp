package com.example.copypasteapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.copypasteapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        prepareBebidaData();

    }

    public void AddresActivityClic(View view){
        startActivity(new Intent(this,AddressActivity.class));
    }

    public void ProfileActivityClic(View view){
        startActivity(new Intent(this,ProfileActivity.class));
    }

    public void FoodActivityClic(View view){
        startActivity(new Intent(this,FoodActivity.class));
    }

    private void prepareBebidaData() {
        final TextView bebida = findViewById(R.id.bebida);
        String url = "http://copypaste.atwebpages.com/index.php/bebida";

        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        bebida.setText(object.getString("articulo_nombre"));
                    }

                } catch (JSONException e) {
                    Log.i("Platomenue", e.getMessage());
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("PlatomenuO", error.toString());
                    }
                }
        );
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}