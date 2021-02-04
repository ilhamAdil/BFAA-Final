package com.dicoding.consumerapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dicoding.consumerapp.R;
import com.dicoding.consumerapp.modelsettergetter.Detail;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    private ArrayList<Detail> listUsers = new ArrayList<>();
    private Activity activity;
    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public interface OnItemClickCallback {
        void onItemClicked(Detail data);
    }

    public FavoriteAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<Detail> getListUsers(){
        return listUsers;
    }

    public void setListUsers(ArrayList<Detail> listUsers){
        if(listUsers.size() > 0){
            this.listUsers.clear();
        }
        this.listUsers.addAll(listUsers);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        holder.tvUsername.setText(listUsers.get(position).getName());
        holder.tvFollowers.setText(listUsers.get(position).getFollowers());
        holder.tvFollowing.setText(listUsers.get(position).getFollowing());
        holder.tvRepository.setText(listUsers.get(position).getRepo());
        Glide.with(holder.itemView.getContext())
                .load(listUsers.get(position).getAvatar())
                .apply(new RequestOptions())
                .into(holder.imgUser);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(listUsers.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listUsers.size();
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername, tvFollowers, tvFollowing, tvRepository;
        ImageView imgUser;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tv_namedetail);
            tvFollowers = itemView.findViewById(R.id.tv_followerdetail);
            tvFollowing = itemView.findViewById(R.id.tv_followingdetail);
            tvRepository = itemView.findViewById(R.id.tv_repositorydetail);
            imgUser = itemView.findViewById(R.id.img_photodetail);
        }
    }
}
