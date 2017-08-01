package com.tomclaw.minion;

import android.support.annotation.NonNull;

/**
 * Created by solkin on 28.07.17.
 */
class IniRecord {

    private
    @NonNull
    final String key;
    private
    @NonNull
    String[] value;

    protected IniRecord(@NonNull String key, @NonNull String... value) {
        this.key = key;
        this.value = value;
    }

    public
    @NonNull
    String getKey() {
        return key;
    }

    public
    @NonNull
    String[] getValue() {
        return value;
    }

    public boolean isArrayValue() {
        return value.length > 0;
    }
}
