package com.proyecto.adminbibliotecaapp.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.proyecto.adminbibliotecaapp.R;
import com.proyecto.adminbibliotecaapp.Vistas.VistaAutores;
import com.proyecto.adminbibliotecaapp.Vistas.VistaCategorias;
import com.proyecto.adminbibliotecaapp.Vistas.VistaUbicaciones;
import com.proyecto.adminbibliotecaapp.Vistas.VistaLibros;
import com.proyecto.adminbibliotecaapp.Vistas.VistaPrestamos;
import com.proyecto.adminbibliotecaapp.Vistas.VistaUsuarios;

public class AdaptadorMenu extends RecyclerView.Adapter<AdaptadorMenu.ViewHolderMenu> {

    Context context;
    String[] listaMenu;

    public AdaptadorMenu(Context context, String[] listaMenu) {
        this.context = context;
        this.listaMenu = listaMenu;
    }

    @NonNull
    @Override
    public ViewHolderMenu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_menu, null, false);
        return new ViewHolderMenu(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMenu holder, int position) {
        int aux = position;

        Glide
                .with(context)
                .load(context.getResources().getString(R.string.url_icon_menu)+listaMenu[position].toLowerCase()+".png")
                .centerInside()
                .placeholder(context.getResources().getDrawable(R.drawable.icon_falta_foto))
                .into(holder.ivOpcion);

        holder.tvOpcion.setText(listaMenu[position].toUpperCase());

        holder.cvOpcionMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;

                switch (listaMenu[aux].toLowerCase()) {
                    case "usuarios":
                        intent = new Intent(context, VistaUsuarios.class);
                        context.startActivity(intent);
                        break;
                    case "autores":
                        intent = new Intent(context, VistaAutores.class);
                        context.startActivity(intent);
                        break;
                    case "ubicaciones":
                        intent = new Intent(context, VistaUbicaciones.class);
                        context.startActivity(intent);
                        break;
                    case "libros":
                        intent = new Intent(context, VistaLibros.class);
                        context.startActivity(intent);
                        break;
                    case "categorias":
                        intent = new Intent(context, VistaCategorias.class);
                        context.startActivity(intent);
                        break;
                    case "prestamos":
                        intent = new Intent(context, VistaPrestamos.class);
                        context.startActivity(intent);
                        break;

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaMenu.length;
    }

    public class ViewHolderMenu extends RecyclerView.ViewHolder {

        CardView cvOpcionMenu;
        ImageView ivOpcion;
        TextView tvOpcion;

        public ViewHolderMenu(@NonNull View itemView) {
            super(itemView);

            cvOpcionMenu = itemView.findViewById(R.id.cvOpcionMenu);
            ivOpcion = itemView.findViewById(R.id.ivOpcion);
            tvOpcion = itemView.findViewById(R.id.tvOpcion);
        }
    }
}
