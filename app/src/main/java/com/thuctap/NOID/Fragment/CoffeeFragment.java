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
    private RecyclerView  recyclerViewCafe, recyclerViewMilkTea , recyclerViewTea , recyclerViewSmoothies , recyclerViewSnack , recyclerViewPackage , recyclerViewOther ;
    private ProductAdapter adapterCafe, adapterMilkTea, adapterTea, adapterSmoothies, adapterSnack, adapterPackage, adapterOther;
    private List<DBProduct> productCafe, productMilkTea, productTea, productSmoothies, productSnack, productPackage, productOther;

    public CoffeeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        String cafe = "-NX9r4LR1f6twnVU28_R"; // Mã danh mục Cafe
        String milktea = "-NXOrczCnri2fMR2gUWP"; // Mã danh mục Trà sữa
        String tea = "-NXDtH8cDFrHbWFztoFk"; // Mã danh mục Trà trái cây
        String smoothies = "-NXE1ZviRaG07k3xa_rl"; // Mã danh mục Sinh tố
        String snack = "-NXOrczCnri2fMR2gUWP"; // Mã danh mục bánh mặn bánh ngọt
        String sppackage = "-NXOrczHNuTFoEhXKwfe"; // Mã danh mục hàng đóng gói
        String other = "-NXPMMB0Gglb68aK9Svr"; // Mã danh mục Khác

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("sanpham");

        Query query_cafe = databaseRef.orderByChild("madm").equalTo(cafe);
        Query query_milktea = databaseRef.orderByChild("madm").equalTo(milktea);
        Query query_tea = databaseRef.orderByChild("madm").equalTo(tea);
        Query query_smoothies = databaseRef.orderByChild("madm").equalTo(smoothies);
        Query query_snack = databaseRef.orderByChild("madm").equalTo(snack);
        Query query_sppackage = databaseRef.orderByChild("madm").equalTo(sppackage);
        Query query_other = databaseRef.orderByChild("madm").equalTo(other);

        /* Danh  mục Cafe */
        query_cafe.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productCafe.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    String name = data.child("tensp").getValue(String.class);
                    String desc = data.child("motasp").getValue(String.class);
                    String price = String.valueOf(data.child("giasp").getValue(Long.class)); // đối với dạng số "50000"
                    /*String price = data.child("giasp").getValue(String.class); // đối với dạng số "50000" // dạng string*/
                    String imageUrl = data.child("hinhsp").getValue(String.class);
                    DBProduct product = new DBProduct(name, desc, price, imageUrl);
                    productCafe.add(product);
                }
                adapterCafe.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        /* Danh  mục Trà sữa */
        query_milktea.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productMilkTea.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    String name = data.child("tensp").getValue(String.class);
                    String desc = data.child("motasp").getValue(String.class);
                    String price = String.valueOf(data.child("giasp").getValue(Long.class)); // đối với dạng số "50000"
                    /*String price = data.child("giasp").getValue(String.class); // đối với dạng số "50000" // dạng string*/
                    String imageUrl = data.child("hinhsp").getValue(String.class);
                    DBProduct product = new DBProduct(name, desc, price, imageUrl);
                    productMilkTea.add(product);
                }
                adapterMilkTea.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        /* Danh mục Trà trái cây */
        query_tea.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productTea.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    String name = data.child("tensp").getValue(String.class);
                    String desc = data.child("motasp").getValue(String.class);
                    String price = String.valueOf(data.child("giasp").getValue(Long.class)); // đối với dạng số "50000"
                    String imageUrl = data.child("hinhsp").getValue(String.class);
                    DBProduct product = new DBProduct(name, desc, price, imageUrl);
                    productTea.add(product);
                }
                adapterTea.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        /* Danh mục Sinh tố */
        query_smoothies.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productSmoothies.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    String name = data.child("tensp").getValue(String.class);
                    String desc = data.child("motasp").getValue(String.class);
                    String price = String.valueOf(data.child("giasp").getValue(Long.class)); // đối với dạng số "50000"
                    String imageUrl = data.child("hinhsp").getValue(String.class);
                    DBProduct product = new DBProduct(name, desc, price, imageUrl);
                    productSmoothies.add(product);
                }
                adapterSmoothies.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        /* Danh mục bánh mặn - bánh ngọt */
        query_snack.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productSnack.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    String name = data.child("tensp").getValue(String.class);
                    String desc = data.child("motasp").getValue(String.class);
                    String price = String.valueOf(data.child("giasp").getValue(Long.class)); // đối với dạng số "50000"
                    /*String price = data.child("giasp").getValue(String.class); // đối với dạng số "50000" // dạng string*/
                    String imageUrl = data.child("hinhsp").getValue(String.class);
                    DBProduct product = new DBProduct(name, desc, price, imageUrl);
                    productSnack.add(product);
                }
                adapterSnack.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        /* Danh mục bánh mặn - bánh ngọt */
        query_tea.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productSnack.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    String name = data.child("tensp").getValue(String.class);
                    String desc = data.child("motasp").getValue(String.class);
                    String price = String.valueOf(data.child("giasp").getValue(Long.class)); // đối với dạng số "50000"
                    /*String price = data.child("giasp").getValue(String.class); // đối với dạng số "50000" // dạng string*/
                    String imageUrl = data.child("hinhsp").getValue(String.class);
                    DBProduct product = new DBProduct(name, desc, price, imageUrl);
                    productSnack.add(product);
                }
                adapterSnack.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        /* Danh mục sản phẩm đóng gói */
        query_sppackage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productPackage.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    String name = data.child("tensp").getValue(String.class);
                    String desc = data.child("motasp").getValue(String.class);
                    String price = String.valueOf(data.child("giasp").getValue(Long.class)); // đối với dạng số "50000"
                    /*String price = data.child("giasp").getValue(String.class); // đối với dạng số "50000" // dạng string*/
                    String imageUrl = data.child("hinhsp").getValue(String.class);
                    DBProduct product = new DBProduct(name, desc, price, imageUrl);
                    productPackage.add(product);
                }
                adapterPackage.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        /* Danh mục sản phẩm khác */
        query_other.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productOther.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    String name = data.child("tensp").getValue(String.class);
                    String desc = data.child("motasp").getValue(String.class);
                    String price = String.valueOf(data.child("giasp").getValue(Long.class)); // đối với dạng số "50000"
                    /*String price = data.child("giasp").getValue(String.class); // đối với dạng số "50000" // dạng string*/
                    String imageUrl = data.child("hinhsp").getValue(String.class);
                    DBProduct product = new DBProduct(name, desc, price, imageUrl);
                    productOther.add(product);
                }
                adapterOther.notifyDataSetChanged();
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
        recyclerViewMilkTea = view.findViewById(R.id.recyclerViewMilkTea);
        recyclerViewTea = view.findViewById(R.id.recyclerViewTea);
        recyclerViewSmoothies = view.findViewById(R.id.recyclerViewSmoothies);
        recyclerViewSnack = view.findViewById(R.id.recyclerViewSnack);
        recyclerViewPackage = view.findViewById(R.id.recyclerViewPackage);
        recyclerViewOther = view.findViewById(R.id.recyclerViewOther);
        // Khởi tạo danh sách sản phẩm
        productCafe = new ArrayList<>();
        productMilkTea = new ArrayList<>();
        productTea = new ArrayList<>();
        productSmoothies = new ArrayList<>();
        productSnack = new ArrayList<>();
        productPackage = new ArrayList<>();
        productOther = new ArrayList<>();

        // Khởi tạo adapter cho mỗi danh mục sản phẩm
        adapterCafe = new ProductAdapter(productCafe);
        adapterMilkTea = new ProductAdapter(productMilkTea);
        adapterTea = new ProductAdapter(productTea);
        adapterSmoothies = new ProductAdapter(productSmoothies);
        adapterSnack = new ProductAdapter(productSnack);
        adapterPackage = new ProductAdapter(productPackage);
        adapterOther = new ProductAdapter(productOther);

        // Thiết lập LayoutManager cho mỗi RecyclerView
        recyclerViewCafe.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewMilkTea.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewTea.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewSmoothies.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewSnack.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewPackage.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewOther.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Thiết lập adapter cho mỗi RecyclerView
        recyclerViewCafe.setAdapter(adapterCafe);
        recyclerViewMilkTea.setAdapter(adapterMilkTea);
        recyclerViewTea.setAdapter(adapterTea);
        recyclerViewSmoothies.setAdapter(adapterSmoothies);
        recyclerViewSnack.setAdapter(adapterSnack);
        recyclerViewPackage.setAdapter(adapterPackage);
        recyclerViewOther.setAdapter(adapterOther);

        return view;
    }
}

