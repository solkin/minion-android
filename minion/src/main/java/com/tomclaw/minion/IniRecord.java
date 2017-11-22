package com.tomclaw.minion;

import android.support.annotation.NonNull;

import java.util.Arrays;

/**
 * Created by solkin on 28.07.17.
 */
public class IniRecord {

    @NonNull
    private final String key;
    @NonNull
    private String[] value;

    protected IniRecord(@NonNull String key, @NonNull String... value) {
        this.key = key.trim();
        setValue(value);
    }

    @NonNull
    public String getKey() {
        return key;
    }

    @NonNull
    public String[] getValues() {
        return value;
    }

    @NonNull
    public String getValue() {
        if (!hasValue()) {
            throw new IllegalStateException("IniRecord with key " + key + " has no value");
        }
        return value[0];
    }

    public void setValue(@NonNull String... value) {
        this.value = value;
        trimValues();
    }

    public boolean hasValue() {
        return value.length > 0;
    }

    private void trimValues() {
        for (int c = 0; c < value.length; c++) {
            value[c] = value[c].trim();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IniRecord record = (IniRecord) o;
        return key.equals(record.key) && Arrays.equals(value, record.value);

    }

    @Override
    public int hashCode() {
        int result = key.hashCode();
        result = 31 * result + Arrays.hashCode(value);
        return result;
    }
}
