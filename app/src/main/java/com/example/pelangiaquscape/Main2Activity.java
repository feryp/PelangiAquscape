package com.example.pelangiaquscape;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.pelangiaquscape.Fragment.BerandaAdminFragment;
import com.example.pelangiaquscape.Fragment.PemberitahuanFragment;
import com.example.pelangiaquscape.Fragment.ProfileFragment;
import com.example.pelangiaquscape.Fragment.RekapFragment;
import com.example.pelangiaquscape.Model.Merek;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Main2Activity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Fragment selectedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        final SharedPreferences pref = getSharedPreferences("MEREK_KEY", Context.MODE_PRIVATE);
        FirebaseDatabase.getInstance().getReference("Merek").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                int i = 1;
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    System.out.println("MEREK KEY: "+ dataSnapshot.getChildren().toString() + " VALUE: "+ dataSnapshot.getChildren() );
                    Log.v("MainActivity", dataSnapshot.getKey());

                    Merek m = ds.getValue(Merek.class);
//                    listMap.put(dataSnapshot.getRef().getKey(), ds.getValue(Merek.class));

                    SharedPreferences.Editor edit = pref.edit();
                    edit.putString(String.valueOf(i), m.getNama());
                    edit.apply();

                    i++;

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BerandaAdminFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new BerandaAdminFragment();
//                            Toast.makeText(MainActivity.this, "Home",Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.nav_rekap:
                            selectedFragment = new RekapFragment();
//                            Toast.makeText(MainActivity.this, "Rekap Kas",Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.nav_inbox:
                            selectedFragment = new PemberitahuanFragment();
//                            Toast.makeText(MainActivity.this, "Pemberitahuan",Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.nav_profile:
//                            SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
//                            editor.putString("profileid", FirebaseAuth.getIntance().getCurrentUser().getUid());
//                            editor.apply();
                            selectedFragment = new ProfileFragment();
//                            Toast.makeText(MainActivity.this, "Profile",Toast.LENGTH_SHORT).show();
                            break;
                    }

                    if (selectedFragment != null) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    }

                    return true;
                }
            };
}
