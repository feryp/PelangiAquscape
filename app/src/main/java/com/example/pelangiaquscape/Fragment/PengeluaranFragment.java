package com.example.pelangiaquscape.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pelangiaquscape.Model.Pembelian;
import com.example.pelangiaquscape.Model.Penjualan;
import com.example.pelangiaquscape.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class PengeluaranFragment extends Fragment {

    BarChart barChart;
    TextView tvTotalPengeluaran;
    final String[] bulan = {"Januari",
            "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
    double jan, feb, mar, apr, may, june, jul, aug, sep, oct, nov, dec;

    int[] RAINBOW_COLOR;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pengeluaran, container, false);

        RAINBOW_COLOR = getContext().getResources().getIntArray(R.array.rainbow);

        barChart = v.findViewById(R.id.bar_pengeluaran);
        tvTotalPengeluaran = v.findViewById(R.id.total_saldo_pengeluaran);

//        ArrayList<BarEntry> barEntries = new ArrayList<>();
//        barEntries.add(new BarEntry(10f, 0));
//        barEntries.add(new BarEntry(6f, 1));
//        barEntries.add(new BarEntry(20f, 2));
//        barEntries.add(new BarEntry(30f, 3));
//        barEntries.add(new BarEntry(15f, 4));
//        barEntries.add(new BarEntry(3f, 5));
//        barEntries.add(new BarEntry(20f, 6));
//        barEntries.add(new BarEntry(22f, 7));
//        barEntries.add(new BarEntry(25f, 8));
//        barEntries.add(new BarEntry(12f, 9));
//        barEntries.add(new BarEntry(15f, 10));
//        barEntries.add(new BarEntry(28f, 11));
//        barEntries.add(new BarEntry(31f, 12));
//
//
//        BarDataSet barDataSet = new BarDataSet(barEntries, "Pengeluaran");
//
//        ArrayList<String> pengeluaran = new ArrayList<>();
//        pengeluaran.add("Januari");
//        pengeluaran.add("Februari");
//        pengeluaran.add("Maret");
//        pengeluaran.add("April");
//        pengeluaran.add("Mei");
//        pengeluaran.add("Juni");
//        pengeluaran.add("Juli");
//        pengeluaran.add("Agustus");
//        pengeluaran.add("September");
//        pengeluaran.add("Oktober");
//        pengeluaran.add("November");
//        pengeluaran.add("Desember");
//
//
//        XAxis xAxis = barChart.getXAxis();
//        xAxis.setGranularity(1f);
//        xAxis.setGranularityEnabled(true);
//        BarData barData = new BarData(barDataSet);
//        barData.setBarWidth(1f); //set custom bar width
//        barData.setValueTextSize(12);
//        Description description = new Description();
//        description.setTextColor(R.color.colorBlueSold);
//        description.setText("Rekap Kas Pengeluaran");
//        barChart.setDescription(description);
//        barChart.setData(barData);
//        barChart.setFitBars(true); // make the x-axis fit exactly all bars
//        barChart.invalidate(); //refresh
//        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(pengeluaran));
//
//        barChart.setTouchEnabled(true);
//        barChart.setDragEnabled(true);
//        barChart.setScaleEnabled(true);
//
//        barDataSet.setGradientColor(Color.parseColor("#F8E166"), Color.parseColor("#F57F29"));
//
//
//        Legend legend = barChart.getLegend();
//        legend.setFormSize(10f);
//        legend.setForm(Legend.LegendForm.CIRCLE);
////        legend.setTypeface(font);
//        legend.setTextSize(12f);
//        legend.setTextColor(Color.BLACK);
//        List<LegendEntry> legendEntries = new ArrayList<>();
//        for (int i = 0; i < pendapatan.size(); i++){
//            LegendEntry entry = new LegendEntry();
//            entry.formColor = ColorTemplate.VORDIPLOM_COLORS[i];
//            entry.label = pendapatan.get(i);
//            legendEntries.add(entry);
//
//        }
//        legend.setXEntrySpace(5f);
//        legend.setYEntrySpace(5f);
//        legend.setCustom(legendEntries);
        loadDataPendapatan(2019);
        return v;
    }

    void loadDataPendapatan(int year) {
        FirebaseDatabase.getInstance().getReference("Pembelian").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Calendar c = Calendar.getInstance();

                double total = 0;
                List<BarEntry> entries = new ArrayList<>();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Pembelian pembelian= ds.getValue(Pembelian.class);
                    c.setTimeInMillis(pembelian.getTanggalPesanan());

                    double totalHarga = pembelian.getTotalHarga();

                    int month = c.get(Calendar.MONTH);
                    int year1 = c.get(Calendar.YEAR);

                    if(year == year1){
                        switch (month) {
                            case 0:
                                jan = jan + totalHarga;
                                break;
                            case 1:
                                feb = feb + totalHarga;

                                break;
                            case 2:
                                mar = mar + totalHarga;

                                break;
                            case 3:
                                apr = apr + totalHarga;

                                break;
                            case 4:
                                may = may + totalHarga;

                                break;
                            case 5:
                                june = june + totalHarga;

                                break;
                            case 6:
                                jul = jul + totalHarga;

                                break;
                            case 7:
                                aug = aug + totalHarga;

                                break;
                            case 8:
                                sep = sep + totalHarga;

                                break;
                            case 9:
                                oct = oct + totalHarga;

                                break;
                            case 10:
                                nov = nov + totalHarga;

                                break;
                            case 11:
                                dec = dec + totalHarga;

                                break;

                        }

                        total += totalHarga;
                    }

                    DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
                    String totalPengeluaran = decimalFormat.format(total);
                    tvTotalPengeluaran.setText("Rp. " + totalPengeluaran);

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

//                    entries.add(new BarEntry(0, (float) 1000000));
//                    entries.add(new BarEntry(1, (float) 1000000));
//                    entries.add(new BarEntry(2, (float) 1000000));
//                    entries.add(new BarEntry(3, (float) 1000000));
//                    entries.add(new BarEntry(4, (float) 1000000));
//                    entries.add(new BarEntry(5, (float) 1000000));
//                    entries.add(new BarEntry(6, (float) 1000000));
//                    entries.add(new BarEntry(7, (float) aug));
//                    entries.add(new BarEntry(8, (float) sep));
//                    entries.add(new BarEntry(9, (float) 1000000));
//                    entries.add(new BarEntry(10, (float) 1000000));
//                    entries.add(new BarEntry(11, (float) 1000000));

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

//                    List<BarDataSet> listDataSet = new ArrayList<>();
//                    for(BarEntry entry:entries){
//                        String bln = bulan[entries.indexOf(entry)];
//                        listDataSet.add(new BarDataSet(entry, bln));
//                    }
                BarDataSet dataSet = new BarDataSet(entries, "Labels");
                dataSet.setColors(RAINBOW_COLOR);

                XAxis xAxis = barChart.getXAxis();


                xAxis.setGranularity(1f);
                xAxis.setGranularityEnabled(true);
                xAxis.setLabelRotationAngle(90);

                YAxis yAxis = barChart.getAxisRight();
                yAxis.setDrawLabels(false);

                BarData barData = new BarData(dataSet);
                barData.setBarWidth(1f); //set custom bar width
                barData.setValueTextSize(12);

                barChart.setData(barData);

//                Legend legend = barChart.getLegend();
//                legend.setWordWrapEnabled(true);
//                legend.setFormSize(10f);
////                legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
//                legend.setOrientation(Legend.LegendOrientation.VERTICAL);
//                legend.setForm(Legend.LegendForm.SQUARE);
//                legend.setTextSize(12f);
//                legend.setTextColor(Color.BLACK);
//                List<LegendEntry> legendEntries = new ArrayList<>();
//
//                for (int i = 0; i < entries.size(); i++) {
//                    LegendEntry entry = new LegendEntry();
//                    entry.formColor = RAINBOW_COLOR[i];
//                    entry.label = bulan[i];
//                    legendEntries.add(entry);
//
//                }
//                legend.setXEntrySpace(5f);
//                legend.setYEntrySpace(5f);
//                legend.setCustom(legendEntries);

                barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(bulan));
                barChart.invalidate();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
