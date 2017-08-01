package com.tomclaw.minion;

import com.tomclaw.minion.storage.Readable;
import com.tomclaw.minion.storage.StringStorage;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

/**
 * Created by solkin on 01.08.17.
 */
public class StringStorageUnitTest {

    @Test
    public void read_dataEquals() throws Exception {
        String data = "sample data";
        StringStorage storage = createStringStorage(data);

        String read = new String(readFully(storage), "UTF-8");

        assertEquals(data, read);
    }

    private StringStorage createStringStorage(String data) {
        return StringStorage.create(data);
    }

    private byte[] readFully(Readable readable) throws IOException {
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
