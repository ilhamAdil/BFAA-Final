package com.dicoding.listviewparcel.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dicoding.listviewparcel.R;
import com.dicoding.listviewparcel.modelsettergetter.Detail;

import java.util.ArrayList;

public class FragmentRecyclerAdapter extends RecyclerView.Adapter<FragmentRecyclerAdapter.FragmentViewHolder> {
    private ArrayList<Detail> Details;

    public FragmentRecyclerAdapter(ArrayList<Detail> details) {
        Details = details;
    }

    @NonNull
    @Override
    public FragmentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_user, viewGroup, false);
        return new FragmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FragmentViewHolder holder, int position) {
        Detail user = Details.get(position);

        Glide.with(holder.itemView.getContext())
                .load(user.getAvatar())
                .apply(new RequestOptions())
                .into(holder.imgPhoto);

        holder.tvName.setText(user.getName());
    }

    @Override
    public int getItemCount() {
        return Details.size();
    }

    public class FragmentViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvName;
        public FragmentViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_photo);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }
}
