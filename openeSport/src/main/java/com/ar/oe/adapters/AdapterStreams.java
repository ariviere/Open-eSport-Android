package com.ar.oe.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ar.oe.R;
import com.ar.oe.classes.Post;
import com.ar.oe.classes.Stream;
import com.ar.oe.utils.DateParsing;

import java.util.ArrayList;

public class AdapterStreams extends BaseAdapter{

	private Context activity;
	private ArrayList<Stream> data;
	private static LayoutInflater inflater = null;
	private ViewHolder holder;
	private DateParsing tb = new DateParsing();

	public AdapterStreams(Context a, ArrayList<Stream> d) {
    	activity = a;
		data = d;
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
		holder.label.setText(data.get(position).getStatus());

		//Show author
		holder.author.setText(activity.getResources().getString(R.string.by) + " " + data.get(position).getName());
		
		//Show date
		holder.date.setText(data.get(position).getViewers());
		
		//Show image
		int resID = activity.getApplicationContext().getResources().getIdentifier(data.get(position).getGame(), "drawable", activity.getApplicationContext().getPackageName());
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
