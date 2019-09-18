package com.example.pelangiaquscape.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.pelangiaquscape.Fragment.PelangganFragment;
import com.example.pelangiaquscape.Fragment.PemasokFragment;

public class PageAdapterMitra extends FragmentStatePagerAdapter {

    int counttab;

    Bundle bundle;

    public PageAdapterMitra(FragmentManager fm, int counttab, Bundle bundle){
        super(fm);
        this.counttab = counttab;
        this.bundle = bundle;

    }
    @Override
    public Fragment getItem(int i) {
        switch (i) {

            case 0:
                PelangganFragment pelangganFragment = new PelangganFragment();
                if(bundle!= null){
                    pelangganFragment.setArguments(bundle);
                }
                return pelangganFragment;
            case 1:
                PemasokFragment pemasokFragment = new PemasokFragment();
                if(bundle!= null){
                    pemasokFragment.setArguments(bundle);
                }
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
