package com.tomclaw.minion.demo.benchmark;

import android.support.annotation.NonNull;

import com.tomclaw.minion.Minion;
import com.tomclaw.minion.demo.R;

import java.util.concurrent.TimeUnit;

import static com.tomclaw.minion.demo.utils.StringUtil.generateRandomString;

/**
 * Created by solkin on 09.08.17.
 */
public class ItemsCreationBenchmarkTask extends BenchmarkTask {

    private String name;

    public ItemsCreationBenchmarkTask(@NonNull Minion minion,
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
    protected void beforeTest(Minion minion) {
        name = generateRandomString();
        minion.getOrCreateGroup(name);
    }

    @Override
    protected void runTest(Minion minion) {
        minion.setValue(name, generateRandomString(), generateRandomString());
    }
}
