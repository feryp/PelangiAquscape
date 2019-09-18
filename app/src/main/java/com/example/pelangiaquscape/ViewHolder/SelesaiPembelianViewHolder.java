package com.example.pelangiaquscape.ViewHolder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.pelangiaquscape.Model.Pembelian;
import com.example.pelangiaquscape.R;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SelesaiPembelianViewHolder extends RecyclerView.ViewHolder {

    private Pembelian pembelian;
    private TextView tvKode, tvHarga, tvStatus, tvJam, tvTanggal;
    private Context context;
    private String key;

    public SelesaiPembelianViewHolder(@NonNull View v, Context c) {
        super(v);
        this.context = c;
        tvKode = v.findViewById(R.id.tv_no_pesanan_selesai);
        tvHarga = v.findViewById(R.id.tv_total_harga);
        tvStatus = v.findViewById(R.id.tv_status_pembelian);
        tvJam = v.findViewById(R.id.tv_jam_pembelian);
        tvTanggal = v.findViewById(R.id.tv_tgl_pembelian);

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
    }
}
