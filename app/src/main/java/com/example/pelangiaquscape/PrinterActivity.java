package com.example.pelangiaquscape;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class PrinterActivity extends AppCompatActivity {

    private static final int REQUEST_DISCOVER_BT = 1;

    ImageView cancel;
    Button cariPrinter;
    BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printer);

        //INIT VIEW
        cancel = findViewById(R.id.im_cancel);
        cariPrinter = findViewById(R.id.btn_cari_printer);

        //adapter
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        cariPrinter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cariPrinter = new Intent(PrinterActivity.this, CariBluetoothActivity.class);
                startActivity(cariPrinter);
                if (!bluetoothAdapter.isDiscovering()){
                    showToast("Membuat perangkat anda dapat ditemukan");
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(intent, REQUEST_DISCOVER_BT);
                }
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
