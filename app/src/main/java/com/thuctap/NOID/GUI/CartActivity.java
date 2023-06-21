package com.thuctap.NOID.GUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thuctap.NOID.Adapter.CartAdapter;
import com.thuctap.NOID.Adapter.HistoryAdapter;
import com.thuctap.NOID.Database.DBCart;
import com.thuctap.NOID.Database.DBOrder;
import com.thuctap.NOID.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class CartActivity extends AppCompatActivity {
    private DatabaseReference database;
    private FirebaseAuth auth;
    private RecyclerView recyclerView;
    private RelativeLayout relay1, relay2;
    private TextView txtPrice, txtTotalPrice, txtName, txtAddress, txtPhone, Back, txtAddProduct, txtEditProfile, txtRemoveCart, txtDayTime;
    private Button btnDatHang, btnOrder;
    private CartAdapter cartAdapter;
    private EditText edtNote;
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
        txtDayTime = findViewById(R.id.txtDayTime);
        Back = findViewById(R.id.Back);
        btnOrder = findViewById(R.id.btnOrder);
        btnDatHang = findViewById(R.id.btnDatHang);
        relay1 = findViewById(R.id.relay1);
        relay2 = findViewById(R.id.relay2);
        edtNote = findViewById(R.id.edtNote);
        loadCartItems();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String currentDateAndTime = sdf.format(new Date());

        txtDayTime.setText(currentDateAndTime);

        txtRemoveCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCart();
            }
        });

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

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createOrder();

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


            database.child("giohang").child(userId).addValueEventListener(new ValueEventListener() {
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
        int totalPrice = 0;
        for (DBCart cartItem : cartItems) {
            String productId = cartItem.getMasp();
            if (cartItemMap.containsKey(productId)) {
                DBCart existingCartItem = cartItemMap.get(productId);
                existingCartItem.setSoluong(existingCartItem.getSoluong() + cartItem.getSoluong());
            } else {
                cartItemMap.put(productId, cartItem);
            }
            totalPrice += cartItem.getTongtien();
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
        btnOrder.setText("Đặt Hàng " + "(" + totalWithExtraFeeText + ")");
    }


    private void loadCart() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference ordersRef = database.child("giohang");

            ordersRef.child(userId).removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            displayCartItems();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Xóa thất bại
                            // Xử lý lỗi
                        }
                    });
        }
    }

    private void removeCart() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
        View dialogview = getLayoutInflater().inflate(R.layout.dialog_remove_cart, null);
        CheckBox chkRemove = dialogview.findViewById(R.id.chkRemoveCart);
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
                FirebaseUser currentUser = auth.getCurrentUser();
                if (currentUser != null) {
                    String userId = currentUser.getUid();
                    DatabaseReference ordersRef = database.child("giohang");

                    ordersRef.child(userId).removeValue()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(CartActivity.this, "Đã xoá giỏ hàng thành công!", Toast.LENGTH_SHORT).show();
                                    dialog.hide();
                                    displayCartItems();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Xóa thất bại
                                    // Xử lý lỗi
                                }
                            });

                }
            }

        });
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        dialog.show();
    }


    private void createOrder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
        View dialogview = getLayoutInflater().inflate(R.layout.dialog_cart_confirm, null);
        CheckBox chkRemove = dialogview.findViewById(R.id.chkConfirmOrder);
        Button btnCreateOrder = dialogview.findViewById(R.id.btnConfirmOrder);
        builder.setView(dialogview);
        AlertDialog dialog = builder.create();

        chkRemove.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                btnCreateOrder.setEnabled(isChecked);
            }
        });

        btnCreateOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = auth.getCurrentUser();
                if (currentUser != null) {
                    String userId = currentUser.getUid();
                    String orderId = database.child("dathang").push().getKey();

                    String currentTime = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());
                    String note = edtNote.getText().toString();
                    int totalAmount = calculateTotalAmount();
                    // Tạo một đối tượng DBOrder mới từ dữ liệu hiện tại
                    DBOrder dbOrder = new DBOrder(userId, orderId, currentTime, note, "Đang chờ xác nhận", cartItems.size(), totalAmount, convertCartItemsToMap(cartItems));
                    dbOrder.setTongtiendh(totalAmount);
                    // Gửi đối tượng DBOrder đến nhánh "dathang"
                    database.child("dathang").child(orderId).setValue(dbOrder)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Đặt hàng thành công
                                    // Xóa giỏ hàng sau khi đã đặt hàng
                                    Toast.makeText(CartActivity.this, "Đã đặt hàng thành công! Vui lòng chờ ít phút để đơn hàng được xác nhận!", Toast.LENGTH_SHORT).show();
                                    loadCart();
                                    dialog.hide();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Xử lý lỗi khi đặt hàng thất bại
                                    Toast.makeText(CartActivity.this, "Đặt hàng thất bại. Vui lòng thử lại sau!", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        dialog.show();

    }

    private Map<String, DBCart> convertCartItemsToMap(ArrayList<DBCart> cartItems) {
        Map<String, DBCart> itemsMap = new HashMap<>();
        for (DBCart cartItem : cartItems) {
            itemsMap.put(cartItem.getMasp(), cartItem);
        }
        return itemsMap;
    }


    private int calculateTotalAmount() {
        int totalAmount = 0;
        for (DBCart cartItem : cartItems) {
            totalAmount += cartItem.getTongtien();
        }
        totalAmount += 30000; // Cộng thêm 30,000, chi phí giao hàng phí tax đã tính vào giá sản phẩm
        return totalAmount;
    }

}