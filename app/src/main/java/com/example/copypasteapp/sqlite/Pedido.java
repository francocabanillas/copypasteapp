package com.example.copypasteapp.sqlite;

public class Pedido {
    private int id;
    private String articulo_id;
    private String nombre;
    private String categoria;
    private String precio;
    private String observacion;
    private String cantidad;

    public Pedido() {
    }

    public Pedido(int id, String articulo_id, String nombre, String categoria, String precio,  String observacion, String cantidad) {
        this.id = id;
        this.articulo_id = articulo_id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.cantidad = cantidad;
        this.observacion = observacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArticulo_id() {
        return articulo_id;
    }

    public void setArticulo_id(String articulo_id) {
        this.articulo_id = articulo_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
}
