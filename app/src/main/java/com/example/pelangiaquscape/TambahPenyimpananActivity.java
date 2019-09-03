package com.example.pelangiaquscape;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

public class TambahPenyimpananActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView cancel, save;
    Spinner spinnerKeteranganBarang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_penyimpanan);

        //INIT VIEW
        cancel = findViewById(R.id.im_cancel);
        save = findViewById(R.id.im_save);

        spinnerKeteranganBarang = findViewById(R.id.spinner_status_barang);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this,
                R.array.status_barang_arrays,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spinnerKeteranganBarang.setAdapter(adapter);

        cancel.setOnClickListener(this);
        save.setOnClickListener(this);

    }

    private void save() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_cancel:
                finish();
                break;
            case R.id.im_save:
                save();
                finish();
                break;
        }
    }
}




