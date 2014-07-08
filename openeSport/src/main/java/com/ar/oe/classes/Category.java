package com.ar.oe.classes;

import java.io.Serializable;

/**
 * Created by aereivir on 16/09/13.
 */
public class Category implements Serializable {
    private String name;
    private String icon;
    private String language;
    private String type;
    private boolean enabled;

    public Category(){

    }

    public Category(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }

    public Category(String name, String icon, String type) {
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
