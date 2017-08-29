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
        key = key.trim();
        synchronized (records) {
            IniRecord record = getRecord(key);
            if (record == null) {
                record = addRecord(key, value);
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

    private
    @NonNull
    IniRecord addRecord(String key, String... value) {
        IniRecord record = new IniRecord(key, value);
        records.put(record.getKey(), record);
        return record;
    }
}
