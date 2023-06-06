package com.thuctap.NOID.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.thuctap.NOID.Adapter.ViewCategoryAdapter;
import com.thuctap.NOID.R;

public class DatHangFragment extends Fragment {

    private TabLayout tab_category;
    private ViewPager view_category;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dat_hang, container, false);
        tab_category = view.findViewById(R.id.tab_category);
        view_category = view.findViewById(R.id.view_category);
        /* View */
        ViewCategoryAdapter viewCategoryAdapter = new ViewCategoryAdapter(getActivity().getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        view_category.setAdapter(viewCategoryAdapter);
        tab_category.setupWithViewPager(view_category);


        return view;
    }
}