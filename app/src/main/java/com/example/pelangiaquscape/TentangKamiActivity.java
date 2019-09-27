package com.example.pelangiaquscape;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class TentangKamiActivity extends AppCompatActivity {

    ImageView cancel;

    LinearLayout syaratKetentuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang_kami);

        cancel = findViewById(R.id.im_cancel);
        syaratKetentuan = findViewById(R.id.syarat_dan_ketentuan);

        syaratKetentuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TentangKamiActivity.this, SyaratKetentuanAplikasiActivity.class);
                startActivity(intent);
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
