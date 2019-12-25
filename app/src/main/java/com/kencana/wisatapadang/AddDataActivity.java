package com.kencana.wisatapadang;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddDataActivity extends AppCompatActivity {

    AksesGPS gps;
    Location loc;
    LocationManager lokasi;
    OkHttpClient.Builder builder = new OkHttpClient.Builder();

    TextInputEditText ti_nama, ti_kategori, ti_lng,ti_lat,ti_alamat;
    TextInputLayout til_nama, til_kategori, til_lng,til_lat,til_alamat;
    Button btn_back,btn_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        //button
        btn_back = findViewById(R.id.btn_back);
        btn_save = findViewById(R.id.btn_save);

        //textInput
        ti_nama = findViewById(R.id.ti_nama);
        ti_kategori = findViewById(R.id.ti_kategori);
        ti_lng = findViewById(R.id.ti_lng);
        ti_lat = findViewById(R.id.ti_lat);
        ti_alamat = findViewById(R.id.ti_alamat);

        //Text Input Layout
        til_nama = findViewById(R.id.til_nama);
        til_kategori = findViewById(R.id.til_kategori);
        til_lng = findViewById(R.id.til_lng);
        til_lat = findViewById(R.id.til_lat);
        til_alamat = findViewById(R.id.til_alamat);

        //button back
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String [] permision = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.requestPermissions(permision,0);
        }

        try{
            gps = new AksesGPS(getApplicationContext());
            loc = gps.ambilLokasi();
            ti_lat.setText(""+loc.getLatitude());
            ti_lng.setText(""+loc.getLongitude());
        }catch(Exception e){
            Toast.makeText(AddDataActivity.this,e.toString(),Toast.LENGTH_LONG).show();
        }

        builder.connectTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20,TimeUnit.SECONDS);
        builder.readTimeout(10,TimeUnit.SECONDS);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpan();
                kosong();
            }
        });
    }
    void kosong(){
        ti_nama.setText("");
        ti_nama.requestFocus();
    }
    void simpan(){
        OkHttpClient client = builder.build();

        RequestBody requestBody = new FormBody.Builder()
                .add("nama",ti_nama.getText().toString())
                .add("alamat",ti_alamat.getText().toString())
                .add("kategori",ti_kategori.getText().toString())
                .add("latitude",ti_lat.getText().toString())
                .add("longitude",ti_lng.getText().toString())
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.100.7/PemogramanGIS/UAS/saveData.php")
                .post(requestBody)
                .addHeader("connection","Keep-Alive")
                .addHeader("connection-type","Application/x-www-urlencoded")
                .build();

        Log.i("Info Kirim","Proses Simpan Dijalankan");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("Err Kirim",e.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AddDataActivity.this, "Pengiriman Gagal",Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.i("Info Kirim",response.message());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AddDataActivity.this,"Pengiriman Berhasil",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
