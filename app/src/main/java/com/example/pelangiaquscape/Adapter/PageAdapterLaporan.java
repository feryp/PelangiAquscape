package com.example.pelangiaquscape.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.pelangiaquscape.fragment.LapBulananFragment;
import com.example.pelangiaquscape.fragment.LapTahunanFragment;

public class PageAdapterLaporan extends FragmentStatePagerAdapter {
    int counttab;

    public PageAdapterLaporan(FragmentManager fm, int counttab) {
        super(fm);
        this.counttab = counttab;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {

            case 0:
                LapBulananFragment lapBulananFragment = new LapBulananFragment();
                return lapBulananFragment;
            case 1:
                LapTahunanFragment lapTahunanFragment = new LapTahunanFragment();
                return lapTahunanFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return counttab;
    }
}
