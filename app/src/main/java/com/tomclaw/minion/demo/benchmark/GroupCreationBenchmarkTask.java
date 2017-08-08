package com.tomclaw.minion.demo.benchmark;

import android.support.annotation.NonNull;

import com.tomclaw.minion.Minion;
import com.tomclaw.minion.demo.R;

import java.util.concurrent.TimeUnit;

import static com.tomclaw.minion.demo.utils.StringUtil.generateRandomString;

/**
 * Created by solkin on 09.08.17.
 */
public class GroupCreationBenchmarkTask extends BenchmarkTask {

    public GroupCreationBenchmarkTask(@NonNull Minion minion,
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
    protected void beforeTest(Minion minion) {
    }

    @Override
    protected void runTest(Minion minion) {
        minion.getOrCreateGroup(generateRandomString());
    }
}
