package com.example.copypasteapp;

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

import com.example.copypasteapp.sqlite.DAOException;
import com.example.copypasteapp.sqlite.PedidoDAO;
import com.example.copypasteapp.ui.AddressActivity;
import com.example.copypasteapp.ui.InvoiceActivity;

import java.util.List;

public class PlatomenuAdapter  extends RecyclerView.Adapter<PlatomenuAdapter.MyViewHolder> {

    private List<Platomenu> platomenuList;



    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView nombre_articulo, categoria_nombre, precio;
        public ImageView imagenplato,botonplus;
        public Context context;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            nombre_articulo = (TextView) view.findViewById(R.id.nombre_articulo);
            categoria_nombre = (TextView) view.findViewById(R.id.categoria_nombre);
            precio = (TextView) view.findViewById(R.id.precio);
            imagenplato = (ImageView) view.findViewById(R.id.imagenplato);
            botonplus = (ImageView) view.findViewById(R.id.botonplus);
        }

        void setOnClickListeners(){
            botonplus.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.botonplus:
                    PedidoDAO dao = new PedidoDAO(view.getContext());
                    try {

                        dao.insertar(nombre_articulo.getText().toString(),categoria_nombre.getText().toString(),
                                precio.getText().toString(),"Incluye entrada y bebida", "1");
                        context.startActivity(new Intent(context, InvoiceActivity.class));

                    } catch (DAOException e) {
                        Log.i("prepareDataInvoice", "====> " + e.getMessage());
                    }
                    break;
            }
        }
    }


    public PlatomenuAdapter(List<Platomenu> platomenuList) {
        this.platomenuList = platomenuList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_platos, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Platomenu platomenu = platomenuList.get(position);
        holder.nombre_articulo.setText(platomenu.getArticulo_nombre());
        holder.categoria_nombre.setText(platomenu.getCategoria_nombre());
        holder.precio.setText(platomenu.getPrecio());
        if (platomenu.getImagen().length()>0) {
            String codedata = platomenu.getImagen();
            codedata = codedata.replace("data:image/jpeg;base64,","");
            byte[] code = Base64.decode(codedata,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(code,0,code.length);
            holder.imagenplato.setImageBitmap(bitmap);
        }
        holder.setOnClickListeners();
    }


    @Override
    public int getItemCount() {
        return platomenuList.size();
    }
}
