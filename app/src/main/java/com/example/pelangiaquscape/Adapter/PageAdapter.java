package com.example.pelangiaquscape.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.pelangiaquscape.fragment.PendapatanFragment;
import com.example.pelangiaquscape.fragment.PengeluaranFragment;

public class PageAdapter extends FragmentStatePagerAdapter {
int counttab;

    public PageAdapter(FragmentManager fm, int counttab) {
        super(fm);
        this.counttab = counttab;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){

            case 1 :
                PendapatanFragment pendapatanFragment = new PendapatanFragment();
                return pendapatanFragment;
            case 2 :
                PengeluaranFragment pengeluaranFragment = new PengeluaranFragment();
                return pengeluaranFragment;
                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return counttab;
    }
}
