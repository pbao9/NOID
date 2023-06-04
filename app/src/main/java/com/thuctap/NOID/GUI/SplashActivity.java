package com.thuctap.NOID.GUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.thuctap.NOID.MainActivity;
import com.thuctap.NOID.R;

public class SplashActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();

        /* Loading page start */
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, RegisterActivity.class));
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        },2000);
        /* End loading page start */
    }
}