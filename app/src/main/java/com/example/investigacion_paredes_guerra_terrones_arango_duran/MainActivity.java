package com.example.investigacion_paredes_guerra_terrones_arango_duran;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Direcciona hacia la pantalla de Bluetooth.
    public void blueTooth(View view){
        Intent b = new Intent(getApplicationContext(), BlueActivity.class);
        startActivity(b);
    }

    //Direcciona hacia la pantalla de Wifi.
    public void wifiTest(View view){
        Intent w = new Intent(getApplicationContext(), WifiActivity.class);
        startActivity(w);
    }
}