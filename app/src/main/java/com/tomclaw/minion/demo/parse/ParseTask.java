package com.tomclaw.minion.demo.parse;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import com.tomclaw.minion.Minion;
import com.tomclaw.minion.demo.R;
import com.tomclaw.minion.demo.compile.CompileActivity;
import com.tomclaw.minion.demo.utils.PleaseWaitTask;
import com.tomclaw.minion.storage.MemoryStorage;
import com.tomclaw.minion.storage.Readable;
import com.tomclaw.minion.storage.StringStorage;
import com.tomclaw.minion.storage.Writable;

import java.io.IOException;
import java.util.Set;

import static com.tomclaw.minion.StreamHelper.readFully;

/**
 * Created by solkin on 30.08.17.
 */
public class ParseTask extends PleaseWaitTask<Activity> {

    @NonNull
    private final String data;
    private
    @Nullable
    Minion minion;
    private long delay;
    private @Nullable
    MemoryStorage storage;

    public ParseTask(@NonNull Activity activity, @NonNull String data) {
        super(activity);
        this.data = data;
    }

    @Override
    public void executeBackground() throws Throwable {
        Readable readable = StringStorage.create(data);
        storage = MemoryStorage.create();
        long startTime = System.currentTimeMillis();
        minion = Minion.lets()
                .load(readable)
                .and()
                .store(storage)
                .sync();
        delay = System.currentTimeMillis() - startTime;
    }

    @Override
    public void onProgressClosed() {
        final Activity activity = getWeakObject();
        if (activity != null && minion != null) {
            Set<String> names = minion.getGroupNames();
            int recordsCount = 0;
            for (String name : names) {
                recordsCount += minion.getOrCreateGroup(name).getRecords().size();
            }
            String result = activity.getString(R.string.parsing_result, delay, names.size(), recordsCount);
            new AlertDialog.Builder(activity)
                    .setTitle(R.string.parse_action)
                    .setMessage(result)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                minion.store();
                                String data = new String(readFully(storage), "UTF-8");
                                Intent intent = new Intent(activity, CompileActivity.class)
                                        .putExtra(CompileActivity.EXTRA_INI_STRUCTURE, data);
                                activity.startActivity(intent);
                                activity.finish();
                            } catch (IOException ignored) {
                            }
                        }
                    })
                    .show();
        }
    }

    @Override
    public int getWaitStringId() {
        return R.string.please_wait;
    }
}
