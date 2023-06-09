package com.thuctap.NOID.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.thuctap.NOID.Fragment.CoffeeFragment;
import com.thuctap.NOID.Fragment.FoodSnackFragment;
import com.thuctap.NOID.Fragment.HistoryFragment;
import com.thuctap.NOID.Fragment.MilkTeaFragment;
import com.thuctap.NOID.Fragment.OtherFragment;
import com.thuctap.NOID.Fragment.PackageFragment;
import com.thuctap.NOID.Fragment.PendingFragment;
import com.thuctap.NOID.Fragment.SmoothiesFragment;
import com.thuctap.NOID.Fragment.TeaFragment;

public class ViewHistoryAdapter extends FragmentStatePagerAdapter {
    public ViewHistoryAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new PendingFragment();
            case 1:
                return new HistoryFragment();
            default:
                return new PendingFragment();
        }
    }
    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Đang diễn ra";
                break;
            case 1:
                title = "Lịch sử đơn hàng";
                break;
        }
        return title;
    }
}
