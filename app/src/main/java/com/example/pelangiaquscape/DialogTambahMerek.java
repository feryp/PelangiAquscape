package com.example.pelangiaquscape;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.pelangiaquscape.Model.Merek;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class DialogTambahMerek extends AppCompatDialogFragment {

    FirebaseDatabase db;
    DatabaseReference dr;
    TextInputEditText etMerek;

    NoticeDialogListener mListener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.input_merek_barang, null);
        etMerek = view.findViewById(R.id.et_input_merek_barang);


        builder.setView(view)
                .setTitle("Merek Barang")
                .setPositiveButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogPositiveClick(DialogTambahMerek.this, etMerek.getText().toString());
                        dismiss();
                    }
                })
                .setNegativeButton("Tambah", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogNegativeClick(DialogTambahMerek.this, etMerek.getText().toString());
                        dismiss();

                    }


                });

        return builder.create();
    }

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogTambahMerek dialog, String nameValue);

        public void onDialogNegativeClick(DialogTambahMerek dialog, String nameValue);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (NoticeDialogListener) context;

        } catch (ClassCastException ex) {
            throw new ClassCastException(context.toString() + "must implement NoticeDialogListener");
        }
    }
}
