package com.example.investigacion_paredes_guerra_terrones_arango_duran;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Set;

public class BlueActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVER_BT = 1;

    TextView mPairedTV,estado;
    Button encendido,apagar,recibir;
    BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue);

        bluetoothAdapter =BluetoothAdapter.getDefaultAdapter();
        estado =findViewById(R.id.statusBluetoothTv);
        encendido =findViewById(R.id.onBtn);
        apagar =findViewById(R.id.offBtn);
        recibir =findViewById(R.id.PairedBtn);
        mPairedTV =findViewById(R.id.pairedTv);

        if(bluetoothAdapter == null){
            estado.setText("Bluetooth is not available");
        }
        else {
            estado.setText("Bluetooth is available");
        }
        encendido.setOnClickListener(new View.OnClickListener(){
            @Override
                    public void onClick(View v){
                if(!bluetoothAdapter.isEnabled()){
                    Intent i= new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(i,REQUEST_ENABLE_BT);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Bluetooth Encendido.",Toast.LENGTH_SHORT).show();
                }
            }
        });
       apagar.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v) {
               if(!bluetoothAdapter.disable()){
                   Intent i= new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                   startActivityForResult(i,REQUEST_DISCOVER_BT);
               }
               else {
                   Toast.makeText(getApplicationContext(),"Bluetooth Apagado.",Toast.LENGTH_SHORT).show();
               }
           }
        });
       recibir.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(bluetoothAdapter.isEnabled()){
                   mPairedTV.setText("Paired Devices");
                   Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
                   for(BluetoothDevice device: devices) {
                       mPairedTV.append("\nDevice: " + device.getName()+"," + device);
                   }
               }
               else{
                   Toast.makeText(getApplicationContext(),"Bluetooth activado emparejar dispositivo.",Toast.LENGTH_SHORT).show();
               }
           }
       });
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
}