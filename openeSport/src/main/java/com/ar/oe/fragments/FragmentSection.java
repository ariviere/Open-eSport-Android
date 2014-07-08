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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ar.oe.R;
import com.ar.oe.classes.Category;
import com.ar.oe.classes.Post;
import com.ar.oe.classes.Stream;
import com.ar.oe.utils.CustomPosts;
import com.ar.oe.utils.DatabaseActions;
import com.ar.oe.utils.JSONFilesManager;
import com.ar.oe.utils.JSONTwitchParsing;
import com.ar.oe.utils.RandomNumber;
import com.astuetz.viewpager.extensions.PagerSlidingTabStrip;

import java.util.ArrayList;

public class FragmentSection extends Fragment{
    private String TAG = "RssFragment";
    private Context context;
    private View view;
    private String category, lang, type;
    private ArrayList<Category> gamesArray, languagesArray;
    private ArrayList<String> gamesTitle = new ArrayList<String>();
    private ViewPager pager;
    private MyPagerAdapter adapter;
    private LinearLayout errorLayout;
    private TextView errorText;
    private ImageView errorImg;
    private ArrayList<String> gamesActive = new ArrayList<String>();
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
        gamesArray = new JSONFilesManager().getCategoriesList(context, "game");
        languagesArray = new JSONFilesManager().getCategoriesList(context, "language");

        for(Category game : gamesArray)
            gamesTitle.add(game.getIcon());

        gamesArray.add(0, new Category("All", "oe_menu"));

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
            return getResources().getIdentifier(gamesActive.get(position), "drawable", context.getPackageName());
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
                return "TEST";
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
            for(Category language : languagesArray){
                ArrayList<Post> postsList = new ArrayList<Post>();
                for(Post post : posts){
                    if(post.getLanguage().equals(language.getIcon())){
                        postsList.add(post);
                    }
                }

                if(postsList.size() > 0){
                    postsSorted.add(postsList);
                    gamesActive.add(language.getIcon());
                }
            }
        }
        else{
            for(Category game : gamesArray){
                ArrayList<Post> postsList = new ArrayList<Post>();
                if(game.getName().equals("All")){
                    postsList.addAll(posts);
                }
                else if(game.getIcon().equals("other")){
                    for(Post post : posts) {
                        if(!gamesTitle.contains(post.getThumbnail()))
                            postsList.add(post);
                    }
                }
                else {
                    for(Post post : posts) {
                        if(post.getThumbnail().equals(game.getIcon())){
                            postsList.add(post);
                        }
                    }
                }
                if(postsList.size() > 0){
                    postsSorted.add(postsList);
                    gamesActive.add(game.getIcon());
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

            posts = dbh.readDb(context, category[0]);

            if(category[0].equals("oe_menu"))
                posts = new CustomPosts().getCustomPosts(context, posts);

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
