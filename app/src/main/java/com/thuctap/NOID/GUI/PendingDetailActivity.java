package com.thuctap.NOID.GUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thuctap.NOID.Adapter.PendingAdapter;
import com.thuctap.NOID.Adapter.PendingDetailAdapter;
import com.thuctap.NOID.Database.DBCart;
import com.thuctap.NOID.Database.DBOrder;
import com.thuctap.NOID.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PendingDetailActivity extends AppCompatActivity {

    private DatabaseReference dathangDB;
    private DatabaseReference khachHangDB;
    private DatabaseReference database;
    private RecyclerView recyclerViewPending;
    private TextView txtDate, txtStatusPending, txtPriceHistory, txtNodata, txtPriceBefore, txtShipCost, txtTotalPriceHistory, txtMaDH, txtMaKH, txtTenKH, txtSDT, txtDC, txtpendingBack, txtProductCartCount, txtProductCartName, txtProductCartPrice;
    private FirebaseAuth auth;
    private PendingAdapter pendingAdapter;
    private PendingDetailAdapter pendingDetailAdapter;
    private List<DBCart> cartList;
    private List<DBOrder> orderList;
    private List<String> orderKeys;

    private Button btnXacNhan, btnHuyDon;

    private final String CHANNEL_ID = "PENDING";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = getWindow();
        window.setBackgroundDrawableResource(R.drawable.statusbar_gradient);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_detail);
        dathangDB = FirebaseDatabase.getInstance().getReference("dathang");
        khachHangDB = FirebaseDatabase.getInstance().getReference("taikhoan");

        cartList = new ArrayList<>();
        orderList = new ArrayList<>();
        orderKeys = new ArrayList<>();

        recyclerViewPending = findViewById(R.id.reyclerViewDetailChiTietDonHang);
        recyclerViewPending.setLayoutManager(new LinearLayoutManager(this));
        pendingDetailAdapter = new PendingDetailAdapter(cartList);
        recyclerViewPending.setAdapter(pendingDetailAdapter);

        txtpendingBack = findViewById(R.id.txtpendingBack);
        txtStatusPending = findViewById(R.id.txtStatusPending);
        txtMaDH = findViewById(R.id.txtMaDH);
        txtTenKH = findViewById(R.id.txtTenKH);
        txtSDT = findViewById(R.id.txtSDT);
        txtDC = findViewById(R.id.txtDC);
        txtDate = findViewById(R.id.txtDate);
        txtTotalPriceHistory = findViewById(R.id.txtTotalPriceHistory);
        txtPriceBefore = findViewById(R.id.txtPriceBefore);
        btnXacNhan = findViewById(R.id.btnXacNhan);
        btnHuyDon = findViewById(R.id.btnXoaDH);
        txtMaDH.setVisibility(View.GONE);
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

                        Intent intent = getIntent();
                        String orderKey = intent.getStringExtra("orderKey");
                        /*txtMaDH.setText(orderKey);*/

                        DatabaseReference orderDetailRef = dathangDB.child(orderKey);
                        orderDetailRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    DBOrder order = snapshot.getValue(DBOrder.class);
                                    if (order != null) {
                                        txtDate.setText(order.getThoigiandh());
                                        txtStatusPending.setText(order.getTinhtrang());
                                        if (order.getTinhtrang().equals("Đang giao")) {
                                            btnXacNhan.setVisibility(View.VISIBLE);
                                        }
                                        if (order.getTinhtrang().equals("Đang chờ xác nhận")) {
                                            /*Add btn đặt hàng vào đây*/
                                            btnHuyDon.setVisibility(View.VISIBLE);
                                        }
                                        if (order.getTinhtrang().equals("Đã hủy")) {
                                            /*Add btn đặt hàng vào đây*/
                                            btnHuyDon.setVisibility(View.GONE);
                                            btnXacNhan.setVisibility(View.GONE);
                                        }
                                        /*Tổng tiền*/
                                        double updatedPrice = order.getTongtiendh();
                                        DecimalFormat decimalFormat = new DecimalFormat("#,### đ");
                                        String formattedPrice = decimalFormat.format(updatedPrice);
                                        txtTotalPriceHistory.setText(formattedPrice);
                                        /*Tiền tạm tính*/
                                        double priceBefore = updatedPrice - 30000;
                                        String formattedPriceBefore = decimalFormat.format(priceBefore);
                                        txtPriceBefore.setText(formattedPriceBefore);

                                        // Lấy danh sách sản phẩm từ đơn hàng
                                        Map<String, DBCart> sanpham = order.getSanpham();
                                        if (sanpham != null) {
                                            cartList.clear();
                                            cartList.addAll(sanpham.values());
                                            pendingDetailAdapter.notifyDataSetChanged();
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        txtpendingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishOrder();
            }
        });

        btnHuyDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelOrder();
            }
        });


    }

    private void finishOrder() {

        AlertDialog.Builder builder = new AlertDialog.Builder(PendingDetailActivity.this);
        View dialogview = getLayoutInflater().inflate(R.layout.dialog_confirm_order, null);
        CheckBox chkFinal = dialogview.findViewById(R.id.chkFinalOrder);
        Button btnConfirm = dialogview.findViewById(R.id.btnFinishOrder);
        builder.setView(dialogview);
        AlertDialog dialog = builder.create();

        chkFinal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                btnConfirm.setEnabled(isChecked);
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy orderKey từ intent
                Intent intent = getIntent();
                String orderKey = intent.getStringExtra("orderKey");

                // Tạo đối tượng Map để cập nhật trường tinhtrang
                Map<String, Object> updateData = new HashMap<>();
                updateData.put("tinhtrang", "Đã giao");
                // Lấy thời gian hiện tại
                String currentTime = getCurrentTime();

                // Cập nhật trạng thái và thời gian
                updateData.put("thoigiannh", currentTime);

                DatabaseReference orderRef = dathangDB.child(orderKey);
                orderRef.updateChildren(updateData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Cập nhật thành công
                                Toast.makeText(PendingDetailActivity.this, "Chúc bạn có phút giây tận hưởng đồ ăn ngon miệng ^^!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(PendingDetailActivity.this, MainActivity.class);
                                intent.putExtra("fragmentIndex", 1);
                                startActivity(intent);
                                finish();
                                dialog.hide();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Cập nhật thất bại
                                Toast.makeText(PendingDetailActivity.this, "Có lỗi xảy ra. Vui lòng thử lại sau.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        dialog.show();


    }


    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    private void cancelOrder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PendingDetailActivity.this);
        View dialogview = getLayoutInflater().inflate(R.layout.dialog_cancel_order, null);
        CheckBox chkRemove = dialogview.findViewById(R.id.chkCancelOrder);
        Button btnConfirm = dialogview.findViewById(R.id.btnConfirm);
        builder.setView(dialogview);
        AlertDialog dialog = builder.create();

        chkRemove.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                btnConfirm.setEnabled(isChecked);
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String orderKey = intent.getStringExtra("orderKey");

                Map<String, Object> updateData = new HashMap<>();
                updateData.put("tinhtrang", "Đã hủy");

                DatabaseReference orderRef = dathangDB.child(orderKey);
                orderRef.updateChildren(updateData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Cập nhật thành công
                                Toast.makeText(PendingDetailActivity.this, "Đã hủy đơn hàng thành công ^^!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(PendingDetailActivity.this, MainActivity.class);
                                intent.putExtra("fragmentIndex", 2);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Cập nhật thất bại
                                Toast.makeText(PendingDetailActivity.this, "Có lỗi xảy ra. Vui lòng thử lại sau.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        dialog.show();
    }
}
