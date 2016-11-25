package org.fabio.serviflashproject;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class CambiarPass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_pass);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        String texto = getResources().getString(R.string.configuracionperfil);
        setTitle(texto);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarpass);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) // Habilitar up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:

                Intent intent = new Intent(CambiarPass.this,menuinicial.class);
                intent.putExtra("pass",1);
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
