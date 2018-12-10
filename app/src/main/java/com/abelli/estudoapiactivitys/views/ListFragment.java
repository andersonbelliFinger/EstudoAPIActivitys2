package com.abelli.estudoapiactivitys.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.abelli.estudoapiactivitys.listener.ChatsListener;
import com.abelli.estudoapiactivitys.R;
import com.abelli.estudoapiactivitys.adapter.ContactsAdapter;
import com.abelli.estudoapiactivitys.constants.ChatConstants;
import com.abelli.estudoapiactivitys.entities.ChatsEntity;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private ViewHolder mViewHolder = new ViewHolder();
    ChatsListener mChatsListener;
    private Context mContext;

    private ContactsAdapter mGuestListAdapter;
    private ChatsEntity mChatsEntity;

    private String recebidoAPI;

    List<ChatsEntity> listAux;
    public List<ChatsEntity> listContents;
    public String[] listBackup = {"Bauru1","Presunto1, queijo1","Carne1","Carne1, queijo1"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listContents = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_list, container, false);

        this.mContext = view.getContext();

        //Oberter a lista (recycler)
        this.mViewHolder.mRecyclerViewContacts = view.findViewById(R.id.recycler_contacts);
        this.mViewHolder.mTextHead = view.findViewById(R.id.head_list);
        this.mViewHolder.mTextOffline = view.findViewById(R.id.txt_offline);
        this.mViewHolder.mTextOffline.setVisibility(View.GONE);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Adicionar outra conversa", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Ações na listagem
        this.mChatsListener = new ChatsListener() {
            // Ao clicar em algum item da lista, será redirecionado para a página de formulário
            @Override
            public void onChatClick(String id, String name, String desc) {
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                bundle.putString("name", name);
                bundle.putString("desc", desc);

                FragmentManager fm = getFragmentManager();
                DetalhesFragment detalhesFragment = new DetalhesFragment();
                detalhesFragment.setArguments(bundle);
                if (fm != null) {
                    fm.beginTransaction().replace(R.id.frame_content, detalhesFragment).addToBackStack(" tag ").commit();
                }
            }
        };

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        listContents.clear();
        listContents.clear();

        this.loadChats();
        this.retornaList();
    }

    public void loadChats() {
        final ProgressDialog progressDialog = new ProgressDialog(this.mContext);
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

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jo = jsonArray.getJSONObject(i);

                        String id_json = jo.getString("id");
                        String name_json = jo.getString("name");
                        String desc_json = jo.getString("description");

                        retornaValores(id_json, name_json, desc_json);
                    }
                } catch (JSONException e) {
                    Toast.makeText(mContext, e.getMessage() + "Deu ruim", Toast.LENGTH_LONG).show();
                    Log.d("DeuRUIMaqui1", "oooooo");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "Erro ao carregar sabores!", Toast.LENGTH_LONG).show();
                Log.d("CONEXAO", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);
    }

    public void recebeDados(String recebido) throws JSONException {
        recebidoAPI = recebido;

        JSONObject jsonObject = new JSONObject(recebido);

        for (int i = 0; i < jsonObject.length(); i++) {
            String id = jsonObject.getString("id");
            String name = jsonObject.getString("name");
            String description = jsonObject.getString("description");

            Log.d("ListFragment", "id: " + id);
            Log.d("ListFragment", "name: " + name);
            Log.d("ListFragment", "description: " + description);

            retornaValores(id, name, description);
        }
    }

    private void retornaValores(String id_valor, String name_valor, String desc_valor) {
        ChatsEntity mChatsEntity;
        mChatsEntity = new ChatsEntity(id_valor, name_valor, desc_valor);
        mChatsEntity.setName(name_valor);
        mChatsEntity.setDescription(desc_valor);

        if(id_valor == null){
            Log.d("ERRONULO1","Toaqui man");
        }else{
            listContents.add(mChatsEntity);
            listAux = listContents;
        }

        retornaList();
    }

    private void retornaList() {
        if (listContents.size() == 0) {
            this.mViewHolder.mTextOffline.setVisibility(View.VISIBLE);
            //this.mViewHolder.mTextOffline.setTextColor(R.color.minhaCor);
            this.mGuestListAdapter = new ContactsAdapter(listBackup, this.mChatsListener);
            this.mViewHolder.mRecyclerViewContacts.setLayoutManager(new LinearLayoutManager(getContext()));
            this.mViewHolder.mRecyclerViewContacts.setHasFixedSize(true);
            //this.mViewHolder.mRecyclerViewContacts.addItemDecoration(new DividerItemDecoration(mContext,LinearLayout.VERTICAL));

            this.mViewHolder.mRecyclerViewContacts.setAdapter(this.mGuestListAdapter);
        } else {
            this.mViewHolder.mTextOffline.setVisibility(View.GONE);
            this.mGuestListAdapter = new ContactsAdapter(listContents, this.mChatsListener);
            this.mViewHolder.mRecyclerViewContacts.setLayoutManager(new LinearLayoutManager(getContext()));
            this.mViewHolder.mRecyclerViewContacts.setHasFixedSize(true);
            //this.mViewHolder.mRecyclerViewContacts.addItemDecoration(new DividerItemDecoration(mContext,LinearLayout.VERTICAL));

            this.mViewHolder.mRecyclerViewContacts.setAdapter(this.mGuestListAdapter);
            this.mGuestListAdapter.notifyDataSetChanged();
        }
        Log.d("mGuestListAdapter", mGuestListAdapter.toString());
        Log.d("SizeEHesse5", String.valueOf(listContents.size()));
    }

    private static class ViewHolder {
        RecyclerView mRecyclerViewContacts;
        TextView mTextHead;
        TextView mTextOffline;
    }
}