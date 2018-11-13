package com.tomclaw.minion.demo;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.tomclaw.minion.demo.adapters.OnItemClickListener;
import com.tomclaw.minion.demo.adapters.SectionItem;
import com.tomclaw.minion.demo.adapters.SectionsRecyclerAdapter;
import com.tomclaw.minion.demo.benchmark.BenchmarkActivity;
import com.tomclaw.minion.demo.compile.CompileActivity;
import com.tomclaw.minion.demo.parse.ParseActivity;

import java.util.Arrays;

import static com.tomclaw.minion.demo.utils.StatusBarHelper.tintStatusBarIcons;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    private static final int ITEM_BENCHMARK = 1;
    private static final int ITEM_PARSE = 2;
    private static final int ITEM_COMPILE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        tintStatusBarIcons(this, true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SectionsRecyclerAdapter adapter = new SectionsRecyclerAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setItems(Arrays.asList(
                new SectionItem(ITEM_BENCHMARK, R.drawable.benchmark, getString(R.string.benchmark), getString(R.string.benchmark_description)),
                new SectionItem(ITEM_PARSE, R.drawable.parse, getString(R.string.parse), getString(R.string.parse_description)),
                new SectionItem(ITEM_COMPILE, R.drawable.compile, getString(R.string.compile), getString(R.string.compile_description))
                )
        );

        adapter.setClickListener(this);
    }

    @Override
    public void onItemClick(SectionItem item) {
        Class<?> clazz;
        switch (item.getId()) {
            case ITEM_BENCHMARK:
                clazz = BenchmarkActivity.class;
                break;
            case ITEM_PARSE:
                clazz = ParseActivity.class;
                break;
            case ITEM_COMPILE:
                clazz = CompileActivity.class;
                break;
            default:
                return;
        }
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
}
