package com.tomclaw.minion.demo.benchmark;

import com.tomclaw.minion.Minion;
import com.tomclaw.minion.demo.R;

import androidx.annotation.NonNull;

import static com.tomclaw.minion.demo.utils.StringUtil.generateRandomString;

/**
 * Created by solkin on 09.08.17.
 */
class ItemsCreationBenchmarkTask extends BenchmarkTask {

    private String name;
    private int index;

    ItemsCreationBenchmarkTask(@NonNull Minion minion,
                               @NonNull BenchmarkRecyclerAdapter adapter,
                               @NonNull BenchmarkCallback callback) {
        super(minion, adapter, callback);
    }

    @Override
    protected int getId() {
        return 0x02;
    }

    @Override
    protected int getTitle() {
        return R.string.benchmark_items_creation;
    }

    @Override
    protected int getTestsCount() {
        return 100000;
    }

    @Override
    protected void beforeTest(Minion minion) {
        index = 0;
        name = generateRandomString();
        minion.getOrCreateGroup(name);
    }

    @Override
    protected void runTest(Minion minion) {
        minion.setValue(name, "key" + index, "value" + index++);
    }
}
