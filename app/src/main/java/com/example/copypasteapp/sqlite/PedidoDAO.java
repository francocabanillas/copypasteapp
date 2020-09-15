package com.example.copypasteapp.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.w3c.dom.DOMImplementation;

import java.util.ArrayList;
import java.util.List;

public class PedidoDAO  {

    private DbHelper _dbHelper;

    public PedidoDAO(Context c) {
        _dbHelper = new DbHelper(c);
    }

    public void insertar(String nombre, String categoria, String precio, String observacion, String cantidad) throws DAOException {
        SQLiteDatabase db = _dbHelper.getWritableDatabase();
        try {
            String[] args = new String[]{nombre, categoria, precio, observacion, cantidad};
            db.execSQL("INSERT INTO pedido(nombre, categoria, precio, observacion, cantidad) VALUES (?,?,?,?,?)", args);

        } catch (Exception e) {
            throw new DAOException("PedidoDAO: Error al insertar: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public void actualizar(String id, String cantidad) throws DAOException {
        SQLiteDatabase db = _dbHelper.getWritableDatabase();
        try {
            String[] args = new String[]{id, cantidad};
            db.execSQL("UPDATE pedido SET cantidad=? WHERE id=? ", args);
        } catch (Exception e) {
            throw new DAOException("PedidoDAO: Error al insertar: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public List<Pedido> obtener() throws DAOException {
        SQLiteDatabase db = _dbHelper.getReadableDatabase();
        List<Pedido> pedidoList = new ArrayList<>();
        try {
            Cursor c = db.rawQuery("select id, nombre, categoria, precio, observacion, cantidad from pedido", null);

            if (c.getCount() > 0) {
                c.moveToFirst();

                do {
                    int id = c.getInt(c.getColumnIndex("id"));
                    String nombre = c.getString(c.getColumnIndex("nombre"));
                    String categoria = c.getString(c.getColumnIndex("categoria"));
                    String precio = c.getString(c.getColumnIndex("precio"));
                    String observacion = c.getString(c.getColumnIndex("observacion"));
                    String cantidad = c.getString(c.getColumnIndex("cantidad"));

                    pedidoList.add(new Pedido(id,nombre,categoria,precio, observacion,cantidad));

                } while (c.moveToNext());
            }
            c.close();
        } catch (Exception e) {
            throw new DAOException("PedidoDAO: Error al obtener: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return pedidoList;
    }

    public void eliminar(int id) throws DAOException {
        SQLiteDatabase db = _dbHelper.getWritableDatabase();

        try {
            String[] args = new String[]{String.valueOf(id)};
            db.execSQL("DELETE FROM pedido WHERE id=?", args);
        } catch (Exception e) {
            throw new DAOException("PedidoDAO: Error al eliminar: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public void eliminarTodos() throws DAOException {
        SQLiteDatabase db = _dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM pedido");
        } catch (Exception e) {
            throw new DAOException("PedidoDAO: Error al eliminar todos: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

}

