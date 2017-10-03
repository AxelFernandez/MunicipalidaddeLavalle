package com.axelfernandez.municipalidaddelavalle;


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
public class Fragment_Sitios extends Fragment {
    ProgressBar progressDialog;
    RecyclerView rv;
    List<Sitios_Entity> list =new ArrayList<>();
    public Fragment_Sitios() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragment__sitios, container, false);
        View nc = inflater.inflate(R.layout.no_conexion, container,false);
        rv = v.findViewById(R.id.sitios_rv);
        progressDialog=v.findViewById(R.id.progressBar);

        MiTarea tn = new MiTarea();
        tn.execute();
        try {
            list= tn.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if (list.get(0).getTitulo().equals("No hay conexion a internet")){ return nc; }


        return v;


    }





    class MiTarea extends AsyncTask<String, Boolean, List<Sitios_Entity>> {
        List<Sitios_Entity> list = new ArrayList<>();



        @Override
        protected void onPreExecute() {

            progressDialog.setVisibility(View.VISIBLE);



        }



        protected List<Sitios_Entity> doInBackground(String... urls) {


            List<Sitios_Entity> data=new ArrayList<>();
            try {
                if (conectadoAInternet()) {

                    HttpURLConnection connection;






                    try{
                        URL url1 = new URL("http://lavallemendoza.gob.ar/public/webservice/sitios");

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

                            for (int i =0; i<buffer.length();i++){

                                Sitios_Entity dataModel = new Sitios_Entity();
                                JSONObject jsonObject =jsonArray.getJSONObject(i);
                                String title = jsonObject.getString("titulo");
                                String content= jsonObject.getString("contenido");
                                content.replace("\r","");
                                String type = jsonObject.getString("tipo");

                                String image;
                                JSONArray imagea = jsonObject.getJSONArray("imagenes");
                                if(imagea.isNull(0)){
                                    image="/public/img/header/logo.png";


                                }
                                else{image = imagea.getString(0);}


                                dataModel.setContenido(content);
                                dataModel.setTitulo(title);
                                dataModel.setTipo(type);
                                dataModel.setImagenes(image);
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

                    Sitios_Entity nc = new Sitios_Entity();
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

        @Override
        protected void onPostExecute(List<Sitios_Entity> sitios_entities) {

            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            rv.setLayoutManager(llm);

            Sitios_Adapter adapter = new Sitios_Adapter(sitios_entities, getContext());
            rv.setAdapter(adapter);
            progressDialog.setVisibility(View.INVISIBLE);
        }


        public boolean conectadoAInternet() throws InterruptedException, IOException
        {
            String comando = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec (comando).waitFor() == 0);
        }

    }


}
