package com.axelfernandez.municipalidaddelavalle;


import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_HorariosColectivos extends Fragment {


    public Fragment_HorariosColectivos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Resources res = getResources();

        View v = inflater.inflate(R.layout.item_horarios, container, false);
        final String appPackageName = getContext().getPackageName(); // getPackageName() from Context or Activity object
        Button b = (Button) v.findViewById(R.id.item_colectivo_button);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.sistemas51.horarioslavalle")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.sistemas51.horarioslavalle")));
                }
            }
        });



        return v;
    }

}
