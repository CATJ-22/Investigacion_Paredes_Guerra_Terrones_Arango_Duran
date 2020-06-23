package com.example.investigacion_paredes_guerra_terrones_arango_duran;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class WifiActivity extends AppCompatActivity {
    //Variables declaradas.
    Button w_on, w_off;
    WifiManager wifiM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        initializeControls();
    }
    //Metodo que inicia las variables.
    public void initializeControls(){
        w_on=(Button)findViewById(R.id.btn_turnon);
        w_off=(Button)findViewById(R.id.btn_turnoff);
        wifiM=(WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }
    //Metodo para encender el Wifi.
    public void onWifi(View view){
        wifiM.setWifiEnabled(true);
        Toast.makeText(this, "Wifi Habilitado", Toast.LENGTH_LONG).show();
    }
    //Metodo para apagar el Wifi.
    public void offWifi(View view){
        wifiM.setWifiEnabled(true);
        Toast.makeText(this, "Wifi Deshabilitado", Toast.LENGTH_LONG).show();
    }
}