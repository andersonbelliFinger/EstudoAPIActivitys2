package com.abelli.estudoapiactivitys.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.abelli.estudoapiactivitys.R;

public class DetalhesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        if (bundle != null) {

//            String id = bundle.getString("id");
//            String nome = bundle.getString("name");
//            String desc = bundle.getString("desc");
//
//            TextView textId = findViewById(R.id.id);
//            TextView textNome = findViewById(R.id.nome);
//            TextView textDesc = findViewById(R.id.desc);
//
//            textId.setText(id);
//            textNome.setText(nome);
//            textDesc.setText(desc);
        }
    }

}
