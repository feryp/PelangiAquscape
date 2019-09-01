package com.example.pelangiaquscape;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

public class TambahPenyimpananActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView cancel, save;
    Spinner spinnerKeteranganBarang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_penyimpanan);

        //INIT VIEW
        cancel = findViewById(R.id.im_cancel);

        spinnerKeteranganBarang = findViewById(R.id.spinner_keterangan_barang);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this,
                R.array.keterangan_barang_arrays,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spinnerKeteranganBarang.setAdapter(adapter);

        cancel.setOnClickListener(this);
        save.setOnClickListener(this);


//        // FORMAT DATE
//        Calendar c = Calendar.getInstance();
//        c.setTimeInMillis(tglInput);
//        SimpleDateFormat simple = new SimpleDateFormat("dd MMM yyyy");
//        Date da = c.getTime();
//        String fors = simple.format(da);
//        // FORMAT DATE END
////        etTanggalInputGudang.setText(fors);


//    @Override
//    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//        Calendar c = Calendar.getInstance();
//        c.set(year, month, dayOfMonth);
//        DateFormat format = new SimpleDateFormat("dd MMM yyyy");
//
//        etTanggalInputGudang.setText(format.format(c.getTime()));
//
//        tglInput = c.getTimeInMillis();
//
//        Calendar s = Calendar.getInstance();
//        s.setTimeInMillis(tglInput);
//
////        Log.v("testinsert", String.valueOf(s.getTimeInMillis()));
////        Log.v("testinsert", String.valueOf(s.get(Calendar.MONTH)));
//
//
    }

    private void save() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_cancel:
                finish();
                break;
            case R.id.im_save:
                save();
                finish();
                break;
        }
    }
}




