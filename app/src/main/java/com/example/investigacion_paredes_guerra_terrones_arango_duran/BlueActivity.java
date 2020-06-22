package com.example.investigacion_paredes_guerra_terrones_arango_duran;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class BlueActivity extends AppCompatActivity {

    BluetoothAdapter bluetoothAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue);
        bluetoothAdapter =BluetoothAdapter.getDefaultAdapter();
    }
    public void on(View view){
        if(bluetoothAdapter == null){
            Toast.makeText(getApplicationContext(),"Bluetooth no es soportado en este celular.",Toast.LENGTH_SHORT).show();
        }

        else
        {
            if(!bluetoothAdapter.isEnabled())
            {
                Intent i= new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(i,1);
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 1)
        {
            if(resultCode == RESULT_OK){
                Toast.makeText(getApplicationContext(),"Bluetooth es aceptado.",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void off(View v){

    }
}