package com.proyecto.adminbibliotecaapp.Vistas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.proyecto.adminbibliotecaapp.Adaptadores.AdaptadorUbicaciones;
import com.proyecto.adminbibliotecaapp.Clases.Ubicacion;
import com.proyecto.adminbibliotecaapp.R;
import com.proyecto.adminbibliotecaapp.databinding.ActivityVistaUbicacionesBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class VistaUbicaciones extends AppCompatActivity {

    private ActivityVistaUbicacionesBinding binding;

    List<Ubicacion> listaUbicaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVistaUbicacionesBinding.inflate(getLayoutInflater());
        View vista = binding.getRoot();
        setContentView(vista);

        binding.rvUbicaciones.setLayoutManager(new GridLayoutManager(this, 1));

        obtenerUbicaciones();

        binding.ibtnUbicacionAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogAdd();
            }
        });
    }

    private void alertDialogAdd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(VistaUbicaciones.this);
        LayoutInflater layoutInflater = this.getLayoutInflater();

        View vista = layoutInflater.inflate(R.layout.alert_dialog_add_ubicacion, null);

        EditText etIdUbicacion = vista.findViewById(R.id.etIdUbicacion);
        EditText etNomUbicacion = vista.findViewById(R.id.etNomUbicacion);

        builder.setView(vista);
        builder.create();
        builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(etIdUbicacion.getText().toString().equals("") || etNomUbicacion.getText().toString().equals("")) {
                    Toast.makeText(VistaUbicaciones.this, "Se deben de llenar todos los campos", Toast.LENGTH_LONG).show();
                } else {
                    agregarUbicacion(etIdUbicacion.getText().toString(), etNomUbicacion.getText().toString());
                }
            }
        });

        builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setCancelable(false);
        builder.show();
    }

    private void agregarUbicacion(String idEitorial, String nomUbicacion) {
        listaUbicaciones.clear();

        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.POST, VistaUbicaciones.this.getString(R.string.url_api),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                        //System.out.println("RESPONSE: "+response);

                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            if(jsonObject.getString("codigo").equals("OK")) {
                                Toast.makeText(VistaUbicaciones.this, jsonObject.getString("mensaje"), Toast.LENGTH_LONG).show();
                                obtenerUbicaciones();

                            }else if(jsonObject.getString("codigo").equals("ERROR")) {
                                Toast.makeText(VistaUbicaciones.this, jsonObject.getString("mensaje"), Toast.LENGTH_LONG).show();
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // En caso de tener algun error en la obtencion de los datos
                Toast.makeText(VistaUbicaciones.this, "ERROR", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                // En este metodo se hace el envio de valores de la aplicacion al servidor
                Map<String, String> parametros = new Hashtable<String, String>();
                parametros.put("accion", "403");
                parametros.put("id_ubicacion", idEitorial);
                parametros.put("nom_ubicacion", nomUbicacion);

                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(VistaUbicaciones.this);
        requestQueue.add(stringRequest);
    }

    private void obtenerUbicaciones() {
        listaUbicaciones = new ArrayList<>();

        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.POST, VistaUbicaciones.this.getString(R.string.url_api),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //Toast.makeText(VistaUbicaciones.this, response, Toast.LENGTH_SHORT).show();
                        //System.out.println("RESPONSE: "+response);

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            //System.out.println(""+jsonObject.getString("codigo"));
                            if(jsonObject.getString("codigo").equals("OK")) {
                                //System.out.println(""+jsonObject.getString("ubicaciones"));
                                JSONArray jsonArray = jsonObject.getJSONArray("ubicaciones");

                                for (int i = 0 ; i < jsonArray.length() ; i++) {
                                    JSONObject item = jsonArray.getJSONObject(i);
                                    listaUbicaciones.add(
                                            new Ubicacion(
                                                    item.getString("id_ubicacion"),
                                                    item.getString("nom_ubicacion")
                                            )
                                    );
                                }

                            }else if(jsonObject.getString("codigo").equals("ERROR")) {
                                Toast.makeText(VistaUbicaciones.this, jsonObject.getString("mensaje"), Toast.LENGTH_LONG).show();
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }

                        binding.rvUbicaciones.setAdapter(new AdaptadorUbicaciones(VistaUbicaciones.this, listaUbicaciones));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // En caso de tener algun error en la obtencion de los datos
                Toast.makeText(VistaUbicaciones.this, "ERROR", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                // En este metodo se hace el envio de valores de la aplicacion al servidor
                Map<String, String> parametros = new Hashtable<String, String>();
                parametros.put("accion", "401");

                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(VistaUbicaciones.this);
        requestQueue.add(stringRequest);

    }
}