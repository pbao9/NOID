package com.thuctap.NOID.GUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thuctap.NOID.Adapter.HistoryAdapter;
import com.thuctap.NOID.Adapter.HistoryDetailAdapter;
import com.thuctap.NOID.Database.DBCart;
import com.thuctap.NOID.Database.DBOrder;
import com.thuctap.NOID.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*public class HistoryDetailActivity extends AppCompatActivity {
    private DatabaseReference dathangDB;
    private DatabaseReference khachHangDB;
    private DatabaseReference database;
    private RecyclerView recyclerViewHistory;
    private TextView txtDate, txtPriceHistory, txtShipCost, txtTotalPriceHistory, txtMaDH, txtMaKH, txtTenKH, txtSDT, txtDC, txtpendingBack;
    private FirebaseAuth auth;
    private HistoryAdapter adapter, adapterCart;
    private List<DBOrder> orderList;
    private List<DBCart> cartList;

    private String makh, tongtiendonhang, thoigian, madh, tensp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = getWindow();
        window.setBackgroundDrawableResource(R.drawable.statusbar_gradient);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);
        dathangDB = FirebaseDatabase.getInstance().getReference("dathang");
        khachHangDB = FirebaseDatabase.getInstance().getReference("taikhoan");

        orderList = new ArrayList<>();
        adapter = new HistoryAdapter(orderList);

        *//*cartList = new ArrayList<>();
        adapterCart = new HistoryAdapter(cartList);*//*

        recyclerViewHistory = findViewById(R.id.reyclerViewDetailHistory);
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewHistory.setAdapter(adapter);

        txtpendingBack = findViewById(R.id.txtpendingBack);
        txtMaDH = findViewById(R.id.txtMaDH);
        txtTenKH = findViewById(R.id.txtTenKH);
        txtSDT = findViewById(R.id.txtSDT);
        txtDC = findViewById(R.id.txtDC);
        txtDate = findViewById(R.id.txtDate);
        txtTotalPriceHistory = findViewById(R.id.txtTotalPriceHistory);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        FirebaseUser currentUser = auth.getCurrentUser();
        String userId = currentUser.getUid();
        if (currentUser != null) {
            database.child("taikhoan").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String name = snapshot.child("name").getValue(String.class);
                        String phone = snapshot.child("phone").getValue(String.class);
                        String address = snapshot.child("address").getValue(String.class);

                        txtTenKH.setText(name);
                        txtDC.setText(address);
                        txtSDT.setText(phone);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        Intent intent = getIntent();
        String orderKey = intent.getStringExtra("KeyDH");
        txtMaDH.setText(orderKey);


        txtpendingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}*/


public class HistoryDetailActivity extends AppCompatActivity {
    private DatabaseReference dathangDB;
    private DatabaseReference khachHangDB;
    private DatabaseReference database;
    private RecyclerView recyclerViewHistory;
    private TextView txtDate, txtPriceHistory, txtShipCost, txtTotalPriceHistory, txtMaDH, txtMaKH, txtTenKH, txtSDT, txtDC, txtpendingBack, txtProductCartCount, txtProductCartName, txtProductCartPrice;
    private FirebaseAuth auth;
    private HistoryAdapter adapter;
    private HistoryDetailAdapter adapterHistoryDetail;
    private List<DBCart> cartList;
    private List<DBOrder> orderList;

    private Button btnDatHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = getWindow();
        window.setBackgroundDrawableResource(R.drawable.statusbar_gradient);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);
        dathangDB = FirebaseDatabase.getInstance().getReference("dathang");
        khachHangDB = FirebaseDatabase.getInstance().getReference("taikhoan");

        cartList = new ArrayList<>();
        orderList = new ArrayList<>();

        recyclerViewHistory = findViewById(R.id.reyclerViewDetailHistory);
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HistoryAdapter(orderList);

        adapterHistoryDetail = new HistoryDetailAdapter(cartList);
        recyclerViewHistory.setAdapter(adapterHistoryDetail);

        txtpendingBack = findViewById(R.id.txtpendingBack);
        txtMaDH = findViewById(R.id.txtMaDH);
        txtTenKH = findViewById(R.id.txtTenKH);
        txtSDT = findViewById(R.id.txtSDT);
        txtDC = findViewById(R.id.txtDC);
        txtDate = findViewById(R.id.txtDate);
        txtTotalPriceHistory = findViewById(R.id.txtTotalPriceHistory);
/*        txtProductCartCount = findViewById(R.id.txtProductCartCount);
        txtProductCartName = findViewById(R.id.txtProductCartName);
        txtProductCartPrice = findViewById(R.id.txtProductCartPrice);*/
        btnDatHang = findViewById(R.id.btnDatHang);
        btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryDetailActivity.this, MainActivity.class);
                intent.putExtra("fragmentIndex", 1);
                startActivity(intent);
            }
        });

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        FirebaseUser currentUser = auth.getCurrentUser();
        String userId = currentUser.getUid();
        if (currentUser != null) {
            database.child("taikhoan").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String name = snapshot.child("name").getValue(String.class);
                        String phone = snapshot.child("phone").getValue(String.class);
                        String address = snapshot.child("address").getValue(String.class);

                        txtTenKH.setText(name);
                        txtDC.setText(address);
                        txtSDT.setText(phone);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        Intent intent = getIntent();
        String orderKey = intent.getStringExtra("KeyDH");
        txtMaDH.setText(orderKey);

        DatabaseReference orderDetailRef = dathangDB.child(orderKey);
        orderDetailRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    DBOrder order = snapshot.getValue(DBOrder.class);
                    DBCart cart = snapshot.getValue(DBCart.class);
                    if (order != null) {
                        txtDate.setText(order.getThoigiandh());

                        double updatedPrice = order.getTongtiendh();
                        DecimalFormat decimalFormat = new DecimalFormat("#,### đ");
                        String formattedPrice = decimalFormat.format(updatedPrice);
                        txtTotalPriceHistory.setText(formattedPrice);

                        // Lấy danh sách sản phẩm từ đơn hàng
                        Map<String, DBCart> sanpham = order.getSanpham();
                        if (sanpham != null) {
                            cartList.clear();
                            cartList.addAll(sanpham.values());
                            adapterHistoryDetail.notifyDataSetChanged();

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        txtpendingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}



