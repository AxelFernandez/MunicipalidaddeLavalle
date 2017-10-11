package com.axelfernandez.municipalidaddelavalle;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_seach extends Fragment {
    ProgressDialog progressDialog;
    View v;
    HttpURLConnection connection;
    List<Noticias_Entity> data = new ArrayList<>();

    public Fragment_seach() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_fragment__noticies, container, false);
        View nc = inflater.inflate(R.layout.no_conexion, container,false);
        progressDialog= new ProgressDialog(getContext());
        progressDialog.setMessage("Buscando");
        progressDialog.show();
        progressDialog.setCancelable(false);

        Bundle datab= getArguments();

        final String query=datab.getString("Query");
        Log.e("q", query);



        final RecyclerView rv = v.findViewById(R.id.noticias_rv);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        new Thread(new Runnable() {
            public void run() {
                List<Noticias_Entity> list = new ArrayList<>();




                try {
                    URL url1 = new URL("http://lavallemendoza.gob.ar/public/webservice/noticias-todas");

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
                        data = new ArrayList<>();
                        JSONObject jsonObjects = jsonArray.getJSONObject(0);
                        String ids = jsonObjects.getString("id");

                        for (int i = 0; i < Integer.valueOf(ids); i++) {
                            Noticias_Entity dataModel = new Noticias_Entity();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String name = jsonObject.getString("titulo");
                            String id = jsonObject.getString("id");
                            String copete = jsonObject.getString("copete");
                            String imagen = jsonObject.getString("imagennota");
                            String fecha= jsonObject.getString("fecha");

                            String names = limpiarAcentos(name);

                            Pattern regex = Pattern.compile("\\b" + Pattern.quote(query) + "\\b", Pattern.CASE_INSENSITIVE);
                            Matcher match = regex.matcher(names);

                            if (match.find()) {
                            dataModel.setId(id);
                            dataModel.setTitulo(name);
                            dataModel.setCopete(copete);
                            dataModel.setFecha(fecha);
                            dataModel.setUrlimagen(imagen);

                            //String desciption = jsonObject.getString("format");

                            data.add(dataModel);}


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
                        Noticias_Adapter adapter = new Noticias_Adapter(data, getContext());
                        rv.setAdapter(adapter);
                        progressDialog.dismiss();
                    }
                });


            }


        }).start();




        return v;
    }
    public static String limpiarAcentos(String cadena) {
        String limpio =null;
        if (cadena !=null) {
            String valor = cadena;
            valor = valor.toUpperCase();
            // Normalizar texto para eliminar acentos, dieresis, cedillas y tildes
            limpio = Normalizer.normalize(valor, Normalizer.Form.NFD);
            // Quitar caracteres no ASCII excepto la enie, interrogacion que abre, exclamacion que abre, grados, U con dieresis.
            limpio = limpio.replaceAll("[^\\p{ASCII}(N\u0303)(n\u0303)(\u00A1)(\u00BF)(\u00B0)(U\u0308)(u\u0308)]", "");
            // Regresar a la forma compuesta, para poder comparar la enie con la tabla de valores
            limpio = Normalizer.normalize(limpio, Normalizer.Form.NFC);
        }
        return limpio;
    }
}
