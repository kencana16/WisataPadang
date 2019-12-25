package com.kencana.wisatapadang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MapActivity extends AppCompatActivity {

    WebView webView;
    int lbr, tinggi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        webView = (WebView) findViewById(R.id.webview);

    }

    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        lbr = webView.getWidth()/2;
        tinggi = webView.getHeight()/3;
        viewWeb();
    }

    public void viewWeb(){

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://192.168.43.168:8081/geoserver/kota_pariaman/wms?service=WMS&version=1.1.0&request=GetMap&layers=kota_pariaman%3Apariaman-kota-jalan-kesehatan&bbox=100.08914139700003%2C-0.675641985999992%2C100.18232260000005%2C-0.5423701239999414&width="+lbr+"&height="+tinggi+"&srs=EPSG%3A4326&format=application/openlayers");
    }
}
