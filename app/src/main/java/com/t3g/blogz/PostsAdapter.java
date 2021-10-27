package com.t3g.blogz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    List<Post> allPosts;

    public PostsAdapter(List<Post> allPosts){
        this.allPosts = allPosts;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.postTitle.setText(allPosts.get(position).getTitle());
        Picasso.get().load(allPosts.get(position).getFeature_image()).into(holder.postImage);

//        item OnclickListener
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = null;
                if(position==0){
                    text = position+1 +"st";
                }else if (position==1){
                    text = position+1 +"nd";
                }else if (position==2){
                    text = position+1 +"rd";
                }else if (position>=3){
                    text = position+1 +"th";
                }
                Toast.makeText(v.getContext(), text + " Post Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(),PostActivity.class);
                intent.putExtra("title",allPosts.get(position).getTitle());
                intent.putExtra("content",allPosts.get(position).getContent());
//                intent.putExtra("content",allPosts.get(position).getFeature_image());
                v.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return allPosts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView postImage;
        TextView postTitle;
        View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            postImage = itemView.findViewById(R.id.post_image);
            postTitle = itemView.findViewById(R.id.postTitle);
        }
    }
}
