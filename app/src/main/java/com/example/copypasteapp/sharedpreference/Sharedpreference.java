package com.example.copypasteapp.sharedpreference;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

import java.util.GregorianCalendar;

public class Sharedpreference {

    private static final String NOMBRE_SHARED = "PREFERENCIA";

    private static final String SHARED_IDCLIENTE  = "IDCLIENTE";
    private static final String SHARED_IDDIRECCION  = "IDDIRECCION";
    private static final String SHARED_DIRECCION  = "DIRECCION";
    private static final String SHARED_LONGITUD  = "LONGITUD";
    private static final String SHARED_LATITUD  = "LATITUD";
    private static final String SHARED_TOKEN = "FIRETOKEN";

    public Sharedpreference() {
    }

    private void savePreference(String id, String value, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(NOMBRE_SHARED, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        GregorianCalendar calendar = new GregorianCalendar();
        editor.putString(id,value);
        editor.commit();
    }

    private String readPreference(String id, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(NOMBRE_SHARED, Context.MODE_PRIVATE);
        return preferences.getString(id,"");
    }

    public void saveIdCliente(String value, Context context) {
        savePreference(SHARED_IDCLIENTE, value, context);
    }

    public String readIdCliente(Context context){
        return readPreference(SHARED_IDCLIENTE, context);
    }

    public void saveIdDireccion(String value, Context context) {
        savePreference(SHARED_IDDIRECCION, value, context);
    }

    public String readIdDireccion(Context context){
        return readPreference(SHARED_IDDIRECCION, context);
    }

    public void saveDireccion(String value, Context context) {
        savePreference(SHARED_DIRECCION, value, context);
    }

    public String readDireccion(Context context){
        return readPreference(SHARED_DIRECCION, context);
    }

    public void saveLongitud(String value, Context context) {
        savePreference(SHARED_LONGITUD, value, context);
    }

    public String readLongitud(Context context){
        return readPreference(SHARED_LONGITUD, context);
    }

    public void saveLatitud(String value, Context context) {
        savePreference(SHARED_LATITUD, value, context);
    }

    public String readLatitud(Context context){
        return readPreference(SHARED_LATITUD, context);
    }

    public void saveToken(String value, Context context) {
        savePreference(SHARED_TOKEN, value, context);
    }

    public String readToken(Context context){
        return readPreference(SHARED_TOKEN, context);
    }


}
