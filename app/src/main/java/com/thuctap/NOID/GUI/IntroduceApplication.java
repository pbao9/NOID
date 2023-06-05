package com.thuctap.NOID.GUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.thuctap.NOID.R;

public class IntroduceApplication extends AppCompatActivity {
    private TextView Back;
    private ImageView imgBoCongThuong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setBackgroundDrawableResource(R.drawable.statusbar_gradient);
        setContentView(R.layout.activity_introduce_application);
        Back = findViewById(R.id.Back);
        imgBoCongThuong = findViewById(R.id.imgBoCongThuong);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntroduceApplication.this, MainActivity.class));
            }
        });

        imgBoCongThuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://moit.gov.vn/"));
                startActivity(intent);
            }
        });



    }
}