package com.example.pelangiaquscape;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pelangiaquscape.Model.Barang;
import com.example.pelangiaquscape.Model.Merek;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DummyInsertDataActivity extends AppCompatActivity {

    Button btn;

    FirebaseDatabase fd;
    DatabaseReference ref;
    BufferedReader br = null;
    String separator = ",";

    List<String> arra = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dummy_insertdata_activity);

        btn = findViewById(R.id.btn_insert_dummy_data);

        fd = FirebaseDatabase.getInstance();
        ref = fd.getReference().child("Merek");
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                Toast.makeText(DummyInsertDataActivity.this, "data changed", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(DummyInsertDataActivity.this, "failed ", Toast.LENGTH_SHORT).show();
//            }
//        });


        arra.add("Try");

//        fd.getReference("Merek").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                int i = 1;
//                for(DataSnapshot ds:dataSnapshot.getChildren()){
//                    for(DataSnapshot da: ds.getChildren()){
//                        arra.add(da.getValue(String.class));
//                        Log.v("real arra", arra.get(i));
//                    }
//                    i++;
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });





        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int inc = 1;
//                String barangtemp = null;
//                List<List<String>> records = new ArrayList<>();
//                List<String> list = new ArrayList<>();

//                try {
//                    String csvfileString = getApplicationInfo().dataDir+ File.separatorChar + "data_toko.csv";
//                    File csvFile = new File(csvfileString);
//                    InputStream is = getApplication().getAssets().open("data_toko.csv");
//                    InputStreamReader isr = new InputStreamReader(is);
//                    br = new BufferedReader(isr);
//                    CSVReader reader = new CSVReader(new FileReader("csvfile.getAbsolutePath()"));
//                    String line;
//                    while ((line = br.readLine()) != null) {
//                        Log.v("DUMMY", line);
//                        String[] values = line.split(separator);
//                        records.add(Arrays.asList(values));

//                        String namaBarang = values[0];
//                        final String merk = values[1];
//                        double harga = Integer.parseInt(values[2]);
//                        Log.v("merk", merk);


//                    }
//                } catch (FileNotFoundException ex) {
//                    ex.printStackTrace();
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }



//                for(int i = 0; i < records.size(); i++){
//                    for(int j = 0; j < records.get(i).size(); j++){
//                        String kodeMerek = null;
//                        for(String k :arra){
//                            Log.v("k", k);
//                            if(k.equalsIgnoreCase(records.get(i).get(1))){
//                                kodeMerek = String.valueOf(arra.indexOf(k));
//
//                            }
//                        }
//                        Barang b = new Barang(records.get(i).get(0), Double.parseDouble(records.get(i).get(2)), 0, "", (kodeMerek != null? kodeMerek: ""),0,0);
//                        Log.v("Try", b.getMerek());
//                        int ka = i+1;
//                        fd.getReference().child("Barang").child(String.valueOf(ka)).setValue(b).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                Toast.makeText(DummyInsertDataActivity.this, "insert barang success", Toast.LENGTH_SHORT).show();
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(DummyInsertDataActivity.this, "insert gagal", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//                    }
//                }
                int inc = 1;
                String merkd = null;
                List<List<String>> records = new ArrayList<>();
                try {
//                    br = new BufferedReader(new FileReader("data_toko.csv"));
                    br = new BufferedReader(new InputStreamReader(getAssets().open("data_toko.csv")));
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                        String[] values = line.split(separator);
                        records.add(Arrays.asList(values));
                        String namaBarang = values[0];
                        String merk = values[1];
                        if(!merk.equals(merkd)){
                            Merek m = new Merek(merk);
                            ref.child(String.valueOf(inc)).setValue(m);
                            inc++;
                            merkd = merk;
                            Log.v("INSERT FIREBASE", merk);
                        }

                    }
                } catch (FileNotFoundException ex) {
//                    Toast.makeText(DummyInsertDataActivity.this, "", Toast.LENGTH_SHORT).show();
//            Logger.getLogger(Main3.class.getName()).log(Level.SEVERE, null, ex);
                    Log.v("HERE", "FILE NOT FOUND");
                    ex.printStackTrace();
                } catch (IOException ex) {
//            Logger.getLogger(Main3.class.getName()).log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                    Log.v("HERE", "IO EXCEPTION");
                }
            }
        });




    }
}
