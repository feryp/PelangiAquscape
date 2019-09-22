package com.example.pelangiaquscape.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pelangiaquscape.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;


public class PengeluaranFragment extends Fragment {

    BarChart barChart;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pengeluaran, container, false);

        barChart = v.findViewById(R.id.bar_pengeluaran);

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(10f, 0));
        barEntries.add(new BarEntry(6f, 1));
        barEntries.add(new BarEntry(20f, 2));
        barEntries.add(new BarEntry(30f, 3));
        barEntries.add(new BarEntry(15f, 4));
        barEntries.add(new BarEntry(3f, 5));
        barEntries.add(new BarEntry(20f, 6));
        barEntries.add(new BarEntry(22f, 7));
        barEntries.add(new BarEntry(25f, 8));
        barEntries.add(new BarEntry(12f, 9));
        barEntries.add(new BarEntry(15f, 10));
        barEntries.add(new BarEntry(28f, 11));
        barEntries.add(new BarEntry(31f, 12));


        BarDataSet barDataSet = new BarDataSet(barEntries, "Pengeluaran");

        ArrayList<String> pengeluaran = new ArrayList<>();
        pengeluaran.add("Januari");
        pengeluaran.add("Februari");
        pengeluaran.add("Maret");
        pengeluaran.add("April");
        pengeluaran.add("Mei");
        pengeluaran.add("Juni");
        pengeluaran.add("Juli");
        pengeluaran.add("Agustus");
        pengeluaran.add("September");
        pengeluaran.add("Oktober");
        pengeluaran.add("November");
        pengeluaran.add("Desember");


        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(1f); //set custom bar width
        barData.setValueTextSize(12);
        Description description = new Description();
        description.setTextColor(R.color.colorBlueSold);
        description.setText("Rekap Kas Pengeluaran");
        barChart.setDescription(description);
        barChart.setData(barData);
        barChart.setFitBars(true); // make the x-axis fit exactly all bars
        barChart.invalidate(); //refresh
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(pengeluaran));

        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);

        barDataSet.setGradientColor(Color.parseColor("#F8E166"), Color.parseColor("#F57F29"));


        Legend legend = barChart.getLegend();
        legend.setFormSize(10f);
        legend.setForm(Legend.LegendForm.CIRCLE);
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

        return v;
    }

}
