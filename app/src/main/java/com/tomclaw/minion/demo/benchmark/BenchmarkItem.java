package com.tomclaw.minion.demo.benchmark;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

/**
 * Created by solkin on 03.08.17.
 */
class BenchmarkItem {

    private int id;

    @StringRes
    private int title;

    private int progress;
    private String result;

    BenchmarkItem(int id, @StringRes int title, int progress, @NonNull String result) {
        this.id = id;
        this.title = title;
        this.progress = progress;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    @StringRes
    int getTitle() {
        return title;
    }

    int getProgress() {
        return progress;
    }

    @NonNull
    String getResult() {
        return result;
    }

    void setProgress(int progress) {
        this.progress = progress;
    }

    void setResult(@NonNull String result) {
        this.result = result;
    }
}
