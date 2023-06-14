package com.thuctap.NOID.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.thuctap.NOID.Adapter.HistoryAdapter;
import com.thuctap.NOID.Database.DBOrder;
import com.thuctap.NOID.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<DataSnapshot> orderSnapshots = new ArrayList<>();
    private HistoryAdapter historyAdapter;
    private Button btnReOrder;
    private List<DBOrder> orderList;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dathangRef = database.getReference("dathang");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            String tinhtrang = "Đã giao";
            dathangRef.orderByChild("makh").equalTo(userId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    orderList.clear();
                    for (DataSnapshot orderSnapshot : snapshot.getChildren()) {
                        DBOrder order = orderSnapshot.getValue(DBOrder.class);
                        if (order.getTinhtrang().equals(tinhtrang)) {
                            orderList.add(order);
                        }
                    }
                    historyAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("Giao hang", "Failed to retrieve order data: " + error.getMessage());
                }
            });
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewHistory);
        orderList = new ArrayList<>();
        historyAdapter = new HistoryAdapter(orderList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(historyAdapter);
        return view;
    }

}