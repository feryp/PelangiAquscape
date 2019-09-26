package com.example.pelangiaquscape;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.style.CubeGrid;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.WanderingCubes;

public class CariBluetoothActivity extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_bluetooth);

        //INIT VIEW
        progressBar = findViewById(R.id.SpinKit);

        CubeGrid cubeGrid = new CubeGrid();

        progressBar.setIndeterminateDrawable(cubeGrid);
    }
}
