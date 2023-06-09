package com.thuctap.NOID.GUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.thuctap.NOID.Database.DBProduct;
import com.thuctap.NOID.R;

import java.text.DecimalFormat;

public class DetailActivity extends AppCompatActivity {
    TextView txtCountProduct, txtProductNameDetail, txtProductPriceDetail,txtProductDescDetail, detailBack, title;
    Button btnTotal;
    private DatabaseReference database;
    ImageView imgProductDetail;

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
        btnTotal = findViewById(R.id.btnTotal);
        imgProductDetail = findViewById(R.id.imgProductDetail);
        detailBack = findViewById(R.id.detailBack);
        title = findViewById(R.id.title);



        detailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        if (intent != null){
            String productId = intent.getStringExtra("productId");
            database = FirebaseDatabase.getInstance().getReference().child("sanpham").child(productId);
            String productName = intent.getStringExtra("productName");
            String productDesc = intent.getStringExtra("productDesc");
            String productPrice = intent.getStringExtra("productPrice");
            String productImage = intent.getStringExtra("productImage");
            String price = productPrice;
            double priceDouble = Double.parseDouble(price);
            DecimalFormat decimalFormat = new DecimalFormat("#,### đ");
            String formattedPrice = decimalFormat.format(priceDouble);
            txtProductNameDetail.setText(productName);
            txtProductDescDetail.setText(productDesc);
            txtProductPriceDetail.setText(formattedPrice);
            txtCountProduct.setText(productId);
            btnTotal.setText("Thêm " + formattedPrice);
            title.setText(productName);
            Picasso.get().load(productImage).into(imgProductDetail);
        }

    }
}