package com.thuctap.NOID.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.thuctap.NOID.Adapter.ProductAdapter;
import com.thuctap.NOID.Database.DBProduct;
import com.thuctap.NOID.R;

import java.util.ArrayList;
import java.util.List;

public class SmoothiesFragment extends Fragment {
    private RecyclerView recyclerViewSmoothies;
    private ProductAdapter adapterSmoothies;
    private List<DBProduct> productSmoothies;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        String smoothies = "-NXE1ZviRaG07k3xa_rl"; // Mã danh mục Sinh tố
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("sanpham");
        Query query_smoothies = databaseRef.orderByChild("madm").equalTo(smoothies);
        query_smoothies.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productSmoothies.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    String id = data.getKey();
                    String name = data.child("tensp").getValue(String.class);
                    String desc = data.child("motasp").getValue(String.class);
                    String price = String.valueOf(data.child("giasp").getValue(Long.class)); // đối với dạng số "50000"
                    /*String price = data.child("giasp").getValue(String.class); // đối với dạng số "50000" // dạng string*/
                    String imageUrl = data.child("hinhsp").getValue(String.class);
                    DBProduct product = new DBProduct(id,name, desc, price, imageUrl);
                    productSmoothies.add(product);
                }
                adapterSmoothies.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_smoothies, container, false);
        recyclerViewSmoothies = view.findViewById(R.id.recyclerViewSmoothies);
        productSmoothies = new ArrayList<>();
        adapterSmoothies = new ProductAdapter(productSmoothies);
        recyclerViewSmoothies.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewSmoothies.setAdapter(adapterSmoothies);
        return view;
    }
}