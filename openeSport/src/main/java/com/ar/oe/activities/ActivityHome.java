package com.ar.oe.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
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
import com.ar.oe.classes.DrawerItem;
import com.ar.oe.classes.Post;
import com.ar.oe.adapters.DrawerAdapter;
import com.ar.oe.drawer.DrawerHeaderItem;
import com.ar.oe.drawer.DrawerItemInterface;
import com.ar.oe.drawer.DrawerListItem;
import com.ar.oe.fragments.FragmentSection;
import com.ar.oe.utils.AppDatas;
import com.ar.oe.utils.DatabaseActions;
import com.ar.oe.utils.DateParsing;
import com.ar.oe.utils.JSONParsing;
import com.ar.oe.utils.RandomNumber;
import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.InterstitialAd;
import com.google.analytics.tracking.android.EasyTracker;

import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;

public class ActivityHome extends ActionBarActivity implements AdListener {
    private InterstitialAd interstitial;
    private String TAG = "Main activity";
    private Context context;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ImageView splashImg, errorImg;
    private TextView errorText;
    private LinearLayout errorLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerAdapter mMenuAdapter;
    private ArrayList<DrawerItem> drawerItems;
    private ArrayList<Fragment> fragments;
    private List<DrawerItemInterface> items;
    private MenuItem refreshItem, settingsItem;
    private boolean refreshing;
    private String language;
    private int currentMenuItem;
    private boolean error = false, crashed = false;
    private Bundle savedState;
    private String[] userSave;
    SharedPreferences sharedPref;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drawer_main);

        context = this;
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        showNewVersionMsg();

        savedState = savedInstanceState;

        //getGraphicElements
        splashImg = (ImageView)findViewById(R.id.splash_img);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        errorLayout = (LinearLayout) findViewById(R.id.error_layout);
        errorImg = (ImageView) findViewById(R.id.error_img);
        errorText = (TextView) findViewById(R.id.error_text);

        loadGoogleAd();

        setLanguageConfig();

	}

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("menu_items", drawerItems);
        outState.putSerializable("error", error);
        outState.putSerializable("crashed", crashed);
    }

    public ArrayList<Fragment> getFragments(){
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        for(DrawerItem item : drawerItems){
            if(item.getType().equals("game"))
                fragments.add(FragmentSection.newInstance("game", item.getIcon()));
            else
                fragments.add(FragmentSection.newInstance("website", item.getIcon()));

        }
        return fragments;
    }

    private void initDrawer(){
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedState != null && !crashed)
            drawerItems = (ArrayList<DrawerItem>)savedState.getSerializable("menu_items");
        if(drawerItems == null)
            drawerItems = new AppDatas().getMenuItems(context);

        items = new ArrayList<DrawerItemInterface>();


        for(DrawerItem drawerItem : drawerItems){
            if(drawerItem.getType().equals("header"))
                items.add(new DrawerHeaderItem(drawerItem.getName()));
            else
                items.add(new DrawerListItem(drawerItem.getName(), drawerItem.getIcon()));
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
        fragments = getFragments();


//        }else{
            // Pass results to MenuListAdapter Class
            mMenuAdapter = new DrawerAdapter(context, items);

            // Set the MenuListAdapter to the ListView
            mDrawerList.setAdapter(mMenuAdapter);

            // Capture button clicks on side menu
            mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
//        }
    }

    private void showDatas(){
        if(!error){
            if(crashed){
                settingsItem.setVisible(true);
                mDrawerLayout.setVisibility(View.VISIBLE);
                errorLayout.setVisibility(View.GONE);
                errorImg.setVisibility(View.GONE);
                errorText.setVisibility(View.GONE);
                initDrawer();
            }

            if(refreshing){
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, FragmentSection.newInstance(drawerItems.get(currentMenuItem).getName(), drawerItems.get(currentMenuItem).getIcon()));
                ft.commitAllowingStateLoss();
            }
            else{
                initDrawer();
            }

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
        if(savedState == null && !crashed)
            selectItem(currentMenuItem);
    }

    private void setLanguageConfig(){
        //Setting language and default source to show (all webistes EN or FR)
        if(sharedPref.getBoolean("activate_prefs", false))
            currentMenuItem = 1;
        else{
            language = getApplicationContext().getResources().getConfiguration().locale.getLanguage();
            currentMenuItem = 4;
            if(language.equals("fr"))
                currentMenuItem = 3;
        }

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

        if(savedState == null){
            showSplashImg();
            new loadArticles().execute(context);
        }else{
            error = savedState.getBoolean("error");
            crashed = savedState.getBoolean("crashed");
            showDatas();
        }

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
            refreshing = true;
            break;

        case(R.id.item_settings):
            Intent intent_tools = new Intent(context, ActivityPreferences.class);
            intent_tools.putExtra("userSave", userSave);
            startActivityForResult(intent_tools, 1);
            break;
		}

		return super.onOptionsItemSelected(item);
	}

    public void startRefresh() {
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

    public void stopRefresh(){
        refreshItem.getActionView().clearAnimation();
        refreshItem.setActionView(null);
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
        getActionBar().hide();
    }

    private void hideSplashImg(){
        splashImg.setVisibility(View.GONE);
        mDrawerLayout.setVisibility(View.VISIBLE);
        getActionBar().show();
    }

	private void selectItem(int position) {
        currentMenuItem = position;
        if(position == 1 && !sharedPref.getBoolean("activate_prefs", false)){
                showAlertDialog(context.getResources().getString(R.string.error_custom));
        }
        else if(position == 1 && sharedPref.getBoolean("activate_prefs", false)){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, FragmentSection.newInstance(sharedPref.getString("filter_type", "game"), drawerItems.get(position).getIcon()));
            ft.commitAllowingStateLoss();
            mDrawerList.setItemChecked(position, true);
        }
        else if(position > 5 && position < 13){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, FragmentSection.newInstance("game", drawerItems.get(position).getIcon()));
            ft.commitAllowingStateLoss();
            mDrawerList.setItemChecked(position, true);
            // Close drawer
        }
        else{
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, FragmentSection.newInstance("website", drawerItems.get(position).getIcon()));
            ft.commitAllowingStateLoss();
            mDrawerList.setItemChecked(position, true);
            // Close drawer
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
                jsonRss = jTools.getJSONfromURL("http://openesport.jit.su/posts/web");
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
//                    post.setPubDate(tb.getSeconds(jsonRss.getJSONObject(i).getString("pubDate")));
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

    private void loadGoogleAd(){
        // Create the interstitial
        interstitial = new InterstitialAd(this, "ca-app-pub-4132391279379480/2817848124");

        // Create ad request
        AdRequest adRequest = new AdRequest();

        // Begin loading your interstitial
        interstitial.loadAd(adRequest);

        // Set Ad Listener to use the callbacks below
        interstitial.setAdListener(this);
    }

    //Ads management
    @Override
    public void onReceiveAd(Ad ad) {
        if (ad == interstitial) {
            interstitial.show();
        }
    }

    @Override
    public void onFailedToReceiveAd(Ad ad, AdRequest.ErrorCode errorCode) {

    }

    @Override
    public void onPresentScreen(Ad ad) {

    }

    @Override
    public void onDismissScreen(Ad ad) {

    }

    @Override
    public void onLeaveApplication(Ad ad) {

    }

    private void showNewVersionMsg(){
        try {
            SharedPreferences userPref = getSharedPreferences("USER", 0);
            String versionName = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0).versionName;
            if(userPref.getBoolean("maj_infos21", true)){
                showAlertDialogWithTitle(context.getResources().getString(R.string.new_version_title), context.getResources().getString(R.string.new_version_desc));
                SharedPreferences.Editor editor = userPref.edit();
                editor.putBoolean("maj_infos21", false);
                editor.commit();
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showAlertDialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void showAlertDialogWithTitle(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if(sharedPref.getBoolean("activate_prefs", false)){
                    currentMenuItem = 1;
                    startRefresh();
                    refreshing = true;
                    showDatas();
                }
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
