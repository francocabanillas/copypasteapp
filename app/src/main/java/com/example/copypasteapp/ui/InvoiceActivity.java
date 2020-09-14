package com.example.copypasteapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        getSupportActionBar().hide();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerInvoice);

        pAdapter = new PedidoAdapter(pedidoList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(pAdapter);

        prepareDataInvoice();

    }

    public void prepareDataInvoice(){
        PedidoDAO dao = new PedidoDAO(getBaseContext());
        try {
            List<Pedido> list = dao.obtener();
            for (int i = 0; i< list.size(); i++)
            {
                pedidoList.add(list.get(i));
            }


            Log.i("DataInvoice cant: ", String.valueOf(pedidoList.size()));
            pAdapter.notifyDataSetChanged();

        } catch (DAOException e) {
            Log.i("prepareDataInvoice", "====> " + e.getMessage());
        }

    }
}