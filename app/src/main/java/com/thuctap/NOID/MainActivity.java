package com.thuctap.NOID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.thuctap.NOID.Adapter.ViewPageAdapter;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavMenu;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* Ánh xạ view */
        bottomNavMenu = findViewById(R.id.bottom_nav);
        viewPager = findViewById(R.id.viewPager);
        setUpViewPage();
        /* Sự kiện Menu bottom Nav */
        bottomNavMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.actionHome:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.actionOrder:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.actionStore:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.actionOther:
                        viewPager.setCurrentItem(3);
                        break;

                }
                return true;

            }
        });
        /* Kết thúc sự kiện Menu bottom Nav */
    }

    /* Chuyển đổi các trang với nhau Trang Chủ - Đặt hàng - Cửa hàng - Khác */
    private void setUpViewPage() {
        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPageAdapter);
    }
}