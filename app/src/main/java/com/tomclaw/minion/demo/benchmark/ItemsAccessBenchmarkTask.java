package com.tomclaw.minion.demo.benchmark;

import com.tomclaw.minion.IniGroup;
import com.tomclaw.minion.Minion;
import com.tomclaw.minion.demo.R;

import androidx.annotation.NonNull;

/**
 * Created by solkin on 09.08.17.
 */
class ItemsAccessBenchmarkTask extends BenchmarkTask {

    private String name;
    private String[] keys;
    private int index;

    ItemsAccessBenchmarkTask(@NonNull Minion minion,
                             @NonNull BenchmarkRecyclerAdapter adapter,
                             @NonNull BenchmarkCallback callback) {
        super(minion, adapter, callback);
    }

    @Override
    protected int getId() {
        return 0x04;
    }

    @Override
    protected int getTitle() {
        return R.string.benchmark_items_access;
    }

    @Override
    protected int getTestsCount() {
        return 100000;
    }

    @Override
    protected void beforeTest(Minion minion) {
        int count = getTestsCount();
        index = 0;
        name = "name";
        keys = new String[count];
        IniGroup group = minion.getOrCreateGroup(name);
        for (int c = 0; c < count; c++) {
            String key = "key" + c;
            keys[c] = key;
            group.getOrCreateRecord(key, "value" + c);
        }
    }

    @Override
    protected void runTest(Minion minion) {
        String key = keys[index++];
        minion.getValue(name, key);
    }
}
