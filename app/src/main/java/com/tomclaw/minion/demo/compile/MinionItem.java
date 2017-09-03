package com.tomclaw.minion.demo.compile;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.tomclaw.minion.IniGroup;
import com.tomclaw.minion.IniRecord;

/**
 * Created by solkin on 03.09.17.
 */
public class MinionItem {

    private final
    @Nullable
    IniGroup group;
    private final
    @Nullable
    IniRecord record;

    public MinionItem(@NonNull IniGroup group) {
        this.group = group;
        this.record = null;
    }

    public MinionItem(@NonNull IniGroup group, @NonNull IniRecord record) {
        this.group = group;
        this.record = record;
    }

    public boolean isGroup() {
        return record == null;
    }

    @Nullable
    public IniGroup getGroup() {
        return group;
    }

    @Nullable
    public IniRecord getRecord() {
        return record;
    }
}
