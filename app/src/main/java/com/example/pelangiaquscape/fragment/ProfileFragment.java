package com.example.pelangiaquscape.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pelangiaquscape.BantuanActivity;
import com.example.pelangiaquscape.EditProfileActivity;
import com.example.pelangiaquscape.LaporanPenjualanActivity;
import com.example.pelangiaquscape.LoginActivity;
import com.example.pelangiaquscape.Model.User;
import com.example.pelangiaquscape.NotifikasiActivity;
import com.example.pelangiaquscape.R;
import com.example.pelangiaquscape.TentangKamiActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    TextView namaPengguna, statusJabatan, bio;
    Button editAkun;
    ImageView image_profile;
    RelativeLayout notifikasi, bantuan, tentangkami, keluar;

    FirebaseUser firebaseUser;
    String profileid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        SharedPreferences preferences = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        profileid = preferences.getString("profileid", "none");

        image_profile = v.findViewById(R.id.image_profile);
        namaPengguna = v.findViewById(R.id.nama_pengguna_profile);
        statusJabatan = v.findViewById(R.id.status_jabatan_profile);
        bio = v.findViewById(R.id.bio_profile);
        editAkun = v.findViewById(R.id.btn_edit_akun);
        notifikasi = v.findViewById(R.id.notifikasi);
        bantuan = v.findViewById(R.id.bantuan);
        tentangkami = v.findViewById(R.id.tentangkami);
        keluar = v.findViewById(R.id.keluar);

//        userInfo();

        editAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ediitAkun = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(ediitAkun);
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

        tentangkami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tentangkami = new Intent(getActivity(), TentangKamiActivity.class);
                startActivity(tentangkami);
            }
        });

        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent keluar = new Intent(getActivity(), LoginActivity.class);
                startActivity(keluar);
            }
        });

        return v;
    }

//    private void userInfo(){
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User").child(profileid);
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (getContext() == null){
//                    return;
//                }
//
//                User user = dataSnapshot.getValue(User.class);
//
//                Glide.with(getContext()).load(user.getImageurl()).into(image_profile);
//                namaPengguna.setText(user.getUsername());
//                statusJabatan.setText(user.getStatusJabatan());
//                bio.setText(user.getBio());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }


}
