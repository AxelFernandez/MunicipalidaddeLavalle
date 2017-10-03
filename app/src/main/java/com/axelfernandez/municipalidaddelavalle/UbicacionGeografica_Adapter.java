package com.axelfernandez.municipalidaddelavalle;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by axelfernandez on 5/9/17.
 */

public class UbicacionGeografica_Adapter extends RecyclerView.Adapter<UbicacionGeografica_Adapter.UbicacionGeografica_Adapter_viewHolder> {


    List<UbicacionGeografica_Entity> ubica;
     public UbicacionGeografica_Adapter(List<UbicacionGeografica_Entity> ubica){
         this.ubica=ubica;

     }

    @Override
    public UbicacionGeografica_Adapter_viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ubicaciongeografica, parent, false);
        UbicacionGeografica_Adapter_viewHolder uvh = new UbicacionGeografica_Adapter_viewHolder(v);
        return uvh;
    }

    @Override
    public void onBindViewHolder(UbicacionGeografica_Adapter_viewHolder holder, int position) {

        holder.titulo.setText(ubica.get(position).getTitulo());
        holder.descripcion.setText(ubica.get(position).getDescripcion());
    }

    @Override
    public int getItemCount() {
        return ubica.size();
    }






    public static class UbicacionGeografica_Adapter_viewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView titulo;
        TextView descripcion;

        public UbicacionGeografica_Adapter_viewHolder(View itemView) {
        super(itemView);
            cv = (CardView) itemView.findViewById(R.id.item_ubicaciongeografica_cv);
            titulo =(TextView) itemView.findViewById(R.id.item_ubicaciongeografica_titulo);
            descripcion = (TextView) itemView.findViewById(R.id.item_ubicaciongeografica_descripcion);

    }
}


}
