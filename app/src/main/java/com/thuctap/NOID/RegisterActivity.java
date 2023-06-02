package com.thuctap.NOID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    /* Authentication */
    private FirebaseAuth auth;
    /* Authentication */

    /* Realtime */
    private FirebaseDatabase database;
    private DatabaseReference reference;
    /* Realtime */

    private TextInputEditText edtEmail, edtName, edtUsername, edtPassword;
    private Button btnSignUp;
    private TextView txtSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        addView();

        /* Nút đăng kí xử lý đăng ký tài khoản khách hàng */
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edtEmail.getText().toString().trim();
                String hoten = edtName.getText().toString().trim();
                String tendangnhap = edtUsername.getText().toString().trim();
                String matkhau = edtPassword.getText().toString().trim();
                String phanquyen = "khachhang";

                if (email.isEmpty()) {
                    edtEmail.setError("Email không được để trống!");
                }
                if (hoten.isEmpty()) {
                    edtName.setError("Tên hiển thị không được để trống!");
                }
                if (tendangnhap.isEmpty()) {
                    edtUsername.setError("Tên đăng nhập không được để trống!");
                }
                if (matkhau.isEmpty()) {
                    edtPassword.setError("Mật khẩu không được để trống");
                } else {
                    auth.createUserWithEmailAndPassword(email, matkhau).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Realtime
                                database = FirebaseDatabase.getInstance();
                                reference = database.getReference("taikhoan");

                                DBUser dbUser = new DBUser(email, hoten, tendangnhap, matkhau, phanquyen);
                                reference.child(tendangnhap).setValue(dbUser);
                                // Realtime

                                Toast.makeText(RegisterActivity.this, "Đăng kí tài khoản thành công!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            } else {
                                Toast.makeText(RegisterActivity.this, "Đăng kí tài khoản thất bại. Kiểm tra lại!" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        /* Kết thúc nút đăng ký */

        /* TextView đăng nhập tài khoản */
        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
        /* Kết thúc TextView đăng nhập */
    }

    private void addView() {
        auth = FirebaseAuth.getInstance();
        edtEmail = findViewById(R.id.edtEmail);
        edtName = findViewById(R.id.edtName);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        txtSignIn = findViewById(R.id.txtSignIn);
    }
}