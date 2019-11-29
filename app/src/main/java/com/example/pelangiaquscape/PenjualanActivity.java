package com.example.pelangiaquscape;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pelangiaquscape.Model.Penjualan;
import com.example.pelangiaquscape.ViewHolder.PenjualanViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PenjualanActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {


    private static final String TAG = "PenjualanActivity";

    ImageView cancel, filter;
    RecyclerView rvPenjualan;

    List<Penjualan> listPenjualan = new ArrayList<>();

    String[] listFilter;

    FirebaseDatabase fd;
    DatabaseReference dr;
    FirebaseRecyclerAdapter adapter;
    ImageView iv;
    TextView tvImage;
    Query query;
    private Penjualan penjualan;
    private String key;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    long millis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan);


        // INIT VIEW
        cancel = findViewById(R.id.im_kembali);
        filter = findViewById(R.id.im_filter);
        rvPenjualan = findViewById(R.id.rv_penjualan);
        rvPenjualan.setHasFixedSize(true);
//        rvPenjualan.setLayoutManager(new LinearLayoutManager(this));

        iv = findViewById(R.id.iv_ilustrasi_pelanggankosong);
        tvImage = findViewById(R.id.tv_pelanggan_kosong);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(PenjualanActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener, year, month, day);

        //SET LISTENER
        cancel.setOnClickListener(this);
        filter.setOnClickListener(this);

        // INIT VIEW END

        fd = FirebaseDatabase.getInstance();
        dr = fd.getReference().child("Penjualan");
        loadDataPenjualan();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_kembali:
                finish();
                break;
            case R.id.im_filter:
               loadDatePicker();



//                listFilter = new String[]{"Oleh Tanggal", "Oleh Waktu"};
//                AlertDialog.Builder builder = new AlertDialog.Builder(PenjualanActivity.this);
//                builder.setTitle("Pilih salah satu");
//                builder.setIcon(R.drawable.ic_list);
//                builder.setSingleChoiceItems(listFilter, -1, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        dialog.dismiss();
//                    }
//                });
//                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//                //show alert dialog
//                AlertDialog alertDialog = builder.create();
////                alertDialog.show();
                break;
        }
    }

    private void loadDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(PenjualanActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Calendar calendar = Calendar.getInstance();

                      //  Toast.makeText(PenjualanActivity.this, "month " + month + " day " + day, Toast.LENGTH_SHORT).show();

                        calendar.set(year, month, day);
                        calendar.set(Calendar.MILLISECOND, 0);
                        calendar.set(Calendar.HOUR, 0);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MINUTE,0);

                       /* SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");

                       String dateString = dateFormat.format(calendar.getTime());*/

                        millis = calendar.getTimeInMillis();

                      /*  String tanggalTest = millisToDate(millis);

                       Toast.makeText(PenjualanActivity.this, "Tanggal test : " + tanggalTest, Toast.LENGTH_SHORT).show();
*/
                        loadDataPenjualanFilter();
                        // 1572627600000
                    }
                }, year, month, day);

        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();

    }

    private String millisToDate(long millis) {

        return DateFormat.getDateInstance(DateFormat.SHORT).format(millis);
        //You can use DateFormat.LONG instead of SHORT

    }

    void loadDataPenjualan() {
        query = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Penjualan")
                .orderByChild("tanggalPenjualan");

        FirebaseRecyclerOptions<Penjualan> options =
                new FirebaseRecyclerOptions.Builder<Penjualan>()
                        .setQuery(query, Penjualan.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<Penjualan, PenjualanViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull PenjualanViewHolder holder, int position, @NonNull final Penjualan model) {
                Log.v("modelPenjualan", model.getNoPenjualan());
                holder.bindData(model);
                holder.setItemClickListener(new PenjualanViewHolder.OnClickListener() {
                    @Override
                    public void onClick(View v, int position) {
                        Toast.makeText(PenjualanActivity.this, "Penjualan", Toast.LENGTH_SHORT).show();
                        Intent p = new Intent(PenjualanActivity.this, DetailPenjualanActivity.class);
                        p.putExtra("penjualan", penjualan);
                        p.putExtra("key", key);
                        p.putExtra("penjualan", model);
                        startActivity(p);
                    }
                });
            }

            @NonNull
            @Override
            public PenjualanViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_penjualan, viewGroup, false);
                Log.v("hereOnCreateView", "true");
                return new PenjualanViewHolder(v);
            }

            @Override
            public void onDataChanged() {
                super.onDataChanged();
                iv.setVisibility(View.GONE);
                tvImage.setVisibility(View.GONE);

            }
        };
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        rvPenjualan.setLayoutManager(manager);
        rvPenjualan.setAdapter(adapter);

        Log.v("itemCount", String.valueOf(rvPenjualan.getAdapter().getItemCount()));
        rvPenjualan.setVisibility(View.VISIBLE);
    }

    void loadDataPenjualanFilter() {
        query = FirebaseDatabase.getInstance().getReference()
                .child("Penjualan")
                .orderByChild("tanggalPenjualan")
                .startAt(millis).endAt(millis + "\uf8ff");
//        Query tanggalQuery = tanggalRef.orderByChild("tanggalPenjualan").startAt(tanggal).endAt(tanggal + "\uf8ff");
//
        FirebaseRecyclerOptions<Penjualan> options =
                new FirebaseRecyclerOptions.Builder<Penjualan>()
                        .setQuery(query, Penjualan.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<Penjualan, PenjualanViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull PenjualanViewHolder holder, int position, @NonNull final Penjualan model) {
                Log.v("modelPenjualan", model.getNoPenjualan());
                holder.bindData(model);
                holder.setItemClickListener(new PenjualanViewHolder.OnClickListener() {
                    @Override
                    public void onClick(View v, int position) {
                        Toast.makeText(PenjualanActivity.this, "Penjualan", Toast.LENGTH_SHORT).show();
                        Intent p = new Intent(PenjualanActivity.this, DetailPenjualanActivity.class);
                        p.putExtra("penjualan", penjualan);
                        p.putExtra("key", key);
                        p.putExtra("penjualan", model);
                        startActivity(p);
                    }
                });
            }

            @NonNull
            @Override
            public PenjualanViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_penjualan, viewGroup, false);
                Log.v("hereOnCreateView", "true");
                return new PenjualanViewHolder(v);
            }

            @Override
            public void onDataChanged() {
                super.onDataChanged();
                iv.setVisibility(View.GONE);
                tvImage.setVisibility(View.GONE);

            }
        };
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        rvPenjualan.setLayoutManager(manager);
        rvPenjualan.setAdapter(adapter);

        Log.v("itemCount", String.valueOf(rvPenjualan.getAdapter().getItemCount()));
        rvPenjualan.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

    }
}
