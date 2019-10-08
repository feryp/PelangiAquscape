package com.example.pelangiaquscape;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.pelangiaquscape.Model.Penjualan;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GrafikPenjualanTahunActivity extends AppCompatActivity {

    ImageView cancel;
    BarChart barChart;
    Spinner spinner;

    List<Penjualan> listPenjualan = new ArrayList<>();

    final String[] bulan = {"Januari",
            "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};

    int[] RAINBOW_COLOR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafik_penjualan_tahun);

        cancel = findViewById(R.id.im_cancel);
        barChart = findViewById(R.id.bar_penjualan_tahun);
        spinner = findViewById(R.id.spinner_tahun);

        RAINBOW_COLOR = getApplicationContext().getResources().getIntArray(R.array.rainbow);

        Description description = new Description();
        description.setTextColor(R.color.colorBlueSold);
        description.setText("Total Penjualan");
        barChart.setDescription(description);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tahun_arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int year = Integer.parseInt(spinner.getSelectedItem().toString());
                barChart.clear();
                loadDataPenjualan(year);
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

    private void loadDataPenjualan(int year) {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Harap Tunggu...");
        pd.show();
        FirebaseDatabase.getInstance().getReference("Penjualan").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Calendar c = Calendar.getInstance();
                double jan = 0,
                        feb = 0,
                        mar = 0,
                        apr = 0,
                        may = 0,
                        june = 0,
                        jul = 0,
                        aug = 0,
                        sep = 0,
                        oct = 0,
                        nov = 0,
                        dec = 0;

                double total = 0;

                List<BarEntry> entries = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Penjualan penjualan = ds.getValue(Penjualan.class);
                    c.setTimeInMillis(penjualan.getTanggalPenjualan());

                    int month = c.get(Calendar.MONTH);
                    int year1 = c.get(Calendar.YEAR);

                    if (year == year1) {
                        switch (month) {
                            case 0:
                                jan = jan + penjualan.getTotalPenjualan();
                                break;
                            case 1:
                                feb = feb + penjualan.getTotalPenjualan();

                                break;
                            case 2:
                                mar = mar + penjualan.getTotalPenjualan();

                                break;
                            case 3:
                                apr = apr + penjualan.getTotalPenjualan();

                                break;
                            case 4:
                                may = may + penjualan.getTotalPenjualan();

                                break;
                            case 5:
                                june = june + penjualan.getTotalPenjualan();

                                break;
                            case 6:
                                jul = jul + penjualan.getTotalPenjualan();

                                break;
                            case 7:
                                aug = aug + penjualan.getTotalPenjualan();

                                break;
                            case 8:
                                sep = sep + penjualan.getTotalPenjualan();

                                break;
                            case 9:
                                oct = oct + penjualan.getTotalPenjualan();

                                break;
                            case 10:
                                nov = nov + penjualan.getTotalPenjualan();

                                break;
                            case 11:
                                dec = dec + penjualan.getTotalPenjualan();

                                break;
                        }

                        total += penjualan.getTotalPenjualan();
                    }
                }

                entries.add(new BarEntry(0, (float) jan));
                entries.add(new BarEntry(1, (float) feb));
                entries.add(new BarEntry(2, (float) mar));
                entries.add(new BarEntry(3, (float) apr));
                entries.add(new BarEntry(4, (float) may));
                entries.add(new BarEntry(5, (float) june));
                entries.add(new BarEntry(6, (float) jul));
                entries.add(new BarEntry(7, (float) aug));
                entries.add(new BarEntry(8, (float) sep));
                entries.add(new BarEntry(9, (float) oct));
                entries.add(new BarEntry(10, (float) nov));
                entries.add(new BarEntry(11, (float) dec));

                System.out.println("jan " + jan + "\n"
                        + "feb " + feb + "\n"
                        + "mar " + mar + "\n"
                        + "apr " + apr + "\n"
                        + "may " + may + "\n"
                        + "june  " + june + "\n"
                        + "jul " + jul + "\n"
                        + "aug " + aug + "\n"
                        + "sep " + sep + "\n"
                        + "oct " + oct + "\n"
                        + "nov " + nov + "\n"
                        + "dec " + dec + "\n"
                );

                BarDataSet dataSet = new BarDataSet(entries, "Total Penjualan");
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

                barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(bulan));
                barChart.setDrawValueAboveBar(false);

                Legend legend = barChart.getLegend();
                legend.setFormSize(10f);
                legend.setForm(Legend.LegendForm.CIRCLE);


                barChart.invalidate();
                pd.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
