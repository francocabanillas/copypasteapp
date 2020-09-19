package com.example.copypasteapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.copypasteapp.sharedpreference.Sharedpreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        prepareInfoData();
    }

    public void AddresActivityClic(View view){
        startActivity(new Intent(this,AddressActivity.class));
    }

    public void InvoiceActivityClic(View view){
        startActivity(new Intent(this,InvoiceActivity.class));
    }

    public void ProfileActivityClic(View view){
        startActivity(new Intent(this,ProfileActivity.class));
    }

    public void FoodActivityClic(View view){
        startActivity(new Intent(this,FoodActivity.class));
    }

    private void prepareInfoData() {
        final TextView nombre_usuario = findViewById(R.id.nombre_usuario);
        final TextView nombre_direccion = findViewById(R.id.nombre_direccion);

        final Sharedpreference preference = new Sharedpreference();

        String cadena = preference.readIdCliente(this);

        String url = "http://copypaste.atwebpages.com/index.php/cliente/"+cadena;

        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        nombre_usuario.setText("Hola, "+object.getString("cliente_nombre"));;
                        if (object.getString("direccion_id")!=null) {
                            preference.saveIdDireccion(object.getString("direccion_id"),getApplicationContext());
                            preference.saveLatitud(object.getString("latitud"),getApplicationContext());
                            preference.saveLongitud(object.getString("longitud"),getApplicationContext());
                            nombre_direccion.setText(object.getString("nombre_direccion")+ " ▼");
                        }
                        else
                        {
                            nombre_direccion.setText("Registre su dirección");
                        }
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
}