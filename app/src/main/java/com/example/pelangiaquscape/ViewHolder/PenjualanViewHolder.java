package com.example.pelangiaquscape.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.pelangiaquscape.Adapter.UploadPenerimaanAdapter;
import com.example.pelangiaquscape.Model.Penjualan;
import com.example.pelangiaquscape.R;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PenjualanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView tvTotalHargaPenjualan, tvNoStruk, tvTanggalPenjualan, tvKeteranganPembayaran, tvJamPembayaran;



    OnClickListener listener;



    public PenjualanViewHolder(View v) {
        super(v);
        tvTotalHargaPenjualan = v.findViewById(R.id.total_harga_penjualan);
        tvNoStruk = v.findViewById(R.id.no_struk_penjualan);
        tvTanggalPenjualan = v.findViewById(R.id.tanggal_list_penjualan);
        tvKeteranganPembayaran = v.findViewById(R.id.keterangan_pembayaran);
        tvJamPembayaran = v.findViewById(R.id.jam_list_penjualan);
        v.setOnClickListener(this);

    }

    public void bindData(Penjualan penjualan){
        BigDecimal bd = new BigDecimal(penjualan.getTotalPenjualan());
        tvTotalHargaPenjualan.setText(bd.toString());
        tvNoStruk.setText(penjualan.getNoPenjualan());
        tvKeteranganPembayaran.setText("Tunai");

        // FORMAT DATE
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(penjualan.getTanggalPenjualan());
        SimpleDateFormat simpleDate = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat simpleHour = new SimpleDateFormat("HH:mm");
        Date da = c.getTime();
        String stringDate = simpleDate.format(da);
        String stringHour = simpleHour.format(da);
        // FORMAT DATE END

        tvTanggalPenjualan.setText(stringDate);
        tvJamPembayaran.setText(stringHour);

    }



    public void setItemClickListener(OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v, getAdapterPosition());
    }


    public interface OnClickListener{
        void onClick(View v, int position);
    }


}
