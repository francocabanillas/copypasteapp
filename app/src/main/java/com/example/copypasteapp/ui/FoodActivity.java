package com.example.copypasteapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.copypasteapp.list.Platomenu;
import com.example.copypasteapp.list.PlatomenuAdapter;
import com.example.copypasteapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FoodActivity extends AppCompatActivity {

    private List<Platomenu> platomenuList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PlatomenuAdapter pAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        getSupportActionBar().hide();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerfood);

        pAdapter = new PlatomenuAdapter(platomenuList,true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(pAdapter);


        prepareEntradaData();
        prepareBebidaData();
        preparePlatomenuData();
    }

    private void preparePlatomenuData() {
        Platomenu platomenu;

        String url = "http://copypaste.atwebpages.com/index.php/menus";

        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        platomenuList.add(new Platomenu(object.getInt("id") , object.getString("id"),object.getString("articulo_nombre") , object.getString("categoria_nombre"),"S/ 8.00",object.getString("imagen_url")));
                        pAdapter.notifyDataSetChanged();
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

    private void prepareEntradaData() {
        final TextView entrada = findViewById(R.id.entrada);
        String url = "http://copypaste.atwebpages.com/index.php/entrada";

        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        entrada.setText(object.getString("articulo_nombre"));
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