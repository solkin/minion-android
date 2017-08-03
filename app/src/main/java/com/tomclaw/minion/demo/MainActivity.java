package com.tomclaw.minion.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.tomclaw.minion.demo.adapters.OnItemClickListener;
import com.tomclaw.minion.demo.adapters.SectionItem;
import com.tomclaw.minion.demo.adapters.SectionsRecyclerAdapter;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SectionsRecyclerAdapter adapter = new SectionsRecyclerAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setItems(Arrays.asList(
                new SectionItem(R.drawable.benchmark, getString(R.string.benchmark), getString(R.string.benchmark_description)),
                new SectionItem(R.drawable.parse, getString(R.string.parse), getString(R.string.parse_description)),
                new SectionItem(R.drawable.compile, getString(R.string.compile), getString(R.string.compile_description))
                )
        );

        adapter.setClickListener(this);
    }

    @Override
    public void onItemClick(SectionItem item) {

    }
}
