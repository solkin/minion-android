package com.tomclaw.minion;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by solkin on 28.07.17.
 */
public class IniGroup {

    private final
    @NonNull
    String name;
    private final
    @NonNull
    List<IniRecord> records;

    protected IniGroup(@NonNull String name) {
        this(name, new ArrayList<IniRecord>());
    }

    protected IniGroup(@NonNull String name, @NonNull List<IniRecord> records) {
        this.name = name;
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
        synchronized (records) {
            for (IniRecord record : records) {
                if (key.equals(record.getKey())) {
                    return record;
                }
            }
            return null;
        }
    }

    public List<IniRecord> getRecords() {
        return Collections.unmodifiableList(records);
    }

    private
    @NonNull
    IniRecord addRecord(String key, String... value) {
        IniRecord record = new IniRecord(key, value);
        records.add(record);
        return record;
    }
}
