package com.example.pelangiaquscape.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.pelangiaquscape.Model.Pembelian;
import com.example.pelangiaquscape.R;
import com.example.pelangiaquscape.UploadPenerimaanActivity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PenerimaanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Pembelian pembelian;
    private TextView tvKode, tvHarga, tvStatus, tvJam, tvTanggal, tvStatusUpload;
    private Context context;
    private String key;

    public PenerimaanViewHolder(@NonNull View v, Context c) {
        super(v);
        this.context = c;
        tvKode = v.findViewById(R.id.no_pesanan_pembelian);
        tvHarga = v.findViewById(R.id.total_harga_pembelian);
        tvStatus = v.findViewById(R.id.status_pembayaran);
        tvJam = v.findViewById(R.id.jam_pembelian);
        tvTanggal = v.findViewById(R.id.tanggal_pembelian);
        tvStatusUpload = v.findViewById(R.id.status_upload);

        v.setOnClickListener(this);

    }

    public void bindData(Pembelian pembelian, String key){

        this.key = key;
        this.pembelian = pembelian;


        tvKode.setText(pembelian.getNoPesanan());
        BigDecimal decimal = new BigDecimal(pembelian.getTotalHarga());
        tvHarga.setText(decimal.toString());
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

        tvStatusUpload.setText("Belum Upload");
        tvStatusUpload.setTextColor(Color.RED);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            default:
                Intent i = new Intent(itemView.getContext(), UploadPenerimaanActivity.class);
                i.putExtra("pembelian", pembelian);
                context.startActivity(i);
                break;
        }
    }
}
