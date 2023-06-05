package com.thuctap.NOID.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thuctap.NOID.GUI.Infomation;
import com.thuctap.NOID.GUI.LoginActivity;
import com.thuctap.NOID.GUI.SplashActivity;
import com.thuctap.NOID.R;

public class KhacFragment extends Fragment {
    private Button btnSignOut;
    private TextView txtUsername, txtLogout, txtLogin, txtProfile, txtSetting, txtTienIch;
    private FirebaseAuth auth;
    private LinearLayout llnTienIch;
    private DatabaseReference reference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_khac, container, false);
        /* Sự kiện Menu bottom Nav */
        /* Authentication */
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser currentUser = auth.getCurrentUser();
        txtUsername = view.findViewById(R.id.txtUsername);
        txtLogout = view.findViewById(R.id.txtLogout);
        txtLogin = view.findViewById(R.id.txtLogin);
        txtProfile = view.findViewById(R.id.txtProfile);
        txtSetting = view.findViewById(R.id.txtSetting);
        llnTienIch = view.findViewById(R.id.llnTienIch);
        txtTienIch = view.findViewById(R.id.txtTienIch);

        if (currentUser != null) {
            String userId = currentUser.getUid();
            /* Hiển thị tên người dùng */
            reference.child("taikhoan").child(userId).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String userName = snapshot.getValue(String.class);
                    if (userName != null) {
                        txtUsername.setText(userName + " | THÀNH VIÊN");
                        txtLogin.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            txtUsername.setTextSize(15);
            txtLogout.setVisibility(View.GONE);
            txtProfile.setVisibility(View.GONE);
            txtSetting.setVisibility(View.GONE);
            llnTienIch.setVisibility(View.GONE);
            txtLogin.setVisibility(View.VISIBLE);
            txtTienIch.setVisibility(View.GONE);
            txtUsername.setText("Vui lòng đăng nhập để có trải nghiệm tốt nhất!");
        }

        txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(requireContext(), "Đã đăng xuất", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(requireContext(), SplashActivity.class));
            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireContext(), LoginActivity.class));
            }
        });

        txtProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireContext(), Infomation.class));
            }
        });

        return view;
    }
}