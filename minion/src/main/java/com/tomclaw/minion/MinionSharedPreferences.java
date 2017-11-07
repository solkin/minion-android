package com.tomclaw.minion;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by solkin on 07/11/2017.
 */
public class MinionSharedPreferences implements SharedPreferences {

    private static final String PREF_GROUP_NAME = "Preferences";

    private Minion minion;

    public MinionSharedPreferences(Minion minion) {
        this.minion = minion;
    }

    @Override
    public Map<String, ?> getAll() {
        Map<String, Object> values = new LinkedHashMap<>() ;
        for (IniRecord record : minion.getOrCreateGroup(PREF_GROUP_NAME).getRecords()) {
            if (record.getValues().length > 1) {
                values.put(record.getKey(), record.getValues());
            } else {
                values.put(record.getKey(), record.getValue());
            }
        }
        return values;
    }

    @Nullable
    @Override
    public String getString(String key, @Nullable String defValue) {
        return minion.getValue(PREF_GROUP_NAME, key, defValue);
    }

    @Nullable
    @Override
    public Set<String> getStringSet(String key, @Nullable Set<String> defSet) {
        String[] values = minion.getValues(PREF_GROUP_NAME, key);
        if (values != null) {
            return new LinkedHashSet<>(Arrays.asList(values));
        }
        return defSet;
    }

    @Override
    public int getInt(String key, int value) {
        return Integer.parseInt(minion.getValue(PREF_GROUP_NAME, key, String.valueOf(value)));
    }

    @Override
    public long getLong(String key, long value) {
        return Long.parseLong(minion.getValue(PREF_GROUP_NAME, key, String.valueOf(value)));
    }

    @Override
    public float getFloat(String key, float value) {
        return Float.parseFloat(minion.getValue(PREF_GROUP_NAME, key, String.valueOf(value)));
    }

    @Override
    public boolean getBoolean(String key, boolean value) {
        return Boolean.parseBoolean(minion.getValue(PREF_GROUP_NAME, key, String.valueOf(value)));
    }

    @Override
    public boolean contains(String key) {
        String value = minion.getValue(PREF_GROUP_NAME, key);
        return value != null;
    }

    @Override
    public Editor edit() {
        return null;
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {

    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {

    }
}
