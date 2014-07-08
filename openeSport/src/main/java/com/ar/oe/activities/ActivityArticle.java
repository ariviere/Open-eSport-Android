package com.ar.oe.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.ar.oe.classes.Post;
import com.ar.oe.R;
import com.ar.oe.fragments.FragmentArticle;
import com.google.analytics.tracking.android.EasyTracker;

public class ActivityArticle extends ActionBarActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

		Bundle b = getIntent().getExtras();
		Post post = (Post) b.getSerializable("post");

        FragmentArticle fragmentArticle = FragmentArticle.newInstance(post);
        getSupportFragmentManager().beginTransaction().add(R.id.article_container, fragmentArticle).commit();

//        ActionBar actionBar = getSupportActionBar();
//
//        actionBar.setDisplayHomeAsUpEnabled(true);

	}

//    @Override
//  	public boolean onOptionsItemSelected(MenuItem item){
//    	switch (item.getItemId()) {
//            case android.R.id.home:
//                finish();
//                break;
//        }
//		return true;
//  	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_article, menu);
//		return true;
//	}

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
