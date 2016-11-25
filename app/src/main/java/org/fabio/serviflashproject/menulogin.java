package org.fabio.serviflashproject;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.fabio.serviflashproject.Modelos.Cliente;
import org.fabio.serviflashproject.Services.WebServices;
import org.fabio.serviflashproject.Util.General;
import org.json.JSONObject;

public class menulogin extends AppCompatActivity {

    EditText txtEmail,txtPass;
    General gn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menulogin);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initComponent();
        gn = new General(this,null);
        validarSesion();
    }

    private void initComponent(){
        txtEmail = (EditText) findViewById(R.id.email);
        txtPass = (EditText) findViewById(R.id.passwordingreso);
        //loginButton = (LoginButton)findViewById(R.id.login_button);
    }

    private void validarSesion(){
        if(gn.sesion()){
            Intent i = new Intent(menulogin.this,menuinicial.class);
            i.putExtra("ban",0);
            startActivity(i);
            finish();
        }
    }

    public boolean sesion(){
        if(txtEmail.getText().toString().equals("") && txtPass.getText().toString().equals(""))
            return true;
        else
            return false;
    }

    public void iniciar(View view){
        final Cliente c = new Cliente();
        c.setEmail(txtEmail.getText().toString());
        c.setPass(txtPass.getText().toString());
        if(sesion()){
            Toast.makeText(menulogin.this, "Los campos son obligatorios", Toast.LENGTH_SHORT).show();
        }else{
            gn.initCargando("Iniciando sesión...");
            new Thread(new Runnable() {
                @Override
                public void run() {
                WebServices cs = new WebServices();
                final JSONObject j = cs.login(c);
                gn.finishCargando();
                System.out.println(j);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    try {
                        if (j == null) {
                            Toast.makeText(menulogin.this, "Error en el servidor", Toast.LENGTH_SHORT).show();
                        } else{
                            if(j.getInt("message") == 3){

                                JSONObject temp = j.getJSONObject("admin");
                                Toast.makeText(menulogin.this,"Bienvenido "+temp.getString("nombreape"),Toast.LENGTH_SHORT).show();
                                Cliente c = new Cliente();
                                c.setId(temp.getInt("id"));
                                c.setEmail(temp.getString("email"));
                                c.setNombreape(temp.getString("nombreape"));
                                c.setSexo(temp.getString("sexo"));
                                c.setPass(txtPass.getText().toString());
                                gn.guardarCliente(c,false);
                                Intent i = new Intent(menulogin.this,menuinicial.class);
                                startActivity(i);
                                finish();
                            }if(j.getInt("message") == 2){
                                Toast.makeText(menulogin.this, "Clave Incorrecta", Toast.LENGTH_SHORT).show();
                            }if(j.getInt("message") == 1){
                                Toast.makeText(menulogin.this, "Usuario no existe en el Sistema", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }catch (Exception ex){
                        Toast.makeText(menulogin.this, "Error webSerice login, "+ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    }
                });

                }
            }).start();
        }
    }

    public void irregistrar(View view){
        //Intent i = new Intent(this,registro.class);
        //startActivity(i);
    }

}
