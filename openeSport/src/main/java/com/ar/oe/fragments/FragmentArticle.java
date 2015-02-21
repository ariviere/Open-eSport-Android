package com.ar.oe.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.ar.oe.R;
import com.ar.oe.classes.Post;


/**
 * Created by ariviere on 24/03/2014.
 */
public class FragmentArticle extends Fragment{

    private LayoutInflater inflater;
    private Post post;
    private WebView webView;
    private boolean refreshing = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;

        View rootView = inflater.inflate(R.layout.fragment_article, container, false);

        post = (Post)getArguments().getSerializable("post");

        setHasOptionsMenu(true);
        ActionBar actionBar = ((ActionBarActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        configureWebView(rootView);

        return rootView;
    }


    public static FragmentArticle newInstance(Post post){
        FragmentArticle articleFragment = new FragmentArticle();
        Bundle bundle = new Bundle();
        bundle.putSerializable("post", post);
        articleFragment.setArguments(bundle);
        return articleFragment;
    }

    private void configureWebView(View view){
        webView = (WebView)view.findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);

        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            webView.getSettings().setUseWideViewPort(true);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setSupportZoom(true);
            webView.getSettings().setDisplayZoomControls(false);
        }

        webView.loadUrl(post.getUrl());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                break;
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_article, menu);

        MenuItem shareItem = menu.findItem(R.id.menu_item_share);
        ShareActionProvider mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        mShareActionProvider.setShareIntent(createShareIntent());

        super.onCreateOptionsMenu(menu, inflater);
    }

    private Intent createShareIntent(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, post.getTitle() + " - " + post.getUrl());
        sendIntent.setType("text/plain");
        return sendIntent;
    }
}
