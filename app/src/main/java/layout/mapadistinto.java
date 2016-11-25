package layout;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import org.fabio.serviflashproject.R;
import org.fabio.serviflashproject.Util.General;

/**
 * A simple {@link Fragment} subclass.
 */
public class mapadistinto extends Fragment implements OnMapReadyCallback {

    public GoogleMap mapa;
    View rootView;
    General gn;
    public Activity myActivity;

    public mapadistinto() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myActivity = (AppCompatActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_mapadistinto, container, false);
        gn = new General(myActivity,null);
        initcomponent();

        return rootView;
    }

    public void initcomponent(){
        ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapadistinto)).getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        //mapa.setMyLocationEnabled(true);
        mapa.getUiSettings().setZoomControlsEnabled(true);
        mapa.getUiSettings().setCompassEnabled(true);
        mapa.getUiSettings().setMapToolbarEnabled(true);

        LatLng valle = new LatLng(10.4635985, -73.254565114);

        /*googleMap.addMarker(new MarkerOptions()
                .position(valle)
                .title("Cali la Sucursal del cielo"));*/

        CameraPosition cameraPosition = CameraPosition.builder()
                .target(valle)
                .zoom(13)
                .build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
