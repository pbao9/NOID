package com.thuctap.NOID.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;
import com.thuctap.NOID.Database.DBOrder;
import com.thuctap.NOID.Database.DBUser;
import com.thuctap.NOID.GUI.HistoryDetailActivity;
import com.thuctap.NOID.R;

import java.text.DecimalFormat;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {
    private static List<DBOrder> orderList;
    private FirebaseAuth auth;

    private static DatabaseReference customerDB, orderDB;

    public HistoryAdapter(List<DBOrder> orderList) {
        this.orderList = orderList;
        orderDB = FirebaseDatabase.getInstance().getReference("dathang");
        notifyDataSetChanged();
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
        /*Hiển thị tình trạng đơn hàng*/
        holder.txtDateOrder.setText(order.getThoigiandh());
        double updatedPrice = order.getTongtiendh();
        DecimalFormat decimalFormat = new DecimalFormat("#,### đ");
        String formattedPrice = decimalFormat.format(updatedPrice);
        holder.txtTotalPriceOrder.setText(formattedPrice);
        holder.txtStatus.setText("Đã giao thành công");
        /* Hiển thị tình trạng đơn hàng*/

        holder.btnReOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDateOrder = itemView.findViewById(R.id.txtDateOrder);
            txtTotalPriceOrder = itemView.findViewById(R.id.txtTotalPriceOrder);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            btnReOrder = itemView.findViewById(R.id.btnReOrder);

        }
    }
}
