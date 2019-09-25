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
import com.example.pelangiaquscape.Model.User;
import com.example.pelangiaquscape.NotifikasiActivity;
import com.example.pelangiaquscape.PengaturanActivity;
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

public class ProfileFragment extends Fragment implements View.OnClickListener {

    TextView tvNamaPengguna, tvStatusJabatan, tvNoHp, tvBiodata;
    Button editAkun;
    ImageView imgFotoProfile;
    LinearLayout akunToko, notifikasi, bantuan, pengaturan, tentangkami, keluar;

    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;


    String namaPengguna, statusJabatan, noHp, biodata, fotoProfile, kodeLogin;
    User user;


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
        tvNoHp = v.findViewById(R.id.no_hp_profile);
        tvBiodata = v.findViewById(R.id.bio_profile);
        editAkun = v.findViewById(R.id.btn_edit_akun);
        akunToko = v.findViewById(R.id.akun_toko);
        notifikasi = v.findViewById(R.id.notifikasi);
        bantuan = v.findViewById(R.id.bantuan);
        pengaturan = v.findViewById(R.id.pengaturan);
        tentangkami = v.findViewById(R.id.tentangkami);
        keluar = v.findViewById(R.id.keluar);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user1 = dataSnapshot.getValue(User.class);
                user = user1;

                namaPengguna = "" + dataSnapshot.child("username").getValue();

                kodeLogin = "" + dataSnapshot.child("kodeLogin").getValue();
                switch (kodeLogin) {
                    case "0":
                        statusJabatan = "Super Admin";
                        break;
                    case "1":
                        statusJabatan = "Pegawai";
                        break;
                }

                noHp = "" + dataSnapshot.child("telepon").getValue();
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
                tvNoHp.setText(noHp);
                tvBiodata.setText(biodata);

                // REGISTER LISTENER
                editAkun.setOnClickListener(ProfileFragment.this);
                akunToko.setOnClickListener(ProfileFragment.this);
                notifikasi.setOnClickListener(ProfileFragment.this);
                bantuan.setOnClickListener(ProfileFragment.this);
                pengaturan.setOnClickListener(ProfileFragment.this);
                tentangkami.setOnClickListener(ProfileFragment.this);
                keluar.setOnClickListener(ProfileFragment.this);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        return v;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_edit_akun:
                Toast.makeText(getActivity().getApplicationContext(), "Edit Akun", Toast.LENGTH_LONG).show();
                Intent ediitAkun = new Intent(getActivity(), EditProfileActivity.class);
                ediitAkun.putExtra("user", user);
                startActivity(ediitAkun);
                break;
            case R.id.akun_toko:
                Toast.makeText(getActivity().getApplicationContext(), "Akun Toko", Toast.LENGTH_LONG).show();
                Intent akunToko = new Intent(getActivity(), AkunTokoActivity.class);
                startActivity(akunToko);
                break;
            case R.id.notifikasi:
                Toast.makeText(getActivity().getApplicationContext(), "Notifikasi", Toast.LENGTH_LONG).show();
                Intent notifikasi = new Intent(getActivity(), NotifikasiActivity.class);
                startActivity(notifikasi);
                break;
            case R.id.bantuan:
                Toast.makeText(getActivity().getApplicationContext(), "Bantuan", Toast.LENGTH_LONG).show();
                Intent bantuan = new Intent(getActivity(), BantuanActivity.class);
                startActivity(bantuan);
                break;
            case R.id.pengaturan:
                Toast.makeText(getActivity().getApplicationContext(), "Pengaturan", Toast.LENGTH_LONG).show();
                Intent pengaturan = new Intent(getActivity(), PengaturanActivity.class);
                startActivity(pengaturan);
                break;
            case R.id.tentangkami:
                Toast.makeText(getActivity().getApplicationContext(), "Tentang Kami", Toast.LENGTH_LONG).show();
                Intent tentangkami = new Intent(getActivity(), TentangKamiActivity.class);
                startActivity(tentangkami);
                break;
            case R.id.keluar:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getActivity().getApplicationContext(), "Keluar", Toast.LENGTH_LONG).show();
                Intent keluar = new Intent(getActivity(), LoginActivity.class);
                startActivity(keluar);
                break;
        }
    }

}

