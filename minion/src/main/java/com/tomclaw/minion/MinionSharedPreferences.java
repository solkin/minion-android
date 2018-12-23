package com.tomclaw.minion;

import android.content.SharedPreferences;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import androidx.annotation.Nullable;

/**
 * Created by solkin on 07/11/2017.
 */
public class MinionSharedPreferences implements SharedPreferences {

    private static final String PREF_GROUP_NAME = "Preferences";

    private Minion minion;

    private Set<OnSharedPreferenceChangeListener> listeners = new HashSet<>();

    public MinionSharedPreferences(Minion minion) {
        this.minion = minion;
    }

    @Override
    public Map<String, ?> getAll() {
        Map<String, Object> values = new LinkedHashMap<>();
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
        String storedValue = minion.getValue(PREF_GROUP_NAME, key);
        if (storedValue != null) {
            return Integer.parseInt(storedValue);
        }
        return value;
    }

    @Override
    public long getLong(String key, long value) {
        String storedValue = minion.getValue(PREF_GROUP_NAME, key);
        if (storedValue != null) {
            return Long.parseLong(storedValue);
        }
        return value;
    }

    @Override
    public float getFloat(String key, float value) {
        String storedValue = minion.getValue(PREF_GROUP_NAME, key);
        if (storedValue != null) {
            return Float.parseFloat(storedValue);
        }
        return value;
    }

    @Override
    public boolean getBoolean(String key, boolean value) {
        String storedValue = minion.getValue(PREF_GROUP_NAME, key);
        if (storedValue != null) {
            return Boolean.parseBoolean(storedValue);
        }
        return value;
    }

    @Override
    public boolean contains(String key) {
        String value = minion.getValue(PREF_GROUP_NAME, key);
        return value != null;
    }

    @Override
    public Editor edit() {
        return new MinionEditor();
    }

    private void notifyListeners(String key) {
        for (OnSharedPreferenceChangeListener listener : listeners) {
            listener.onSharedPreferenceChanged(this, key);
        }
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        listeners.add(onSharedPreferenceChangeListener);
    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        listeners.remove(onSharedPreferenceChangeListener);
    }

    public class MinionEditor implements Editor {

        @Override
        public Editor putString(String key, @Nullable String value) {
            minion.setValue(PREF_GROUP_NAME, key, value);
            notifyListeners(key);
            return this;
        }

        @Override
        public Editor putStringSet(String key, @Nullable Set<String> value) {
            if (value != null) {
                String[] values = value.toArray(new String[0]);
                minion.setValue(PREF_GROUP_NAME, key, values);
            }
            notifyListeners(key);
            return this;
        }

        @Override
        public Editor putInt(String key, int value) {
            minion.setValue(PREF_GROUP_NAME, key, Integer.toString(value));
            notifyListeners(key);
            return this;
        }

        @Override
        public Editor putLong(String key, long value) {
            minion.setValue(PREF_GROUP_NAME, key, Long.toString(value));
            notifyListeners(key);
            return this;
        }

        @Override
        public Editor putFloat(String key, float value) {
            minion.setValue(PREF_GROUP_NAME, key, Float.toString(value));
            notifyListeners(key);
            return this;
        }

        @Override
        public Editor putBoolean(String key, boolean value) {
            minion.setValue(PREF_GROUP_NAME, key, Boolean.toString(value));
            notifyListeners(key);
            return this;
        }

        @Override
        public Editor remove(String key) {
            minion.removeRecord(PREF_GROUP_NAME, key);
            notifyListeners(key);
            return this;
        }

        @Override
        public Editor clear() {
            Set<String> keys = new HashSet<>();
            for (IniGroup group : minion.getGroups()) {
                for (IniRecord record : group.getRecords()) {
                    keys.add(record.getKey());
                }
            }
            minion.clear();
            for (String key : keys) {
                notifyListeners(key);
            }
            return this;
        }

        @Override
        public boolean commit() {
            minion.store();
            return true;
        }

        @Override
        public void apply() {
            minion.store();
        }
    }
}
