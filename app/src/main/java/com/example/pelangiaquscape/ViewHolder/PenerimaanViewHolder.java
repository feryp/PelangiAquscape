package com.example.pelangiaquscape.ViewHolder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pelangiaquscape.Model.Pembelian;
import com.example.pelangiaquscape.R;
import com.example.pelangiaquscape.UploadPenerimaanActivity;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PenerimaanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView imCheck;
    private Pembelian pembelian;
    private TextView tvKode, tvHarga, tvStatus, tvJam, tvTanggal, tvStatusUpload;
    private Context context;
    private String key;
    private Uri uri;

    public PenerimaanViewHolder(@NonNull View v, Context c) {
        super(v);
        this.context = c;
        tvKode = v.findViewById(R.id.no_pesanan_pembelian);
        tvHarga = v.findViewById(R.id.total_harga_pembelian);
        tvStatus = v.findViewById(R.id.status_pembayaran);
        tvJam = v.findViewById(R.id.jam_pembelian);
        tvTanggal = v.findViewById(R.id.tanggal_pembelian);
        tvStatusUpload = v.findViewById(R.id.status_upload);
        imCheck = v.findViewById(R.id.im_progress);

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
        FirebaseStorage.getInstance()
                .getReference("Faktur")
                .child(pembelian.getNoPesanan()+".jpg")
                .getDownloadUrl()
                .addOnSuccessListener((Uri uri) ->{

                    imCheck.setImageResource(R.drawable.ic_checkmark);
                    tvStatusUpload.setText("Sudah Upload");
                    tvStatusUpload.setTextColor(Color.GREEN);
                    this.uri = uri;


        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            default:
                Intent i = new Intent(itemView.getContext(), UploadPenerimaanActivity.class);
                i.putExtra("pembelian", pembelian);
                if(uri!=null)
                    i.putExtra("uri", uri.toString());
                ((Activity)context).startActivityForResult(i,1);
                break;
        }
    }
}
