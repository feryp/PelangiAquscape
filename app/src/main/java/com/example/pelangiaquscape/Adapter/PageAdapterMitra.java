package com.example.pelangiaquscape.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.pelangiaquscape.fragment.LapBulananFragment;
import com.example.pelangiaquscape.fragment.LapTahunanFragment;
import com.example.pelangiaquscape.fragment.PelangganFragment;
import com.example.pelangiaquscape.fragment.PemasokFragment;

public class PageAdapterMitra extends FragmentStatePagerAdapter {

    int counttab;

    public PageAdapterMitra(FragmentManager fm, int counttab){
        super(fm);
        this.counttab = counttab;

    }
    @Override
    public Fragment getItem(int i) {
        switch (i) {

            case 0:
                PelangganFragment pelangganFragment = new PelangganFragment();
                return pelangganFragment;
            case 1:
                PemasokFragment pemasokFragment = new PemasokFragment();
                return pemasokFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return counttab;
    }
}
