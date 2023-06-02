package com.thuctap.NOID;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private TextInputEditText loginUsername, loginPassword;
    private Button btnSignIn;
    private TextView txtRegAccount, txtForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        loginUsername = findViewById(R.id.loginUsername);
        loginPassword = findViewById(R.id.loginPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        txtForgotPassword = findViewById(R.id.txtForgotPassword);
        txtRegAccount = findViewById(R.id.txtRegAccount);

        /* Authentication */
//        btnSignIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String email = loginUsername.getText().toString().trim();
//                String password = loginPassword.getText().toString().trim();
//                if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//                    if (!password.isEmpty()) {
//                        auth.signInWithEmailAndPassword(email, password)
//                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                                    @Override
//                                    public void onSuccess(AuthResult authResult) {
//                                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
//                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                                        finish();
//                                    }
//                                }).addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        Toast.makeText(LoginActivity.this, "Đăng nhập không thành công!", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                    } else {
//                        loginPassword.setError("Mật khẩu không được để trống");
//                    }
//                } else if (email.isEmpty()) {
//                    loginUsername.setError("Tên đăng nhập không được để trống!");
//                } else {
//                    loginUsername.setError("vui lòng nhập email thực!");
//                }
//            }
//        });
        /* Authentication */

        /* Realtime DB */
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkUsername() | !checkPassword()) {

                } else {
                    checkUser();
                }
            }
        });

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

                dialogview.findViewById(R.id.btnConfirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String userEmail = edtForgotEmail.getText().toString();
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
    /* Kiểm tra editext Username */
    public Boolean checkUsername() {
        String val = loginUsername.getText().toString();
        if (val.isEmpty()){
            loginUsername.setError("Không được để tên đăng nhập trống!");
            return false;
        }else{
            loginUsername.setError(null);
            return true;
        }
    }
    /* Kiểm tra editext Password */
    public Boolean checkPassword () {
        String val = loginPassword.getText().toString();
        if (val.isEmpty()){
            loginPassword.setError("Không được để tên đăng nhập trống!");
            return false;
        }else{
            loginPassword.setError(null);
            return true;
        }
    }
    public void checkUser(){
        String userUsername = loginUsername.getText().toString().trim();
        String userPassword = loginPassword.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("taikhoan");
        Query checkUserDatabase = reference.orderByChild("tendangnhap").equalTo(userUsername);
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    loginUsername.setError(null);
                    String passwordFromDB = snapshot.child(userUsername).child("matkhau").getValue(String.class);
                    if (passwordFromDB.equals(userPassword)) {
                        loginUsername.setError(null);
                        String nameFromDB = snapshot.child(userUsername).child("hoten").getValue(String.class);
                        String emailFromDB = snapshot.child(userUsername).child("email").getValue(String.class);
                        String usernameFromDB = snapshot.child(userUsername).child("tendangnhap").getValue(String.class);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công! " + "\n" + "Chào mừng " + nameFromDB, Toast.LENGTH_SHORT).show();
                        intent.putExtra("hoten", nameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("tendangnhap", usernameFromDB);
                        intent.putExtra("matkhau", passwordFromDB);
                        startActivity(intent);
                    } else {
                        loginPassword.setError("Sai mật khẩu!");
                        loginPassword.requestFocus();
                    }
                } else {
                    loginUsername.setError("Tên đăng nhập không tồn tại!");
                    loginUsername.requestFocus();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}