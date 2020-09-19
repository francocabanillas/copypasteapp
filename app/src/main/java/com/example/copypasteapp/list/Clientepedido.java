package com.example.copypasteapp.list;

public class Clientepedido {
    private int id;
    private String numeropedido;
    private String fecha;
    private String estado;
    private String estado_nombre;
    private String preciototal;

    public Clientepedido(int id, String numeropedido, String fecha, String estado, String preciototal) {
        this.id = id;
        this.numeropedido = numeropedido;
        this.fecha = fecha;
        this.estado = estado;
        this.preciototal = preciototal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeropedido() {
        return numeropedido;
    }

    public void setNumeropedido(String numeropedido) {
        this.numeropedido = numeropedido;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstado_nombre() {
        if (estado.equals("0"))
        {estado_nombre="Anulado";}
        else if (estado.equals("2"))
        {estado_nombre="Entregado";}
        else if (estado.equals("1"));
        { estado_nombre="Pendiente";}
        return estado_nombre;
    }

    public void setEstado_nombre(String estado_nombre) {
        this.estado_nombre = estado_nombre;
    }

    public String getPreciototal() {
        return preciototal;
    }

    public void setPreciototal(String preciototal) {
        this.preciototal = preciototal;
    }
}
