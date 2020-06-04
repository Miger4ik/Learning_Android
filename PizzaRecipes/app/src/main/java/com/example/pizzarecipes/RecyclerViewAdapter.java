package com.example.pizzarecipes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewViewHolder> {

    private ArrayList<CardForLayout> recyclerViewItems;

    public static class RecyclerViewViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView headerText;
        public TextView contentText;

        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            headerText = itemView.findViewById(R.id.headerTextView);
            contentText = itemView.findViewById(R.id.contentTextView);
        }
    }

    public RecyclerViewAdapter (ArrayList<CardForLayout> recyclerViewItems) {
        this.recyclerViewItems = recyclerViewItems;
    }

    @NonNull
    @Override
    public RecyclerViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_for_layout, parent, false);
        RecyclerViewViewHolder recyclerViewViewHolder = new RecyclerViewViewHolder(view);
        return recyclerViewViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewViewHolder holder, int position) {
        CardForLayout cardForLayout = recyclerViewItems.get(position);

        holder.imageView.setImageResource(cardForLayout.getImageResource());
        holder.headerText.setText(cardForLayout.getHeaderText());
        holder.contentText.setText(cardForLayout.getContentText());
    }

    @Override
    public int getItemCount() {
        return recyclerViewItems.size();
    }


}
