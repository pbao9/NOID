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
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thuctap.NOID.Adapter.CartAdapter;
import com.thuctap.NOID.Database.DBCart;
import com.thuctap.NOID.Database.DBOrder;
import com.thuctap.NOID.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class CartActivity extends AppCompatActivity {
    private DatabaseReference database;
    private FirebaseAuth auth;
    private RecyclerView recyclerView;
    private RelativeLayout relay1, relay2;
    private TextView txtPrice, txtTotalPrice, txtName, txtAddress, txtPhone, Back, txtAddProduct, txtEditProfile, txtRemoveCart;
    private Button btnDatHang, btnTotal;
    private CartAdapter cartAdapter;
    private ArrayList<DBCart> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = getWindow();
        window.setBackgroundDrawableResource(R.drawable.statusbar_gradient);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        database = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.recyclerViewCart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        txtPrice = findViewById(R.id.txtPrice);
        txtTotalPrice = findViewById(R.id.txtTotalPrice);
        txtName = findViewById(R.id.txtName);
        txtAddress = findViewById(R.id.txtAddress);
        txtPhone = findViewById(R.id.txtPhone);
        txtAddProduct = findViewById(R.id.txtAddProduct);
        txtEditProfile = findViewById(R.id.txtEditProfile);
        txtRemoveCart = findViewById(R.id.txtRemoveCart);
        Back = findViewById(R.id.Back);
        btnTotal = findViewById(R.id.btnTotal);
        btnDatHang = findViewById(R.id.btnDatHang);
        relay1 = findViewById(R.id.relay1);
        relay2 = findViewById(R.id.relay2);
        loadCartItems();

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txtEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, Infomation.class);
                startActivity(intent);
            }
        });

        txtAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, MainActivity.class);
                intent.putExtra("fragmentIndex", 1);
                startActivity(intent);
            }
        });
        btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, MainActivity.class);
                intent.putExtra("fragmentIndex", 1);
                startActivity(intent);
            }
        });
    }



    private void loadCartItems() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            database.child("taikhoan").child(userId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String name = snapshot.child("name").getValue(String.class);
                        String phone = snapshot.child("phone").getValue(String.class);
                        String address = snapshot.child("address").getValue(String.class);

                        txtName.setText(name);
                        txtPhone.setText(phone);
                        txtAddress.setText(address);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            database.child("orders").child(userId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    cartItems = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        DBCart cartItem = dataSnapshot.getValue(DBCart.class);
                        cartItems.add(cartItem);
                    }
                    displayCartItems();
                    if (cartItems.isEmpty()) {
                        // Hiển thị giao diện RL1
                        // ...
                        relay2.setVisibility(View.VISIBLE);
                        relay1.setVisibility(View.GONE);
                    } else {
                        // Hiển thị giao diện LLN1
                        // ...
                        relay2.setVisibility(View.GONE);
                        relay1.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle database error
                }
            });
        }
    }

    private void displayCartItems() {
        if (cartItems == null || cartItems.isEmpty()) {
            // Handle empty cart
            return;
        }

        // Combine duplicate items
        ArrayList<DBCart> combinedCartItems = new ArrayList<>();
        HashMap<String, DBCart> cartItemMap = new HashMap<>();
        double totalPrice = 0;
        for (DBCart cartItem : cartItems) {
            String productId = cartItem.getProductId();
            if (cartItemMap.containsKey(productId)) {
                DBCart existingCartItem = cartItemMap.get(productId);
                existingCartItem.setProductCount(existingCartItem.getProductCount() + cartItem.getProductCount());
            } else {
                cartItemMap.put(productId, cartItem);
            }
            totalPrice += Double.parseDouble(cartItem.getProductTotalPrice());
        }

        combinedCartItems.addAll(cartItemMap.values());

        cartAdapter = new CartAdapter(combinedCartItems);
        recyclerView.setAdapter(cartAdapter);
        // Hiển thị tổng giá tiền và tổng giá tiền + chi phí phát sinh
        DecimalFormat decimalFormat = new DecimalFormat("#,### đ");
        String totalPriceText = decimalFormat.format(totalPrice);
        String totalWithExtraFeeText = decimalFormat.format(totalPrice + 30000); // Tổng giá tiền + 30,000 (chi phí phát sinh)

        txtPrice.setText(totalPriceText);
        txtTotalPrice.setText(totalWithExtraFeeText);
        btnTotal.setText("Đặt Hàng " + "(" + totalWithExtraFeeText + ")");
    }



}