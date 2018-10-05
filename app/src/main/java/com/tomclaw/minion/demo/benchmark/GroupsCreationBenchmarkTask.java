package com.tomclaw.minion.demo.benchmark;

import androidx.annotation.NonNull;

import com.tomclaw.minion.Minion;
import com.tomclaw.minion.demo.R;

/**
 * Created by solkin on 09.08.17.
 */
class GroupsCreationBenchmarkTask extends BenchmarkTask {

    private int index;

    GroupsCreationBenchmarkTask(@NonNull Minion minion,
                                @NonNull BenchmarkRecyclerAdapter adapter,
                                @NonNull BenchmarkCallback callback) {
        super(minion, adapter, callback);
    }

    @Override
    protected int getId() {
        return 0x01;
    }

    @Override
    protected int getTitle() {
        return R.string.benchmark_groups_creation;
    }

    @Override
    protected int getTestsCount() {
        return 100000;
    }

    @Override
    protected void beforeTest(Minion minion) {
        index = 0;
    }

    @Override
    protected void runTest(Minion minion) {
        minion.getOrCreateGroup("group" + index++);
    }
}
