package com.thuctap.NOID.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.thuctap.NOID.Database.DBCart;
import com.thuctap.NOID.Database.DBOrder;
import com.thuctap.NOID.Database.DBProduct;
import com.thuctap.NOID.GUI.HistoryDetailActivity;
import com.thuctap.NOID.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {
    private static List<DBOrder> orderList;
    private List<DBProduct> productList;
    private List<String> orderKeys; // Danh sách để lưu trữ orderKey
    private FirebaseAuth auth;
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference orderDB;

    public HistoryAdapter(List<DBOrder> orderList) {
        this.orderList = orderList;
        auth = FirebaseAuth.getInstance();
        orderKeys = new ArrayList<>(); // Khởi tạo danh sách orderKeys
        orderDB = FirebaseDatabase.getInstance().getReference().child("dathang");
        orderDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String orderKey = snapshot.getKey();
                orderKeys.add(orderKey); // Lưu trữ orderKey vào danh sách
                notifyDataSetChanged(); // Thông báo cập nhật dữ liệu mới
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

            // Các phương thức ChildEventListener khác
        });

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recyclerview_history, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DBOrder order = orderList.get(position);
        /*String orderKey = orderDB.getKey();*/
        String orderKey = orderKeys.get(position);
        /*Hiển thị tình trạng đơn hàng*/
        holder.txtDateOrder.setText(order.getThoigiandh());
        double updatedPrice = order.getTongtiendh();
        DecimalFormat decimalFormat = new DecimalFormat("#,### đ");
        String formattedPrice = decimalFormat.format(updatedPrice);
        holder.txtTotalPriceOrder.setText(formattedPrice);
        holder.txtStatus.setText("Đã giao thành công");
         /*Hiển thị tình trạng đơn hàng*/

        holder.btnReOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), HistoryDetailActivity.class);
                intent.putExtra("KeyDH", orderKey);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtDateOrder, txtTotalPriceOrder, txtStatus;
        Button btnReOrder;
        RecyclerView recyclerView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDateOrder = itemView.findViewById(R.id.txtDateOrder);
            txtTotalPriceOrder = itemView.findViewById(R.id.txtTotalPriceOrder);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            btnReOrder = itemView.findViewById(R.id.btnReOrder);
            recyclerView = itemView.findViewById(R.id.reyclerViewDetailHistory);

        }

    }
}

