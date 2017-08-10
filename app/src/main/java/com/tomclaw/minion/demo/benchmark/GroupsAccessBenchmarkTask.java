package com.tomclaw.minion.demo.benchmark;

import android.support.annotation.NonNull;

import com.tomclaw.minion.Minion;
import com.tomclaw.minion.demo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.tomclaw.minion.demo.utils.StringUtil.generateRandomString;

/**
 * Created by solkin on 09.08.17.
 */
public class GroupsAccessBenchmarkTask extends BenchmarkTask {

    private Random random;
    private List<String> names;

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
        random = new Random(System.currentTimeMillis());
        names = new ArrayList<>();
        int count = getTestsCount();
        for (int c = 0; c < count; c++) {
            String name = generateRandomString();
            names.add(name);
            minion.getOrCreateGroup(name);
        }
    }

    @Override
    protected void runTest(Minion minion) {
        String name = names.get(random.nextInt(names.size()));
        minion.getGroup(name);
    }
}
