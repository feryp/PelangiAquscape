package com.example.pelangiaquscape.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.pelangiaquscape.Fragment.ProsesPembelianFragment;
import com.example.pelangiaquscape.Fragment.SelesaiPembelianFragment;

public class PageAdapterPembelian extends FragmentStatePagerAdapter {

    int counttab;

    public PageAdapterPembelian(FragmentManager fm, int counttab){
        super(fm);
        this.counttab = counttab;

    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {

            case 0:
                ProsesPembelianFragment prosesPembelianFragment = new ProsesPembelianFragment();
                return prosesPembelianFragment;
            case 1:
                SelesaiPembelianFragment selesaiPembelianFragment = new SelesaiPembelianFragment();
                return selesaiPembelianFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return counttab;
    }
}
