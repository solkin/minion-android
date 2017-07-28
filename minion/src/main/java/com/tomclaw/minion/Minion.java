package com.tomclaw.minion;

import com.tomclaw.minion.storage.Readable;
import com.tomclaw.minion.storage.Writable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static android.R.attr.data;
import static android.R.attr.lines;

/**
 * Created by solkin on 27.07.17.
 */
public class Minion {

    private static final String COMMENT_START = "#";
    private static final String GROUP_START = "[";
    private static final String GROUP_END = "]";
    private static final String KEY_VALUE_DIVIDER = "=";

    private static Executor executor = Executors.newSingleThreadExecutor();

    private final Readable readable;
    private final Writable writable;
    private final boolean async;

    private final List<IniGroup> groups = new ArrayList<>();

    private Minion(Readable readable, Writable writable, boolean async) {
        this.readable = readable;
        this.writable = writable;
        this.async = async;
    }

    private void load(final ResultCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                loadSync(callback);
            }
        };
        if (async) {
            executor.execute(runnable);
        } else {
            runnable.run();
        }
    }

    private void loadSync(ResultCallback callback) {
        try {
            final InputStream inputStream = readable.read();
            parse(inputStream);
            callback.onReady(this);
        } catch (Exception ex) {
            callback.onFailure(ex);
        }
    }

    private void parse(InputStream inputStream) throws IOException, UnsupportedFormatException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        IniGroup lastGroup = null;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.startsWith(COMMENT_START)) {
                continue;
            }

            if (line.startsWith(GROUP_START) && line.endsWith(GROUP_END)) {
                String name = line.substring(1, line.length() - 1);
                IniGroup group = new IniGroup(name);
                groups.add(group);
                lastGroup = group;
                continue;
            }

            if (line.contains(KEY_VALUE_DIVIDER) && lastGroup != null) {
                int index = line.indexOf(KEY_VALUE_DIVIDER);
                if (index <= 0) {
                    throw new UnsupportedFormatException();
                }
                String key = line.substring(0, index);
                String value = line.substring(index + 1);

                IniRecord record = new IniRecord(key, value);
                lastGroup.addRecord(record);
            }
        }
        reader.close();
    }

    public static Builder lets() {
        return new Builder();
    }

    public static class Builder {

        private Readable readable;
        private Writable writable;
        private boolean async;
        private ResultCallback callback;

        private Builder() {
        }

        public Builder load(Readable readable) {
            this.readable = readable;
            return this;
        }

        public Builder store(Writable writable) {
            this.writable = writable;
            return this;
        }

        public Builder and() {
            // Empty method just for better syntax.
            return this;
        }

        public void sync(ResultCallback callback) {
            this.async = false;
            build(callback);

        }

        public void async(ResultCallback callback) {
            this.async = true;
            build(callback);
        }

        private void build(ResultCallback callback) {
            this.callback = callback;
            Minion minion = new Minion(readable, writable, async);
            minion.load(callback);
        }

    }
}
