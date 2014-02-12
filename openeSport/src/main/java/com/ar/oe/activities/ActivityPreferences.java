package com.ar.oe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.ar.oe.R;
import com.google.analytics.tracking.android.EasyTracker;

public class ActivityPreferences extends PreferenceActivity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

//        final ActionBar actionBar = getActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
  	public boolean onOptionsItemSelected(MenuItem item){
    	switch (item.getItemId()) {
		case android.R.id.home:
            setResult(RESULT_OK, new Intent());
			finish();
			break;
    	}
		return true;
  	}

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK, new Intent());
        finish();
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
