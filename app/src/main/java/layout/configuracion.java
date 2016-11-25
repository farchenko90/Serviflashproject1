package layout;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.fabio.serviflashproject.CambiarPass;
import org.fabio.serviflashproject.R;
import org.fabio.serviflashproject.fotoperfil;
import org.fabio.serviflashproject.perfil;

/**
 * A simple {@link Fragment} subclass.
 */
public class configuracion extends Fragment {

    public LinearLayout linearLayout,linerpass,linerfoto;
    View rootView;
    boolean ban = true;

    public configuracion() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_configuracion, container, false);
        initComponent();
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perfil();
            }
        });

        linerpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass();
            }
        });

        linerfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foto();
            }
        });

        return rootView;
    }

    public void foto(){
        Intent i = new Intent(getActivity(),fotoperfil.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
    }

    public void perfil(){
        Intent i = new Intent(getActivity(),perfil.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
    }

    public void pass(){
        Intent i = new Intent(getActivity(), CambiarPass.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
    }

    public void initComponent(){
        linerfoto = (LinearLayout) rootView.findViewById(R.id.linerfoto);
        linearLayout = (LinearLayout) rootView.findViewById(R.id.linerperfil);
        linerpass = (LinearLayout) rootView.findViewById(R.id.linerpass);
    }

}
