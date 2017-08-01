package com.tomclaw.minion;

import com.tomclaw.minion.storage.StringStorage;

import org.junit.Test;

import static com.tomclaw.minion.StreamHelper.readFully;
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
}
