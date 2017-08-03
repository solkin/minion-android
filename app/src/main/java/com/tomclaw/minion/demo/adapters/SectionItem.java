package com.tomclaw.minion.demo.adapters;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

/**
 * Created by solkin on 03.08.17.
 */
public class SectionItem {

    private int id;

    @DrawableRes
    private int icon;

    @NonNull
    private String text;

    @NonNull
    private String description;

    public SectionItem(int id, @DrawableRes int icon, @NonNull String text,
                       @NonNull String description) {
        this.id = id;
        this.icon = icon;
        this.text = text;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public int getIcon() {
        return icon;
    }

    @NonNull
    public String getText() {
        return text;
    }

    @NonNull
    public String getDescription() {
        return description;
    }
}
