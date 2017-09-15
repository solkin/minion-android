package com.tomclaw.minion;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by solkin on 28.07.17.
 */
@SuppressWarnings("WeakerAccess")
public class IniGroup {

    private final
    @NonNull
    String name;
    private final
    @NonNull
    Map<String, IniRecord> records;

    protected IniGroup(@NonNull String name) {
        this(name, new LinkedHashMap<String, IniRecord>());
    }

    protected IniGroup(@NonNull String name, @NonNull Map<String, IniRecord> records) {
        this.name = name.trim();
        this.records = records;
    }

    public
    @NonNull
    String getName() {
        return name;
    }

    public
    @NonNull
    IniRecord getOrCreateRecord(String key, String... value) {
        String trimmedKey = key.trim();
        synchronized (records) {
            IniRecord record = getRecord(trimmedKey);
            if (record == null) {
                record = addRecord(trimmedKey, value);
            }
            return record;
        }
    }

    public
    @Nullable
    IniRecord getRecord(String key) {
        return records.get(key);
    }

    public Collection<IniRecord> getRecords() {
        return Collections.unmodifiableCollection(records.values());
    }

    public int getRecordsCount() {
        return records.size();
    }

    public
    @Nullable
    IniRecord removeRecord(String key) {
        return records.remove(key);
    }

    private
    @NonNull
    IniRecord addRecord(String key, String... value) {
        IniRecord record = new IniRecord(key, value);
        records.put(record.getKey(), record);
        return record;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IniGroup group = (IniGroup) o;
        return name.equals(group.name) && records.equals(group.records);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + records.hashCode();
        return result;
    }
}
