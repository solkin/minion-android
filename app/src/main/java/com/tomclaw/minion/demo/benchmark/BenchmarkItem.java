package com.tomclaw.minion.demo.benchmark;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

/**
 * Created by solkin on 03.08.17.
 */
@SuppressWarnings("WeakerAccess")
public class BenchmarkItem {

    private int id;

    @StringRes
    private int title;

    private int progress;
    private String result;

    public BenchmarkItem(int id, @StringRes int title, int progress, @NonNull String result) {
        this.id = id;
        this.title = title;
        this.progress = progress;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    @StringRes
    public int getTitle() {
        return title;
    }

    public int getProgress() {
        return progress;
    }

    @NonNull
    public String getResult() {
        return result;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void setResult(@NonNull String result) {
        this.result = result;
    }
}
