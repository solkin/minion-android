package com.tomclaw.minion.demo.benchmark;

import android.support.annotation.NonNull;

import com.tomclaw.minion.Minion;
import com.tomclaw.minion.demo.R;

/**
 * Created by solkin on 09.08.17.
 */
public class GroupsAccessBenchmarkTask extends BenchmarkTask {

    private String[] names;
    private int index;

    public GroupsAccessBenchmarkTask(@NonNull Minion minion,
                                     @NonNull BenchmarkRecyclerAdapter adapter,
                                     @NonNull BenchmarkCallback callback) {
        super(minion, adapter, callback);
    }

    @Override
    protected int getId() {
        return 0x03;
    }

    @Override
    protected int getTitle() {
        return R.string.benchmark_groups_access;
    }

    @Override
    protected int getTestsCount() {
        return 100000;
    }

    @Override
    protected void beforeTest(Minion minion) {
        int count = getTestsCount();
        index = 0;
        names = new String[count];
        for (int c = 0; c < count; c++) {
            String name = "name" + c;
            names[c] = name;
            minion.getOrCreateGroup(name);
        }
    }

    @Override
    protected void runTest(Minion minion) {
        String name = names[index++];
        minion.getGroup(name);
    }
}
