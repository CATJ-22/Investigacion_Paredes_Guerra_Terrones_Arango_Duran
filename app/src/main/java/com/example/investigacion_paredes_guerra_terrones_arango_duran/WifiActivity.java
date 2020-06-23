package com.example.investigacion_paredes_guerra_terrones_arango_duran;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class WifiActivity extends AppCompatActivity {
    //Variables declaradas.
    Button w_on, w_off;
    WifiManager wifiM;
    WifiReceiver wifiReceiver;
    ListView wifiList;
    ListAdapter wifiAdapter;
    List MyList;

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
        wifiList=(ListView)findViewById(R.id.listview);

    }

    //Metodo para encender el Wifi.
    public void onWifi(View view){

        wifiM.setWifiEnabled(true);

        //if()

            wifiReceiver = new WifiReceiver();

            registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);

            } else {
                wifiM.startScan();
                MyList = wifiM.getScanResults();
                wifiAdapter = new ListAdapter(getApplicationContext(), MyList);
                wifiList.setAdapter(wifiAdapter);
            }

        Toast.makeText(this, "Wifi Habilitado"+wifiM.setWifiEnabled(true), Toast.LENGTH_LONG).show();

    }

    //Metodo para apagar el Wifi.
    public void offWifi(View view){

        wifiM.setWifiEnabled(false);
        Toast.makeText(this, "Wifi Deshabilitado", Toast.LENGTH_LONG).show();
    }

    class WifiReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }
}