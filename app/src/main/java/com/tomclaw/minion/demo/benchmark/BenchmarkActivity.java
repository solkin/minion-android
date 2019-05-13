package com.tomclaw.minion.demo.benchmark;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tomclaw.minion.Minion;
import com.tomclaw.minion.demo.R;
import com.tomclaw.minion.demo.utils.Task;
import com.tomclaw.minion.demo.utils.TaskExecutor;
import com.tomclaw.minion.storage.MemoryStorage;
import com.tomclaw.minion.storage.Writable;

import static com.tomclaw.minion.demo.utils.StatusBarHelper.tintStatusBarIcons;

/**
 * Created by solkin on 01.08.17.
 */
public class BenchmarkActivity extends AppCompatActivity implements BenchmarkTask.BenchmarkCallback {

    private BenchmarkRecyclerAdapter adapter;
    private Minion minion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        tintStatusBarIcons(this, true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benchmark);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.benchmark);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        adapter = new BenchmarkRecyclerAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        prepareMinion();
        startBenchmark();
    }

    private void startBenchmark() {
        onComplete(0x00);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return false;
        }
    }

    private void prepareMinion() {
        Writable writable = MemoryStorage.create();
        minion = Minion
                .lets()
                .store(writable)
                .sync();
    }

    @Override
    public void onComplete(int id) {
        prepareMinion();
        Task task;
        switch (id) {
            case 0x00:
                task = new GroupsCreationBenchmarkTask(minion, adapter, this);
                break;
            case 0x01:
                task = new ItemsCreationBenchmarkTask(minion, adapter, this);
                break;
            case 0x02:
                task = new GroupsAccessBenchmarkTask(minion, adapter, this);
                break;
            case 0x03:
                task = new ItemsAccessBenchmarkTask(minion, adapter, this);
                break;
            default:
                return;
        }
        TaskExecutor
                .getInstance()
                .execute(task);
    }
}
