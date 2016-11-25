package org.fabio.serviflashproject.Adaptador;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.fabio.serviflashproject.R;
import org.fabio.serviflashproject.Util.General;
import org.fabio.serviflashproject.Util.ImagenesListView.ImageLoader;
import org.fabio.serviflashproject.mapaenvio;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by root on 22/11/16.
 */

public class AdaptadorPedidos extends ArrayAdapter{

    Activity context;
    ArrayList<JSONObject> listaGlobal;
    ArrayList<JSONObject> listaDatos;
    General gn;
    String idpedido;
    String idempledo;
    Button aceptar;
    View item;

    public AdaptadorPedidos(Activity context,ArrayList<JSONObject> lista){

        super(context, R.layout.item_pedido,lista);
        this.context = context;

        this.listaDatos = lista;
        gn = new General(context,null);

        listaGlobal = new ArrayList<>();
        listaGlobal.addAll(lista);

    }

    public View getView(final int position, View convertView, ViewGroup parent){
        item = convertView;
        //System.out.println("getview");
        ImageLoader cargarImagen = new ImageLoader(context);
        item = context.getLayoutInflater().inflate(R.layout.item_pedido, null);
        aceptar = (Button) item.findViewById(R.id.aceptar);
        TextView lblNombre = (TextView) item.findViewById(R.id.lblNombres);
        TextView lblDescripcion = (TextView) item.findViewById(R.id.lblDescripcion);
        TextView lblEstado = (TextView) item.findViewById(R.id.lblEstado);
        TextView lblPlaca = (TextView) item.findViewById(R.id.lblPlaca);
        //ImageView foto = (ImageView) item.findViewById(R.id.moto);

        try{

            lblNombre.setText(listaDatos.get(position).getString("nombreape"));
            lblDescripcion.setText(listaDatos.get(position).getString("descripcion")+"\n");
            lblPlaca.setText(listaDatos.get(position).getString("placamoto"));
            lblEstado.setText(listaDatos.get(position).getString("estado"));
            //cargarImagen.DisplayImage(listaDatos.get(position).getString("ruta"),foto);
            montarImagen(listaDatos.get(position).getString("ruta"));

        }catch (Exception ex){
            System.out.println("ERROR LISTA PETICIONES: "+ex.getMessage());
        }

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            try{
                idpedido = listaDatos.get(position).getString("id");
                idempledo = listaDatos.get(position).getString("idempleado");
                System.out.println(idempledo);
                Intent intent = new Intent(context, mapaenvio.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("Dato",idpedido);
                intent.putExtra("Idemplado",idempledo);
                context.startActivity(intent);
            }catch (Exception ex){
                System.out.println("ERROR: "+ex.getMessage());
            }
            }
        });

        return item;
    }

    private void montarImagen(final String ruta){
        new Thread(new Runnable() {
            @Override
            public void run() {
            URL newurl = null;
            Bitmap mIcon_val = null;
            try {
                newurl = new URL(ruta);
                mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                System.out.println("SE VA A DESCARGAR LA FOTO");
            } catch (IOException e) {
                System.out.println("ERROR EN FOTO "+e.getMessage());
                e.printStackTrace();
            }
            final Bitmap ftax = mIcon_val;
                context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    final ImageView img = ((ImageView)item.findViewById(R.id.moto));
                    Drawable drawable = new BitmapDrawable(ftax);
                    img.setImageDrawable(drawable);
                }
            });
            }
        }).start();
    }

}
