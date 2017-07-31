package com.tomclaw.minion;

/**
 * Created by solkin on 28.07.17.
 */
class IniRecord {

    private final String key;
    private String[] value;

    protected IniRecord(String key, String... value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String[] getValue() {
        return value;
    }
}
