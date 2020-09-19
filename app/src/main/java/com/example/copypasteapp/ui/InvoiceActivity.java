package com.example.copypasteapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.copypasteapp.R;
import com.example.copypasteapp.sharedpreference.Sharedpreference;
import com.example.copypasteapp.sqlite.DAOException;
import com.example.copypasteapp.sqlite.OnEditTextChanged;
import com.example.copypasteapp.sqlite.Pedido;
import com.example.copypasteapp.sqlite.PedidoAdapter;
import com.example.copypasteapp.sqlite.PedidoDAO;

import org.json.JSONArray;
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

    public double[] enteredNumber = new double[1000];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        getSupportActionBar().hide();

        totalizado2 = (TextView) findViewById(R.id.totalizado2);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerInvoice);
        btnSave = (Button) findViewById(R.id.btnSave);



        pAdapter = new PedidoAdapter(pedidoList, new OnEditTextChanged() {
            @Override
            public void onTextChanged(int position, String charSequence) {
                Log.i("TagC","posicion"+position+" "+charSequence);
                enteredNumber[position]= Double.valueOf(charSequence.replace("S/ ", ""));
                updateTotalValue();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(pAdapter);

        prepareDataInvoice();


        updateTotalValue();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        prepareDataInvoice();

        updateTotalValue();
    }

    private Double updateTotalValue(){
        Double sum=0.0;
        for (int i= 0; i<1000; i++) {
            sum += enteredNumber[i];
        }

        totalizado2.setText("Total S/ "+String.format("%.2f",sum));

        return sum;
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

    public void guardarPedido(View view) {
        if (pedidoList.size()==0) {
            Toast toast = Toast.makeText(InvoiceActivity.this,"Para confirmar, ingrese su pedido", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            guardarPedido();
            limpiarSQL();
            prepareDataInvoice();
        }
    }

    private void guardarPedido(){
        final String id = "";
        final Double sum = updateTotalValue();
        Sharedpreference sharedpreference = new Sharedpreference();
        final String idDireccion = sharedpreference.readIdDireccion(this);
        String cadena = sharedpreference.readIdCliente(this);
        String url = "http://copypaste.atwebpages.com/index.php/pedido/"+cadena;

        StringRequest stringRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length()>0){
                        for (int i=0; i<jsonArray.length(); i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            Log.i("Cabecera id", object.getString("id"));
                            guardarDetalle(object.getString("id"));
                            mostrarMensaje(object.getString("id"));
                        }
                    }

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
                params.put("cliente_direccion_id", idDireccion);
                params.put("precio_total", String.valueOf(sum));
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

    private void guardarDetalle(String id){
        List<Pedido> list = pedidoList;
        for (int i = 0; i< list.size(); i++)
        {
            guardadoDetalleCampos(id,list.get(i).getArticulo_id(),list.get(i).getCantidad(),list.get(i).getPrecio().replace("S/ ", ""));
            Log.i("Detalle id", ""+(i+1));
        }
    }

    public void guardadoDetalleCampos(String id,String articulo_id, String cantidad, String precio) {
        final String articuloId = articulo_id;
        final String tCantidad = cantidad;
        final String tPrecio = precio;
        String url = "http://copypaste.atwebpages.com/index.php/detalle/"+id;
        StringRequest stringRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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
                params.put("articulo_id", articuloId);
                params.put("cantidad", tCantidad);
                params.put("precio", tPrecio);
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

    public void limpiarSQL(){
        PedidoDAO dao = new PedidoDAO(this);
        try {
            dao.eliminarTodos();
        } catch (DAOException e) {
            Log.i("prepareDataInvoice", "====> " + e.getMessage());
        }
    }

    private void mostrarMensaje(String id){
        RequestQueue myRequest = Volley.newRequestQueue(getApplicationContext());
        JSONObject json = new JSONObject();

        try {
            Sharedpreference preference = new Sharedpreference();
            json.put("to",preference.readToken(this));
            JSONObject notification = new JSONObject();
            notification.put("titulo", "Pedido registrado - N° "+ id);
            notification.put("detalle","Gracias por su compra, el delivery llegará en 30 minutos");

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
}