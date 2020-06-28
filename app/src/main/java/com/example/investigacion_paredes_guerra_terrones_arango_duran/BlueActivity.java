package com.example.investigacion_paredes_guerra_terrones_arango_duran;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Set;

public class BlueActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVER_BT = 1;
    private static final String TAG = null;
    public ArrayList<BluetoothDevice> mBTDevices = new ArrayList<>();
    public DeviceAdapter mDevicesAdapter;
    ListView lvNewDevices;

    TextView mPairedTV,estado;
    Button encendido,apagar,recibir;
    BluetoothAdapter bluetoothAdapter;
    Button btnBuscarDispositivo ;

    //Broadcast Receivier para los dipsositovos que todavia no estan emparejados
    private BroadcastReceiver Receiverdevices = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.d(TAG, "onReceiver :ACTION FOUND");
            if(action.equals(BluetoothDevice.ACTION_FOUND)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mBTDevices.add(device);
                Log.d(TAG,"ONRECIEVE" + device.getName() + ": " + device.getAddress());
                mDevicesAdapter = new DeviceAdapter(context, R.layout.activity_device_list_adapter,mBTDevices);
                lvNewDevices.setAdapter(mDevicesAdapter);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue);

        // Register for broadcasts when a device is discovered.
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);

        btnBuscarDispositivo =(Button)findViewById(R.id.btnBuscarDispositivos);
        bluetoothAdapter =BluetoothAdapter.getDefaultAdapter();
        estado =findViewById(R.id.statusBluetoothTv);
        encendido =findViewById(R.id.onBtn);
        apagar =findViewById(R.id.offBtn);
        //Para buscar nuevos dispositivos
        lvNewDevices = findViewById(R.id.NewDevices);
        mBTDevices = new ArrayList<>();
        // Para emparejar nuevos dispositivos
        recibir =findViewById(R.id.PairedBtn);
        mPairedTV =findViewById(R.id.pairedTv);
        ArrayList<BluetoothDevice> arrayDevices;
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
                   mPairedTV.setText("");
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

    // Create a BroadcastReceiver for ACTION_FOUND.
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(receiver);
    }

    //BUSCAR DISPOSITIVOS
    public void BuscarDispositivos(View view) {
    Log.d(TAG,"btnBuscarDispositivos: Looking for unpaired devices.");
    if(bluetoothAdapter.isDiscovering()){
        bluetoothAdapter.cancelDiscovery();
        Log.d(TAG,"btnBuscarDispositivos: Canceling discovery");
           //checkBTPermissions();
        bluetoothAdapter.startDiscovery();
        IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(Receiverdevices,discoverDevicesIntent);

    }
    }
/*
    private void checkBTPermissions(){
        if(Build.VERSION)

    }*/

    // PAIR DEVICES
/*
    private final BroadcastReceiver bReceiver2= new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Cada vez que se descubra un nuevo dispositivo Bluethooth, s
             final String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {

            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {

            }
        }
    };
*/
}