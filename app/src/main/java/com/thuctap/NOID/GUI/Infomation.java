package com.thuctap.NOID.GUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.thuctap.NOID.Fragment.KhacFragment;
import com.thuctap.NOID.MainActivity;
import com.thuctap.NOID.R;

public class Infomation extends AppCompatActivity {
    private TextView Back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation);
        Back = findViewById(R.id.Back);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Infomation.this, MainActivity.class));
            }
        });
    }
}