package com.example.pelangiaquscape.ViewHolder;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import com.example.pelangiaquscape.Model.Merek;
import com.example.pelangiaquscape.Model.Penyimpanan;
import com.example.pelangiaquscape.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PenyimpananViewHolder extends RecyclerView.ViewHolder {

    private TextView tvKode, tvMerek, tvStok, tvSatuan, tvKeterangan, tvJam, tvTanggal, tvJenisPenyimpanan;
//    private Context context;


    private LinearLayout ll;

    SharedPreferences preferences;

    public PenyimpananViewHolder(@NonNull View v) {
        super(v);
        tvKode = v.findViewById(R.id.list_nama_barang_inventory);
        tvMerek = v.findViewById(R.id.merek_inventory);
        tvStok = v.findViewById(R.id.stok_barang_inventory);
        tvSatuan = v.findViewById(R.id.satuan_unit_barang_inventory);
        tvKeterangan = v.findViewById(R.id.keterangan_status_inventory);
        tvJam = v.findViewById(R.id.jam_penyimpanan);
        tvTanggal = v.findViewById(R.id.tgl_inventory);
        tvJenisPenyimpanan = v.findViewById(R.id.keterangan_barang_keluar);
        ll = v.findViewById(R.id.linear_ket);

    }

    public void bindData(Penyimpanan penyimpanan) {

        FirebaseDatabase.getInstance().getReference("Barang").child(penyimpanan.getKeyBarang()).child("merek").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue(String.class) != null) {
                    FirebaseDatabase.getInstance().getReference("Merek").child(dataSnapshot.getValue(String.class)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Merek m = dataSnapshot.getValue(Merek.class);
                            tvMerek.setText(m.getNama());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }else{
                    tvMerek.setText("UNKNOWN");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        // GET CALENDAR FROM PENYIMPANAN
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(penyimpanan.getTimeInMilis());
        SimpleDateFormat tglFormat = new SimpleDateFormat("dd MMMM yyyy");
        SimpleDateFormat jamFormat = new SimpleDateFormat("HH:mm");
        Date date = cal.getTime();
        String dateFormat = tglFormat.format(date);
        String timeFormat = jamFormat.format(date);


        // SET ALL TEXTVIEW FROM PENYIMPANAN
//        int noMerek = Integer.valueOf(penyimpanan.getMerekBarang());
//        String merek = preferences.getString(String.valueOf(noMerek),"unknown");
        tvKode.setText(penyimpanan.getKodeBarang());
        BigDecimal de = new BigDecimal(penyimpanan.getJumlahBarang());
        tvStok.setText(de.toString());
        tvSatuan.setText("pcs");
        tvKeterangan.setText(penyimpanan.getKeteranganBarang());
        tvTanggal.setText(dateFormat);
        tvJam.setText(timeFormat);

        final int sdk = android.os.Build.VERSION.SDK_INT;

        switch(penyimpanan.getJenisPenyimpanan()){
            case 0:
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    ll.setBackgroundDrawable(ContextCompat.getDrawable(itemView.getContext(), R.drawable.keterangan_barang_masuk) );
                } else {
                    ll.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.keterangan_barang_masuk));
                }
                tvJenisPenyimpanan.setText("Barang Masuk");
                break;
            case 1:
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    ll.setBackgroundDrawable(ContextCompat.getDrawable(itemView.getContext(), R.drawable.keterangan_barang_keluar) );
                } else {
                    ll.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.keterangan_barang_keluar));
                }
                tvJenisPenyimpanan.setText("Barang Keluar");
                break;
        }

//        tvKode.setText(penyimpanan.getNamaBarang());
//        tvMerek.setText(penyimpanan.getNamaBarang());
    }
}
