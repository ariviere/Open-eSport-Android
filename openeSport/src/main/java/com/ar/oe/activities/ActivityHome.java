package com.ar.oe.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ar.oe.R;
import com.ar.oe.classes.Category;
import com.ar.oe.classes.Post;
import com.ar.oe.adapters.DrawerAdapter;
import com.ar.oe.drawer.DrawerHeaderItem;
import com.ar.oe.drawer.DrawerItemInterface;
import com.ar.oe.drawer.DrawerListItem;
import com.ar.oe.fragments.FragmentSection;
import com.ar.oe.utils.JSONFilesManager;
import com.ar.oe.utils.JSONParsing;
import com.google.analytics.tracking.android.EasyTracker;

import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;
import com.parse.Parse;
import com.parse.ParseObject;

public class ActivityHome extends ActionBarActivity {

    private String TAG = "Main activity";
    private Context context;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ImageView splashImg;
    private Button errorButton;
    private TextView errorText;
    private LinearLayout errorLayout;
    private ProgressBar progressBar;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerAdapter mMenuAdapter;
    private ArrayList<Category> categories;
    private List<DrawerItemInterface> items;
    private int currentMenuItem;
    private Bundle savedState;
    private SharedPreferences sharedPref;
    private ArrayList<Post> articles;

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drawer_main);

        Parse.initialize(this, "E60N4EVmgFUKNTb8Zw0XX7pvehdxVWI0hcufUbgW", "HlcnTmcD8OCnRoMYwvmpCXkhi1O0iT8FnCJpNbM8");

        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();
        
        context = this;
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        savedState = savedInstanceState;

        splashImg = (ImageView)findViewById(R.id.splash_img);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        errorLayout = (LinearLayout) findViewById(R.id.error_layout);
        errorButton = (Button) findViewById(R.id.error_button);
        errorText = (TextView) findViewById(R.id.error_text);
        progressBar = (ProgressBar) findViewById(R.id.loading);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        if(savedState == null){
            showSplashImg();
            populateDatabase();
        }else{
            showDatas();
        }
	}

    private void launchPrefScreen(){
        if(!sharedPref.getBoolean("pref_saved", false)){
            Intent intent_tools = new Intent(context, ActivityPreferences.class);
            intent_tools.putExtra("first_launch", true);
            startActivityForResult(intent_tools, 1);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("menu_items", categories);
    }

    private void initDrawer(){
        if(savedState != null)
            categories = (ArrayList<Category>)savedState.getSerializable("menu_items");
        if(categories == null){
            categories = new ArrayList<Category>();
            categories.add(new Category(context.getResources().getString(R.string.home), "oe_menu", "all"));

            categories.add(new Category(context.getResources().getString(R.string.games), null, "header"));
            categories.addAll(JSONFilesManager.getCategoriesList(context, "game"));
            categories.remove(categories.size()-1);

            categories.add(new Category(context.getResources().getString(R.string.websites), null, "header"));
            categories.addAll(JSONFilesManager.getCategoriesList(context, "website"));
        }

        items = new ArrayList<DrawerItemInterface>();


        for(Category category : categories){
            if(category.getType().equals("header"))
                items.add(new DrawerHeaderItem(category.getName()));
            else
                items.add(new DrawerListItem(category.getName(), category.getIcon()));
        }


        // Set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_navigation_drawer, R.string.drawer_open,
                R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        mMenuAdapter = new DrawerAdapter(context, items);

        mDrawerList.setAdapter(mMenuAdapter);

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

    private void showDatas(){
        launchPrefScreen();

        initDrawer();

        mMenuAdapter.notifyDataSetChanged();
        mDrawerList.setAdapter(mMenuAdapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        if(splashImg.getVisibility() == View.VISIBLE) {
            hideSplashImg();
        }

        mDrawerLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

        selectItem(0);
    }

    private void showBlank(){
        if(splashImg.getVisibility() == View.VISIBLE) {
            hideSplashImg();
        }

        mDrawerLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
        errorText.setVisibility(View.VISIBLE);
        errorButton.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_articles, menu);
        return true;
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
        //Home button
        case(android.R.id.home):
			if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
				mDrawerLayout.closeDrawer(mDrawerList);
			} else {
				mDrawerLayout.openDrawer(mDrawerList);
			}
            break;
        case(R.id.item_settings):
            Intent intent_tools = new Intent(context, ActivityPreferences.class);
            startActivityForResult(intent_tools, 1);
            break;
		}

		return super.onOptionsItemSelected(item);
	}

    // The click listener for ListView in the navigation drawer
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
            selectItem(position);
		}
	}

    private void showSplashImg(){
        splashImg.setVisibility(View.VISIBLE);
        mDrawerLayout.setVisibility(View.GONE);
        getSupportActionBar().hide();
    }

    private void hideSplashImg(){
        splashImg.setVisibility(View.GONE);
        mDrawerLayout.setVisibility(View.VISIBLE);
        getSupportActionBar().show();
    }

	private void selectItem(int position) {
        try {
            currentMenuItem = position;
            if (position > 1 && position < 9) {
                FragmentSection fragmentSection = FragmentSection.newInstance(articles, "game", categories.get(position).getIcon());
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragmentSection);
                ft.commit();
                mDrawerList.setItemChecked(position, true);
            } else {
                FragmentSection fragmentSection = FragmentSection.newInstance(articles, "website", categories.get(position).getIcon());
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragmentSection);
                ft.commit();
                mDrawerList.setItemChecked(position, true);
            }
        }catch (IllegalStateException e){
            e.printStackTrace();
        }
        mDrawerLayout.closeDrawer(mDrawerList);
	}

    public void startRefreshButton(View view){
        populateDatabase();
        progressBar.setVisibility(View.VISIBLE);
        errorLayout.setVisibility(View.GONE);
        errorText.setVisibility(View.GONE);
        errorButton.setVisibility(View.GONE);
    }

    final Handler mHandler = new Handler();

    final Runnable showDatas = new Runnable() {
        public void run() {
            showDatas();
        }
    };

    final Runnable showBlank = new Runnable() {
        public void run() {
            showBlank();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("pref_saved", true);
                editor.commit();

                currentMenuItem = 0;
                showDatas();
            }
        }
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
