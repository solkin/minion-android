package com.tomclaw.minion.demo.compile;

import com.tomclaw.minion.IniGroup;
import com.tomclaw.minion.IniRecord;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by solkin on 03.09.17.
 */
@SuppressWarnings("WeakerAccess")
public class MinionItem {

    @NonNull
    private final IniGroup group;
    @Nullable
    private final IniRecord record;

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
