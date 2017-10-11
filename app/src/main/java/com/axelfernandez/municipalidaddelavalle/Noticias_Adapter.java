package com.axelfernandez.municipalidaddelavalle;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by axelfernandez on 6/9/17.
 */

public class Noticias_Adapter extends RecyclerView.Adapter<Noticias_Adapter.Noticias_Adapter_ViewHolder>{
    List<Noticias_Entity> list = new ArrayList<>();


Context context;
    Noticias_Adapter(List<Noticias_Entity> list, Context context){
        this.list = list;
        this. context=context;
    }


    @Override
    public Noticias_Adapter_ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_noticias, parent, false);
        Noticias_Adapter_ViewHolder nvh = new Noticias_Adapter_ViewHolder(v);
        return nvh;
    }

    @Override
    public void onBindViewHolder(Noticias_Adapter_ViewHolder holder, final int position) {


        String fechanueva= String.valueOf(list.get(position).getFecha());

        holder.titulo.setText(list.get(position).getTitulo());
        holder.copete.setText(list.get(position).getCopete());
        holder.fecha.setText(fechanueva.substring(0,10));
        Picasso.with(context).load("http://lavallemendoza.gob.ar"+list.get(position).getUrlimagen()).into(holder.imagen);
        holder.b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, webView_Noticias.class);
                intent.putExtra("id",list.get(position).getId());
                intent.putExtra("title",list.get(position).getTitulo());
                intent.putExtra("copete", list.get(position).getCopete());
                intent.putExtra("imagen", list.get(position).getUrlimagen());

                context.startActivity(intent);
            }
        });

    }




    @Override
    public int getItemCount() {

        return list.size();

    }




    public static class Noticias_Adapter_ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView titulo;
        TextView copete;
        TextView fecha;
        ImageView imagen;
        Button b1;

        Noticias_Adapter_ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.item_noticias_cv);
            titulo = (TextView)itemView.findViewById(R.id.item_noticias_titulo);
            copete = (TextView)itemView.findViewById(R.id.item_noticias_copete);
            fecha = (TextView) itemView.findViewById(R.id.item_noticias_fecha);
            imagen=(ImageView) itemView.findViewById(R.id.item_noticias_imagen);
            b1= (Button) itemView.findViewById(R.id.item_noticias_noticiacompleta);
        }
    }

    }