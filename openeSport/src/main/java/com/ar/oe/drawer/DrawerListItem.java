package com.ar.oe.drawer;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ar.oe.R;
import com.ar.oe.adapters.DrawerAdapter;

public class DrawerListItem implements DrawerItemInterface {
    private  String         title;
    private  String         img;
    private  LayoutInflater inflater;

    public DrawerListItem(String title, String img) {
        this.title = title;
        this.img = img;
    }

    @Override
    public int getViewType() {
        return DrawerAdapter.RowType.LIST_ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;
        if (convertView == null) {
            view = (View) inflater.inflate(R.layout.drawer_list_item, null);
            // Do some initialization
        } else {
            view = convertView;
        }

        TextView txtTitle;
		ImageView imgIcon;

        //Locate the TextViews in drawer_list_item.xml
		txtTitle = (TextView) view.findViewById(R.id.title);
		// Locate the ImageView in drawer_list_item.xml
		imgIcon = (ImageView) view.findViewById(R.id.icon);

		// Set the results into TextViews
		txtTitle.setText(title);

		// Set the results into ImageView
        int iconImg = inflater.getContext().getResources().getIdentifier(img + "_drawer", "drawable", inflater.getContext().getPackageName());
		imgIcon.setImageResource(iconImg);

        return view;
    }
}
