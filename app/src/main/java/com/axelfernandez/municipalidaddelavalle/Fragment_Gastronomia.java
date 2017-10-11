package com.axelfernandez.municipalidaddelavalle;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Gastronomia extends Fragment {


    public Fragment_Gastronomia() {
        // Required empty public constructor
    }
    HttpURLConnection connection;
    ProgressDialog progressDialog;
    RecyclerView rv;
    List<Sitios_Entity> list = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragment__sitios, container, false);
        View nc = inflater.inflate(R.layout.no_conexion, container, false);
        rv = v.findViewById(R.id.sitios_rv);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Cargando");
        progressDialog.setCancelable(false);
        progressDialog.show();
        new Thread(new Runnable() {
            public void run() {
                list = new ArrayList<>();

                try {
                    URL url1 = new URL("http://lavallemendoza.gob.ar/public/webservice/gastronomia");

                    connection = (HttpURLConnection) url1.openConnection();
                    connection.connect();
                    InputStream inputStream = connection.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line = "";
                    StringBuffer buffer = new StringBuffer();
                    while ((line = reader.readLine()) != null) {

                        buffer.append(line);
                        String result = buffer.toString();
                        JSONArray jsonArray = new JSONArray(result);

                        for (int i = 0; i < buffer.length(); i++) {

                            Sitios_Entity dataModel = new Sitios_Entity();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String title = jsonObject.getString("titulo");
                            String content = jsonObject.getString("contenido");
                            content.replace("\r", "");
                            String type = jsonObject.getString("tipo");

                            String image;
                            JSONArray imagea = jsonObject.getJSONArray("imagenes");
                            if (imagea.isNull(0)) {
                                image = "/public/img/header/logo.png";


                            } else {
                                image = imagea.getString(0);
                            }


                            dataModel.setContenido(content);
                            dataModel.setTitulo(title);
                            dataModel.setTipo(type);
                            dataModel.setImagenes(image);
                            list.add(dataModel);


                        }


                    }


                } catch (MalformedURLException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();

                } catch (JSONException e) {
                    e.printStackTrace();

                }


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LinearLayoutManager llm = new LinearLayoutManager(getContext());
                        rv.setLayoutManager(llm);

                        Sitios_Adapter adapter = new Sitios_Adapter(list, getContext());
                        rv.setAdapter(adapter);
                        progressDialog.dismiss();
                    }
                });


            }


        }).start();


        return v;


    }

}

