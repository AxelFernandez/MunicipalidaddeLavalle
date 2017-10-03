package com.axelfernandez.municipalidaddelavalle;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Telefonos extends Fragment {


    public Fragment_Telefonos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Resources res = getResources();

        View v = inflater.inflate(R.layout.fragment_fragment__ubicacion_geografica, container, false);
        RecyclerView rv = v.findViewById(R.id.ubicacionGeografica_rv);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        String[] titulo = res.getStringArray(R.array.Tel_nombre);
        String[] desc = res.getStringArray(R.array.Tel_telefono);
        List<UbicacionGeografica_Entity> ubica = new ArrayList<>();
        for(int i = 0; i<titulo.length;i++){

            UbicacionGeografica_Entity obj = new UbicacionGeografica_Entity(titulo[i], desc[i]);
            ubica.add(obj);



        }






        UbicacionGeografica_Adapter adapter = new UbicacionGeografica_Adapter(ubica);
        rv.setAdapter(adapter);



        return v;
    }

}
