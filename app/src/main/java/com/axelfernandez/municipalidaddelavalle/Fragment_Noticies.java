package com.axelfernandez.municipalidaddelavalle;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Noticies extends Fragment {
Context context = getContext();
     ProgressBar progressDialog;
View v;
    HttpURLConnection connection;

    public Fragment_Noticies() {
        //new MiTarea().execute();
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_fragment__noticies, container, false);
       View nc = inflater.inflate(R.layout.no_conexion, container,false);
        progressDialog= v.findViewById(R.id.progressBarNoticias);




        RecyclerView rv = v.findViewById(R.id.noticias_rv);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        List<Noticias_Entity> list = new ArrayList<>();
      MiTarea tn = new MiTarea();
       tn.execute();
        try {
            list =tn.get();
            if (list.get(0).getTitulo().equals("No hay conexion a internet")){progressDialog.setVisibility(View.GONE); return nc; }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        progressDialog.setVisibility(View.INVISIBLE);
        Noticias_Adapter adapter = new Noticias_Adapter(list, getContext());
        rv.setAdapter(adapter);


        return v;


}



    public boolean conectadoAInternet() throws InterruptedException, IOException
    {
        String comando = "ping -c 1 google.com";
        return (Runtime.getRuntime().exec (comando).waitFor() == 0);
    }

     class MiTarea extends AsyncTask<String, Boolean, List<Noticias_Entity>> {
        List<Noticias_Entity> list = new ArrayList<>();

        protected void onPreExecute() {
            progressDialog.setVisibility(View.VISIBLE);
        }

        protected List<Noticias_Entity> doInBackground(String... urls) {
            List<Noticias_Entity> data=null;

            try {
                if (conectadoAInternet()) {

                    try{
                        URL url1 = new URL("http://lavallemendoza.gob.ar/public/webservice/noticias");

                        connection = (HttpURLConnection) url1.openConnection();
                        connection.connect();
                        InputStream inputStream= connection.getInputStream();

                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        String line = "";
                        StringBuffer buffer = new StringBuffer();
                        while ((line=reader.readLine())!=null){

                            buffer.append(line);
                            String result = buffer.toString();
                            JSONArray jsonArray = new JSONArray(result);
                            data = new ArrayList<>();

                            for (int i =0; i<19;i++){
                                Noticias_Entity dataModel = new Noticias_Entity();
                                JSONObject jsonObject =jsonArray.getJSONObject(i);
                                String name = jsonObject.getString("titulo");
                                String id= jsonObject.getString("id");
                                String copete = jsonObject.getString("copete");
                                String fecha = jsonObject.getString("fecha");
                                String imagen = jsonObject.getString("imagennota");
                                //  int ids = Integer.parseInt(id);
                                dataModel.setId(id);
                                dataModel.setTitulo(name);
                                dataModel.setCopete(copete);
                                dataModel.setFecha(fecha);
                                dataModel.setUrlimagen(imagen);

                                //String desciption = jsonObject.getString("format");

                                data.add(dataModel);


                            }


                        }


                    } catch (MalformedURLException e) {
                        e.printStackTrace();

                    } catch (IOException e) {
                        e.printStackTrace();

                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                    return data;


                }
                else{

                Noticias_Entity nc = new Noticias_Entity();
                    nc.setTitulo("No hay conexion a internet");
                    data.add(nc);

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return list;
        }


        }



    }






