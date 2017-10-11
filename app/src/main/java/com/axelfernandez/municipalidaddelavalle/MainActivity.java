package com.axelfernandez.municipalidaddelavalle;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import static com.axelfernandez.municipalidaddelavalle.R.menu.main;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    MaterialSearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        Fragment_Noticies fn = new Fragment_Noticies();
        fragmentTransaction.replace(R.id.content_frame, fn);
        fragmentTransaction.commit();


       // StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
       // StrictMode.setThreadPolicy(policy);






        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


         searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                FragmentManager fragmentManager;
                FragmentTransaction fragmentTransaction;
                Bundle bdle= new Bundle();
                bdle.putString("Query", query);
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Fragment_seach fh = new Fragment_seach();
                fragmentTransaction.replace(R.id.content_frame, fh);
                fragmentTransaction.commit();
                fh.setArguments(bdle);
                getSupportActionBar().setTitle("Resultados de: "+query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
               }
            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_desp_history) {


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ProgressDialog progressDialog = new ProgressDialog(this);

        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        if (id == R.id.nav_desp_history) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            FragmentHistoria fh = new FragmentHistoria();
            fragmentTransaction.replace(R.id.content_frame, fh);
            fragmentTransaction.commit();

            getSupportActionBar().setTitle("Historia");


        } else if (id == R.id.nav_dep_eco) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            Fragment_UbicacionGeografica ug = new Fragment_UbicacionGeografica();
            fragmentTransaction.replace(R.id.content_frame, ug);
            fragmentTransaction.commit();
            getSupportActionBar().setTitle("Ubicacion Geografica");

        }
        else if (id== R.id.nav_not_nov){

            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            Fragment_Noticies fn = new Fragment_Noticies();
            fragmentTransaction.replace(R.id.content_frame, fn);
            fragmentTransaction.commit();
            getSupportActionBar().setTitle("Noticias");

        }
        else if (id==R.id.nav_tur_interes){

            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            Fragment_Sitios fs = new Fragment_Sitios();
            fragmentTransaction.replace(R.id.content_frame, fs);

            fragmentTransaction.commit();
            getSupportActionBar().setTitle("Sitios");


        }
else if (id== R.id.nav_tur_aloj){

            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            Fragment_Alojamientos fs = new Fragment_Alojamientos();
            fragmentTransaction.replace(R.id.content_frame, fs);

            fragmentTransaction.commit();
            getSupportActionBar().setTitle("Alojamiento");
        }
        else if (id==R.id.nav_tur_gastr){


            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            Fragment_Gastronomia fs = new Fragment_Gastronomia();
            fragmentTransaction.replace(R.id.content_frame, fs);

            fragmentTransaction.commit();
            getSupportActionBar().setTitle("Gastronomia");
        }
        else if(id==R.id.nav_tur_infor){

            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            Fragment_ContactoInformes fs = new Fragment_ContactoInformes();
            fragmentTransaction.replace(R.id.content_frame, fs);

            fragmentTransaction.commit();
            getSupportActionBar().setTitle("Contacto e Informes");


        }
        else if(id==R.id.nav_tweb_info){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            Fragment_Telefonos fs = new Fragment_Telefonos();
            fragmentTransaction.replace(R.id.content_frame, fs);
            fragmentTransaction.commit();
            getSupportActionBar().setTitle("Telefonos Utiles");

        }
        else if (id==R.id.nav_colect){

            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            Fragment_HorariosColectivos fs = new Fragment_HorariosColectivos();
            fragmentTransaction.replace(R.id.content_frame, fs);
            fragmentTransaction.commit();
            getSupportActionBar().setTitle("Horarios de Colectivos");
        }
        drawer.closeDrawer(GravityCompat.START);


        return true;

    }


}



