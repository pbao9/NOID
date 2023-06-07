package com.thuctap.NOID.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.thuctap.NOID.Database.DBProduct;
import com.thuctap.NOID.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.squareup.picasso.Picasso;

public class ProductAdapter extends ArrayAdapter<DBProduct> {
    private Context context;
    private int resource;
    private List<DBProduct> productList;

    public ProductAdapter(Context context, List<DBProduct> productList) {
        super(context, R.layout.custom_listview, productList);
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_listview, parent, false);
            holder = new ViewHolder();
            holder.txtProductName = convertView.findViewById(R.id.txtProductName);
            holder.txtProductDesc = convertView.findViewById(R.id.txtProductDesc);
            holder.txtProductPrice = convertView.findViewById(R.id.txtProductPrice);
            holder.imgProduct = convertView.findViewById(R.id.imgProduct);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        DBProduct dbProduct = productList.get(position);

        holder.txtProductName.setText(dbProduct.getName());
        holder.txtProductDesc.setText(dbProduct.getDesc());
        /*Hiển thị thêm . đ trong giatiensp */
        double priceDouble = Double.parseDouble(dbProduct.getPrice());
        DecimalFormat decimalFormat = new DecimalFormat("#,### đ");
        String formattedGiatiensp = decimalFormat.format(priceDouble);
        holder.txtProductPrice.setText(formattedGiatiensp);
        /*Hiển thị thêm . đ trong giatiensp */
        /*holder.txtProductPrice.setText(dbProduct.getPrice());*/

        String imageUrl = dbProduct.getImageUrl();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.get().load(imageUrl).into(holder.imgProduct);
        } else {
            // Handle case when imageUrl is empty or null
            // For example, you can set a default image to ImageView
            holder.imgProduct.setImageResource(R.drawable.cafe_2);
        }


        return convertView;
    }

    private static class ViewHolder {
        TextView txtProductName;
        TextView txtProductDesc;
        TextView txtProductPrice;
        ImageView imgProduct;
    }
}
