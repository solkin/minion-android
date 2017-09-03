package com.tomclaw.minion.storage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by solkin on 28.07.17.
 */
public class MemoryStorage implements Readable, Writable {

    private ByteArrayOutputStream stream;

    private MemoryStorage() {
        stream = new ByteArrayOutputStream();
    }

    @Override
    public InputStream read() {
        return new ByteArrayInputStream(stream.toByteArray());
    }

    @Override
    public OutputStream write() throws IOException {
        return stream;
    }

    public static MemoryStorage create() {
        return new MemoryStorage();
    }
}
