package com.example.investigacion_paredes_guerra_terrones_arango_duran;
//jaja que trol el david
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ola();
    }

    public void ola(){
        Toast.makeText(this, "QUE ONDA!", Toast.LENGTH_LONG);
    }
}