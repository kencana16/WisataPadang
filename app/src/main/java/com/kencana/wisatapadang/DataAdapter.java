package com.kencana.wisatapadang;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends BaseAdapter {

    List<Data> daftar;
    Context context;

    public DataAdapter(Context ctx){
        this.context=ctx;
        daftar = new ArrayList<>();
    }

    public void loadData(JSONObject jsonObject){
        try {
            JSONArray data = jsonObject.getJSONArray("features");
            daftar.clear();

            for (int i=0; i<data.length(); i++){
                JSONObject object = data.getJSONObject(i);
                Data d = new Data(
                        object.getJSONObject("properties").getString("name"),
                        object.getJSONObject("properties").getString("kategori"),
                        object.getJSONObject("properties").getString("alamat"),
                        object.getJSONObject("geometry").getJSONArray("coordinates").getString(0),
                        object.getJSONObject("geometry").getJSONArray("coordinates").getString(1)
                );
                daftar.add(d);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Error on LoadData : ",e.toString());
        }
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return daftar.size();
    }

    @Override
    public Object getItem(int position) {
        return daftar.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.list_data,viewGroup,false);
        TextView tvNama= view.findViewById(R.id.tvNama);
        TextView tvKategori= view.findViewById(R.id.tvKategori);
        TextView tvAlamat= view.findViewById(R.id.tvAlamat);
        TextView tvLng= view.findViewById(R.id.tvLng);
        TextView tvLat= view.findViewById(R.id.tvLat);

        Data d = daftar.get(position);
        tvNama.setText(d.nama);
        tvKategori.setText(d.kategori);
        tvAlamat.setText(d.alamat);
        tvLng.setText(d.lng);
        tvLat.setText(d.lat);

        return view;
    }
}
