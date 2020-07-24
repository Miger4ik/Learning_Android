package com.example.retrofitrecyclerview;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private ArrayList<PostItem> posts;

    public PostAdapter (ArrayList<PostItem> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        PostViewHolder postViewHolder = new PostViewHolder(view);
        return  postViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        PostItem post = posts.get(position);
        holder.idTextView.setText(post.getId() + "");
        holder.userIdTextView.setText(post.getUserId() + "");
        holder.titleTextView.setText(post.getTitle());
        holder.bodyTextView.setText(post.getBody());
    }

    @Override
    public int getItemCount() {
        if (posts == null) {
            Log.d("count", posts.size() + "");
            return 0;
        }
        Log.d("count", posts.size() + "");
        return posts.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder{

        public TextView idTextView;
        public TextView userIdTextView;
        public TextView titleTextView;
        public TextView bodyTextView;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            idTextView = itemView.findViewById(R.id.idTextView);
            userIdTextView = itemView.findViewById(R.id.userIdTextView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            bodyTextView = itemView.findViewById(R.id.bodyTextView);
        }
    }
}
