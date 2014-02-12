package com.ar.oe.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ar.oe.R;
import com.ar.oe.classes.Post;
import com.ar.oe.classes.Stream;
import com.ar.oe.utils.AppDatas;
import com.ar.oe.utils.CustomPosts;
import com.ar.oe.utils.DatabaseActions;
import com.ar.oe.utils.JSONTwitchParsing;
import com.ar.oe.utils.RandomNumber;
import com.astuetz.viewpager.extensions.PagerSlidingTabStrip;

import java.util.ArrayList;

public class FragmentSection extends Fragment{
    private String TAG = "RssFragment";
    private Context context;
    private View view;
    private String category, lang, type;
    private String[] gamesName, langsName;
    private int[] gamesIcon, langsIcon;
    private ViewPager pager;
    private MyPagerAdapter adapter;
    private LinearLayout errorLayout;
    private TextView errorText;
    private ImageView errorImg;
    private ArrayList<Integer> gamesActive = new ArrayList<Integer>();
    private ArrayList<Post> posts = new ArrayList<Post>();
    private FragmentSection thisActivity;
    private PagerSlidingTabStrip tabs;
    private ArrayList<ArrayList <Post>> postsSorted = new ArrayList<ArrayList<Post>>();
    private ActionBar actionBar;
    private boolean noArticle = false;
    private ArrayList<Stream> streams = null;

    public FragmentSection() {
        thisActivity = this;
    }

    public static FragmentSection newInstance(String type, String category){
        FragmentSection fragmentRssWebsite = new FragmentSection();
        Bundle args = new Bundle();
        args.putString("category", category);
        args.putString("type", type);
        fragmentRssWebsite.setArguments(args);
        return fragmentRssWebsite;
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_website, container, false);
        context = view.getContext();
        category = getArguments().getString("category");
        type = getArguments().getString("type");


        lang = context.getResources().getConfiguration().locale.getLanguage();
        gamesName = new AppDatas().getGamesName();
        gamesIcon = new AppDatas().getGamesDrawable();
        langsName = new AppDatas().getLangsName();
        langsIcon = new AppDatas().getLangsDrawable();

        pager = (ViewPager) view.findViewById(R.id.pager);
        tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        errorLayout = (LinearLayout) view.findViewById(R.id.error_layout);
        errorText = (TextView) view.findViewById(R.id.error_text);
        errorText.setText(getResources().getString(R.string.error_articles));
        errorImg = (ImageView) view.findViewById(R.id.error_img);


        if(savedInstanceState != null){
            noArticle = savedInstanceState.getBoolean("noArticle");
            if(!noArticle){
                posts = (ArrayList<Post>) savedInstanceState.getSerializable("posts");
                showUI();
            }else{
                int randomNum = new RandomNumber().generateRandomNumber();
                String imgName = "error" + randomNum;
                int resID = context.getResources().getIdentifier(imgName, "drawable", context.getPackageName());
                errorImg.setImageResource(resID);
                tabs.setVisibility(View.GONE);
                pager.setVisibility(View.GONE);
                errorLayout.setVisibility(View.VISIBLE);
                errorText.setVisibility(View.VISIBLE);
                errorImg.setVisibility(View.VISIBLE);
            }
        }
        else
            new loadingArticles().execute(category);

//        new loadStreams().execute();

        actionBar = ((ActionBarActivity)getActivity()).getSupportActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        return view;
	}


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("posts", posts);
        outState.putSerializable("noArticle", noArticle);
    }

    public class MyPagerAdapter extends FragmentStatePagerAdapter implements PagerSlidingTabStrip.IconTabProvider{

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getPageIconResId(int position){
//            if(position == gamesActive.size())
//                return R.drawable.tv;
//            else
                return gamesActive.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
//            Log.d(TAG, "getPageTitle " + type);
//            if(type.equals("game") && position == 0){
//                Log.d("game and french", TAG);
//                return getResources().getString(R.string.french);
//            }
//            else if(type.equals("game") && position == 1)
//                return getResources().getString(R.string.english);
//            else {
//                Log.d(TAG, "no title");
                return "";
//            }
        }

        @Override
        public int getCount() {
            return gamesActive.size();
        }

        @Override
        public Fragment getItem(int position) {
//            if(position == gamesActive.size())
//                return FragmentStreams.newInstance(0, streams);
//            else
            if(type.equals("website") && position != 0)
                return FragmentArticles.newInstance("game", 0, postsSorted.get(position));
            else
                return FragmentArticles.newInstance(type, 0, postsSorted.get(position));
        }
    }

    public void showUI(){
        try{
            ((ActionBarActivity)getActivity()).getSupportActionBar().removeAllTabs();
        }catch (NullPointerException e){

        }

        if(type.equals("game")){
            for(int i = 0 ; i < langsName.length ; i++){
                ArrayList<Post> checkExistPosts = new AppDatas().sortPostsv2(langsName[i], posts);
                if(checkExistPosts.size() > 0){
                    postsSorted.add(checkExistPosts);
                    gamesActive.add(langsIcon[i]);
                }
            }
        }
        else{
            for(int i = 0 ; i < gamesName.length ; i++){
                ArrayList<Post> checkExistPosts = new AppDatas().sortPosts(gamesName[i], posts);
                if(checkExistPosts.size() > 0){
                    postsSorted.add(checkExistPosts);
                    gamesActive.add(gamesIcon[i]);
                }
            }

            if(gamesActive.size() == 2){
                postsSorted.remove(0);
                gamesActive.remove(0);
                tabs.setVisibility(View.GONE);
            }
        }

        pager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the
                        // corresponding tab.
                        ((ActionBarActivity)getActivity()).getSupportActionBar().setSelectedNavigationItem(position);
                    }
                });
        adapter = new MyPagerAdapter(thisActivity.getChildFragmentManager());

        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(1);
        tabs.setViewPager(pager);

        pager.setCurrentItem(0);

    }

    class loadingArticles extends AsyncTask<String, Void, Void> {

        protected Void doInBackground(String... category) {
            DatabaseActions dbh = new DatabaseActions(context);
            if(category[0].equals("custom")){
                posts = dbh.readDb("oe_menu_drawer");
                posts = new CustomPosts().getCustomPosts(context, posts);
            }
            else
                posts = dbh.readDb(category[0]);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(posts.size() == 0){
                noArticle = true;
                int randomNum = new RandomNumber().generateRandomNumber();
                String imgName = "error" + randomNum;
                int resID = context.getResources().getIdentifier(imgName, "drawable", context.getPackageName());
                errorImg.setImageResource(resID);
                tabs.setVisibility(View.GONE);
                pager.setVisibility(View.GONE);
                errorLayout.setVisibility(View.VISIBLE);
                errorText.setVisibility(View.VISIBLE);
                errorImg.setVisibility(View.VISIBLE);
            }
            else{
                noArticle = false;
                showUI();
            }
        }
    }

    public class loadStreams extends AsyncTask<Context, Void, Void> {
        @Override
        protected Void doInBackground(Context... context) {
            JSONTwitchParsing jTools = new JSONTwitchParsing();

            try{
                String[] gameToStream = category.split("_");
                streams = jTools.getJSONfromGame(gameToStream[0]);

            }catch(Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
//            Log.d("category name: ", streams.get(0).getName() + " ");
            adapter.notifyDataSetChanged();
            pager.setAdapter(adapter);

        }
    }
}
