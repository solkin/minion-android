package com.tomclaw.minion;

import android.support.annotation.Nullable;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by solkin on 31.07.17.
 */
public class StreamHelper {

    public static void safeClose(@Nullable Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException ignored) {
            }
        }
    }

}
