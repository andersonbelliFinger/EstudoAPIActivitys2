package com.abelli.estudoapiactivitys.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.abelli.estudoapiactivitys.R;
import com.abelli.estudoapiactivitys.entities.ChatsEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RetrieveDataAPI {

    private RecyclerView mRecyclerView;
    private ListView listView;
    private List<ChatsEntity> chatsAdapter;

    private ChatsEntity mChatsEntity;

    private int count = 0;
    private int getCount() {return count;}

    private String str = "";
    public String getStr() {return str;}
    private void setStr(String str) {this.str = str;}

    private String[] strList = {};

    private Context mContext;

   public void getJSON(final String urlWebService, final Context context) {
        @SuppressLint("StaticFieldLeak")
        class GetJSON extends AsyncTask<Void, Void, String> {

          @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

           @Override
            public void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    loadIntoListView(s,context);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(context, s, Toast.LENGTH_LONG).show();

                setStr(s);
                String findStr = "id";
                int lastIndex = 0;

                while(lastIndex != -1){
                    lastIndex = str.indexOf(findStr,lastIndex);
                    if(lastIndex != -1){
                        count++;
                        lastIndex += findStr.length();
                    }
                }
                retornaCount(count);
                retornaJson(s);
                Log.d("COUNT",String.valueOf(count));
            }

            //in this method we are fetching the json string
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    //creating a URL
                    URL url = new URL(urlWebService);

                    //Opening the URL using HttpURLConnection
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    //StringBuilder object to read the string from the service
                    StringBuilder sb = new StringBuilder();

                    //We will use a buffered reader to read the string from service
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    //A simple string to read values from each line
                    String json;

                    //reading until we don't find null
                    while ((json = bufferedReader.readLine()) != null) {

                        //appending it to string builder
                        sb.append(json + "\n");
                    }

                    //finally returning the read string
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }

            }
        }

        //creating asynctask object and executing it
        GetJSON getJSON = new GetJSON();

        List test = (List) getJSON;

        getJSON.execute();
    }

    private String retornaJson(String json){
        return json;
    }

    private int retornaCount(int i){
        count = i;
        Log.d("i",String.valueOf(i));
        Log.d("COUNT1",String.valueOf(count));
        Log.d("COUNT2",String.valueOf(getCount()));
        return i;
    }

    private void loadIntoListView(String json, Context context) throws JSONException {
        //creating a json array from the json string
        JSONArray jsonArray = new JSONArray(json);

        //creating a string array for listview
        String[] chats = new String[jsonArray.length()];

        //looping through all the elements in json array
        for (int i = 0; i < jsonArray.length(); i++) {

            //getting json object from the json array
            JSONObject obj = jsonArray.getJSONObject(i);

            //getting the NAME from the json object and putting it inside string array
            chats[i] = obj.getString("NAME");
        }

        //the array adapter to load data into list
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, chats);
        listView.setAdapter(arrayAdapter);

//        //attaching adapter to listview
//        mRecyclerView.setAdapter(arrayAdapter);
    }
    private void loadIntoListView1(String json, Context context) throws JSONException {
        //creating a json array from the json string
        JSONArray jsonArray = new JSONArray(json);

        //creating a string array for listview
        String[] chats = new String[jsonArray.length()];

        //looping through all the elements in json array
        for (int i = 0; i < jsonArray.length(); i++) {

            //getting json object from the json array
            JSONObject obj = jsonArray.getJSONObject(i);

            //getting the NAME from the json object and putting it inside string array
            chats[i] = obj.getString("NAME");
        }

        //the array adapter to load data into list
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, chats);
        listView.setAdapter(arrayAdapter);

//        //attaching adapter to listview
//        mRecyclerView.setAdapter(arrayAdapter);
    }
}
