package com.example.champscie;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ChampionAdapter extends RecyclerView.Adapter<ChampionAdapter.ViewHolder> implements Filterable {
    private Context context;
    private List<Champion> championList;
    private List<Champion> filteredChampionList;

    public ChampionAdapter(Context context, List<Champion> championList) {
        this.context = context;
        this.championList = championList;
        this.filteredChampionList = new ArrayList<>(championList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Champion champion = championList.get(position);

        Glide.with(context)
                .load(champion.getImageUrl())
                .into(holder.imageView);

        holder.tvName.setText(champion.getName());
        holder.tvTitle.setText(champion.getTitle());
        holder.tvTags.setText(champion.getTags().toString());
        holder.tvBlurb.setText(champion.getBlurb());
    }

    @Override
    public int getItemCount() {
        return filteredChampionList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString().toLowerCase().trim();
                if (charString.isEmpty()) {
                    filteredChampionList = new ArrayList<>(championList);
                } else {
                    List<Champion> filteredList = new ArrayList<>();
                    for (Champion champion : championList) {
                        if (champion.getName().toLowerCase().contains(charString)) {
                            filteredList.add(champion);
                        }
                    }
                    filteredChampionList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredChampionList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredChampionList = (ArrayList<Champion>) results.values;
                notifyDataSetChanged();
            }
        };
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