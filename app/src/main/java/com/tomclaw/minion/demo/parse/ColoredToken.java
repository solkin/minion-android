package com.tomclaw.minion.demo.parse;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

/**
 * Created by solkin on 30.08.17.
 */
class ColoredToken {

    private final
    @NonNull
    String token;
    private final
    @ColorInt
    int color;

    public ColoredToken(@NonNull String token, @ColorInt int color) {
        this.token = token;
        this.color = color;
    }

    public
    @NonNull
    String getToken() {
        return token;
    }

    public
    @ColorInt
    int getColorSpan() {
        return color;
    }
}
