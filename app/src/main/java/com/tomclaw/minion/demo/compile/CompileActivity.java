package com.tomclaw.minion.demo.compile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tomclaw.minion.demo.R;

import static com.tomclaw.minion.demo.utils.StatusBarHelper.tintStatusBarIcons;

/**
 * Created by solkin on 01.08.17.
 */
public class CompileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        tintStatusBarIcons(this, true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benchmark);
    }
}
