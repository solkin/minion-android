package com.tomclaw.minion.storage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * Created by solkin on 28.07.17.
 */
public class MemoryStorage implements Readable, Writable {

    private PipedInputStream inputStream;

    private MemoryStorage() {
        inputStream = new PipedInputStream();
    }

    @Override
    public InputStream read() {
        return inputStream;
    }

    @Override
    public OutputStream write() throws IOException {
        return new PipedOutputStream(inputStream);
    }

    public static MemoryStorage create() {
        return new MemoryStorage();
    }
}
