package com.thuctap.NOID.GUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thuctap.NOID.R;

public class Infomation extends AppCompatActivity {
    private TextView Back, txtDeleteAccount;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private Button btnUpdate;
    private EditText edtMailShow, edtHoTen, edtDienThoai, edtDiaChi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = getWindow();
        window.setBackgroundDrawableResource(R.drawable.statusbar_gradient);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation);
        addView();

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser currentUser = auth.getCurrentUser();
        String userId = currentUser.getUid();

        if (currentUser != null) {
            reference.child("taikhoan").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String email = snapshot.child("email").getValue(String.class);
                        String name = snapshot.child("name").getValue(String.class);
                        String phone = snapshot.child("phone").getValue(String.class);
                        String address = snapshot.child("address").getValue(String.class);

                        edtMailShow.setText(email);
                        edtHoTen.setText(name);
                        edtDienThoai.setText(phone);
                        edtDiaChi.setText(address);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoten = edtHoTen.getText().toString().trim();
                String diachi = edtDiaChi.getText().toString().trim();

                reference = database.getReference("taikhoan").child(userId);

                reference.child("name").setValue(hoten);
//                    reference.child("phone").setValue(dienthoai);
                reference.child("address").setValue(diachi);

                Toast.makeText(Infomation.this, "Cập nhật thông tin thành công!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Infomation.this, MainActivity.class));

            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Infomation.this, MainActivity.class));
            }
        });

        txtDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Infomation.this);
                View dialogview = getLayoutInflater().inflate(R.layout.dialog_removeaccount, null);
                CheckBox chkDeleteAccount = dialogview.findViewById(R.id.chkDeleteAccount);
                Button btnConfirm = dialogview.findViewById(R.id.btnConfirm);
                builder.setView(dialogview);
                AlertDialog dialog = builder.create();

                chkDeleteAccount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            btnConfirm.setEnabled(true);
                        } else {
                            btnConfirm.setEnabled(false);
                        }
                    }
                });

                dialogview.findViewById(R.id.btnConfirm).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        auth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    reference.child("taikhoan").child(userId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(Infomation.this, "Đã xoá tài khoản. Hẹn gặp lại!", Toast.LENGTH_SHORT).show();
                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    startActivity(new Intent(Infomation.this, SplashActivity.class));
                                                    finish();
                                                }
                                            }, 2500);
                                        }
                                    });

                                }else{

                                }
                            }
                        });
                    }
                });
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                dialog.show();
            }
        });
    }

    private void addView() {
        Back = findViewById(R.id.Back);
        txtDeleteAccount = findViewById(R.id.txtDeleteAccount);
        btnUpdate = findViewById(R.id.btnUpdateInfomation);
        edtMailShow = findViewById(R.id.edtMailShow);
        edtHoTen = findViewById(R.id.edtHoTen);
        edtDienThoai = findViewById(R.id.edtDienThoai);
        edtDiaChi = findViewById(R.id.edtDiaChi);
    }


}