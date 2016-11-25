package org.fabio.serviflashproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.fabio.serviflashproject.Modelos.Cliente;
import org.fabio.serviflashproject.Services.WebServices;
import org.fabio.serviflashproject.Util.General;
import org.fabio.serviflashproject.Util.Notificaciones.initNotificacion;
import org.json.JSONObject;

import layout.configuracion;
import layout.listapedido;
import layout.mapadistinto;
import layout.mapapedido;

import static org.fabio.serviflashproject.R.id.listapedidos;

public class menuinicial extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    General gn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuinicial);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navHeaderView = navigationView.getHeaderView(0);
        gn = new General(this,navHeaderView);

        new initNotificacion(this).initPush();

    }

    public void actualizarmenuperfil(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                WebServices web = new WebServices();
                final JSONObject j = web.miperfil(gn.getIdCliente());
                //gn.finishCargando();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (j != null) {
                            Cliente c = new Cliente();
                            c.setId(j.optInt("id"));
                            c.setEmail(j.optString("email"));
                            c.setNombreape(j.optString("nombreape"));
                            c.setSexo(j.optString("sexo"));
                            //c.setPass();
                            gn.guardarCliente(c,false);
                        }
                    }
                });
            }
        }).start();
    }

    public void onStart(){
        super.onStart();
        actualizarmenuperfil();
        listapedido gps = new listapedido();
        getSupportFragmentManager().beginTransaction().replace(R.id.fgl,gps).commit();
        String texto = getResources().getString(R.string.home);
        setTitle(texto);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            int perfil = bundle.getInt("perfil");
            System.out.println("entro a: "+perfil);
            if(perfil == 1){
                configuracion confi = new configuracion();
                getSupportFragmentManager().beginTransaction().replace(R.id.fgl,confi).commit();
                String texto1 = getResources().getString(R.string.perfil);
                setTitle(texto1);
            }

        }

        Intent intentpass = getIntent();
        Bundle bundlepass = intentpass.getExtras();
        if(bundlepass != null){
            int pass = bundle.getInt("pass");
            if(pass == 1){
                configuracion confi = new configuracion();
                getSupportFragmentManager().beginTransaction().replace(R.id.fgl,confi).commit();
                String texto1 = getResources().getString(R.string.perfil);
                setTitle(texto1);
            }

        }

        Intent intentfoto = getIntent();
        Bundle bundlefoto = intentfoto.getExtras();
        if(bundlefoto != null){
            int pass = bundlefoto.getInt("foto");
            if(pass == 1){
                configuracion confi = new configuracion();
                getSupportFragmentManager().beginTransaction().replace(R.id.fgl,confi).commit();
                String texto1 = getResources().getString(R.string.perfil);
                setTitle(texto1);
            }

        }

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
        getMenuInflater().inflate(R.menu.menuinicial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            listapedido gps = new listapedido();
            getSupportFragmentManager().beginTransaction().replace(R.id.fgl,gps).commit();
            String texto = getResources().getString(R.string.home);
            setTitle(texto);
        } else if (id == R.id.nav_gallery) {
            mapapedido gpsexample = new mapapedido();
            getSupportFragmentManager().beginTransaction().replace(R.id.fgl,gpsexample).commit();
            String texto = getResources().getString(R.string.pedido);
            setTitle(texto);
        } else if (id == R.id.nav_slideshow) {
            mapadistinto distinto = new mapadistinto();
            getSupportFragmentManager().beginTransaction().replace(R.id.fgl,distinto).commit();
            String texto = getResources().getString(R.string.pedido2);
            setTitle(texto);
        } else if (id == R.id.nav_manage) {
            configuracion confi = new configuracion();
            getSupportFragmentManager().beginTransaction().replace(R.id.fgl,confi).commit();
            String texto = getResources().getString(R.string.perfil);
            setTitle(texto);
        } else if (id == R.id.nav_exit) {
            AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setTitle("serviflash");
            b.setMessage("Quieres cerrar la sesión ?");
            b.setCancelable(false);
            b.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    gn.quitarCuenta();
                    dialog.cancel();
                    //LoginManager.getInstance().logOut();
                    Intent i = new Intent(getApplicationContext(),menulogin.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }
            });
            b.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            AlertDialog al = b.create();
            al.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
