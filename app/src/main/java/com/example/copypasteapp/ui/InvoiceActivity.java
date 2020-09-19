package com.example.copypasteapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.copypasteapp.Platomenu;
import com.example.copypasteapp.PlatomenuAdapter;
import com.example.copypasteapp.R;
import com.example.copypasteapp.sqlite.DAOException;
import com.example.copypasteapp.sqlite.Pedido;
import com.example.copypasteapp.sqlite.PedidoAdapter;
import com.example.copypasteapp.sqlite.PedidoDAO;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvoiceActivity extends AppCompatActivity {


    private List<Pedido> pedidoList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PedidoAdapter pAdapter;
    public TextView totalizado2;
    public Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        getSupportActionBar().hide();

        totalizado2 = (TextView) findViewById(R.id.totalizado2);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerInvoice);
        btnSave = (Button) findViewById(R.id.btnSave);

        pAdapter = new PedidoAdapter(pedidoList,this.getBaseContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(pAdapter);

        prepareDataInvoice();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarMensaje();
            }
        });
    }

    private void mostrarMensaje(){
        RequestQueue myRequest = Volley.newRequestQueue(getApplicationContext());
        JSONObject json = new JSONObject();

        try {
            String token = "fPhcuOFOpUw:APA91bHwTmYKIuMag3YjM1EDn8eOCv483UQYhOlO-yTJPn2Nv924_1zX2D_817cdmM5AUcTKnAqui_cXinxKO5JTZYZEJO6DJywHTrQbQp0d4MV7CB-tcDOXVawPui-CgQk9Gk31dhjJ";
            json.put("to",token);
            JSONObject notification = new JSONObject();
            notification.put("titulo", "Ha sido confirmado");
            notification.put("detalle","El pedido 15155 está siendo atendido, pronto llegaremos a su domicilio");

            json.put("data", notification);
            String URL = "https://fcm.googleapis.com/fcm/send";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, json,null,null){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> header = new HashMap<>();
                    header.put("content-type","application/json");
                    header.put("authorization","key=AAAAAmVz3II:APA91bHUb-9NUhLopgwcao1Uxj-N4DFsCQcwFSj9Y8sJjeonGVy97LuEiorQH03mv3-Xh7-MgsSShJ4bQotp7vYzGM3rnG_jza5MaGfNBHfQlFPuQqzXBG8WOXbAHd9o_8-EIxHmdf5p");
                    return header;
                }
            };

            myRequest.add(request);

        }catch (JSONException E){
            E.printStackTrace();;
        }
    }

    private void loadListFood(){
        Double total = 0.0;
        PedidoDAO dao = new PedidoDAO(getBaseContext());
        try {
            List<Pedido> list = dao.obtener();
            for (int i = 0; i< list.size(); i++)
            {
                total = total +
                        ( Integer.parseInt(list.get(i).getCantidad()) *
                                Double.parseDouble(list.get(i).getPrecio().replace("S/ ", "")));
            }
            totalizado2.setText("S/ "+String.format("%.2f",total));
            pAdapter.notifyDataSetChanged();

        } catch (DAOException e) {
            Log.i("prepareDataInvoice", "====> " + e.getMessage());
        }
    }

    public void prepareDataInvoice(){
        PedidoDAO dao = new PedidoDAO(getBaseContext());
        try {
            List<Pedido> list = dao.obtener();
            for (int i = 0; i< list.size(); i++)
            {
                pedidoList.add(list.get(i));
            }

            pAdapter.notifyDataSetChanged();

        } catch (DAOException e) {
            Log.i("prepareDataInvoice", "====> " + e.getMessage());
        }

    }
}