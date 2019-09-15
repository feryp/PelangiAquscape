package com.example.pelangiaquscape.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pelangiaquscape.AkunTokoActivity;
import com.example.pelangiaquscape.BantuanActivity;
import com.example.pelangiaquscape.EditProfileActivity;
import com.example.pelangiaquscape.LoginActivity;
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
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {

    TextView tvNamaPengguna, tvStatusJabatan, tvBiodata;
    Button editAkun;
    ImageView imgFotoProfile;
    LinearLayout akunToko, notifikasi, bantuan, tentangkami, keluar;

    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;



    String namaPengguna, statusJabatan, biodata, fotoProfile, kodeLogin;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User").child(firebaseAuth.getUid());

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);


        imgFotoProfile = v.findViewById(R.id.image_profile);
        tvNamaPengguna = v.findViewById(R.id.nama_pengguna_profile);
        tvStatusJabatan = v.findViewById(R.id.status_jabatan_profile);
        tvBiodata = v.findViewById(R.id.bio_profile);
        editAkun = v.findViewById(R.id.btn_edit_akun);
        akunToko = v.findViewById(R.id.akun_toko);
        notifikasi = v.findViewById(R.id.notifikasi);
        bantuan = v.findViewById(R.id.bantuan);
        tentangkami = v.findViewById(R.id.tentangkami);
        keluar = v.findViewById(R.id.keluar);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                namaPengguna = "" + dataSnapshot.child("namapengguna").getValue();

                kodeLogin = "" + dataSnapshot.child("kode_login").getValue();
                switch (kodeLogin) {
                    case "0":
                        statusJabatan = "Super Admin";
                        break;
                    case "1":
                        statusJabatan = "Pegawai";
                        break;
                }

                biodata = "" + dataSnapshot.child("bio").getValue();
                fotoProfile = "" + dataSnapshot.child("fotoProfile").getValue();


                try {
                    Picasso.get().load(fotoProfile).into(imgFotoProfile);
                } catch (IllegalArgumentException e) {
                    imgFotoProfile.setImageResource(R.drawable.ic_foto_profile);
                }

                //set data
                tvNamaPengguna.setText(namaPengguna);
                tvStatusJabatan.setText(statusJabatan);
                tvBiodata.setText(biodata);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        editAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(), "Edit Akun", Toast.LENGTH_LONG).show();
                Intent ediitAkun = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(ediitAkun);
            }
        });

        akunToko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "Akun Toko", Toast.LENGTH_LONG).show();
                Intent akunToko = new Intent(getActivity(), AkunTokoActivity.class);
                startActivity(akunToko);

            }
        });

        notifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(), "Notifikasi", Toast.LENGTH_LONG).show();
                Intent notifikasi = new Intent(getActivity(), NotifikasiActivity.class);
                startActivity(notifikasi);
            }
        });

        bantuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(), "Bantuan", Toast.LENGTH_LONG).show();
                Intent bantuan = new Intent(getActivity(), BantuanActivity.class);
                startActivity(bantuan);
            }
        });

        tentangkami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(), "Tentang Kami", Toast.LENGTH_LONG).show();
                Intent tentangkami = new Intent(getActivity(), TentangKamiActivity.class);
                startActivity(tentangkami);
            }
        });

        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getActivity().getApplicationContext(), "Keluar", Toast.LENGTH_LONG).show();
                Intent keluar = new Intent(getActivity(), LoginActivity.class);
                startActivity(keluar);
            }
        });


        return v;
    }



}
