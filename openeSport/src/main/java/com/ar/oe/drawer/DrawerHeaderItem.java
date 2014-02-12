package com.ar.oe.drawer;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ar.oe.R;
import com.ar.oe.adapters.DrawerAdapter;

public class DrawerHeaderItem implements DrawerItemInterface {
    private final String         name;

    public DrawerHeaderItem(String name) {
        this.name = name;
    }

    @Override
    public int getViewType() {
        return DrawerAdapter.RowType.HEADER_ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;
        if (convertView == null) {
            view = (View) inflater.inflate(R.layout.drawer_header, null);
            view.setOnClickListener(null);
            view.setOnLongClickListener(null);
            view.setLongClickable(false);
        } else {
            view = convertView;
        }

        TextView text = (TextView) view.findViewById(R.id.drawer_header_text);
        text.setText(name);

        return view;
    }
}
