package com.tomclaw.minion;

import com.tomclaw.minion.storage.MemoryStorage;
import com.tomclaw.minion.storage.Readable;
import com.tomclaw.minion.storage.StringStorage;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created by solkin on 01.08.17.
 */
public class MinionUnitTest {

    @Test
    public void loadDataSync_isCorrect() {
        String data = "[first_group]\nfirst_key=first_value\n[second_group]\n" +
                "second_key=second_value\narray_key=value1,value2,value3";
        Minion minion = Minion.lets()
                .load(StringStorage.create(data))
                .sync();

        assertEquals(minion.getValue("first_group", "first_key"), "first_value");
        assertEquals(minion.getValue("second_group", "second_key"), "second_value");
        assertArrayEquals(minion.getArrayValue("second_group", "array_key"),
                new String[]{"value1", "value2", "value3"});
    }

    @Test
    public void storeDataSync_isCorrect() throws Exception {
        MemoryStorage storage = MemoryStorage.create();
        Minion minion = Minion.lets()
                .store(storage)
                .sync();

        minion.setValue("first_group", "first_key", "first_value");
        minion.setValue("second_group", "second_key", "second_value");
        minion.setValue("second_group", "array_key", "value1", "value2", "value3");
        minion.store();

        String data = new String(readFully(storage), "UTF-8");
        assertEquals(data, "[first_group]\nfirst_key=first_value\n[second_group]\n" +
                "second_key=second_value\narray_key=value1,value2,value3");
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
