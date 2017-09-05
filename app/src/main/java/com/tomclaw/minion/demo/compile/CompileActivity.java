package com.tomclaw.minion.demo.compile;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.tomclaw.minion.IniGroup;
import com.tomclaw.minion.IniRecord;
import com.tomclaw.minion.Minion;
import com.tomclaw.minion.StreamHelper;
import com.tomclaw.minion.demo.R;
import com.tomclaw.minion.demo.parse.ParseActivity;
import com.tomclaw.minion.storage.MemoryStorage;

import java.io.IOException;
import java.io.OutputStream;

import static com.tomclaw.minion.StreamHelper.safeClose;
import static com.tomclaw.minion.demo.utils.StatusBarHelper.tintStatusBarIcons;
import static com.tomclaw.minion.demo.utils.StringUtil.generateRandomString;

/**
 * Created by solkin on 01.08.17.
 */
public class CompileActivity extends AppCompatActivity implements GroupListener, RecordListener {

    public static final String EXTRA_INI_STRUCTURE = "ini_structure";

    private MemoryStorage storage;
    private Minion minion;
    private MinionRecyclerAdapter adapter;

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

        adapter = new MinionRecyclerAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setGroupListener(this);
        adapter.setRecordListener(this);

        storage = MemoryStorage.create();
        if (savedInstanceState == null) {
            String extra = getIntent().getStringExtra(EXTRA_INI_STRUCTURE);
            if (extra != null) {
                bytesToStorage(extra.getBytes());
            } else {
                // Default state.
            }
        } else {
            final byte[] data = savedInstanceState.getByteArray(EXTRA_INI_STRUCTURE);
            if (data != null) {
                bytesToStorage(data);
            }
        }
        minion = Minion.lets().load(storage).and().store(storage).sync();

        adapter.setData(minion);
        adapter.notifyDataSetChanged();
    }

    private void bytesToStorage(byte[] data) {
        OutputStream outputStream = null;
        try {
            outputStream = storage.write();
            outputStream.write(data);
            outputStream.flush();
        } catch (IOException ignored) {
        } finally {
            safeClose(outputStream);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        minion.store();
        try {
            byte[] data = StreamHelper.readFully(storage);
            outState.putByteArray(EXTRA_INI_STRUCTURE, data);
        } catch (IOException ignored) {
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_compile, menu);
        for (int c = 0; c < menu.size(); c++) {
            MenuItem item = menu.getItem(c);
            Drawable drawable = item.getIcon();
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable, ContextCompat.getColor(this, R.color.colorAccent));
            item.setIcon(drawable);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.insert:
                minion.getOrCreateGroup(generateRandomString());
                adapter.setData(minion);
                adapter.notifyDataSetChanged();
                break;
            case R.id.compile:
                minion.store();
                try {
                    String string = new String(StreamHelper.readFully(storage), "UTF-8");
                    Intent intent = new Intent(CompileActivity.this, ParseActivity.class)
                            .putExtra(ParseActivity.EXTRA_INI_STRUCTURE, string);
                    startActivity(intent);
                    finish();
                } catch (IOException ignored) {
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onInsertRecord(IniGroup item) {
        minion.setValue(item.getName(), generateRandomString(), generateRandomString());
        adapter.setData(minion);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onGroupDelete(IniGroup item) {
        minion.removeGroup(item.getName());
        adapter.setData(minion);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDeleteRecord(IniGroup group, IniRecord record) {
        minion.removeRecord(group.getName(), record.getKey());
        adapter.setData(minion);
        adapter.notifyDataSetChanged();
    }
}
