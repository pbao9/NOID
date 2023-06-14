package com.thuctap.NOID.GUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thuctap.NOID.Adapter.HistoryAdapter;
import com.thuctap.NOID.Database.DBCart;
import com.thuctap.NOID.Database.DBOrder;
import com.thuctap.NOID.Adapter.HistoryDetailAdapter;
import com.thuctap.NOID.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HistoryDetailActivity extends AppCompatActivity {
    private DatabaseReference dathangDB;
    private DatabaseReference khachHangDB;
    private DatabaseReference database;
    private RecyclerView recyclerViewHistory;
    private TextView txtDate, txtPriceHistory, txtShipCost, txtTotalPriceHistory, txtMaDH, txtMaKH, txtTenKH, txtSDT, txtDC, txtpendingBack;
    private FirebaseAuth auth;
    private HistoryAdapter adapter;
    private List<DBOrder> productList;
    private String makh, tongtiendonhang, thoigian, madh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = getWindow();
        window.setBackgroundDrawableResource(R.drawable.statusbar_gradient);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);

        productList = new ArrayList<>();
        adapter = new HistoryAdapter(productList);

        recyclerViewHistory = findViewById(R.id.reyclerViewHistory);
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewHistory.setAdapter(adapter);

        txtpendingBack = findViewById(R.id.txtpendingBack);
        txtMaDH = findViewById(R.id.txtMaDH);
        txtTenKH = findViewById(R.id.txtTenKH);
        txtSDT = findViewById(R.id.txtSDT);
        txtDC = findViewById(R.id.txtDC);
        txtDate = findViewById(R.id.txtDate);
        txtTotalPriceHistory = findViewById(R.id.txtTotalPriceHistory);

        txtpendingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}


