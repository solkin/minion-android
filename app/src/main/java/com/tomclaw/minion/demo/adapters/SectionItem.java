package com.tomclaw.minion.demo.adapters;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

/**
 * Created by solkin on 03.08.17.
 */
public class SectionItem {

    @DrawableRes
    private int icon;

    @NonNull
    private String text;

    @NonNull
    private String description;

    public SectionItem(int icon, @NonNull String text, @NonNull String description) {
        this.icon = icon;
        this.text = text;
        this.description = description;
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
