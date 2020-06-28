package com.example.investigacion_paredes_guerra_terrones_arango_duran;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.investigacion_paredes_guerra_terrones_arango_duran.Adapters.ListAdapterWifi;

import java.util.List;


public class WifiActivity extends AppCompatActivity {
    //Variables utilizadas.
    TextView red_on;
    WifiManager wifiM;
    wifiReceiver wifiR;
    ListAdapter listAdapterWifi;
    ListView wifiList;
    List wifi_List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        //Llamada de los metodos.
        initializeControls();
    }
    //Metodo que inicia las variables.
    public void initializeControls(){
        red_on=(TextView)findViewById(R.id.txt_redOn);
        wifiM=(WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiList=(ListView)findViewById(R.id.list_view);
        wifiR = new wifiReceiver();
        registerReceiver(wifiR, new IntentFilter(wifiM.SCAN_RESULTS_AVAILABLE_ACTION));
        if(wifiM.getWifiState()==wifiM.WIFI_STATE_ENABLED) {
            this.mostradorRed();
        }
    }
    //Mostrar las redes disponibles.
    public void mostradorRed(){
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        else
            scanWifiList();
    }

    //Metodo para escanear las redes cercanas.
    private void scanWifiList() {
        wifiM.startScan();
        wifi_List = wifiM.getScanResults();
        setAdapter();
    }

    //Metodo para establecer el adaptador de la Listview.
    private void setAdapter() {
        listAdapterWifi = new ListAdapterWifi(getApplicationContext(), wifi_List);
        wifiList.setAdapter(listAdapterWifi);
    }

    //Clase para recibir datos.
    class wifiReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }

    //Metodo para encender el Wifi.
    public void onWifi(View view){
        if(wifiM.getWifiState()==wifiM.WIFI_STATE_DISABLED){
            wifiM.setWifiEnabled(true);
            red_on.setVisibility(view.VISIBLE);
            wifiList.setVisibility(view.VISIBLE);
            Toast.makeText(this, "Wifi Habilitado", Toast.LENGTH_SHORT).show();
        }
        this.mostradorRed();
    }

    //Metodo para apagar el Wifi.
    public void offWifi(View view){
        wifiM.setWifiEnabled(false);
        Toast.makeText(this, "Wifi Deshabilitado", Toast.LENGTH_SHORT).show();
        red_on.setVisibility(view.INVISIBLE);
        wifiList.setVisibility(view.INVISIBLE);
    }

}
