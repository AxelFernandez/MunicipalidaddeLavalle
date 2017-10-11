package com.axelfernandez.municipalidaddelavalle;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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

public class webView_Noticias extends AppCompatActivity {
    WebSettings webSettings;
    WebView myWebView;
    HttpURLConnection connection;
    String cuerpo = "";
    ProgressDialog pg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         pg = new ProgressDialog(this);
        pg.setCancelable(false);

        setContentView(R.layout.activity_web_view__noticias);
        final TextView copetes = (TextView) findViewById(R.id.noticia_completa_copete);

        final TextView tcuerpo= (TextView) findViewById(R.id.noticia_completa_cuerpo);
        final ImageView imagen= (ImageView) findViewById(R.id.noticia_completa_imagen);



        final String id = getIntent().getExtras().getString("id");
       final String titulo = getIntent().getExtras().getString("title");
        final String copete = getIntent().getExtras().getString("copete");
        final String imagenurl = getIntent().getExtras().getString("imagen");


        getSupportActionBar().setTitle(titulo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pg.setMessage("Cargando");
        pg.show();
        new Thread(new Runnable() {
            public void run() {
            try{

                URL url1 = new URL("http://lavallemendoza.gob.ar/public/webservice/noticia/id/"+id);

                connection = (HttpURLConnection) url1.openConnection();
                connection.connect();
                InputStream inputStream= connection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                StringBuffer buffer = new StringBuffer();
                while ((line=reader.readLine())!=null) {

                    buffer.append(line);
                    String result = buffer.toString();
                    JSONArray jsonArray = new JSONArray(result);
                    JSONObject jsonObject =jsonArray.getJSONObject(0);
                    cuerpo = jsonObject.getString("contenido");

                }


                } catch (MalformedURLException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();

                } catch (JSONException e) {
                    e.printStackTrace();


                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        copetes.setText(copete);
                        tcuerpo.setText(cuerpo);


                        pg.dismiss();
                    }
                });
            }


        }).start();
        Picasso.with(getApplicationContext()).load("http://lavallemendoza.gob.ar"+imagenurl).into(imagen);
    }



}
