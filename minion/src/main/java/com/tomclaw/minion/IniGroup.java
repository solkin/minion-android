package com.tomclaw.minion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by solkin on 28.07.17.
 */
class IniGroup {

    private String name;
    private List<IniRecord> records;

    protected IniGroup(String name) {
        this.name = name;
        this.records = new ArrayList<>();
    }

    protected IniGroup(String name, List<IniRecord> records) {
        this.name = name;
        this.records = records;
    }

    public String getName() {
        return name;
    }

    public List<IniRecord> getRecords() {
        return Collections.unmodifiableList(records);
    }

    protected void addRecord(IniRecord record) {
        records.add(record);
    }
}
