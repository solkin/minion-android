package com.tomclaw.minion;

import androidx.annotation.Nullable;

import com.tomclaw.minion.storage.Readable;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

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

    public static byte[] readFully(Readable readable) throws IOException {
        ByteArrayOutputStream readDataStream = new ByteArrayOutputStream();
        InputStream input = readable.read();
        int read;
        while ((read = input.read()) != -1) {
            readDataStream.write(read);
        }
        byte[] readData = readDataStream.toByteArray();
        readDataStream.close();
        input.close();
        return readData;
    }

}
