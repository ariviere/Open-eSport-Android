package com.ar.oe.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;

import com.ar.oe.R;
import com.ar.oe.adapters.AdapterStreams;
import com.ar.oe.classes.Stream;

import java.util.ArrayList;


public class FragmentStreams extends Fragment {
    private static final String ARG_POSITION = "position";
    private ArrayList<Stream> streams = new ArrayList<Stream>();
    Context context;
    GridView articlesListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game, container, false);
        context = container.getContext();
        streams = (ArrayList<Stream>) getArguments().getSerializable("streams");
        if(streams != null && streams.size() != 0){
            articlesListView = (GridView)rootView.findViewById(R.id.list_view_articles);
            articlesListView.setAdapter(new AdapterStreams(context, streams));

            articlesListView.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(streams.get(position).getUrl()));
                    startActivity(intent);
                }
            });
        }
        return rootView;
    }

    public FragmentStreams(){
    }

    public static FragmentStreams newInstance(int position, ArrayList<Stream> streams) {
        FragmentStreams f = new FragmentStreams();
        Bundle b = new Bundle();
        b.putSerializable("streams", streams);
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }
}