package com.thuctap.NOID.GUI;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thuctap.NOID.Database.DBUser;
import com.thuctap.NOID.MainActivity;
import com.thuctap.NOID.R;

public class RegisterActivity extends AppCompatActivity {
    /* Authentication */
    private FirebaseAuth auth;
    /* Authentication */

    /* Realtime */
    private FirebaseDatabase database;
    private DatabaseReference reference;
    /* Realtime */

    private TextInputEditText edtEmail, edtPassword, edtPhone;
    private Button btnSignUp;
    private TextView txtSignIn, txtContinue;

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
                String password = edtPassword.getText().toString().trim();
                String name = "";
                String address = "";
                String phone = edtPhone.getText().toString().trim();

                reference = FirebaseDatabase.getInstance().getReference().child("taikhoan");
                /* Sự kiện xử lý số điện thoại trùng lặp trong Realtime DB*/
                reference.orderByChild("phone").equalTo(phone).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (phone.isEmpty()) {
                            edtPhone.setError("Số điện thoại không được để trống!");
                        }
                        if (email.isEmpty()) {
                            edtEmail.setError("Email không được để trống!");
                        }
                        if (password.isEmpty()) {
                            edtPassword.setError("Mật khẩu không được để trống");
                        } else if (snapshot.exists()) {
                            /* Trường hợp số điện thoại đã tồn tại */
                            Toast.makeText(RegisterActivity.this, "Số điện thoại này đã tồn tại!", Toast.LENGTH_SHORT).show();
                            edtPhone.setText(" ");
                            /* Kiểm tra email đã tồn tại hoặc bị trùng với email cửa hàng */
                        } else if (email.equals("kylekhanh1028@gmail.com")) {
                            Toast.makeText(RegisterActivity.this, "Địa chỉ email hoặc tên đăng nhập không được cho phép!", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        /* Thêm */
                                        FirebaseUser user = auth.getCurrentUser();
                                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("taikhoan");
                                        DBUser dbUser = new DBUser(email, name, password, address, phone);
                                        /* Khởi tạo biến UserID để lấy userID add vào Realtime DB */
                                        String userID = user.getUid();
                                        reference.child(userID).setValue(dbUser);
                                        Toast.makeText(RegisterActivity.this, "Đăng kí tài khoản thành công!", Toast.LENGTH_SHORT).show();
                                        auth.getInstance().signOut();
                                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Đăng kí tài khoản thất bại. Kiểm tra lại!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
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

        txtContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.getInstance().signOut();
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });
        /* Kết thúc TextView đăng nhập */
    }

    private void addView() {
        auth = FirebaseAuth.getInstance();
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        txtSignIn = findViewById(R.id.txtSignIn);
        txtContinue = findViewById(R.id.txtContinue);
    }
}