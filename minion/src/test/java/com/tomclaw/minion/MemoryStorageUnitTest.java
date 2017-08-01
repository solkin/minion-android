package com.tomclaw.minion;

import com.tomclaw.minion.storage.MemoryStorage;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

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

        ByteArrayOutputStream readDataStream = new ByteArrayOutputStream();
        InputStream input = storage.read();
        int read;
        while ((read = input.read()) != -1) {
            readDataStream.write(read);
        }
        byte[] readData = readDataStream.toByteArray();
        readDataStream.close();
        input.close();

        assertArrayEquals(writeData, readData);
    }

    private byte[] createRandomData() {
        return "sample data".getBytes();
    }

    private MemoryStorage createMemoryStorage() {
        return MemoryStorage.create();
    }
}
