package com.example.copypasteapp.list;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.copypasteapp.R;
import com.example.copypasteapp.sqlite.DAOException;
import com.example.copypasteapp.sqlite.PedidoDAO;
import com.example.copypasteapp.ui.InvoiceActivity;

import java.util.List;

public class ClientepedidoAdapter extends RecyclerView.Adapter<ClientepedidoAdapter.MyViewHolder> {

    private List<Clientepedido> clientepedidoList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView numeropedidoF, estado_nombreF, fechaF, preciototalF ;
        public Context context;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            numeropedidoF = (TextView) view.findViewById(R.id.numeropedidoF);
            estado_nombreF = (TextView) view.findViewById(R.id.estado_nombreF);
            fechaF = (TextView) view.findViewById(R.id.fechaF);
            preciototalF = (TextView) view.findViewById(R.id.preciototalF);
        }
    }


    public ClientepedidoAdapter(List<Clientepedido> clientepedidoList) {
        this.clientepedidoList = clientepedidoList;
    }

    @Override
    public ClientepedidoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_pedido, parent, false);

        return new ClientepedidoAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ClientepedidoAdapter.MyViewHolder holder, int position) {
        Clientepedido clientepedido = clientepedidoList.get(position);
        holder.numeropedidoF.setText("N° "+clientepedido.getNumeropedido());
        holder.estado_nombreF.setText(clientepedido.getEstado_nombre());
        holder.fechaF.setText(clientepedido.getFecha());
        holder.preciototalF.setText("S/ "+String.format("%.2f",Double.parseDouble(clientepedido.getPreciototal())));
    }


    @Override
    public int getItemCount() {
        return clientepedidoList.size();
    }

}
