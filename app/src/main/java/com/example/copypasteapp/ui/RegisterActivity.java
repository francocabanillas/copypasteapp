package com.example.copypasteapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.copypasteapp.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
    }

    public void registrar(View v){

        final Intent intent = new Intent(this, LoginActivity.class);

        final EditText clientenombre = (EditText)findViewById(R.id.clientenombre);
        final EditText correo = (EditText)findViewById(R.id.correo);
        final EditText celular = (EditText)findViewById(R.id.celular);
        final EditText clave = (EditText)findViewById(R.id.clave);
        final EditText clave2 = (EditText)findViewById(R.id.clave2);

        String url = "http://copypaste.atwebpages.com/index.php/cliente";

        if (clientenombre.getText().toString().length()==0){
            Toast toast = Toast.makeText(RegisterActivity.this,"Ingrese su nombre", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else if (correo.getText().toString().length()==0){
            Toast toast = Toast.makeText(RegisterActivity.this,"Ingrese su correo", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else if (celular.getText().toString().length()==0){
            Toast toast = Toast.makeText(RegisterActivity.this,"Ingrese su celular", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else if (clave.getText().toString().length()==0){
            Toast toast = Toast.makeText(RegisterActivity.this,"Ingrese su clave", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else if (clave2.getText().toString().length()==0){
            Toast toast = Toast.makeText(RegisterActivity.this,"Repita su clave", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else if (!clave.getText().toString().equals(clave2.getText().toString())) {

            Toast toast = Toast.makeText(RegisterActivity.this,"La contraseña ingresada no coincide", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        }else{

            StringRequest stringRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        Log.i("onResponse", jsonArray.toString());


                            Toast toast = Toast.makeText(RegisterActivity.this,"Ha sido registrado", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            startActivity(intent);

                    } catch (JSONException e) {
                        Log.i("JSONException", e.getMessage());
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("onErrorResponse", error.toString());
                        }
                    }
            ){
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap();
                    params.put("cliente_nombre", clientenombre.getText().toString());
                    params.put("correo", correo.getText().toString());
                    params.put("celular", celular.getText().toString());
                    params.put("clave", clave.getText().toString());
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

}