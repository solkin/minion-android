package com.tomclaw.minion;

import com.tomclaw.minion.storage.MemoryStorage;

import org.junit.Test;

import java.io.OutputStream;

import static com.tomclaw.minion.StreamHelper.readFully;
import static org.junit.Assert.assertArrayEquals;

/**
 * Created by solkin on 01.08.17.
 */
public class MemoryStorageUnitTest {

    @Test
    public void readAfterWrite_dataEquals() throws Exception {
        byte[] writeData = createRandomData();
        MemoryStorage storage = createMemoryStorage();
        OutputStream output = storage.write();

        output.write(writeData);
        output.close();

        byte[] readData = readFully(storage);

        assertArrayEquals(writeData, readData);
    }

    private byte[] createRandomData() {
        return "sample data".getBytes();
    }

    private MemoryStorage createMemoryStorage() {
        return MemoryStorage.create();
    }
}
