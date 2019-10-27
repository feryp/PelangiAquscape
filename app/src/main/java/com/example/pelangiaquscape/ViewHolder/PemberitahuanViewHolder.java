package com.example.pelangiaquscape.ViewHolder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pelangiaquscape.DetailPemberitahuanActivity;
import com.example.pelangiaquscape.Interface.ItemClickListener;
import com.example.pelangiaquscape.Model.Pemberitahuan;
import com.example.pelangiaquscape.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PemberitahuanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Pemberitahuan pemberitahuan;
    private ImageView imPesan;
    private TextView tvJudul, tvIsi, tvWaktu;
    private Context context;
    private Uri uri;
    private String key;

    private ItemClickListener itemClickListener;

    private View.OnClickListener onClickListener;

    public PemberitahuanViewHolder(@NonNull View v, Context c) {
        super(v);
        this.context = c;
        tvJudul = v.findViewById(R.id.tv_judul_pemberitahuan);
        tvIsi = v.findViewById(R.id.tv_isi_pemberitahuan);
        tvWaktu = v.findViewById(R.id.tv_jam_pemberitahuan);
        imPesan = v.findViewById(R.id.im_pesan);

        itemView.setOnClickListener(this);
    }


    public void bind(Pemberitahuan pemberitahuan) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(pemberitahuan.getWaktu());
        Date time = calendar.getTime();

        SimpleDateFormat formatJam = new SimpleDateFormat("hh:mm");
        String jam = formatJam.format(time);

        tvJudul.setText(pemberitahuan.getJudul());
        tvIsi.setText(pemberitahuan.getPesan());
        tvWaktu.setText(jam);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                Intent intent = new Intent(itemView.getContext(), DetailPemberitahuanActivity.class);
                intent.putExtra("pemberitahuan", pemberitahuan);
                if (uri != null)
                    intent.putExtra("uri", uri.toString());
                ((Activity) context).startActivityForResult(intent, 1);
                break;
        }
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
