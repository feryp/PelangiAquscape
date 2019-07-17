package com.example.pelangiaquscape;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

public class TambahPemasokActivity extends AppCompatActivity {

    ImageView cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pemasok);

        cancel =(ImageView) findViewById(R.id.im_cancel);

        Spinner spinner1 = (Spinner) findViewById(R.id.spinner_jenis_perusahaan);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner_klasifikasi_perusahaan);
        Spinner spinner3 = (Spinner) findViewById(R.id.spinner_kualifikasi_perusahaan);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.jenis_perusahaan_arrays, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spinner1.setAdapter(adapter1);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.klasifikasi_perusahaan_arrays, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spinner1.setAdapter(adapter2);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,R.array.kualifikasi_perusahaan_arrays, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spinner1.setAdapter(adapter3);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
