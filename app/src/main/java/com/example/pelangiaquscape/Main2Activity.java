package com.example.pelangiaquscape;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.pelangiaquscape.Fragment.BerandaAdminFragment;
import com.example.pelangiaquscape.Fragment.PemberitahuanFragment;
import com.example.pelangiaquscape.Fragment.ProfileFragment;
import com.example.pelangiaquscape.Fragment.RekapFragment;

public class Main2Activity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Fragment selectedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);


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
