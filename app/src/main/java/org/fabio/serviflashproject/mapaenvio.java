package org.fabio.serviflashproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.fabio.serviflashproject.Services.WebServices;
import org.fabio.serviflashproject.Util.General;
import org.json.JSONObject;

public class mapaenvio extends AppCompatActivity implements OnMapReadyCallback {

    General gn;
    public GoogleMap map;
    int id,idempleado;
    boolean ban = true;

    Double origenlat,origenlong;
    LatLng origen;
    Double destinolat,destinolong;
    LatLng destino;
    Double mensajerolat,mensajerolng;
    LatLng mensajero;
    Marker markerorigen,markerdestino,markermensajero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapaenvio);
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapaenvio)).getMapAsync(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarenvio);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) // Habilitar up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String texto = getResources().getString(R.string.mapaenvio);
        setTitle(texto);

        gn = new General(this,null);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Intent intent1 = getIntent();
        Bundle bundle1 = intent.getExtras();
        if(bundle != null){
            String datos = bundle.getString("Dato");
            id = Integer.parseInt(datos);
            String datosemp = bundle1.getString("Idemplado");
            idempleado = Integer.parseInt(datosemp);
        }

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                Intent intent = new Intent(mapaenvio.this,menuinicial.class);
                startActivity(intent);
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void onDestroy(){
        super.onDestroy();
        ban = false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        this.map.getUiSettings().setZoomControlsEnabled(true);
        this.map.getUiSettings().setCompassEnabled(true);
        this.map.getUiSettings().setMapToolbarEnabled(true);
        posicionpedido();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    while (ban){
                        Thread.sleep(30000);
                        posicionmensajero();
                    }
                }catch (Exception ex){
                    System.out.println(ex.getMessage());
                }
            }
        }).start();
        posicionmensajero();
    }

    private void posicionpedido(){
        //System.out.println("id "+idpedido);

        new Thread(new Runnable() {
            @Override
            public void run() {
                final WebServices cs = new WebServices();
                final JSONObject j = cs.pedido(id);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (j == null) {
                                Toast.makeText(mapaenvio.this, "No hay pedidos realizados", Toast.LENGTH_SHORT).show();
                            } else {
                                origenlat = Double.parseDouble(j.getString("origenlat"));
                                origenlong = Double.parseDouble(j.getString("origenlong"));
                                origen = new LatLng(origenlat,origenlong);
                                destinolat = Double.parseDouble(j.getString("destinolat"));
                                destinolong = Double.parseDouble(j.getString("destinolong"));
                                destino = new LatLng(destinolat,destinolong);
                                mostrarposicion();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(mapaenvio.this, "Error webSerice Principal, " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }

    public void mostrarposicion(){

        markerorigen = map.addMarker(new MarkerOptions()
                .position(origen)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_action_room))
                .title("Origen del pedido"));
        CameraPosition cameraPosition = CameraPosition.builder()
                .target(origen)
                .zoom(13)
                .build();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        markerdestino = map.addMarker(new MarkerOptions()
                .position(destino)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_1465164822_pin))
                .title("Destino del pedido"));
        CameraPosition cameradestino = CameraPosition.builder()
                .target(destino)
                .zoom(13)
                .build();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    private void posicionmensajero(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final WebServices cs = new WebServices();
                final JSONObject j = cs.mensajero(idempleado);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (j == null) {
                                Toast.makeText(mapaenvio.this, "No hay pedidos realizados", Toast.LENGTH_SHORT).show();
                            } else {
                                mensajerolat = Double.parseDouble(j.getString("latitud"));
                                mensajerolng = Double.parseDouble(j.getString("longitud"));
                                mensajero = new LatLng(mensajerolat,mensajerolng);
                                mostarposicionmensajero();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(mapaenvio.this, "Error webSerice Principal, " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }

    public void mostarposicionmensajero(){
        markermensajero = map.addMarker(new MarkerOptions()
                .position(mensajero)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.motogps))
                .title("Mensajero Asignado"));
        CameraPosition cameraPosition = CameraPosition.builder()
                .target(mensajero)
                .zoom(13)
                .build();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

}
