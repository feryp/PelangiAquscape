package com.example.pelangiaquscape.ViewHolder;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pelangiaquscape.Interface.ItemClickListener;
import com.example.pelangiaquscape.Model.Pegawai;
import com.example.pelangiaquscape.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class PegawaiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    public TextView tv_nama_pegawai;
    public TextView tv_jabatan;
    public ImageView im_foto_pegawai;

    private Pegawai pegawai;
    private Uri mImageUri;

    private View.OnLongClickListener onLongClickListener;

    private ItemClickListener itemClickListener;

    private View.OnClickListener onClickListener;

    String fotoProfile;
    Context mContext;


    public PegawaiViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_nama_pegawai = itemView.findViewById(R.id.tv_nama_pegawai);
        tv_jabatan = itemView.findViewById(R.id.tv_jabatan);
        im_foto_pegawai = itemView.findViewById(R.id.foto_pegawai);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void bindData(Pegawai pegawai) {
        this.pegawai = pegawai;
        tv_nama_pegawai.setText(pegawai.getNamaPegawai());
        tv_jabatan.setText(pegawai.getJabatan());


        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference pegawaiRef = storageRef.child("Profile").child(pegawai.getId() + ".jpg");
        pegawaiRef.getDownloadUrl().addOnSuccessListener(uri -> {

            try {
                if (uri != null){
                    mImageUri = uri;
                    Picasso.get().load(uri).into(im_foto_pegawai);
                }

            } catch (IllegalArgumentException e){
                im_foto_pegawai.setImageResource(R.drawable.pegawai);
            }

        });

//        try {
//            Picasso.get().load(fotoProfile).into(im_foto_pegawai);
//        } catch (IllegalArgumentException e){
//            im_foto_pegawai.setImageResource(R.drawable.pegawai);
//        }

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                itemClickListener.onClick(v, getAdapterPosition(), false);
                break;
        }

    }

    public void setOnLongClickListener(View.OnLongClickListener longClickListener) {
        this.onLongClickListener = longClickListener;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public boolean onLongClick(View v) {
        onLongClickListener.onLongClick(v);
        return false;
    }
}
