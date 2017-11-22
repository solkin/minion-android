package com.tomclaw.minion;

import com.tomclaw.minion.storage.MemoryStorage;
import com.tomclaw.minion.storage.Readable;
import com.tomclaw.minion.storage.StringStorage;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Set;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by solkin on 01.08.17.
 */
public class MinionUnitTest {

    @Test
    public void loadDataSync_isCorrect() {
        String data = "[first_group]\nfirst_key=first_value\n[second_group]\n" +
                "#comment\n;comment\n" +
                "second_key=second_value\narray_key=value1 , value2 , value3";
        Minion minion = Minion.lets()
                .load(StringStorage.create(data))
                .sync();

        assertEquals(minion.getValue("first_group", "first_key"), "first_value");
        assertEquals(minion.getValue("second_group", "second_key"), "second_value");
        assertArrayEquals(minion.getValues("second_group", "array_key"),
                new String[]{"value1", "value2", "value3"});
    }

    @Test
    public void storeDataSync_isCorrect() throws Exception {
        MemoryStorage storage = MemoryStorage.create();
        Minion minion = Minion.lets()
                .store(storage)
                .sync();

        minion.setValue("first_group", "first_key ", "first_value");
        minion.setValue("second_group", " second_key", "second_value");
        minion.setValue("second_group", " array_key ", "value1 ", " value2 ", " value3");
        minion.store();

        String data = new String(readFully(storage), "UTF-8");
        assertEquals(data, "[first_group]\nfirst_key=first_value\n[second_group]\n" +
                "second_key=second_value\narray_key=value1,value2,value3");
    }

    @Test
    public void getGroup_doNotCreateGroupIfNotExist() {
        MemoryStorage storage = MemoryStorage.create();
        Minion minion = Minion.lets()
                .load(storage)
                .sync();

        minion.getGroup("test_group");

        IniGroup group = minion.getGroup("test_group");
        assertNull(group);
    }

    @Test
    public void getGroupNames_returnGroupNamesCorrectly() {
        MemoryStorage storage = MemoryStorage.create();
        Minion minion = Minion.lets()
                .load(storage)
                .sync();
        minion.getOrCreateGroup("test_group_1");
        minion.getOrCreateGroup("test_group_2");

        Set<String> groupNames = minion.getGroupNames();

        assertArrayEquals(groupNames.toArray(), new String[]{"test_group_1", "test_group_2"});
    }

    @Test
    public void getGroupsCount_returnGroupsCountCorrectly() {
        MemoryStorage storage = MemoryStorage.create();
        Minion minion = Minion.lets()
                .load(storage)
                .sync();
        minion.getOrCreateGroup("test_group_1");
        minion.getOrCreateGroup("test_group_2");

        int groupsCount = minion.getGroupsCount();

        assertEquals(groupsCount, 2);
    }

    @Test
    public void getGroups_returnGroupsCorrectly() {
        IniGroup[] original = new IniGroup[]{
                new IniGroup("test_group_1"),
                new IniGroup("test_group_2")
        };
        MemoryStorage storage = MemoryStorage.create();
        Minion minion = Minion.lets()
                .load(storage)
                .sync();
        minion.getOrCreateGroup("test_group_1");
        minion.getOrCreateGroup("test_group_2");

        Collection<IniGroup> groups = minion.getGroups();

        assertArrayEquals(groups.toArray(), original);
    }

    @Test
    public void getOrCreateGroup_createGroupIfNotExist() {
        String name = "test_group";
        MemoryStorage storage = MemoryStorage.create();
        Minion minion = Minion.lets()
                .load(storage)
                .sync();

        minion.getOrCreateGroup(name);

        IniGroup group = minion.getGroup(name);
        assertNotNull(group);
        assertEquals(group.getName(), name);
    }

    @Test
    public void removeGroup_removeGroupCorrectly() {
        String name = "test_group";
        MemoryStorage storage = MemoryStorage.create();
        Minion minion = Minion.lets()
                .load(storage)
                .sync();
        minion.getOrCreateGroup(name);

        minion.removeGroup(name);

        IniGroup group = minion.getGroup(name);
        assertNull(group);
    }

    @Test
    public void clear_removeAllGroups() {
        MemoryStorage storage = MemoryStorage.create();
        Minion minion = Minion.lets()
                .load(storage)
                .sync();
        minion.getOrCreateGroup("test_group1");
        minion.getOrCreateGroup("test_group2");
        minion.getOrCreateGroup("test_group3");

        minion.clear();

        int groupsCount = minion.getGroupsCount();
        assertEquals(groupsCount, 0);
    }

    @Test
    public void setValue_setsValueCorrectly() {
        String name = "test_group";
        String key = "test_key";
        String value = "test_value";
        MemoryStorage storage = MemoryStorage.create();
        Minion minion = Minion.lets()
                .load(storage)
                .sync();

        minion.setValue(name, key, value);

        String resultValue = minion.getValue(name, key);
        assertEquals(resultValue, value);
    }

    @Test
    public void removeRecord_removesRecordAndReturnIt_ifRecordExist() {
        String name = "test_group";
        String key = "test_key";
        String value = "test_value";
        MemoryStorage storage = MemoryStorage.create();
        Minion minion = Minion.lets()
                .load(storage)
                .sync();
        IniRecord original = minion.setValue(name, key, value);

        IniRecord removed = minion.removeRecord(name, key);

        assertEquals(original, removed);
        String resultValue = minion.getValue(name, key);
        assertNull(resultValue);
    }

    @Test
    public void removeRecord_removesRecordAndReturnNull_ifRecordDoesNotExist() {
        String name = "test_group";
        String key = "test_key";
        String value = "test_value";
        MemoryStorage storage = MemoryStorage.create();
        Minion minion = Minion.lets()
                .load(storage)
                .sync();
        minion.setValue(name, key, value);

        IniRecord removed = minion.removeRecord(name, "non_exist_key");

        assertNull(removed);
    }

    @Test
    public void getValue_getsValueCorrectly_noDefaultValue() {
        String name = "test_group";
        String key = "test_key";
        String value = "test_value";
        MemoryStorage storage = MemoryStorage.create();
        Minion minion = Minion.lets()
                .load(storage)
                .sync();
        minion.setValue(name, key, value);

        String resultValue = minion.getValue(name, key);

        assertEquals(resultValue, value);
    }

    @Test
    public void getValue_returnsNull_noSuchRecordAndNoDefaultValue() {
        String name = "test_group";
        String key = "test_key";
        MemoryStorage storage = MemoryStorage.create();
        Minion minion = Minion.lets()
                .load(storage)
                .sync();

        String resultValue = minion.getValue(name, key);

        assertNull(resultValue);
    }

    @Test
    public void getValue_returnsDefaultValue_noSuchRecordButDefaultValueSpecified() {
        String name = "test_group";
        String key = "test_key";
        String defValue = "test_def_value";
        MemoryStorage storage = MemoryStorage.create();
        Minion minion = Minion.lets()
                .load(storage)
                .sync();

        String resultValue = minion.getValue(name, key, defValue);

        assertEquals(resultValue, defValue);
    }

    @Test
    public void getValues_getsValuesCorrectly_noDefaultValue() {
        String name = "test_group";
        String key = "test_key";
        String value1 = "test_value1";
        String value2 = "test_value2";
        String value3 = "test_value3";
        MemoryStorage storage = MemoryStorage.create();
        Minion minion = Minion.lets()
                .load(storage)
                .sync();
        minion.setValue(name, key, value1, value2, value3);

        String[] resultValue = minion.getValues(name, key);

        assertArrayEquals(resultValue, new String[]{value1, value2, value3});
    }

    @Test
    public void getValues_returnsNull_noSuchRecordsAndNoDefaultValue() {
        String name = "test_group";
        String key = "test_key";
        MemoryStorage storage = MemoryStorage.create();
        Minion minion = Minion.lets()
                .load(storage)
                .sync();

        String[] resultValue = minion.getValues(name, key);

        assertNull(resultValue);
    }

    @Test
    public void getValues_returnsDefaultValue_noSuchRecordButDefaultValueSpecified() {
        String name = "test_group";
        String key = "test_key";
        String[] defValue = new String[]{"test_value1", "test_value2", "test_value3"};
        MemoryStorage storage = MemoryStorage.create();
        Minion minion = Minion.lets()
                .load(storage)
                .sync();

        String[] resultValue = minion.getValues(name, key, defValue);

        assertArrayEquals(resultValue, defValue);
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
