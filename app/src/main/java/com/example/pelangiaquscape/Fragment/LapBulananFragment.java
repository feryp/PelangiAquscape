package com.example.pelangiaquscape.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pelangiaquscape.GrafikKeuntunganBulanActivity;
import com.example.pelangiaquscape.GrafikPenjualanBulanActivity;
import com.example.pelangiaquscape.Model.ItemKeranjang;
import com.example.pelangiaquscape.Model.Pembelian;
import com.example.pelangiaquscape.Model.Penjualan;
import com.example.pelangiaquscape.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;


public class LapBulananFragment extends Fragment implements View.OnClickListener{
    RelativeLayout TotalPenjualan, TotalKeuntungan;
    Spinner spinner;

    TextView tvTotalPenjualan, tvTotalKeuntungan, tvTotalTransaksi, tvTotalTerjual, tvTotalProdukPalingLaku;

    Calendar calendar = Calendar.getInstance();
    final int YEAR = calendar.get(Calendar.YEAR);
    final int MONTH = calendar.get(Calendar.MONTH);

    Penjualan penjualan;

    DecimalFormat decimalFormat = new DecimalFormat("#,###.00");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v  = inflater.inflate(R.layout.fragment_lap_bulanan, container, false);

        // INIT VIEW
        TotalPenjualan = v.findViewById(R.id.container_total_penjualan);
        TotalKeuntungan = v.findViewById(R.id.container_total_keuntungan);
        tvTotalKeuntungan = v.findViewById(R.id.tv_nominal_keuntungan);
        tvTotalPenjualan = v.findViewById(R.id.tv_nominal_penjualan);
        tvTotalTransaksi = v.findViewById(R.id.tv_jumlah_transaksi);
        tvTotalTerjual = v.findViewById(R.id.tv_jumlah_produk_terjual);
        tvTotalProdukPalingLaku = v.findViewById(R.id.tv_jumlah_produk_laku);

        spinner = v.findViewById(R.id.spinner_bulan);


        // REGISTER LISTENER
        TotalPenjualan.setOnClickListener(this);
        TotalKeuntungan.setOnClickListener(this);


        // INIT ARRAY RESOURCE FOR SPINNER
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.bulan_arrays,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spinner.setAdapter(adapter);

//        spinner.setSelection(((ArrayAdapter)spinner.getAdapter()).getPosition(String.valueOf(YEAR)));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadDataPenjualan(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Inflate the layout for this fragment
        return v;
    }


    void loadDataPenjualan(int month){
        FirebaseDatabase.getInstance().getReference("Penjualan").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Calendar c = Calendar.getInstance();

                double totalPenjualan = 0;
                int jmlTransaksi = 0;
                int jmlProdukTerjual = 0;
                int qtyProdukPalingLaku = 0;
                ItemKeranjang produkPalingLaku = null;


                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Penjualan p = ds.getValue(Penjualan.class);
                    List<ItemKeranjang> listKeranjang = p.getListItemKeranjang();

                    c.setTimeInMillis(p.getTanggalPenjualan());
                    int pYear = c.get(Calendar.YEAR);
                    int pMonth = c.get(Calendar.MONTH);

                    if(month == pMonth && YEAR == pYear){
                        totalPenjualan = totalPenjualan +  p.getTotalPenjualan();
                        jmlTransaksi = jmlTransaksi + 1;
                        for(ItemKeranjang ik:listKeranjang){
                            jmlProdukTerjual = jmlProdukTerjual + ik.getQty();
                            if(ik.getQty() > qtyProdukPalingLaku){
                                qtyProdukPalingLaku = ik.getQty();
                                produkPalingLaku = ik;
                            }

                        }
                    }



                }


                String totalPenjualanBulan= decimalFormat.format(totalPenjualan);
                tvTotalPenjualan.setText("Rp. " + totalPenjualanBulan);
                tvTotalTransaksi.setText(String.valueOf(jmlTransaksi));
                tvTotalTerjual.setText(String.valueOf(jmlProdukTerjual));
                if(produkPalingLaku != null)
                tvTotalProdukPalingLaku.setText(produkPalingLaku.getKode());

                double finalTotalPenjualan = totalPenjualan;
                FirebaseDatabase.getInstance().getReference("Pembelian").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Calendar c = Calendar.getInstance();
                        double totalPembelian = 0;
                        double totalPendapatan = 0;
                        for(DataSnapshot data: dataSnapshot.getChildren()){

                            Pembelian pembelian = data.getValue(Pembelian.class);
                            c.setTimeInMillis(pembelian.getTanggalPesanan());
                            int pMonth = c.get(Calendar.MONTH);
                            int pYear = c.get(Calendar.YEAR);
                            if(month == pMonth && YEAR == pYear && !pembelian.getProses()) {
                                totalPembelian = totalPembelian + pembelian.getTotalHarga();
                            }
                        }

                        totalPendapatan = finalTotalPenjualan - totalPembelian;
                        String totalKeuntunganBulan = decimalFormat.format(totalPendapatan);
                        tvTotalKeuntungan.setText("Rp. " + totalKeuntunganBulan);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.container_total_penjualan:
                Intent TotalPenjualan = new Intent(getActivity(), GrafikPenjualanBulanActivity.class);
                TotalPenjualan.putExtra("penjualan", penjualan);
                startActivity(TotalPenjualan);
                break;
            case R.id.container_total_keuntungan:
                Intent TotalKeuntungan = new Intent(getActivity(), GrafikKeuntunganBulanActivity.class);
                startActivity(TotalKeuntungan);
                break;
        }
    }

    interface SimpleCallback{
        void callback(Object data);
    }
}
