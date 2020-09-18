package com.example.copypasteapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.copypasteapp.Platomenu;
import com.example.copypasteapp.PlatomenuAdapter;
import com.example.copypasteapp.R;
import com.example.copypasteapp.sqlite.DAOException;
import com.example.copypasteapp.sqlite.Pedido;
import com.example.copypasteapp.sqlite.PedidoAdapter;
import com.example.copypasteapp.sqlite.PedidoDAO;

import java.util.ArrayList;
import java.util.List;

public class InvoiceActivity extends AppCompatActivity {


    private List<Pedido> pedidoList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PedidoAdapter pAdapter;
    public TextView totalizado2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        getSupportActionBar().hide();

        totalizado2 = (TextView) findViewById(R.id.totalizado2);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerInvoice);

        pAdapter = new PedidoAdapter(pedidoList,this.getBaseContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(pAdapter);

        prepareDataInvoice();

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