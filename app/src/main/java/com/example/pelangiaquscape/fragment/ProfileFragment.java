package com.example.pelangiaquscape.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.pelangiaquscape.BantuanActivity;
import com.example.pelangiaquscape.EditProfileActivity;
import com.example.pelangiaquscape.LaporanPenjualanActivity;
import com.example.pelangiaquscape.NotifikasiActivity;
import com.example.pelangiaquscape.R;
import com.example.pelangiaquscape.TentangKamiActivity;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        Button btn_edit_profile = v.findViewById(R.id.btn_edit_akun);
        RelativeLayout notifikasi = v.findViewById(R.id.notifikasi);
        RelativeLayout bantuan = v.findViewById(R.id.bantuan);
        RelativeLayout tentangkamu = v.findViewById(R.id.tentangkami);

        btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btn_edit_profile = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(btn_edit_profile);
            }
        });

        notifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent notifikasi = new Intent(getActivity(), NotifikasiActivity.class);
                startActivity(notifikasi);
            }
        });

        bantuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bantuan = new Intent(getActivity(), BantuanActivity.class);
                startActivity(bantuan);
            }
        });

        tentangkamu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tentangkami = new Intent(getActivity(), TentangKamiActivity.class);
                startActivity(tentangkami);
            }
        });

        return v;
    }

}
