package com.t3g.blogz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView post_list;
    List<Post> posts;
    PostsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        posts = new ArrayList<>();
        post_list = findViewById(R.id.recyclerView);
        extractPosts(getResources().getString(R.string.url));
        GridLayoutManager manager = new GridLayoutManager(this,2);
        post_list.setLayoutManager(manager);
        adapter = new PostsAdapter(posts);
        post_list.setAdapter(adapter);
    }

    public void extractPosts(String URL){
//        use volley to extract data
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(JSONArray response) {
                Log.d("TAG", "onResponse: " + response.toString());
                for(int i = 0; i < response.length(); i++){
                    try {
                        Post p = new Post();
                        JSONObject jsonObjectData = response.getJSONObject(i);
//                    extract date
                        p.setDate(jsonObjectData.getString("date"));
//                    extract title
                        JSONObject titleObject = jsonObjectData.getJSONObject("title");
                        p.setTitle(titleObject.getString("rendered"));
//                    extract content
                        JSONObject contentObject = jsonObjectData.getJSONObject("content");
                        p.setContent(contentObject.getString("rendered"));
//                    extract content
                        JSONObject excerptObject = jsonObjectData.getJSONObject("excerpt");
                        p.setExcerpt(excerptObject.getString("rendered"));
//                        feature image
                        p.setFeature_image(jsonObjectData.getString("jetpack_featured_media_url"));

                        posts.add(p);
                        adapter.notifyDataSetChanged();

                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }
}