package com.example.pelangiaquscape.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pelangiaquscape.R;

import java.util.List;

public class UploadPenerimaanAdapter extends RecyclerView.Adapter<UploadPenerimaanAdapter.ViewHolder>{

    public List<String> fileNameList;
    public List<String> fileDoneList;

    public UploadPenerimaanAdapter(List<String> fileNameList, List<String> fileDoneList){

        this.fileNameList = fileNameList;
        this.fileDoneList = fileDoneList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_penerimaan, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        String fileName = fileNameList.get(i);
        viewHolder.fileNameView.setText(fileName);

        String fileDone = fileDoneList.get(i);

        if (fileDone.equals("uploading")){

            viewHolder.fileDoneView.setImageResource(R.drawable.progress);
        } else {
            viewHolder.fileDoneView.setImageResource(R.drawable.ic_checkmark);
        }

    }

    @Override
    public int getItemCount() {
        return fileNameList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public TextView fileNameView;
        public ImageView fileDoneView;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

            fileNameView = (TextView) mView.findViewById(R.id.tv_filename);
            fileDoneView = (ImageView) mView.findViewById(R.id.im_progress);
        }
    }
}
