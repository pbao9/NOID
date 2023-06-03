package com.thuctap.NOID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.thuctap.NOID.Adapter.ViewPageAdapter;
import com.thuctap.NOID.GUI.Home;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavMenu;
    private ViewPager viewPager;
    private Button btnLogin;
    private FirebaseAuth auth;

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
//                        startActivity(new Intent(getApplicationContext(), Home.class));
//                        overridePendingTransition(0,0);
                        return true;
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

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
                        bottomNavMenu.getMenu().findItem(R.id.actionStore).setChecked(true);
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
