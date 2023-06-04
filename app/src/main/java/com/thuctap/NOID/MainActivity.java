package com.thuctap.NOID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.thuctap.NOID.Adapter.ViewPageAdapter;
import com.thuctap.NOID.Fragment.DatHangFragment;
import com.thuctap.NOID.Fragment.HoatDongFragment;
import com.thuctap.NOID.Fragment.TrangChuFragment;
import com.thuctap.NOID.GUI.Home;
import com.thuctap.NOID.GUI.LoginActivity;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavMenu;
    private ViewPager viewPage;
    private Button btnLogin;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* Ánh xạ view */
        bottomNavMenu = findViewById(R.id.bottom_nav);
        viewPage = findViewById(R.id.viewPager);

        setUpViewPage();
         /*Sự kiện Menu bottom Nav*/
        bottomNavMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.actionHome:
                        viewPage.setCurrentItem(0);
                        return true;
                    case R.id.actionOrder:
                        viewPage.setCurrentItem(1);
                        break;
                    case R.id.actionHistory:
                        viewPage.setCurrentItem(2);
                        break;
                    case R.id.actionOther:
                        viewPage.setCurrentItem(3);
                        break;
                }
                return true;

            }
        });
         /*Kết thúc sự kiện Menu bottom Nav*/

    }
/*
    *//* Chuyển đổi các trang với nhau Trang Chủ - Đặt hàng - Cửa hàng - Khác */
    private void setUpViewPage() {
        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPage.setAdapter(viewPageAdapter);

        viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavMenu.getMenu().findItem(R.id.actionHome).setChecked(true);
                        break;
                    case 1:
                        bottomNavMenu.getMenu().findItem(R.id.actionOrder).setChecked(true);
                        break;
                    case 2:
                        bottomNavMenu.getMenu().findItem(R.id.actionHistory).setChecked(true);
                        break;
                    case 3:
                        bottomNavMenu.getMenu().findItem(R.id.actionOther).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
