package layout;


import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.fabio.serviflashproject.Adaptador.AdaptadorPedidos;
import org.fabio.serviflashproject.R;
import org.fabio.serviflashproject.Services.WebServices;
import org.fabio.serviflashproject.Util.General;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class listapedido extends Fragment {

    public TextView txt;
    public Context context;
    View rootView;
    ListView listaPedidos;
    ProgressBar pb;
    ImageView lblEstado;
    ArrayList<JSONObject> listaDatosPedidos;
    public Activity myActivity;
    AdaptadorPedidos adaptadorPedidos;
    public static boolean bandera = false;
    General gn;

    public listapedido() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_listapedido, container, false);
        gn = new General(myActivity,null);
        listaPedidos = (ListView) rootView.findViewById(R.id.listapedidos);
        pb = (ProgressBar) rootView.findViewById(R.id.pb);
        lblEstado = (ImageView) rootView.findViewById(R.id.lblimages);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        cargarPedidos();
    }

    public void onResume(){
        super.onResume();
        //pb.setVisibility(View.VISIBLE);
        cargarPedidos();
        /*listaPedidos.setAdapter(null);
        new Thread(new Runnable() {
            @Override
            public void run() {
            try{
                while(true){
                    listaDatosPedidos = new ArrayList<>();
                    cargarPedidos();
                    Thread.sleep(20000);
                }
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
            }
        }).start();*/
        //cargarPedidos();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myActivity = (AppCompatActivity) activity;
    }

    private void cargarPedidos(){
        new Thread(new Runnable() {
            @Override
            public void run() {
            final WebServices cs = new WebServices();
            final JSONArray j = cs.listapedidos(gn.getIdCliente());
            myActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                try {
                    listaDatosPedidos = new ArrayList<>();
                    if (j==null) {
                        Toast.makeText(myActivity, "No hay pedidos realizados", Toast.LENGTH_SHORT).show();
                    } else {
                        bandera = true;
                        for (int i = 0; i < j.length(); i++) {
                            listaDatosPedidos.add(j.getJSONObject(i));
                        }
                    }


                    adaptadorPedidos = new AdaptadorPedidos(myActivity, listaDatosPedidos);
                    listaPedidos.setAdapter(adaptadorPedidos);
                    if(listaDatosPedidos.size() == 0){
                        lblEstado.setVisibility(View.VISIBLE);
                    }else{
                        lblEstado.setVisibility(View.GONE);
                        //txt.setVisibility(View.GONE);
                    }
                } catch (Exception ex) {
                    Toast.makeText(myActivity, "Error webSerice Principal, " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
                pb.setVisibility(View.GONE);
                }
            });
            }
        }).start();
    }

}
