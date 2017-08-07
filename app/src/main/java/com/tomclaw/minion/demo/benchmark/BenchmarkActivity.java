package com.tomclaw.minion.demo.benchmark;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.tomclaw.minion.demo.R;

import java.util.Arrays;

/**
 * Created by solkin on 01.08.17.
 */
public class BenchmarkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benchmark);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.benchmark);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        BenchmarkRecyclerAdapter adapter = new BenchmarkRecyclerAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setItems(Arrays.asList(
                new BenchmarkItem(1, R.string.benchmark_groups_creation, 100, "1028 ops/sec"),
                new BenchmarkItem(2, R.string.benchmark_items_creation, 30, ""),
                new BenchmarkItem(3, R.string.benchmark_groups_access, 0, ""),
                new BenchmarkItem(4, R.string.benchmark_items_access, 0, "")
                )
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
