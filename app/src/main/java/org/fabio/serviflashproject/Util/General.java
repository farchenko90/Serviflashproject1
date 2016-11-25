//package corp.app.com.serviflash_cliente.Util;
package org.fabio.serviflashproject.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.fabio.serviflashproject.Modelos.Cliente;
import org.fabio.serviflashproject.R;
import org.fabio.serviflashproject.Services.Server;
import org.fabio.serviflashproject.Util.ImagenesListView.ImageLoader;


/**
 * Created by fabio on 5/12/15.
 */
public class General {

    public static Context contexto;
    public static FragmentManager fragManager;
    private Activity context;
    private ProgressDialog dialogCargando;
    private FragmentActivity myContext;
    public static String ciudad;

    public General(Activity context){

        this.contexto = context;
    }

    public General(Context context, View v){
        this.contexto = context;
        if(v != null){
            ImageLoader cargarImagen = new ImageLoader(context);
            Cliente cl = cargarCliente();
            ((TextView)v.findViewById(R.id.username)).setText(cl.getNombreape());
            ((TextView)v.findViewById(R.id.email)).setText(cl.getEmail());
            final ImageView imgSector = ((ImageView)v.findViewById(R.id.circle_image));
            if(cl.getSexo().equals("M")){
                cargarImagen.DisplayImage(Server.rutaimg+"/img/man.png",imgSector);
            }else{
                cargarImagen.DisplayImage(Server.rutaimg+"/img/female.png",imgSector);
            }
        }
    }

    public void mostrarDialog(String titulo, String mensaje, boolean cancelable){
        AlertDialog.Builder b = new AlertDialog.Builder(contexto);
        b.setTitle(titulo);
        b.setMessage(mensaje);
        b.setCancelable(cancelable);
        b.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog al = b.create();
        al.show();
    }

    public void initCargando(String mensaje){
        dialogCargando = new ProgressDialog(contexto);
        dialogCargando.setMessage(mensaje);
        dialogCargando.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialogCargando.setCancelable(false);
        dialogCargando.show();
    }



    public void finishCargando(){
        dialogCargando.dismiss();
    }

    public void guardarCliente(Cliente c,boolean face){
        SharedPreferences prefs = contexto.getSharedPreferences("serviflash", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("id", c.getId());
        editor.putString("email", c.getEmail());
        editor.putString("sexo",c.getSexo());
        editor.putBoolean("cuenta", true);
        editor.putString("nombreape", c.getNombreape());
        editor.putString("pass", c.getPass());
        editor.putString("idface",c.getIdface());
        editor.putBoolean("face",face);
        editor.commit();
    }

    public int getIdCliente(){
        SharedPreferences prefs = contexto.getSharedPreferences("serviflash", Context.MODE_PRIVATE);
        return prefs.getInt("id", 0);
    }

    public Cliente cargarCliente(){
        SharedPreferences prefs = contexto.getSharedPreferences("serviflash", Context.MODE_PRIVATE);
        Cliente c = new Cliente();
        c.setId(prefs.getInt("id",0));
        c.setEmail(prefs.getString("email", null));
        c.setSexo(prefs.getString("sexo", null));
        c.setNombreape(prefs.getString("nombreape", null));
        c.setPass(prefs.getString("pass", null));
        return c;
    }

    public void quitarCuenta(){
        System.out.println("cuenta false");
        SharedPreferences prefs = contexto.getSharedPreferences("serviflash", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("cuenta", false);
        editor.commit();
    }

    public boolean sesion(){
        SharedPreferences prefs = contexto.getSharedPreferences("serviflash", Context.MODE_PRIVATE);
        return prefs.getBoolean("cuenta", false);
    }


}
