package com.tomclaw.minion.demo.parse;

import android.widget.EditText;

import androidx.annotation.NonNull;

import com.tomclaw.minion.demo.R;
import com.tomclaw.minion.demo.utils.WeakObjectTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by solkin on 30.08.17.
 */
class LoadAssetTask extends WeakObjectTask<EditText> {

    private final
    @NonNull
    StringBuilder total;

    LoadAssetTask(EditText object) {
        super(object);
        total = new StringBuilder();
    }

    @Override
    public void executeBackground() throws Throwable {
        EditText editText = getWeakObject();
        if (editText != null) {
            InputStream inputStream = editText.getResources().openRawResource(R.raw.example);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                total.append(line).append('\n');
            }
        }
    }

    @Override
    public void onPostExecuteMain() {
        EditText editText = getWeakObject();
        if (editText != null) {
            editText.setText(total);
        }
    }
}
