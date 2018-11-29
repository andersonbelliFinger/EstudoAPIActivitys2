package com.abelli.estudoapiactivitys.views;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abelli.estudoapiactivitys.R;

public class DetalhesFragment extends Fragment {


    public DetalhesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalhes, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {

            //String id = bundle.getString("id");
            String nome = bundle.getString("name");
            String desc = bundle.getString("desc");

            //TextView textId = view.findViewById(R.id.id);
            TextView textNome = view.findViewById(R.id.nome);
            TextView textDesc = view.findViewById(R.id.desc);

            //textId.setText(id);
            textNome.setText(nome);
            textDesc.setText(desc);
        }

        return view;
    }

}
