package com.thuctap.NOID.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

public class TeaFragment extends Fragment {
    private RecyclerView recyclerViewTea;
    private ProductAdapter adapterTea;
    private List<DBProduct> productTea;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        String tea = "-NXDtH8cDFrHbWFztoFk"; // Mã danh mục Trà trái cây
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("sanpham");
        Query query_tea = databaseRef.orderByChild("madm").equalTo(tea);

        query_tea.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productTea.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    String id = data.getKey();
                    String name = data.child("tensp").getValue(String.class);
                    String desc = data.child("motasp").getValue(String.class);
                    String price = String.valueOf(data.child("giasp").getValue(Long.class)); // đối với dạng số "50000"
                    String imageUrl = data.child("hinhsp").getValue(String.class);
                    DBProduct product = new DBProduct(id, name, desc, price, imageUrl);
                    productTea.add(product);
                }
                adapterTea.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tea, container, false);
        recyclerViewTea = view.findViewById(R.id.recyclerViewTea);
        productTea = new ArrayList<>();
        adapterTea = new ProductAdapter(productTea);
        recyclerViewTea.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewTea.setAdapter(adapterTea);
        return view;
    }
}