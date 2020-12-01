package com.tomclaw.minion.demo.adapters;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

/**
 * Created by solkin on 03.08.17.
 */
@SuppressWarnings("WeakerAccess")
public class SectionItem {

    private final int id;

    @DrawableRes
    private final int icon;

    @NonNull
    private final String text;

    @NonNull
    private final String description;

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
