package com.thuctap.NOID.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.thuctap.NOID.Fragment.DatHangFragment;
import com.thuctap.NOID.Fragment.HoatDongFragment;
import com.thuctap.NOID.Fragment.KhacFragment;
import com.thuctap.NOID.Fragment.TrangChuFragment;


public class ViewPageAdapter extends FragmentStatePagerAdapter {

    public ViewPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TrangChuFragment();
            case 1:
                return new DatHangFragment();
            case 2:
                return new HoatDongFragment();
            case 3:
                return new KhacFragment();
            default:
                return new TrangChuFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
