package com.tomclaw.minion.demo.parse;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.tomclaw.minion.demo.R;
import com.tomclaw.minion.demo.utils.Task;
import com.tomclaw.minion.demo.utils.TaskExecutor;

import static com.tomclaw.minion.demo.utils.StatusBarHelper.tintStatusBarIcons;

/**
 * Created by solkin on 01.08.17.
 */
public class ParseActivity extends AppCompatActivity {

    public static final String EXTRA_INI_STRUCTURE = "ini_structure";

    @Nullable
    private EditText input;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        tintStatusBarIcons(this, true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.parse);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        input = findViewById(R.id.input);
        if (input != null) {
            input.addTextChangedListener(new IniSyntaxHighlighter());
        }

        if (savedInstanceState == null) {
            String extra = getIntent().getStringExtra(EXTRA_INI_STRUCTURE);
            if (extra != null) {
                input.setText(extra);
            } else {
                loadExample();
            }
        }
    }

    private void loadExample() {
        TaskExecutor.getInstance().execute(new LoadAssetTask(input));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_parse, menu);
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
                return true;
            case R.id.parse:
                if (input != null) {
                    String data = input.getText().toString();
                    Task parseTask = new ParseTask(this, data);
                    TaskExecutor.getInstance().execute(parseTask);
                }
                return true;
            default:
                return false;
        }
    }
}
