package com.abelli.estudoapiactivitys.data;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.abelli.estudoapiactivitys.constants.ChatConstants;
import com.abelli.estudoapiactivitys.entities.ChatsEntity;
import com.abelli.estudoapiactivitys.views.ListFragment;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class RetrieveDataAPI {

    private ViewHolder mViewHolder = new ViewHolder();

    private Context mContext;
    public List<ChatsEntity> list_API;

    public RetrieveDataAPI(Context context, String table_name) {
        this.mViewHolder.table_API = table_name;
        this.mContext = context;
    }

    private void loadChats() {
        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setTitle("Carregando lista");
        progressDialog.setMessage("Carregando...");
        progressDialog.show();

        Runnable progressRunnable = new Runnable() {
            @Override
            public void run() {
                progressDialog.cancel();
            }
        };

        //Timer para a progress bar
        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 5000);

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET,
                ChatConstants.URL_PASTEL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    //mViewHolder.valores = new String[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jo = jsonArray.getJSONObject(i);

                        //mViewHolder.valores[i] = jo.toString();
                        mViewHolder.valores = jo.toString();

                        ListFragment listFragment = new ListFragment();
                        listFragment.recebeDados(mViewHolder.valores);

                        carregaValoresRetornados(mViewHolder.valores);
                    }
                } catch (JSONException e) {
                    Toast.makeText(mContext, e.getMessage() + "Problema ao carregar JSON!", Toast.LENGTH_LONG).show();
                    Log.d("RetrieveDataApi", "JsonException: " + e);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "Erro ao carregar dados!", Toast.LENGTH_LONG).show();
                Log.d("CONEXAO", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);
    }

    public class ViewHolder{
        private String table_API;
        //private String[] valores;
        private String valores;
    }

    public void carregaValoresRetornados(String valoresAPI) {
        mViewHolder.valores = valoresAPI;
    }

    public String retornaValoresRetornados() {
        loadChats();
        return mViewHolder.valores;
    }

//    public void carregaValoresRetornados(String[] valoresAPI) {
//        mViewHolder.valores = valoresAPI;
//    }
//
//    public String[] retornaValoresRetornados() {
//        loadChats();
//        return mViewHolder.valores;
//    }

}
