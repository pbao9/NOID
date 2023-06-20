package com.thuctap.NOID.Fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.thuctap.NOID.Adapter.HistoryAdapter;
import com.thuctap.NOID.Adapter.PendingAdapter;
import com.thuctap.NOID.Database.DBCart;
import com.thuctap.NOID.Database.DBOrder;
import com.thuctap.NOID.Database.DBProduct;
import com.thuctap.NOID.R;

import java.util.ArrayList;
import java.util.List;


public class PendingFragment extends Fragment {
    private RecyclerView recyclerView;
    private PendingAdapter pendingAdapter;
    private TextView txtNoAccount, txtNoData;
    private List<DBOrder> orderList;
    private List<String> orderKeys;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dathangRef = database.getReference("dathang");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pending, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewPending);
        txtNoAccount = view.findViewById(R.id.txtNoAccountPending);
        txtNoData = view.findViewById(R.id.txtNoDataPending);
        orderList = new ArrayList<>();

        pendingAdapter = new PendingAdapter(orderList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(pendingAdapter);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            String tinhtrang = "Đang chờ xác nhận";
            String tinhtrang1 = "Đang giao";
            String tinhtrang2 = "Đã huỷ";
            txtNoAccount.setVisibility(View.GONE);
            dathangRef.orderByChild("makh").equalTo(userId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    orderList.clear();
                    for (DataSnapshot orderSnapshot : snapshot.getChildren()) {
                        DBOrder order = orderSnapshot.getValue(DBOrder.class);

                        if (order.getTinhtrang().equals(tinhtrang) || order.getTinhtrang().equals(tinhtrang1) || order.getTinhtrang().equals(tinhtrang2)) {
                            orderList.add(order);
                        }
                    }
                    pendingAdapter.notifyDataSetChanged();
                    if (orderList.isEmpty()) {
                        // Không có thông tin đơn hàng
                        txtNoData.setVisibility(View.VISIBLE);
                    } else {
                        // Có thông tin đơn hàng
                        txtNoData.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }else {
            // Không có tài khoản đăng nhập
            txtNoAccount.setVisibility(View.VISIBLE);
            txtNoData.setVisibility(View.GONE);
        }
        return view;
    }

}