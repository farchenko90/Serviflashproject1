package layout;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.fabio.serviflashproject.Modelos.Pedido;
import org.fabio.serviflashproject.R;
import org.fabio.serviflashproject.Services.WebServices;
import org.fabio.serviflashproject.Util.GPService;
import org.fabio.serviflashproject.Util.General;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class mapapedido extends Fragment implements OnMapReadyCallback {

    View rootView;
    public static GoogleMap mapa;
    public static Marker marker;
    public static Marker markerdestino;
    public static Activity myActivity;
    public double lat;
    public double log;
    public EditText descripcion,destino,barrio;
    public Button boton,solictud;
    General gn;
    public LatLng envio;

    public mapapedido() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_mapapedido, container, false);
        gn = new General(myActivity,null);
        initComponent();

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscardireccion();
            }
        });

        solictud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solicitudservicio();
            }
        });

        return rootView;
    }

    public void initComponent() {
        ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapapedido)).getMapAsync(this);
        destino = (EditText) rootView.findViewById(R.id.destino);
        boton = (Button) rootView.findViewById(R.id.buscar);
        solictud = (Button) rootView.findViewById(R.id.solicitar);
        descripcion = (EditText) rootView.findViewById(R.id.descripcion);
        barrio = (EditText) rootView.findViewById(R.id.barrio);
        //texto = (TextView) rootView.findViewById(R.id.)
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myActivity = (AppCompatActivity) activity;
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            Intent intent = new Intent(myActivity.getBaseContext() , GPService.class);
            GPService.ban = true;
            GPService.context = myActivity;
            GPService.gn = gn;
            myActivity.startService(intent);
        }catch(Exception ex){
            System.out.println("ERROR INICIAR SERVICIO GPS: "+ex.getMessage());
        }
    }

    public void onResume(){
        super.onResume();
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION,myActivity.getApplicationContext(),myActivity)) {
            obtenerposicion();
        }
        else
        {
            requestPermission(Manifest.permission.ACCESS_FINE_LOCATION,1,myActivity.getApplicationContext(),myActivity);
        }

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (ActivityCompat.checkSelfPermission(myActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(myActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        //obtenerposicion();

    }

    public static void requestPermission(String strPermission, int perCode, Context _c, Activity _a){

        if (ActivityCompat.shouldShowRequestPermissionRationale(_a,strPermission)){
            Toast.makeText(myActivity,"No tienes permiso de GPS",Toast.LENGTH_LONG).show();
        } else {

            ActivityCompat.requestPermissions(_a,new String[]{strPermission},perCode);
        }
    }

    public static boolean checkPermission(String strPermission,Context _c,Activity _a){
        int result = ContextCompat.checkSelfPermission(_c, strPermission);
        if (result == PackageManager.PERMISSION_GRANTED){

            return true;

        } else {

            return false;

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        //mapa.setMyLocationEnabled(true);
        mapa.getUiSettings().setZoomControlsEnabled(true);
        mapa.getUiSettings().setCompassEnabled(true);
        mapa.getUiSettings().setMapToolbarEnabled(true);

        //gpstrackets.valemonda(); asi es por static
        //gps.iniciandoprocesos();
        //gps.obtenerposicion(mapa,myActivity);

    }

    public void buscardireccion(){
        if(destino.getText().toString().equals("")){
            Toast.makeText(myActivity,"El campo de direccion es obligatorio",Toast.LENGTH_LONG).show();
        }else{
            String direccion = destino.getText().toString();
            obtenerdireccion(direccion);
        }
    }


    public void obtenerdireccion(String dire){
        Geocoder geo = new Geocoder(getContext(), Locale.getDefault());
        try {
            String direccion = dire+", Valledupar, Cesar";
            List<Address> address = null;
            address = geo.getFromLocationName(direccion,1);
            System.out.println("adress: "+address);
            System.out.println("Lat"+address.get(0).getLatitude());
            System.out.println("Lon"+address.get(0).getLongitude());
            lat = address.get(0).getLatitude();
            log = address.get(0).getLongitude();
            envio = new LatLng(lat,log);
            LatLng pos = new LatLng(lat,log);
            if(markerdestino != null){
                markerdestino.remove();
            }
            markerdestino = mapa.addMarker(new MarkerOptions()
                    .position(pos)
                    .title("Direccion de envio")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_action_room)));
            mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 16.5f));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void obtenerposicion(){
        //System.out.println("ENTRO A LA FUNCION");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    while (GPService.posicion == null){
                        Thread.sleep(500);
                    }
                    //gn.finishCargando();
                    myActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //gn.finishCargando();
                            if(marker != null){
                                marker.remove();
                            }

                            LatLng pos = GPService.posicion;
                            System.out.println("mi pos: "+GPService.posicion);
                            if(pos != null){
                                System.out.println("mi pos: "+pos);
                                //gn.finishCargando();
                                if(mapa != null){
                                    //System.out.println("mi posicion actual: "+pos);
                                    marker = mapa.addMarker(new MarkerOptions()
                                            .position(pos)
                                            .title("Mi posicion actual")
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_1465164822_pin)));
                                    mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 13.0f));
                                }else{
                                    System.out.println("Error posicion perdida");
                                }
                                //gn.finishCargando();
                            }
                        }
                    });


                }catch(Exception e){
                    System.out.println("ERROR POSICION: " + e.getMessage());
                }
            }
        }).start();

    }

    public void solicitudservicio(){
        if(descripcion.getText().toString().equals("") || destino.getText().toString().equals("")){
            Toast.makeText(myActivity, "Los campos son obligatorios", Toast.LENGTH_SHORT).show();
        }else{
            final Pedido p = new Pedido();
            gn.initCargando("validando Solicitud");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    p.setOrigenlat(GPService.posicion.latitude);
                    p.setOrigenlong(GPService.posicion.longitude);
                    p.setDestinolat(lat);
                    p.setDestinolong(log);
                    p.setDescripcion(descripcion.getText().toString());
                    p.setDirecciondestino(destino.getText().toString());
                    p.setBarrio(barrio.getText().toString());
                    p.setDireccionorigen(gn.ciudad);
                    p.setIdcliente(gn.getIdCliente());
                    WebServices cs = new WebServices();
                    final JSONObject j = cs.solicitarpedido(p);
                    gn.finishCargando();
                    myActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (j == null) {
                                    Toast.makeText(myActivity, "Error en el servidor", Toast.LENGTH_SHORT).show();
                                }else{
                                    if(j.getInt("msg") == 1){
                                        Toast.makeText(myActivity, j.getString("message"), Toast.LENGTH_SHORT).show();
                                        limpiarcampos();
                                    }else if(j.getInt("msg") == 2){
                                        Toast.makeText(myActivity, j.getString("message"), Toast.LENGTH_SHORT).show();
                                        limpiarcampos();
                                    }
                                }
                            }catch (Exception ex){
                                Toast.makeText(myActivity, "Error webSerice login, "+ex.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }).start();
        }
    }

    public void limpiarcampos(){
        descripcion.getText().toString().equals("");
        destino.getText().toString().equals("");
    }

}
