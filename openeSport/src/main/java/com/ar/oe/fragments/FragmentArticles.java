package com.ar.oe.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.ar.oe.R;
import com.ar.oe.activities.ActivityArticle;
import com.ar.oe.activities.ActivityHome;
import com.ar.oe.adapters.AdapterArticles;
import com.ar.oe.classes.Post;
import com.ar.oe.utils.CustomComparator;

import java.util.ArrayList;
import java.util.Collections;

public class FragmentArticles extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private static final String ARG_POSITION = "position";
    private String type;
    private ArrayList<Post> posts = new ArrayList<Post>();
    Context context;
    GridView articlesListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game, container, false);
        context = container.getContext();
        posts = (ArrayList<Post>) getArguments().getSerializable("posts");
        type = getArguments().getString("type");
        if(posts.size() != 0){
            articlesListView = (GridView)rootView.findViewById(R.id.list_view_articles);
            Collections.sort(posts, new CustomComparator());
            articlesListView.setAdapter(new AdapterArticles(context, posts, type));

            articlesListView.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Intent intent = new Intent(context, ActivityArticle.class);
                    intent.putExtra("post", posts.get(position));
                    startActivity(intent);
                }
            });
        }

        SwipeRefreshLayout swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(R.color.lol_color,
                R.color.sc2_color,
                R.color.dota2_color,
                R.color.csgo_color);

        return rootView;
    }

    @Override
    public void onRefresh() {
        ((ActivityHome) getActivity()).populateDatabase();
    }
    public FragmentArticles(){
    }

    public static FragmentArticles newInstance(String type, int position, ArrayList<Post> posts) {
        FragmentArticles f = new FragmentArticles();
        Bundle b = new Bundle();
        b.putSerializable("posts", posts);
        b.putInt(ARG_POSITION, position);
        b.putString("type", type);
        f.setArguments(b);
        return f;
    }
}