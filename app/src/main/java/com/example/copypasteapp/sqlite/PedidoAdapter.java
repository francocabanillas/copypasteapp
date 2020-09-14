package com.example.copypasteapp.sqlite;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView nombre_articulo2, observacion2, precio2, total2, categoria_nombre2;
        public EditText cantidad2;
        public Button subir2,bajar2;

        public MyViewHolder(View view) {
            super(view);
            nombre_articulo2 = (TextView) view.findViewById(R.id.nombre_articulo2);
            observacion2 = (TextView) view.findViewById(R.id.observacion2);
            precio2 = (TextView) view.findViewById(R.id.precio2);
            total2 = (TextView) view.findViewById(R.id.total2);
            categoria_nombre2 = (TextView) view.findViewById(R.id.categoria_nombre2);
            cantidad2 = (EditText) view.findViewById(R.id.cantidad2);
            subir2 = (Button) view.findViewById(R.id.subir2);
            bajar2 = (Button) view.findViewById(R.id.bajar2);
        }
        void setOnClickListeners(){
            subir2.setOnClickListener(this);
            bajar2.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.subir2:

                    String cantidad = cantidad2.getText().toString();
                    if (cantidad.equals("")){cantidad2.setText("1");}
                    else{cantidad2.setText(String.valueOf(Integer.parseInt(cantidad)+1));}

                    break;
                case R.id.bajar2:

                    String cantidad_ = cantidad2.getText().toString();
                    if (cantidad_.equals("")){cantidad2.setText("1");}
                    else{
                        if (!cantidad_.equals("1")) {
                            cantidad2.setText(String.valueOf(Integer.parseInt(cantidad_)-1));
                        }
                    }

                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + view.getId());
            }
        }
    }


    public PedidoAdapter(List<Pedido> pedidoList) {
        this.pedidoList = pedidoList;
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

        holder.setOnClickListeners();
        Log.i("Probando", "observacion:" + pedido.getObservacion());
//        if ((pedido.getPrecio().length()>0) && (pedido.getPrecio().length()>0)) {
//            holder.total2.setText("Total S/ "+String.valueOf(Integer.parseInt(pedido.getPrecio())*Integer.parseInt(pedido.getCantidad())));
//        }
//        else
//        {
//            holder.total2.setText("Total: S/ 0.00");
//        }
    }

    @Override
    public int getItemCount() {
        return pedidoList.size();
    }
}
