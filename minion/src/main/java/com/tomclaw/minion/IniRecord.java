package com.tomclaw.minion;

import android.support.annotation.NonNull;

/**
 * Created by solkin on 28.07.17.
 */
public class IniRecord {

    private
    @NonNull
    final String key;
    private
    @NonNull
    String[] value;

    protected IniRecord(@NonNull String key, @NonNull String... value) {
        this.key = key.trim();
        this.value = value;
        trimValues();
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

    private void trimValues() {
        for (int c = 0;c< value.length;c++) {
            value[c] = value[c].trim();
        }
    }
}
