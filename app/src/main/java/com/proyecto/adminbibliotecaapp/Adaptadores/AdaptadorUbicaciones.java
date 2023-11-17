package com.proyecto.adminbibliotecaapp.Adaptadores;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.proyecto.adminbibliotecaapp.Clases.Ubicacion;
import com.proyecto.adminbibliotecaapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class AdaptadorUbicaciones extends RecyclerView.Adapter<AdaptadorUbicaciones.ViewHolder> {

    Context context;
    List<Ubicacion> listaUbicaciones;

    public AdaptadorUbicaciones(Context context, List<Ubicacion> listaUbicaciones) {
        this.context = context;
        this.listaUbicaciones = listaUbicaciones;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_ubicaciones, null, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvUbicacion.setText(listaUbicaciones.get(position).getNomUbicacion().toUpperCase());

        holder.ibtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogEdit(position);
            }
        });

        holder.ibtnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                borrarUbicacion(listaUbicaciones.get(position).getIdUbicacion(), position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaUbicaciones.size();
    }

    private void alertDialogEdit(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View vista = layoutInflater.inflate(R.layout.alert_dialog_edit_ubicacion, null);

        EditText etIdUbicacion = vista.findViewById(R.id.etIdUbicacion);
        etIdUbicacion.setEnabled(false);
        etIdUbicacion.setFocusable(false);
        etIdUbicacion.setText(listaUbicaciones.get(position).getIdUbicacion());
        EditText etNomUbicacion = vista.findViewById(R.id.etNomUbicacion);
        etNomUbicacion.setText(listaUbicaciones.get(position).getNomUbicacion());

        builder.setView(vista);
        builder.create();
        builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(etIdUbicacion.getText().toString().equals("") || etNomUbicacion.getText().toString().equals("")) {
                    Toast.makeText(context, "Se deben de llenar todos los campos", Toast.LENGTH_LONG).show();
                } else {
                    editarUbicacion(etIdUbicacion.getText().toString(), etNomUbicacion.getText().toString(), position);
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

    private void editarUbicacion(String idUbicacion, String nomUbicacion, int position) {
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.POST, context.getString(R.string.url_api),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                        //System.out.println("RESPONSE: "+response);

                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            if(jsonObject.getString("codigo").equals("OK")) {
                                Toast.makeText(context, jsonObject.getString("mensaje"), Toast.LENGTH_LONG).show();
                                listaUbicaciones.get(position).setNomUbicacion(nomUbicacion);
                                notifyDataSetChanged();
                            }else if(jsonObject.getString("codigo").equals("ERROR")) {
                                Toast.makeText(context, jsonObject.getString("mensaje"), Toast.LENGTH_LONG).show();
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // En caso de tener algun error en la obtencion de los datos
                Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                // En este metodo se hace el envio de valores de la aplicacion al servidor
                Map<String, String> parametros = new Hashtable<String, String>();
                parametros.put("accion", "404");
                parametros.put("id_ubicacion", idUbicacion);
                parametros.put("nom_ubicacion", nomUbicacion);

                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void borrarUbicacion(String idUbicacion, int position) {
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.POST, context.getString(R.string.url_api),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                        //System.out.println("RESPONSE: "+response);

                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            if(jsonObject.getString("codigo").equals("OK")) {
                                Toast.makeText(context, jsonObject.getString("mensaje"), Toast.LENGTH_LONG).show();
                                listaUbicaciones.remove(position);
                                notifyDataSetChanged();
                            }else if(jsonObject.getString("codigo").equals("ERROR")) {
                                Toast.makeText(context, jsonObject.getString("mensaje"), Toast.LENGTH_LONG).show();
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // En caso de tener algun error en la obtencion de los datos
                Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                // En este metodo se hace el envio de valores de la aplicacion al servidor
                Map<String, String> parametros = new Hashtable<String, String>();
                parametros.put("accion", "405");
                parametros.put("id_ubicacion", idUbicacion);

                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvUbicacion;
        ImageButton ibtnEdit, ibtnRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvUbicacion = itemView.findViewById(R.id.tvUbicacion);
            ibtnEdit = itemView.findViewById(R.id.ibtnEditar);
            ibtnRemove = itemView.findViewById(R.id.ibtnEliminar);
        }
    }
}
