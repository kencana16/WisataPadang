package com.kencana.wisatapadang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ViewDataActivity extends AppCompatActivity {

    ListView listView;
    DataAdapter adapter;
    String url="http://192.168.43.168:8081/geoserver/kota_padang/ows?service=WFS&version=1.0.0&request=GetFeature&typeName=kota_padang%3AwisataPadang&maxFeatures=50&outputFormat=application%2Fjson";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);

        listView = findViewById(R.id.listview);
        adapter = new DataAdapter(this);
        aksesData();
        listView.setAdapter(adapter);
    }

    private void aksesData() {
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                try{
                    URL alamat = new URL(url);
                    HttpURLConnection koneksi = (HttpURLConnection) alamat.openConnection();
                    InputStream inputStream = koneksi.getInputStream();
                    InputStreamReader reader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(reader);

                    String barisutuh = "";
                    String baris = bufferedReader.readLine();
                    while (baris!=null){
                        barisutuh=barisutuh+baris;
                        baris = bufferedReader.readLine();

                    }
                    try{
                        JSONObject jsonObject = new JSONObject(barisutuh);
                        return  jsonObject;
                    }
                    catch (Exception e){
                        Log.e("Eror On JSONObject : ",e.toString());
                    }

                } catch (Exception e){
                    Log.e("Error On Read Stream : ",e.toString());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o){
                JSONObject jsonObject = (JSONObject) o;
                adapter.loadData(jsonObject);
            }
        };
        task.execute();
    }
}
