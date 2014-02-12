package com.ar.oe.classes;

import java.io.Serializable;

/**
 * Created by aereivir on 16/09/13.
 */
public class DrawerItem implements Serializable {
    private String name;
    private String icon;
    private String type;

    public DrawerItem(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }

    public DrawerItem(String name, String icon, String type) {
        this.name = name;
        this.icon = icon;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
