package com.ar.oe.adapters;

import java.util.ArrayList;
import java.util.Map;

import com.ar.oe.classes.Post;
import com.ar.oe.utils.AppDatas;
import com.ar.oe.utils.DateParsing;
import com.ar.oe.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterArticles extends BaseAdapter{

	private Context activity;
	private ArrayList<Post> data;
	private static LayoutInflater inflater = null;
	private ViewHolder holder;
	private DateParsing tb = new DateParsing();
    private String iconType;
	
	public AdapterArticles(Context a, ArrayList<Post> d, String iconType) {
    	activity = a;
		data = d;
        this.iconType = iconType;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public int getCount() {
		return data.toArray().length;
	}

	public Object getItem(int arg0) {
		return arg0;
	}

	public long getItemId(int arg0) {
		return arg0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;

		//Graphic elements init
		if (convertView == null) {
			vi = inflater.inflate(R.layout.row_post, null);
			holder = new ViewHolder();
			holder.label = (TextView) vi.findViewById(R.id.title);
			holder.author = (TextView) vi.findViewById(R.id.author);
			holder.date = (TextView) vi.findViewById(R.id.date);
			holder.image = (ImageView) vi.findViewById(R.id.thumb);
			vi.setTag(holder);
		} else
			holder = (ViewHolder) vi.getTag();

		//Show title
		if(data.get(position).getTitle().length() > 65)
			holder.label.setText(data.get(position).getTitle().substring(0, 65) + "...");
		else
			holder.label.setText(data.get(position).getTitle());

		//Show author
		if(data.get(position).getAuthor() == null || data.get(position).getAuthor().equals("null") || data.get(position).getAuthor().equals("") || data.get(position).getAuthor().equals(data.get(position).getWebsite()))
			holder.author.setText(activity.getResources().getString(R.string.by) + " " + data.get(position).getWebsite());
		else
			holder.author.setText(activity.getResources().getString(R.string.by) + " " + data.get(position).getAuthor() + " - " + data.get(position).getWebsite());
		
		//Show date
		String goodDate = tb.timeFromPresent(tb.getSeconds(data.get(position).getPubDate()));
		holder.date.setText(goodDate);
		
		//Show image
        int resID;
        if(iconType.equals("game")){
            Map<String, String> websitesIcons = new AppDatas().getWebsitesIcons(activity);
            String websiteName = websitesIcons.get(data.get(position).getWebsite()) != null ? websitesIcons.get(data.get(position).getWebsite()) : "other";
		    resID = activity.getApplicationContext().getResources().getIdentifier(websiteName, "drawable", activity.getApplicationContext().getPackageName());
        }
        else
            resID = activity.getApplicationContext().getResources().getIdentifier(data.get(position).getThumbnail(), "drawable", activity.getApplicationContext().getPackageName());

        holder.image.setImageResource(resID);
		
		return vi;
	}
	
	public static class ViewHolder {
		public TextView label;
		public TextView author;
		public TextView date;
		public ImageView image;
	}

}
