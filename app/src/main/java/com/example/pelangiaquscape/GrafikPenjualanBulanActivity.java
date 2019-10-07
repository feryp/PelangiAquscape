package com.example.pelangiaquscape;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class GrafikPenjualanBulanActivity extends AppCompatActivity {

    ImageView cancel;
    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafik_penjualan_bulan);

        cancel = findViewById(R.id.im_cancel);
        barChart = findViewById(R.id.bar_penjualan_bulan);

        Spinner spinner = findViewById(R.id.spinner_bulan);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.bulan_arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spinner.setAdapter(adapter);

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(10f,0));
        barEntries.add(new BarEntry(6f,1));
        barEntries.add(new BarEntry(20f,2));
        barEntries.add(new BarEntry(30f,3));



        BarDataSet barDataSet = new BarDataSet(barEntries, "Total Penjualan");

        ArrayList<String> totalKeuntungan = new ArrayList<>();
        totalKeuntungan.add("Minggu 1");
        totalKeuntungan.add("Minggu 2");
        totalKeuntungan.add("Minggu 3");
        totalKeuntungan.add("Minggu 4");



        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(1f); //set custom bar width
        barData.setValueTextSize(12);
        Description description = new Description();
        description.setTextColor(R.color.colorBlueSold);
        description.setText("Total Penjualan Per-Bulan");
        barChart.setDescription(description);
        barChart.setData(barData);
        barChart.setFitBars(true); // make the x-axis fit exactly all bars
        barChart.invalidate(); //refresh
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(totalKeuntungan));

        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);

        barDataSet.setGradientColor(Color.parseColor("#238BD8"), Color.parseColor("#2346D8"));


        Legend legend = barChart.getLegend();
        legend.setFormSize(10f);
        legend.setForm(Legend.LegendForm.CIRCLE);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
