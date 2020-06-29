package com.example.investigacion_paredes_guerra_terrones_arango_duran;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Set;

public class BlueActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVER_BT = 1;
    private static final String TAG = "BlueActivity";
    public ArrayList<BluetoothDevice> mBTDevices = new ArrayList<>();
    public DeviceAdapter mDevicesAdapter;
    ListView lvNewDevices;

    TextView mPairedTV, estado;
    Button encendido, apagar, recibir;
    BluetoothAdapter bluetoothAdapter;
    Button btnBuscarDispositivo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue);

        //VARIABLES
        btnBuscarDispositivo = (Button) findViewById(R.id.btnBuscarDispositivos);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        estado = findViewById(R.id.statusBluetoothTv);
        encendido = findViewById(R.id.onBtn);
        apagar = findViewById(R.id.offBtn);
        //Para buscar nuevos dispositivos
        lvNewDevices = findViewById(R.id.NewDevices);
        mBTDevices = new ArrayList<>();
        // Para emparejar nuevos dispositivos
        recibir = findViewById(R.id.PairedBtn);
        mPairedTV = findViewById(R.id.pairedTv);
        ArrayList<BluetoothDevice> arrayDevices;

        // Register for broadcasts when a device is discovered.
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);

        // //Broadcasts when bond state changes (ie:pairing)
        IntentFilter filterbond = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(receiver1, filterbond);
        lvNewDevices.setOnItemClickListener(BlueActivity.this);


        encendido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bluetoothAdapter.isEnabled()) {
                    Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(i, REQUEST_ENABLE_BT);
                } else {
                    Toast.makeText(getApplicationContext(), "Bluetooth Encendido.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        apagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bluetoothAdapter.disable()) {
                    Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(i, REQUEST_DISCOVER_BT);
                } else {
                    mPairedTV.setText("");
                    Toast.makeText(getApplicationContext(), "Bluetooth Apagado.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        recibir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bluetoothAdapter.isEnabled()) {
                    mPairedTV.setText("Paired Devices");
                    Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
                    for (BluetoothDevice device : devices) {
                        mPairedTV.append("\nDevice: " + device.getName() + "," + device);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Bluetooth activado emparejar dispositivo.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Bluetooth es aceptado.", Toast.LENGTH_SHORT).show();
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
        unregisterReceiver(receiver);
        unregisterReceiver(Receiverdevices);
        unregisterReceiver(receiver1);
    }

// BUSCAR DISPOSITIVOS

    //Broadcast Receivier para los dipsositovos que todavia no estan emparejados
    private BroadcastReceiver Receiverdevices = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.d(TAG, "onReceiver :ACTION FOUND");
            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mBTDevices.add(device);
                Log.d(TAG, "ONRECIEVE" + device.getName() + ": " + device.getAddress());
                mDevicesAdapter = new DeviceAdapter(context, R.layout.activity_device_list_adapter, mBTDevices);
                lvNewDevices.setAdapter(mDevicesAdapter);
            }
        }
    };

    //BOTON DE BUSCAR DISPOSITIVOS
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void BuscarDispositivos(View view) {
        Log.d(TAG, "btnBuscarDispositivos: Looking for unpaired devices.");
        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
            Log.d(TAG, "btnBuscarDispositivos: Canceling discovery");
            checkBTPermissions();
            bluetoothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(Receiverdevices, discoverDevicesIntent);
        }
        if(!bluetoothAdapter.isDiscovering()){
            checkBTPermissions();
            bluetoothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(Receiverdevices, discoverDevicesIntent);
        }
    }

    //PERMISO PARA PODER QUE SE MUESTRE LOS DISPOSITIVOS EN EL LIST VIEW
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkBTPermissions() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
            permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
            if (permissionCheck != 0) {
                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001);
            }
        } else {
            Log.d(TAG, "checkBTPermissions: o need to check permissions. SDK version < LOLLIPOP.");
        }
    }


    // EMPAREJAR DISPOSITIVOS
    private BroadcastReceiver receiver1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if(action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)){
                BluetoothDevice mDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                //case1: bonded already
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDED){
                    Log.d(TAG, "BroadcastReceiver: BOND_BONDED.");
                }
                //case2: creating a bone
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDING) {
                    Log.d(TAG, "BroadcastReceiver: BOND_BONDING.");
                }
                //case3: breaking a bond
                if (mDevice.getBondState() == BluetoothDevice.BOND_NONE) {
                    Log.d(TAG, "BroadcastReceiver: BOND_NONE.");
                }
            }
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // CANCEL DISCOVERY
        bluetoothAdapter.cancelDiscovery();

        Log.d(TAG, "onItemClick: You Clicked on a device.");
        String deviceName = mBTDevices.get(position).getName();
        String deviceAddress = mBTDevices.get(position).getAddress();

        Log.d(TAG, "onItemClick: deviceName = " + deviceName);
        Log.d(TAG, "onItemClick: deviceAddress = " + deviceAddress);

        //CREATE THE BOND
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2){
            Log.d(TAG, "Trying to pair with " + deviceName);
            mBTDevices.get(position).createBond();
        }

    }
}
