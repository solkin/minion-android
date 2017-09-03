package com.tomclaw.minion.demo.compile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.tomclaw.minion.IniGroup;
import com.tomclaw.minion.IniRecord;
import com.tomclaw.minion.Minion;
import com.tomclaw.minion.demo.R;

import static com.tomclaw.minion.demo.utils.StatusBarHelper.tintStatusBarIcons;
import static com.tomclaw.minion.demo.utils.StringUtil.generateRandomString;

/**
 * Created by solkin on 01.08.17.
 */
public class CompileActivity extends AppCompatActivity implements GroupListener, RecordListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        tintStatusBarIcons(this, true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.compile);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        MinionRecyclerAdapter adapter = new MinionRecyclerAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setGroupListener(this);
        adapter.setRecordListener(this);

        Minion minion = Minion.lets().buildSimple();
        for (int c = 0; c < 3; c++) {
            String name = generateRandomString();
            for (int i = 0; i < 10; i++) {
                minion.setValue(name, generateRandomString(), generateRandomString());
            }
        }

        adapter.setData(minion);
        adapter.notifyDataSetChanged();
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

    @Override
    public void onInsertRecord(IniGroup item) {

    }

    @Override
    public void onGroupDelete(IniGroup item) {

    }

    @Override
    public void onDeleteRecord(IniGroup group, IniRecord record) {

    }
}
