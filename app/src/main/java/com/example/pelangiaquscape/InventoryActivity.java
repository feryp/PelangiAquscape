package com.example.pelangiaquscape;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


public class InventoryActivity extends AppCompatActivity {

    ImageView cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        cancel = findViewById(R.id.im_cancel);
        FloatingActionButton fab_inventory = findViewById(R.id.fab_inventory);

        fab_inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InventoryActivity.this, "Tambah Inventory", Toast.LENGTH_SHORT).show();
                Intent fab_inventory = new Intent(InventoryActivity.this, TambahInventoryActivity.class);
                startActivity(fab_inventory);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}
