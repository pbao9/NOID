package com.thuctap.NOID.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thuctap.NOID.GUI.LoginActivity;
import com.thuctap.NOID.R;

public class TrangChuFragment extends Fragment {

    private CardView cardViewLogin;
    private Button btnLogin;
    private TextView txtUsername;
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private LinearLayout lln1, lln2;

    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trang_chu, container, false);
        /* Ánh xạ */
        cardViewLogin = view.findViewById(R.id.cardViewLogin);
        txtUsername = view.findViewById(R.id.txtUsername);
        btnLogin = view.findViewById(R.id.btnLogin);
        lln1 = view.findViewById(R.id.lln1);
        lln2 = view.findViewById(R.id.lln2);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireContext(), LoginActivity.class));
            }
        });
        /* Sự kiện Menu bottom Nav */
        /* Authentication */
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser currentUser = auth.getCurrentUser();


        if (currentUser != null) {
            String userId = currentUser.getUid();
            /* Hiển thị tên người dùng */
            reference.child("taikhoan").child(userId).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String userName = snapshot.getValue(String.class);
                    if (userName != null) {
                        txtUsername.setText("Chào " + userName);
                        lln1.setVisibility(View.GONE);
                        lln2.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            lln1.setVisibility(View.VISIBLE);
            lln2.setVisibility(View.GONE);
        }
        return view;
    }
}