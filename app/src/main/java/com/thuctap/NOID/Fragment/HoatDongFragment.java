package com.thuctap.NOID.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.thuctap.NOID.Adapter.ViewHistoryAdapter;
import com.thuctap.NOID.R;

public class HoatDongFragment extends Fragment {
    private TabLayout tab_history;
    private ViewPager view_history;

    public HoatDongFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hoat_dong, container, false);
        tab_history = view.findViewById(R.id.tab_history);
        view_history = view.findViewById(R.id.view_history);
        ViewHistoryAdapter viewHistoryAdapter = new ViewHistoryAdapter(getActivity().getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        view_history.setAdapter(viewHistoryAdapter);
        tab_history.setupWithViewPager(view_history);
        return view;
    }
}