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
import com.thuctap.NOID.GUI.IntroduceApplication;
import com.thuctap.NOID.GUI.LoginActivity;
import com.thuctap.NOID.GUI.SplashActivity;
import com.thuctap.NOID.R;

public class KhacFragment extends Fragment {
    private Button btnLogout, btnLogin, btnProfile, btnIntroduce, btnSetting;
    private TextView txtUsername, txtTienIch;
    private FirebaseAuth auth;
    private View v1;
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
        v1 = view.findViewById(R.id.v1);
        txtUsername = view.findViewById(R.id.txtUsername);
        btnLogout = view.findViewById(R.id.btnLogout);
        btnLogin = view.findViewById(R.id.btnLogin);
        btnProfile = view.findViewById(R.id.btnProfile);
        btnIntroduce = view.findViewById(R.id.btnIntroduce);
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
                        txtUsername.setText(userName + " | KHÁCH HÀNG");
                        btnLogin.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            txtUsername.setTextSize(15);
            btnLogout.setVisibility(View.GONE);
            btnProfile.setVisibility(View.GONE);
            llnTienIch.setVisibility(View.GONE);
            btnLogin.setVisibility(View.VISIBLE);
            txtTienIch.setVisibility(View.GONE);
            v1.setVisibility(View.GONE);
            txtUsername.setText("Vui lòng đăng nhập để có trải nghiệm tốt nhất!");
        }

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(requireContext(), "Đã đăng xuất", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(requireContext(), SplashActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireContext(), LoginActivity.class));
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireContext(), Infomation.class));
            }
        });

        btnIntroduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireContext(), IntroduceApplication.class));
            }
        });


        return view;
    }
}