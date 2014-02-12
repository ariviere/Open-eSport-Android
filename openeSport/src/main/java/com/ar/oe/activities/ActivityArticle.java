package com.ar.oe.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import com.ar.oe.classes.Post;
import com.ar.oe.R;
import com.google.analytics.tracking.android.EasyTracker;

public class ActivityArticle extends ActionBarActivity {
	Post post;
    WebView webView;
	private ShareActionProvider mShareActionProvider;
    MenuItem refreshItem;
    boolean refreshing = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

		Bundle b = getIntent().getExtras();
		post = (Post) b.getSerializable("post");

		webView = (WebView)findViewById(R.id.webview);
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
        webView.setWebViewClient(new WebViewClient(){
            public void onPageFinished(WebView view, String url){
                if(refreshing){
                    MenuItemCompat.getActionView(refreshItem).clearAnimation();
                    MenuItemCompat.setActionView(refreshItem, null);
                    refreshing = false;
                }
            }
        });

        final ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);

	}

    private void refresh(){
        LayoutInflater inflater = (LayoutInflater) getApplication()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ImageView iv = (ImageView) inflater.inflate(R.layout.action_refresh,
                null);

        Animation rotation = AnimationUtils.loadAnimation(getApplication(),
                R.anim.refresh_rotate);
        rotation.setRepeatCount(Animation.INFINITE);
        iv.startAnimation(rotation);
        refreshItem.setActionView(iv);
    }

    @Override
  	public boolean onOptionsItemSelected(MenuItem item){
    	switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
        case R.id.item_refresh:
            webView.reload();
            refreshing = true;
            refresh();
    	}
		return true;
  	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_article, menu);
		MenuItem shareItem = menu.findItem(R.id.menu_item_share);
		mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
		mShareActionProvider.setShareIntent(createShareIntent());
        refreshItem = menu.findItem(R.id.item_refresh);
        refreshing = true;
        refresh();
		return true;
	}

	private Intent createShareIntent(){
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, post.getTitle() + " - " + post.getUrl());
		sendIntent.setType("text/plain");
		return sendIntent;
	}

    @Override
    public void onStart() {
        super.onStart();
        EasyTracker.getInstance(this).activityStart(this);  // Add this method.
    }

    @Override
    public void onStop() {
        super.onStop();
        EasyTracker.getInstance(this).activityStop(this);  // Add this method.
    }
	
}
