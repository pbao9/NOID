package com.thuctap.NOID.GUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.thuctap.NOID.Database.DBCart;
import com.thuctap.NOID.Database.DBProduct;
import com.thuctap.NOID.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {
    private TextView detailBack, txtProductNameDetail, txtCountProduct, txtProductPriceDetail, txtProductDescDetail;
    private Button btnTotal, btnUp, btnDown;
    private DatabaseReference database;
    private FirebaseAuth auth;
    private RelativeLayout rlvPay;
    private ImageView imgProductDetail;
    private EditText edtNote;
    private int count = 0;
    private double initialProductPrice;
    private String productId, productName, productPrice, productTotalPrice, productImage, productDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = getWindow();
        window.setBackgroundDrawableResource(R.drawable.statusbar_gradient);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        txtProductNameDetail = findViewById(R.id.txtProductNameDetail);
        txtCountProduct = findViewById(R.id.txtCountProduct);
        txtProductPriceDetail = findViewById(R.id.txtProductPriceDetail);
        txtProductDescDetail = findViewById(R.id.txtProductDescDetail);
        detailBack = findViewById(R.id.detailBack);
        edtNote = findViewById(R.id.edtNote);
        btnTotal = findViewById(R.id.btnTotal);
        btnUp = findViewById(R.id.btnUp);
        btnDown = findViewById(R.id.btnDown);
        imgProductDetail = findViewById(R.id.imgProductDetail);
        imgProductDetail = findViewById(R.id.imgProductDetail);

        database = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        if (intent != null) {
            productId = intent.getStringExtra("productId");
            productName = intent.getStringExtra("productName");
            productPrice = intent.getStringExtra("productPrice");
            productImage = intent.getStringExtra("productImage");
            productDesc = intent.getStringExtra("productDesc");

            double giasp = Double.parseDouble(productPrice);
            DecimalFormat decimalFormat = new DecimalFormat("#,### đ");
            String formattedPrice = decimalFormat.format(giasp);

            txtProductNameDetail.setText(productName);
            txtProductDescDetail.setText(productDesc);
            txtProductPriceDetail.setText(formattedPrice);
            Picasso.get().load(productImage).into(imgProductDetail);
        }

        detailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count > 0) {
                    count--;
                    updateProductPrice();
                    txtCountProduct.setText(String.valueOf(count));
                    checkEnableBtnTotal();
                }
            }
        });

        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                updateProductPrice();
                txtCountProduct.setText(String.valueOf(count));
                checkEnableBtnTotal();
            }
        });

        btnTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });
    }

    private void updateProductPrice() {
        double updatedPrice = Double.parseDouble(productPrice) * count;
        DecimalFormat decimalFormat = new DecimalFormat("#,### đ");
        String formattedPrice1 = decimalFormat.format(updatedPrice);
        btnTotal.setText("Thêm " + formattedPrice1);
    }

    private void checkEnableBtnTotal() {
        btnTotal.setEnabled(count >= 1);
    }

    private void addToCart() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            String cartItemId = database.child("giohang").push().getKey();
            String note = edtNote.getText().toString();
            int updatedPrice = Integer.parseInt(productPrice) * count;
            // Lấy ngày giờ hiện tại
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String currentDateAndTime = sdf.format(new Date());
            DBCart cartItem = new DBCart(null, null, productId, productName, note, currentDateAndTime, count, Integer.parseInt(productPrice), updatedPrice);

            database.child("giohang").child(userId).child(cartItemId).setValue(cartItem)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(DetailActivity.this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(DetailActivity.this, "Thêm vào giỏ hàng thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(this, "Vui lòng đăng nhập hoặc đăng kí tài khoản!", Toast.LENGTH_SHORT).show();
        }
    }
}