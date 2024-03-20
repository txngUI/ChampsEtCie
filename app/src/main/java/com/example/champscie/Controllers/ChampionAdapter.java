package com.example.champscie.Controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.champscie.Models.MinChampion;
import com.example.champscie.R;

import java.util.List;

public class ChampionAdapter extends RecyclerView.Adapter<ChampionAdapter.ViewHolder> {
    private Context context;
    private List<MinChampion> championList;
    private ChampionClickListener championClickListener;

    public ChampionAdapter(Context context, List<MinChampion> championList, ChampionClickListener clickListener) {
        this.context = context;
        this.championList = championList;
        this.championClickListener = clickListener;
    }

    public List<MinChampion> getChampionList() {
        return championList;
    }

    public void setFilteredList(List<MinChampion> filteredList) {
        this.championList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MinChampion champion = championList.get(position);

        Glide.with(context)
                .load(champion.getImageUrl())
                .into(holder.imageView);

        holder.tvName.setText(champion.getName());
        holder.tvTitle.setText(champion.getTitle());
        holder.tvTags.setText(champion.getTags().toString());
        holder.tvBlurb.setText(champion.getBlurb());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                championClickListener.onChampionClick(champion);
            }
        });
    }

    @Override
    public int getItemCount() {
        return championList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvName;
        TextView tvTitle;
        TextView tvTags;
        TextView tvBlurb;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            tvName = itemView.findViewById(R.id.tvName);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTags = itemView.findViewById(R.id.tvTags);
            tvBlurb = itemView.findViewById(R.id.tvBlurb);
        }
    }
}