package org.fabio.serviflashproject;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.fabio.serviflashproject.Modelos.Cliente;
import org.fabio.serviflashproject.Services.WebServices;
import org.fabio.serviflashproject.Util.General;
import org.json.JSONObject;

import layout.configuracion;

public class perfil extends AppCompatActivity {

    General gn;
    EditText nombre,cedula,email,usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        gn = new General(this);
        initComponent();
        actualizarPerfill();

        String texto = getResources().getString(R.string.configuracionperfil);
        setTitle(texto);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarperfil);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) // Habilitar up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    public void initComponent(){
        nombre = (EditText)findViewById(R.id.txtnombre);
        cedula = (EditText)findViewById(R.id.txtcedula);
        email = (EditText)findViewById(R.id.txtemail);
        usuario = (EditText) findViewById(R.id.txtuser);
    }

    public void actualizarPerfill(){
        gn.initCargando("Cargando Datos Del Perfil");
        new Thread(new Runnable() {
            @Override
            public void run() {
                WebServices web = new WebServices();
                final JSONObject j = web.miperfil(gn.getIdCliente());
                gn.finishCargando();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (j != null) {
                            nombre.setText(j.optString("nombreape"));
                            cedula.setText(j.optString("cedula"));
                            email.setText(j.optString("email"));
                            usuario.setText(j.optString("login"));
                        }
                    }
                });
            }
        }).start();
    }

    public void modificar(View view){
        final Cliente c = new Cliente();
        new Thread(new Runnable() {
            @Override
            public void run() {
                c.setNombreape(nombre.getText().toString());
                c.setCedula(cedula.getText().toString());
                c.setEmail(email.getText().toString());
                c.setLogin(usuario.getText().toString());
                WebServices web = new WebServices();
                final JSONObject j = web.Modificarcliente(c,gn.getIdCliente());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            if (j == null) {
                                Toast.makeText(perfil.this, "Error en el servidor", Toast.LENGTH_SHORT).show();
                            }else{
                                if(j.getInt("std") == 1){
                                    Toast.makeText(perfil.this, j.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }catch (Exception ex){
                            Toast.makeText(perfil.this, "Error webSerice login, "+ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:

                Intent intent = new Intent(perfil.this,menuinicial.class);
                intent.putExtra("perfil",1);
                startActivity(intent);
                finish();

                /*configuracion confi = new configuracion();
                getSupportFragmentManager().beginTransaction().replace(R.id.fgl,confi).commit();
                String texto = getResources().getString(R.string.perfil);
                setTitle(texto);*/

            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
