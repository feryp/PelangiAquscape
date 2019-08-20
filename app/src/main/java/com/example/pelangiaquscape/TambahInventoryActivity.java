package com.example.pelangiaquscape;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

public class TambahInventoryActivity extends AppCompatActivity {

    ImageView cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_inventory);

        Spinner spinner = findViewById(R.id.spinner_keterangan_barang);
        cancel = findViewById(R.id.im_cancel);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.keterangan_barang_arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spinner.setAdapter(adapter);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



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

}


