package com.tomclaw.minion.demo.parse;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import com.tomclaw.minion.Minion;
import com.tomclaw.minion.demo.R;
import com.tomclaw.minion.demo.utils.PleaseWaitTask;
import com.tomclaw.minion.storage.Readable;
import com.tomclaw.minion.storage.StringStorage;

import java.util.Set;

/**
 * Created by solkin on 30.08.17.
 */
public class ParseTask extends PleaseWaitTask {

    @NonNull
    private final String data;
    private
    @Nullable
    Minion minion;
    private long delay;

    public ParseTask(@NonNull Context context, @NonNull String data) {
        super(context);
        this.data = data;
    }

    @Override
    public void executeBackground() throws Throwable {
        Readable readable = StringStorage.create(data);
        long startTime = System.currentTimeMillis();
        minion = Minion.lets()
                .load(readable)
                .sync();
        delay = System.currentTimeMillis() - startTime;
    }

    @Override
    public void onProgressClosed() {
        Context context = getWeakObject();
        if (context != null && minion != null) {
            Set<String> names = minion.getGroupNames();
            int recordsCount = 0;
            for (String name : names) {
                recordsCount += minion.getOrCreateGroup(name).getRecords().size();
            }
            String result = context.getString(R.string.parsing_result, delay, names.size(), recordsCount);
            new AlertDialog.Builder(context)
                    .setTitle(R.string.parse_action)
                    .setMessage(result)
                    .setPositiveButton(R.string.ok, null)
                    .show();
        }
    }

    @Override
    public int getWaitStringId() {
        return R.string.please_wait;
    }
}
