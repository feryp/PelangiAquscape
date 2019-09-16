package com.example.pelangiaquscape;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.pelangiaquscape.Adapter.PageAdapterLaporan;
import com.example.pelangiaquscape.Adapter.PageAdapterMitra;

public class MitraBisnisActivity extends AppCompatActivity {

    ImageView cancel;

    boolean fromTambahPembelian;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mitra_bisnis);


        Intent i = getIntent();
        try{
            fromTambahPembelian = i.getBooleanExtra("fromTambahPembelian", false);
            bundle = new Bundle();
            bundle.putBoolean("fromTambahPembelian", fromTambahPembelian);
        }catch(NullPointerException ex){

        }

        cancel = findViewById(R.id.im_cancel);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Pelanggan"));
        tabLayout.addTab(tabLayout.newTab().setText("Pemasok"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PageAdapterMitra pageAdapterMitra = new PageAdapterMitra(getSupportFragmentManager(),tabLayout.getTabCount(), bundle);
        viewPager.setAdapter(pageAdapterMitra);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        if(fromTambahPembelian)
            viewPager.setCurrentItem(1);



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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
