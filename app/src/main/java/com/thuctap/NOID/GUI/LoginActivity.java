package com.thuctap.NOID.GUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thuctap.NOID.MainActivity;
import com.thuctap.NOID.R;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private FirebaseUser currentUser;
    private TextInputEditText loginEmail, loginPassword;
    private Button btnSignIn;
    private TextView txtRegAccount, txtForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /* Lấy người dùng đang hiện tại */
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("taikhoan");

        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        txtForgotPassword = findViewById(R.id.txtForgotPassword);
        txtRegAccount = findViewById(R.id.txtRegAccount);


        /* Authentication */
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginEmail.getText().toString().trim();
                String password = loginPassword.getText().toString().trim();
                /* Kiểm tra Email cửa hàng */
                if (email.equals("kylekhanh1028@gmail.com") || email.equals("khanh1028")) {
                    Toast.makeText(LoginActivity.this, "Địa chỉ email hoặc tên đăng nhập không được cho phép!", Toast.LENGTH_SHORT).show();
                    return;
                }
                /* Kiểm tra Email cửa hàng */
                else if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    if (!password.isEmpty()) {
                        auth.signInWithEmailAndPassword(email, password)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        updatePasswordtoRealtimeDatabase(password);
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(LoginActivity.this, "Đăng nhập không thành công!", Toast.LENGTH_SHORT).show();
                                    }
                                });

                    } else {
                        loginPassword.setError("Mật khẩu không được để trống");
                    }
                } else if (email.isEmpty()) {
                    loginEmail.setError("Tên đăng nhập không được để trống!");
                } else {
                    loginEmail.setError("vui lòng nhập email thực!");
                }
            }
        });
        /* Authentication */

        /* Mở activity Tạo tài khoản */
        txtRegAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        /* Mở dialog quên mật khẩu */
        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                View dialogview = getLayoutInflater().inflate(R.layout.dialog_forgotpassword, null);
                TextInputEditText edtForgotEmail = dialogview.findViewById(R.id.edtForgotEmail);

                builder.setView(dialogview);
                AlertDialog dialog = builder.create();
                /* Sự kiện nút Xác nhận */
                dialogview.findViewById(R.id.btnConfirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String userEmail = edtForgotEmail.getText().toString();
                        if (userEmail.equals("kylekhanh1028@gmail.com") || userEmail.equals("khanh1028")) {
                            Toast.makeText(LoginActivity.this, "Địa chỉ email hoặc tên đăng nhập không được cho phép!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (TextUtils.isEmpty(userEmail) && !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                            Toast.makeText(LoginActivity.this, "Nhập email đăng kí tài khoản!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        auth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Vui lòng kiểm tra Email!", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Không thể gửi, vui lòng kiểm tra lại!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                });

                /*  Sự kiện nút Huỷ */
                dialogview.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                dialog.show();
            }
        });
    }
    /* Update mật khẩu */
    private void updatePasswordtoRealtimeDatabase(String newPassword) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference().child("taikhoan").child(userId).child("password");
        ref.setValue(newPassword);
    }
}