package com.example.pelangiaquscape;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.pelangiaquscape.Model.Pembelian;
import com.example.pelangiaquscape.Model.Penjualan;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Observable;

public class GrafikKeuntunganBulanActivity extends AppCompatActivity {

    ImageView cancel;
    private BarChart barChart;
    Spinner spinner;

    List<Penjualan> listPenjualan = new ArrayList<>();

    Calendar calendar = Calendar.getInstance();
    final int YEAR = calendar.get(Calendar.YEAR);
    final int MONTH = calendar.get(Calendar.MONTH);

    final String[] minggu = {"Minggu 1", "Minggu 2", "Minggu 3", "Minggu 4"};

    int[] RAINBOW_COLOR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafik_keuntungan_bulan);

        cancel = findViewById(R.id.im_cancel);
        barChart = findViewById(R.id.bar_keuntungan_bulan);
        spinner =  findViewById(R.id.spinner_bulan);

        RAINBOW_COLOR = getApplicationContext().getResources().getIntArray(R.array.rainbow);

        Description description = new Description();
        description.setTextColor(R.color.colorBlueSold);
        description.setText("Total Keuntungan");
        barChart.setDescription(description);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.bulan_arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                int week = Integer.parseInt(spinner.getSelectedItem().toString());
                barChart.clear();
                loadDataPenjualan(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void loadDataPenjualan(int month) {
        FirebaseDatabase.getInstance().getReference("Penjualan").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Calendar c = Calendar.getInstance();

                double totalPenjualan = 0;

                List<BarEntry> entries = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Penjualan penjualan = ds.getValue(Penjualan.class);
                    c.setTimeInMillis(penjualan.getTanggalPenjualan());

                    int pYear = c.get(Calendar.YEAR);
                    int pMonth = c.get(Calendar.MONTH);

                    if (month == pMonth && YEAR == pYear){
                        totalPenjualan = totalPenjualan + penjualan.getTotalPenjualan();

                    }


                }

                double finalTotalPenjualan = totalPenjualan;
                FirebaseDatabase.getInstance().getReference("Pembelian").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Calendar c = Calendar.getInstance();
                        double minggu1 = 0,
                                minggu2 = 0,
                                minggu3 = 0,
                                minggu4 = 0;

                        double totalPembelian = 0;
                        double totalPendapatan = 0;

                        List<BarEntry> entries = new ArrayList<>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()){
                            Pembelian pembelian = ds.getValue(Pembelian.class);
                            c.setTimeInMillis(pembelian.getTanggalPesanan());
                            int week = c.get(Calendar.WEEK_OF_MONTH);
                            int month1 = c.get(Calendar.MONTH);

                            if (month == month1){
                                switch (week){
                                    case 0:
                                        minggu1 = minggu1 + pembelian.getTanggalPesanan();
                                        break;
                                    case 1:
                                        minggu2 = minggu2 + pembelian.getTanggalPesanan();
                                        break;
                                    case 2:
                                        minggu3 = minggu3 + pembelian.getTanggalPesanan();
                                        break;
                                    case 3:
                                        minggu4 = minggu4 + pembelian.getTanggalPesanan();
                                        break;

                                }

                                totalPembelian = totalPembelian + pembelian.getTotalHarga();

                            }

                            totalPendapatan = finalTotalPenjualan - totalPembelian;
                        }

                        entries.add(new BarEntry(0, (float) minggu1));
                        entries.add(new BarEntry(1, (float) minggu2));
                        entries.add(new BarEntry(2, (float) minggu3));
                        entries.add(new BarEntry(3, (float) minggu4));

                        System.out.println("minggu 1" + minggu1 + "\n"
                                + "minggu 2 " + minggu2 + "\n"
                                + "minggu 3 " + minggu3 + "\n"
                                + "minggu 4 " + minggu4 + "\n"

                        );

                        BarDataSet dataSet = new BarDataSet(entries, "Total Keuntungan");
//                dataSet.setColors(RAINBOW_COLOR);
                        dataSet.setGradientColor(Color.parseColor("#238BD8"), Color.parseColor("#2346D8"));

                        XAxis xAxis = barChart.getXAxis();
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

                        xAxis.setGranularity(1f);
                        xAxis.setGranularityEnabled(true);
                        xAxis.setLabelRotationAngle(90);

                        YAxis yAxis = barChart.getAxisRight();
                        yAxis.setDrawGridLines(false);
                        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
                        yAxis.setDrawLabels(false);


                        BarData barData = new BarData(dataSet);
                        barData.setBarWidth(1f); //set custom bar width
                        barData.setValueTextSize(12);

                        barChart.setData(barData);

                        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(minggu));
                        barChart.setDrawValueAboveBar(false);

                        Legend legend = barChart.getLegend();
                        legend.setFormSize(10f);
                        legend.setForm(Legend.LegendForm.CIRCLE);


                        barChart.invalidate();

                    }



                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}
