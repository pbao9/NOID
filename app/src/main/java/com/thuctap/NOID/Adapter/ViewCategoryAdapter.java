package com.thuctap.NOID.Adapter;

import android.text.Html;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.thuctap.NOID.Fragment.CoffeeFragment;
import com.thuctap.NOID.Fragment.FoodSnackFragment;
import com.thuctap.NOID.Fragment.MilkTeaFragment;
import com.thuctap.NOID.Fragment.PackageFragment;
import com.thuctap.NOID.Fragment.SmoothiesFragment;
import com.thuctap.NOID.Fragment.TeaFragment;

public class ViewCategoryAdapter extends FragmentStatePagerAdapter {

    public ViewCategoryAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new CoffeeFragment();
            case 1:
                return new MilkTeaFragment();
            case 2:
                return new TeaFragment();
            case 3:
                return new SmoothiesFragment();
            case 4:
                return new FoodSnackFragment();
            case 5:
                return new PackageFragment();
            default:
                return new CoffeeFragment();
        }
    }

    @Override
    public int getCount() {
        return 6;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Cà Phê";
                break;
            case 1:
                title = "Trà Sữa";
                break;
            case 2:
                title = "Trà Trái Cây";
                break;
            case 3:
                title = "Sinh Tố";
                break;
            case 4:
                title = "Bánh Ngọt";
                break;
            case 5:
                title = "Sản phẩm đóng gói";
                break;
        }
        return  title;
    }
}
