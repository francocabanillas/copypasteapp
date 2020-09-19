package com.example.copypasteapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.copypasteapp.R;
import com.example.copypasteapp.sharedpreference.Sharedpreference;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AddressActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap map;
    SupportMapFragment mapFragment;
    android.widget.SearchView searchView;
    EditText txDireccion;

    String vTitle;
    Double vLat, vLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        getSupportActionBar().hide();

        searchView = (android.widget.SearchView) findViewById(R.id.sv_location);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        txDireccion = (EditText) findViewById(R.id.txDireccion);

        Sharedpreference sharedpreference = new Sharedpreference();
        vTitle = sharedpreference.readDireccion(this);
        vLat = Double.parseDouble(sharedpreference.readLatitud(this));
        vLong= Double.parseDouble(sharedpreference.readLongitud(this));
        txDireccion.setText(sharedpreference.readDireccion(this));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                txDireccion.setText("");
                vTitle="";
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;

                if (location != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(AddressActivity.this);
                    try {
                        addressList = geocoder.getFromLocationName(location,1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (addressList.size()>0){
                        Address address = addressList.get(0);
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        map.addMarker(new MarkerOptions().position(latLng).title(location));
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,18));

                        vTitle =location;
                        vLong = address.getLongitude();
                        vLat= address.getLatitude();
                        txDireccion.setText(vTitle);
                    }


                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        map.getUiSettings().setZoomControlsEnabled(true);
        map.setTrafficEnabled(true);

        Sharedpreference sharedpreference = new Sharedpreference();
        if (!sharedpreference.readIdDireccion(this).equals("0")){
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(vLat, vLong))
                    .title(vTitle)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(vLat, vLong), 18));
        }

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                vTitle="";
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(latLng.latitude+":"+latLng.longitude);
                map.clear();
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,18));
                map.addMarker(markerOptions);

                Geocoder geocoder;
                List<Address> addresses=null;
                geocoder = new Geocoder(AddressActivity.this, Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (addresses.size()==0) {
                    vTitle ="Dirección por coordenadas";}
                else {
                    vTitle=addresses.get(0).getAddressLine(0);}
                vLong = latLng.longitude;
                vLat= latLng.latitude;
                txDireccion.setText(vTitle);
            }
        });
    }

    public void guardarCambios(View v){

        if (txDireccion.getText().toString().length()==0){
            Toast toast = Toast.makeText(AddressActivity.this,"Ingrese la dirección", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else if (txDireccion.getText().toString().equals("Dirección por coordenadas")){
            Toast toast = Toast.makeText(AddressActivity.this,"Ingrese la dirección exacta", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else
        {
            vTitle = txDireccion.getText().toString().substring(0,30);;

            Sharedpreference sharedpreference = new Sharedpreference();
            if (sharedpreference.readIdDireccion(this).equals("0")){
                guardarDireccion();
                actualizaDireccion();
            } else {
                eliminarDireccion();
                guardarDireccion();
                actualizaDireccion();
            }
            startActivity(new Intent(this, MainActivity.class));
        }


    }

    private void guardarDireccion(){
        Sharedpreference sharedpreference = new Sharedpreference();
        String cadena = sharedpreference.readIdCliente(this);
        String url = "http://copypaste.atwebpages.com/index.php/clientedireccion/"+cadena;


        StringRequest stringRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast toast = Toast.makeText(AddressActivity.this,"Ha sido actualizado su direccion", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("onErrorResponse", error.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap();
                params.put("nombre_direccion", vTitle);
                params.put("latitud", vLat.toString());
                params.put("longitud", vLong.toString());
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void eliminarDireccion(){
        Sharedpreference sharedpreference = new Sharedpreference();
        String idCliente = sharedpreference.readIdCliente(this);
        final String idDireccion = sharedpreference.readIdDireccion(this);
        String url = "http://copypaste.atwebpages.com/index.php/clientedireccion/"+idCliente;


        StringRequest stringRequest= new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("eliminarDireccion", "Ok");
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("onErrorResponse", error.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap();
                params.put("id", idDireccion);
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void actualizaDireccion() {

        final Sharedpreference preference = new Sharedpreference();
        String cadena = preference.readIdCliente(this);

        String url = "http://copypaste.atwebpages.com/index.php/cliente/"+cadena;

        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        if (object.getString("direccion_id")!=null) {
                            preference.saveIdDireccion(object.getString("direccion_id"),getApplicationContext());
                            preference.saveLatitud(object.getString("latitud"),getApplicationContext());
                            preference.saveLongitud(object.getString("longitud"),getApplicationContext());
                        }
                    }

                } catch (JSONException e) {
                    Log.i("prepareI", e.getMessage());
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("prepareIn", error.toString());
                    }
                }
        );
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


}