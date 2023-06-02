package com.thuctap.NOID.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.thuctap.NOID.Fragment.HomeFragment;
import com.thuctap.NOID.Fragment.OrderFragment;
import com.thuctap.NOID.Fragment.OtherFragment;
import com.thuctap.NOID.Fragment.StoreFragment;


public class ViewPageAdapter extends FragmentStatePagerAdapter {

    public ViewPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new OrderFragment();
            case 2:
                return new StoreFragment();
            case 3:
                return new OtherFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
