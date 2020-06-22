package com.example.investigacion_paredes_guerra_terrones_arango_duran;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class BlueActivity extends AppCompatActivity {

    BluetoothAdapter bluetoothAdapter;
    ArrayAdapter<String>arrayAdapter;
    ArrayList arrayList;
    ListView li;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue);
        bluetoothAdapter =BluetoothAdapter.getDefaultAdapter();
        li = (ListView)findViewById(R.id.listview);
        arrayList = new ArrayList();

        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,arrayList);
        li.setAdapter(arrayAdapter);

        findPairedDevices();
    }
    public void Scan(View view){
        bluetoothAdapter.startDiscovery();
    }
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                arrayList.add(device.getName());
                arrayAdapter.notifyDataSetChanged();
            }
        }
    }
    private void findPairedDevices(){
        int index = 0;
        Set<BluetoothDevice> bluetoothDeviceSet = bluetoothAdapter.getBondedDevices();
        String[] str = new String[bluetoothDeviceSet.size()];

        if(bluetoothDeviceSet.size()>0){
            for(BluetoothDevice device: bluetoothDeviceSet)
            {
                str[index] = device.getName();
                index++;
            }
            arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,str);
            li.setAdapter(arrayAdapter);
        }
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
    public void off(View view){
        if(bluetoothAdapter == null){
            Toast.makeText(getApplicationContext(),"Bluetooth no es soportado en este celular.",Toast.LENGTH_SHORT).show();
        }

        else
        {
            if(!bluetoothAdapter.disable())
            {
                Intent i= new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                startActivityForResult(i,1);
            }
        }
    }
}