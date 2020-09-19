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
import com.example.copypasteapp.list.Clientepedido;
import com.example.copypasteapp.list.ClientepedidoAdapter;
import com.example.copypasteapp.list.Platomenu;
import com.example.copypasteapp.list.PlatomenuAdapter;
import com.example.copypasteapp.sharedpreference.Sharedpreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListinvoiceActivity extends AppCompatActivity {

    private List<Clientepedido> clientepedidoList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ClientepedidoAdapter pAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listinvoice);
        getSupportActionBar().hide();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerlistinvoice);

        pAdapter = new ClientepedidoAdapter(clientepedidoList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(pAdapter);


        preparePedidoData();
    }

    private void preparePedidoData() {
        Clientepedido clientepedido;
        Sharedpreference sharedpreference = new Sharedpreference();
        String url = "http://copypaste.atwebpages.com/index.php/pedido/cliente/"+sharedpreference.readIdCliente(this);

        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        //Clientepedido(int id, String numeropedido, String fecha, String estado, String preciototal)
                        clientepedidoList.add(new
                                Clientepedido(object.getInt("id") ,
                                              object.getString("id"),
                                            object.getString("fecha"),
                                            object.getString("estado") ,
                                            object.getString("precio_total")));
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