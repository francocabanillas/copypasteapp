package com.example.copypasteapp;

public class Platomenu {
    private int id;
    private String articulo_nombre;
    private String categoria_nombre;
    private String precio;
    private String imagen;

    public Platomenu(int id, String articulo_nombre, String categoria_nombre, String precio, String imagen) {
        this.id = id;
        this.articulo_nombre = articulo_nombre;
        this.categoria_nombre = categoria_nombre;
        this.precio = precio;
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArticulo_nombre() {
        return articulo_nombre;
    }

    public void setArticulo_nombre(String articulo_nombre) {
        this.articulo_nombre = articulo_nombre;
    }

    public String getCategoria_nombre() {
        return categoria_nombre;
    }

    public void setCategoria_nombre(String categoria_nombre) {
        this.categoria_nombre = categoria_nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
