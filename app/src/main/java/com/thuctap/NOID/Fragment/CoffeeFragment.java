package com.thuctap.NOID.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;
import com.thuctap.NOID.Adapter.ProductAdapter;
import com.thuctap.NOID.Database.DBProduct;
import com.thuctap.NOID.R;

import java.util.ArrayList;
import java.util.List;

public class CoffeeFragment extends Fragment {
    private RecyclerView recyclerViewCafe;
    private ProductAdapter adapterCafe;
    private List<DBProduct> productCafe;

    public CoffeeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        String cafe = "-NX9ppXXBjY8T0_6wBy5"; // Mã danh mục Cafe
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("sanpham");
        Query query_cafe = databaseRef.orderByChild("madm").equalTo(cafe);

        query_cafe.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productCafe.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    String id = data.getKey();
                    String name = data.child("tensp").getValue(String.class);
                    String desc = data.child("motasp").getValue(String.class);
                    String price = String.valueOf(data.child("giasp").getValue(Long.class)); // đối với dạng số "50000"
                    /*String price = data.child("giasp").getValue(String.class); // đối với dạng số "50000" // dạng string*/
                    String imageUrl = data.child("hinhsp").getValue(String.class);
                    DBProduct product = new DBProduct(id,name, desc, price, imageUrl);
                    productCafe.add(product);
                }
                adapterCafe.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coffee, container, false);
        recyclerViewCafe = view.findViewById(R.id.recyclerViewCafe);
        productCafe = new ArrayList<>();
        adapterCafe = new ProductAdapter(productCafe);
        recyclerViewCafe.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewCafe.setAdapter(adapterCafe);
        return view;
    }
}

