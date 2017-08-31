package com.tomclaw.minion.demo.benchmark;

import android.support.annotation.NonNull;

import com.tomclaw.minion.IniGroup;
import com.tomclaw.minion.Minion;
import com.tomclaw.minion.demo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.tomclaw.minion.demo.utils.StringUtil.generateRandomString;

/**
 * Created by solkin on 09.08.17.
 */
public class ItemsAccessBenchmarkTask extends BenchmarkTask {

    private Random random;
    private String name;
    private List<String> keys;

    public ItemsAccessBenchmarkTask(@NonNull Minion minion,
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
        random = new Random(System.currentTimeMillis());
        name = generateRandomString();
        keys = new ArrayList<>();
        int count = getTestsCount();
        IniGroup group = minion.getOrCreateGroup(name);
        for (int c = 0; c < count; c++) {
            String key = generateRandomString();
            keys.add(key);
            group.getOrCreateRecord(key, generateRandomString());
        }
    }

    @Override
    protected void runTest(Minion minion) {
        String key = keys.get(random.nextInt(keys.size()));
        minion.getValue(name, key);
    }
}
