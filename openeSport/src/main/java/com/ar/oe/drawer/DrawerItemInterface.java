package com.ar.oe.drawer;

import android.view.LayoutInflater;
import android.view.View;

import java.io.Serializable;

public interface DrawerItemInterface extends Serializable {
    public int getViewType();
    public View getView(LayoutInflater inflater, View convertView);
}
