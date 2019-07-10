package com.example.pelangiaquscape.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.pelangiaquscape.GrafikKeuntunganBulanActivity;
import com.example.pelangiaquscape.GrafikPenjualanBulanActivity;
import com.example.pelangiaquscape.R;

import java.net.Inet4Address;


public class LapBulananFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v  = inflater.inflate(R.layout.fragment_lap_bulanan, container, false);

        RelativeLayout TotalPenjualan = v.findViewById(R.id.container_total_penjualan);
        RelativeLayout TotalKeuntungan = v.findViewById(R.id.container_total_keuntungan);

        Spinner spinner = v.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.bulan_arrays,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spinner.setAdapter(adapter);


        TotalPenjualan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent TotalPenjualan = new Intent(getActivity(), GrafikPenjualanBulanActivity.class);
                startActivity(TotalPenjualan);
            }
        });


        TotalKeuntungan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent TotalKeuntungan = new Intent(getActivity(), GrafikKeuntunganBulanActivity.class);
                startActivity(TotalKeuntungan);
            }
        });



        // Inflate the layout for this fragment
        return v;
    }


}
