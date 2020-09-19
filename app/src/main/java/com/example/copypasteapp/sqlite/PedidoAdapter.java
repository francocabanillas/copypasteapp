package com.example.copypasteapp.sqlite;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.DashPathEffect;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.copypasteapp.R;
import com.example.copypasteapp.ui.InvoiceActivity;

import java.util.List;

public class PedidoAdapter  extends RecyclerView.Adapter<PedidoAdapter.MyViewHolder> {

    private List<Pedido> pedidoList;
    private Context contextInvoice;
    public TextView totalizado2;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView nombre_articulo2, observacion2, precio2, total2, categoria_nombre2;
        public EditText cantidad2;
        public Button subir2, bajar2;

        public int vId, vCantidad;
        public double vPrecioU, vTotal;
        public Context context;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            nombre_articulo2 = (TextView) view.findViewById(R.id.nombre_articulo2);
            observacion2 = (TextView) view.findViewById(R.id.observacion2);
            precio2 = (TextView) view.findViewById(R.id.precio2);
            total2 = (TextView) view.findViewById(R.id.total2);
            categoria_nombre2 = (TextView) view.findViewById(R.id.categoria_nombre2);
            cantidad2 = (EditText) view.findViewById(R.id.cantidad2);
            subir2 = (Button) view.findViewById(R.id.subir2);
            bajar2 = (Button) view.findViewById(R.id.bajar2);

            View view1 = View.inflate(context, R.layout.activity_invoice, null);
            totalizado2 = (TextView) view1.findViewById(R.id.totalizado2);

        }

        void setOnClickListeners() {
            subir2.setOnClickListener(this);
            bajar2.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.subir2:
                    vCantidad = vCantidad + 1;
                    cantidad2.setText(String.valueOf(vCantidad));
                    vTotal = vCantidad * vPrecioU;
                    pedidoList.get(vId).setCantidad(String.valueOf(vCantidad));
                    total2.setText("S/ " + String.format("%.2f", vTotal));
                    totalizado2.setText("2");

                    PedidoDAO dao = new PedidoDAO(view.getContext());
                    try {
                        dao.actualizar(String.valueOf(pedidoList.get(vId).getId()), pedidoList.get(vId).getCantidad());
                    } catch (DAOException e) {
                        Log.i("onClick", "====> " + e.getMessage());
                    }

                    break;

                case R.id.bajar2:
                    if (vCantidad > 1) {
                        vCantidad = vCantidad - 1;
                        cantidad2.setText(String.valueOf(vCantidad));
                        vTotal = vCantidad * vPrecioU;
                        pedidoList.get(vId).setCantidad(String.valueOf(vCantidad));
                        total2.setText("S/ " + String.format("%.2f", vTotal));
                        totalizado2.setText("2");

                        PedidoDAO dao2 = new PedidoDAO(view.getContext());
                        try {
                            dao2.actualizar(String.valueOf(pedidoList.get(vId).getId()), pedidoList.get(vId).getCantidad());
                        } catch (DAOException e) {
                            Log.i("onClick", "====> " + e.getMessage());
                        }
                    }

                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + view.getId());
            }
        }
    }

    public PedidoAdapter(List<Pedido> pedidoList, Context context) {
        this.pedidoList = pedidoList;
        this.contextInvoice = context;
    }

    @Override
    public PedidoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_detalle, parent, false);

        return new PedidoAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PedidoAdapter.MyViewHolder holder, int position) {
        Pedido pedido = pedidoList.get(position);
        holder.nombre_articulo2.setText(pedido.getNombre());
        holder.precio2.setText(pedido.getPrecio());
        holder.categoria_nombre2.setText(pedido.getCategoria());
        holder.cantidad2.setText(pedido.getCantidad());
        holder.observacion2.setText(pedido.getObservacion());
        holder.vId=position;
        holder.vCantidad= Integer.parseInt(pedido.getCantidad());
        holder.vPrecioU= Double.parseDouble(pedido.getPrecio().replace("S/ ", ""));
        holder.vTotal = holder.vCantidad*holder.vPrecioU;
        totalizado2.setText("2");
        holder.total2.setText("S/ " + String.format("%.2f",holder.vTotal));
        holder.setOnClickListeners();

        SumaTotal();
    }

    public Double SumaTotal(){
        Double total = 0.0;
        List<Pedido> list = pedidoList;
        for (int i = 0; i< list.size(); i++)
        {
            total = total +
                    ( Integer.parseInt(list.get(i).getCantidad()) *
                    Double.parseDouble(list.get(i).getPrecio().replace("S/ ", "")));
        }
        Log.i("SumaTotal", String.valueOf(total));
        return total;
    }


    @Override
    public int getItemCount() {
        SumaTotal();
        return pedidoList.size();
    }


}
