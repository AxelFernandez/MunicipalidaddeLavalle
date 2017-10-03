package com.axelfernandez.municipalidaddelavalle;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by axelfernandez on 8/9/17.
 */

public class Sitios_Adapter extends RecyclerView.Adapter<Sitios_Adapter.Sitios_Adapter_viewHolder> {
    List<Sitios_Entity> sities;
    List<String> url;

Context context;
    Sitios_Adapter(List<Sitios_Entity> sities,Context context){
        this.sities = sities;
        this.context=context;
    }

    @Override
    public Sitios_Adapter_viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sitios_gastro_aloj, parent, false);
        Sitios_Adapter_viewHolder pvh = new Sitios_Adapter_viewHolder(v);
        return pvh;

    }

    @Override
    public void onBindViewHolder(Sitios_Adapter_viewHolder holder, int position) {
        holder.titulo.setText(sities.get(position).getTitulo());
        holder.contenido.setText(sities.get(position).getContenido());
        Picasso.with(context).load("http://lavallemendoza.gob.ar/"+sities.get(position).getImagenes()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return sities.size();
    }


    public static class Sitios_Adapter_viewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView titulo;
        TextView contenido;
        ImageView image;

        Sitios_Adapter_viewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.item_sitios_cv);
            titulo = (TextView)itemView.findViewById(R.id.item_sitios_titulo);
            contenido = (TextView)itemView.findViewById(R.id.item_sitios_contenido);
            image=itemView.findViewById(R.id.item_sitios_image);
        }
    }
}
