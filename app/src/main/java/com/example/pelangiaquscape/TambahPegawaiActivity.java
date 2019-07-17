package com.example.pelangiaquscape;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

public class TambahPegawaiActivity extends AppCompatActivity {

    ImageView cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pegawai);

        cancel =(ImageView) findViewById(R.id.im_cancel);

        Spinner spinner = (Spinner) findViewById(R.id.spinner_hak_akses);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.akses_arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spinner.setAdapter(adapter);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
