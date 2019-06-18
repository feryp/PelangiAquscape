package com.example.pelangiaquscape.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.pelangiaquscape.LaporanPenjualanActivity;
import com.example.pelangiaquscape.R;


public class BerandaFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v  = inflater.inflate(R.layout.fragment_beranda, container, false);

        LinearLayout linearLayout = v.findViewById(R.id.container_laporan);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent container_laporan = new Intent(getActivity(), LaporanPenjualanActivity.class);
                startActivity(container_laporan);
            }
        });
        return v;
    }


}
