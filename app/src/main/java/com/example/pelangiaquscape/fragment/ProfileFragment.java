package com.example.pelangiaquscape.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.pelangiaquscape.R;


public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ListView listView;

        String mTitle[] = {"Notifikasi", "Bantuan", "Tentang Kami", "Keluar"};
        int images[] = {R.drawable.ic_notif, R.drawable.ic_bantuan, R.drawable.ic_info, R.drawable.ic_keluar};
        int images2[] = {R.drawable.ic_arrow_blue};

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);


    }

}
