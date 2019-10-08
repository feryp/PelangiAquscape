package com.example.pelangiaquscape.Fragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

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
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class PendapatanFragment extends Fragment {

    BarChart barChart;
    TextView tvTotalPendapatan, tvWaktuToday;
    Spinner spinner;

    List<Penjualan> listPenjualan = new ArrayList<>();

    final String[] bulan = {"Januari",
            "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};


    int[] RAINBOW_COLOR ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pendapatan, container, false);

//        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "res/font/roboto_regular.ttf");

        tvTotalPendapatan = v.findViewById(R.id.total_saldo);
        tvWaktuToday = v.findViewById(R.id.tv_waktu_pendapatan);
        barChart = v.findViewById(R.id.bar_pendapatan);
        spinner = v.findViewById(R.id.spinner_tahun_pendapatan);

        RAINBOW_COLOR = getContext().getResources().getIntArray(R.array.rainbow);



        Description description = new Description();
        description.setTextColor(R.color.colorBlueSold);
        description.setText("Rekap Kas Pendapatan");
        barChart.setDescription(description);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int year = Integer.parseInt(spinner.getSelectedItem().toString());
                barChart.clear();
                loadDataPendapatan(year);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);

//        loadDataPendapatan();

        return v;


    }

    void loadDataPenjulaan(int year){
        FirebaseDatabase.getInstance().getReference("Pembelian").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Calendar c = Calendar.getInstance();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void loadDataPendapatan(int year) {
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Harap Tunggu...");
        pd.show();
        FirebaseDatabase.getInstance().getReference("Penjualan").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Calendar c = Calendar.getInstance();

                double jan = 0,
                        feb= 0,
                        mar=0,
                        apr=0,
                        may=0,
                        june=0,
                        jul=0,
                        aug=0,
                        sep=0,
                        oct=0,
                        nov=0,
                        dec=0;


                double total = 0;

                List<BarEntry> entries = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Penjualan penjualan = ds.getValue(Penjualan.class);
                    c.setTimeInMillis(penjualan.getTanggalPenjualan());


                    int month = c.get(Calendar.MONTH);
                    int year1 = c.get(Calendar.YEAR);

                    if(year == year1){
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


                    SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
                    String date = format.format(new Date());
                    tvWaktuToday.setText(date);
                    DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
                    String totalHarga = decimalFormat.format(total);
                    tvTotalPendapatan.setText("Rp. " + totalHarga);




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
//                xAxis.setEnabled(false);
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


                barChart.invalidate();

                pd.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
