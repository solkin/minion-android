package com.tomclaw.minion;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.tomclaw.minion.storage.Readable;
import com.tomclaw.minion.storage.Writable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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

    private final LinkedHashMap<String, IniGroup> groups = new LinkedHashMap<>();

    private Minion(Readable readable, Writable writable, boolean async) {
        this.readable = readable;
        this.writable = writable;
        this.async = async;
    }

    public
    @Nullable
    IniRecord addRecord(@NonNull String name,
                        @NonNull String key,
                        @NonNull String... value) {
        IniGroup group = getOrCreateGroup(name);
        return group.getOrCreateRecord(key, value);
    }

    public
    @NonNull
    IniGroup getOrCreateGroup(@NonNull String name) {
        synchronized (groups) {
            IniGroup group = getGroup(name);
            if (group == null) {
                group = addGroup(name);
            }
            return group;
        }
    }

    public
    @Nullable
    IniGroup getGroup(@NonNull String name) {
        return groups.get(name);
    }

    private
    @NonNull
    IniGroup addGroup(String name) {
        IniGroup group = new IniGroup(name);
        groups.put(name, group);
        return group;
    }

    private void load(@NonNull final ResultCallback callback) {
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

    private void loadSync(@NonNull ResultCallback callback) {
        try {
            final InputStream inputStream = readable.read();
            parse(inputStream);
            callback.onReady(this);
        } catch (Exception ex) {
            callback.onFailure(ex);
        }
    }

    private void parse(@NonNull InputStream inputStream) throws IOException, UnsupportedFormatException {
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
                groups.put(name, group);
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

                lastGroup.getOrCreateRecord(key, value);
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

        public Builder load(@NonNull Readable readable) {
            this.readable = readable;
            return this;
        }

        public Builder store(@NonNull Writable writable) {
            this.writable = writable;
            return this;
        }

        public Builder and() {
            // Empty method just for better syntax.
            return this;
        }

        public Minion sync() {
            this.async = false;
            this.callback = new EmptyResultCallback();
            return build();
        }

        public void async(@NonNull ResultCallback callback) {
            this.async = true;
            this.callback = callback;
            build();
        }

        private Minion build() {
            Minion minion = new Minion(readable, writable, async);
            minion.load(callback);
            return minion;
        }

    }
}
