package com.example.copypasteapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.copypasteapp.R;
import com.example.copypasteapp.sharedpreference.Sharedpreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();
        prepareInfoData();
    }

    private void prepareInfoData() {
        final TextView cliente_nombre2 = findViewById(R.id.cliente_nombre2);
        final TextView correo2 = findViewById(R.id.correo2);
        final TextView celular2 = findViewById(R.id.celular2);

        Sharedpreference preference = new Sharedpreference();
        String cadena = preference.readIdCliente(this);
        String url = "http://copypaste.atwebpages.com/index.php/cliente/"+cadena;

        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        cliente_nombre2.setText(object.getString("cliente_nombre"));;
                        correo2.setText(object.getString("correo"));;
                        celular2.setText(object.getString("celular"));;
                    }

                } catch (JSONException e) {
                    Log.i("prepareI", e.getMessage());
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("prepareIn", error.toString());
                    }
                }
        );
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void MainActivityClic(View view){

        final TextView cliente_nombre2 = findViewById(R.id.cliente_nombre2);
        Sharedpreference preference = new Sharedpreference();
        String cadena = preference.readIdCliente(this);
        String url = "http://copypaste.atwebpages.com/index.php/cliente/"+cadena;

        StringRequest stringRequest= new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    Log.i("prepareI", jsonArray.toString());
                    Toast toast = Toast.makeText(ProfileActivity.this,"Ha sido actualizado", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                } catch (JSONException e) {
                    Log.i("prepareI", e.getMessage());
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("prepareIn", error.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap();
                params.put("cliente_nombre", cliente_nombre2.getText().toString());
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}