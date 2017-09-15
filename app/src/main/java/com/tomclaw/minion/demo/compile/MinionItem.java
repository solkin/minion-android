package com.tomclaw.minion.demo.compile;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.tomclaw.minion.IniGroup;
import com.tomclaw.minion.IniRecord;

/**
 * Created by solkin on 03.09.17.
 */
class MinionItem {

    @NonNull
    private final
    IniGroup group;
    private final
    @Nullable
    IniRecord record;

    MinionItem(@NonNull IniGroup group) {
        this.group = group;
        this.record = null;
    }

    MinionItem(@NonNull IniGroup group, @NonNull IniRecord record) {
        this.group = group;
        this.record = record;
    }

    boolean isGroup() {
        return record == null;
    }

    @Nullable
    IniGroup getGroup() {
        return group;
    }

    @Nullable
    IniRecord getRecord() {
        return record;
    }
}
