package com.ar.oe.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ar.oe.R;
import com.ar.oe.classes.Category;
import com.ar.oe.workers.DataService;
import com.ar.oe.classes.Post;
import com.ar.oe.adapters.DrawerAdapter;
import com.ar.oe.drawer.DrawerHeaderItem;
import com.ar.oe.drawer.DrawerItemInterface;
import com.ar.oe.drawer.DrawerListItem;
import com.ar.oe.fragments.FragmentSection;
import com.ar.oe.utils.DatabaseActions;
import com.ar.oe.utils.DateParsing;
import com.ar.oe.utils.JSONFilesManager;
import com.ar.oe.utils.JSONParsing;
import com.ar.oe.utils.RandomNumber;
import com.foxykeep.datadroid.activity.generic.GenericDataActivityCompat;
import com.google.analytics.tracking.android.EasyTracker;

import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;

public class ActivityHome extends GenericDataActivityCompat<DataService.WORKER_TYPE> {
    private String TAG = "Main activity";
    private final static String SERVER_URL = "http://openesport.herokuapp.com/posts/web";
    private Context context;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ImageView splashImg, errorImg;
    private TextView errorText;
    private LinearLayout errorLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerAdapter mMenuAdapter;
    private ArrayList<Category> categories;
    private List<DrawerItemInterface> items;
    private MenuItem refreshItem, settingsItem;
    private boolean refreshing;
    private int currentMenuItem;
    private boolean error = false, crashed = false;
    private Bundle savedState;
    private FragmentSection fragmentSection;
    private SharedPreferences sharedPref;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drawer_main);

        context = this;
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        savedState = savedInstanceState;

        splashImg = (ImageView)findViewById(R.id.splash_img);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        errorLayout = (LinearLayout) findViewById(R.id.error_layout);
        errorImg = (ImageView) findViewById(R.id.error_img);
        errorText = (TextView) findViewById(R.id.error_text);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        if(savedState == null){
            showSplashImg();
            new loadArticles().execute(context);
        }else{
            error = savedState.getBoolean("error");
            crashed = savedState.getBoolean("crashed");
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
        outState.putSerializable("error", error);
        outState.putSerializable("crashed", crashed);
    }

    @Override
    protected DataService.WORKER_TYPE getWorkerTypeByOrdinal(int ordinal) {
        return DataService.WORKER_TYPE.values()[ordinal];
    }

    private void initDrawer(){
        if(savedState != null && !crashed)
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

        if(!error){
            if(crashed){
                settingsItem.setVisible(true);
                mDrawerLayout.setVisibility(View.VISIBLE);
                errorLayout.setVisibility(View.GONE);
                errorImg.setVisibility(View.GONE);
                errorText.setVisibility(View.GONE);
            }

            initDrawer();

            mMenuAdapter.notifyDataSetChanged();
            mDrawerList.setAdapter(mMenuAdapter);
            mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

            crashed = false;
        }
        else{
            int randomNumber = new RandomNumber().generateRandomNumber();
            String imgName = "error" + randomNumber;
            int resID = context.getResources().getIdentifier(imgName, "drawable", context.getPackageName());
            errorImg.setImageResource(resID);

            mDrawerLayout.setVisibility(View.GONE);
            settingsItem.setVisible(false);

            errorLayout.setVisibility(View.VISIBLE);
            errorImg.setVisibility(View.VISIBLE);
            errorText.setVisibility(View.VISIBLE);
            crashed = true;

        }

        if(splashImg.getVisibility() == View.VISIBLE) hideSplashImg();
        if(refreshing) {
            stopRefresh();
        }
        if(!crashed)
            selectItem(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_articles, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        refreshItem = menu.findItem(R.id.item_refresh);
        settingsItem = menu.findItem(R.id.item_settings);

        return super.onPrepareOptionsMenu(menu);
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
        //Refresh button
        case(R.id.item_refresh):
            startRefresh();
            //Launching data load
            new loadArticles().execute(context);
            break;

        case(R.id.item_settings):
            Intent intent_tools = new Intent(context, ActivityPreferences.class);
            startActivityForResult(intent_tools, 1);
            break;
		}

		return super.onOptionsItemSelected(item);
	}

    public void startRefresh() {
        refreshing = true;
        LayoutInflater inflater = (LayoutInflater) getApplication()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ImageView iv = (ImageView) inflater.inflate(R.layout.action_refresh,
                null);

        Animation rotation = AnimationUtils.loadAnimation(getApplication(),
                R.anim.refresh_rotate);
        rotation.setRepeatCount(Animation.INFINITE);
        iv.startAnimation(rotation);
        MenuItemCompat.setActionView(refreshItem, iv);
    }

    public void stopRefresh(){
        MenuItemCompat.getActionView(refreshItem).clearAnimation();
        MenuItemCompat.setActionView(refreshItem, null);
    }

    @Override
    public void onRequestFinishedSuccess(DataService.WORKER_TYPE workerType, Bundle payload) {

    }

    @Override
    public void onRequestFinishedError(DataService.WORKER_TYPE workerType, Bundle payload) {

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
        currentMenuItem = position;
        if(position > 1 && position < 9){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            fragmentSection = FragmentSection.newInstance("game", categories.get(position).getIcon());
            ft.replace(R.id.content_frame, fragmentSection, "articlesFragment");
            ft.commit();
            mDrawerList.setItemChecked(position, true);
        }
        else{
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            fragmentSection = FragmentSection.newInstance("website", categories.get(position).getIcon());
            ft.replace(R.id.content_frame, fragmentSection, "articlesFragment");
            ft.commit();
            mDrawerList.setItemChecked(position, true);
        }
        mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

    public class loadArticles extends AsyncTask<Context, Void, Void> {
        private DateParsing tb = new DateParsing();

        @Override
        protected Void doInBackground(Context... context) {
            DatabaseActions dbh = new DatabaseActions(context[0]);
            dbh.removeDatas();
            JSONParsing jTools = new JSONParsing();
            JSONArray jsonRss = null;

            try{
                jsonRss = jTools.getJSONfromURL(SERVER_URL);
            }catch(Exception e){
                error = true;
                return null;
            }

            ArrayList<Post> posts = new ArrayList<Post>();
            try {
                for(int i = 0 ; i < jsonRss.length() ; i++){
                    Post post = new Post();
                    post.setTitle(jsonRss.getJSONObject(i).getString("title"));
                    post.setWebsite(jsonRss.getJSONObject(i).getString("website"));
                    post.setAuthor(jsonRss.getJSONObject(i).getString("author"));
                    post.setThumbnail(jsonRss.getJSONObject(i).getString("category"));
                    post.setLanguage(jsonRss.getJSONObject(i).getString("language"));
                    post.setPubDate(jsonRss.getJSONObject(i).getString("pubDate"));
                    post.setUrl(jsonRss.getJSONObject(i).getString("link"));
                    posts.add(post);
                }
            } catch (JSONException e){
                error = true;
                return null;
            } catch (NullPointerException e){
                error = true;
                return null;
            }
            dbh.insertColumns(posts);
            error = false;

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            showDatas();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("pref_saved", true);
                editor.commit();

                currentMenuItem = 0;
                startRefresh();
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
