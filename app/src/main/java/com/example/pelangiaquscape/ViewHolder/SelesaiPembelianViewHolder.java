package com.example.pelangiaquscape.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.pelangiaquscape.DetailSelesaiPembelianActivity;
import com.example.pelangiaquscape.Interface.ItemClickListener;
import com.example.pelangiaquscape.Model.Pembelian;
import com.example.pelangiaquscape.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SelesaiPembelianViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private Pembelian pembelian;
    private TextView tvKode, tvHarga, tvStatus, tvJam, tvTanggal;
    private Context context;
    private String key;

    private ItemClickListener itemClickListener;


    public SelesaiPembelianViewHolder(@NonNull View v, Context c) {
        super(v);
        this.context = c;
        tvKode = v.findViewById(R.id.tv_no_pesanan_selesai);
        tvHarga = v.findViewById(R.id.tv_total_harga);
        tvStatus = v.findViewById(R.id.tv_status_pembelian);
        tvJam = v.findViewById(R.id.tv_jam_pembelian);
        tvTanggal = v.findViewById(R.id.tv_tgl_pembelian);
        v.setOnClickListener(this);

    }

    public void bindData(Pembelian pembelian, String key){

        this.key = key;
        this.pembelian = pembelian;

        tvKode.setText(pembelian.getNoPesanan());
//        BigDecimal decimal = new BigDecimal(pembelian.getTotalHarga());
        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
        String harga = decimalFormat.format(pembelian.getTotalHarga());
        tvHarga.setText("Rp. " + harga);
        //        switch (pembelian.getStatusPembelian()) {
//            case 1:
//                tvStatusPembelian.setText("Lunas");
//                break;
//            case 2:
//                tvStatusPembelian.setText("Belum Lunas");
//                break;
//        }

        tvStatus.setText("Lunas");
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(pembelian.getTanggalPesanan());
        Date date = cal.getTime();

        SimpleDateFormat formatHour =  new SimpleDateFormat("hh:mm");
        SimpleDateFormat formatDate = new SimpleDateFormat("dd MMMM yyyy");

        String hour = formatHour.format(date);
        String dt = formatDate.format(date);

        tvJam.setText(hour);
        tvTanggal.setText(dt);
    }

    public void setOnClickListener(ItemClickListener listener){
        this.itemClickListener = listener;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            default:
                Intent intent = new Intent(context, DetailSelesaiPembelianActivity.class);
                intent.putExtra("key", key);
                intent.putExtra("value", pembelian);
                intent.putExtra("no", getAdapterPosition() +1);
                context.startActivity(intent);
                break;
        }
    }
}
