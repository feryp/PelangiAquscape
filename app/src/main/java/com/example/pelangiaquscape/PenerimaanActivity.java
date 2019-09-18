package com.example.pelangiaquscape;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

public class PenerimaanActivity extends AppCompatActivity {

    RecyclerView rvPenerimaan;
    LinearLayout llPenerimaan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penerimaan);

        // INIT VIEW
        rvPenerimaan = findViewById(R.id.rv_penerimaan);
        rvPenerimaan.setHasFixedSize(true);
        rvPenerimaan.setLayoutManager(new LinearLayoutManager(this));
        llPenerimaan = findViewById(R.id.ll_penerimaan);




    }
}
