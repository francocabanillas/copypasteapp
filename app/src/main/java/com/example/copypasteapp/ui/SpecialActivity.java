package com.example.copypasteapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.copypasteapp.R;
import com.example.copypasteapp.list.Platomenu;
import com.example.copypasteapp.list.PlatomenuAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SpecialActivity extends AppCompatActivity {

    private List<Platomenu> platomenuList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PlatomenuAdapter pAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special);

        getSupportActionBar().hide();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerspecial);

        pAdapter = new PlatomenuAdapter(platomenuList,false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(pAdapter);


        preparePlatomenuData();
    }

    private void preparePlatomenuData() {
        Platomenu platomenu;

        String url = "http://copypaste.atwebpages.com/index.php/carta";

        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        platomenuList.add(new Platomenu(object.getInt("id") , object.getString("id"),object.getString("articulo_nombre") , object.getString("categoria_nombre"),object.getString("precio"),object.getString("imagen_url")));
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
}