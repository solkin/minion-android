package com.tomclaw.minion;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by ivsolkin on 28/09/2017.
 */
public class IniRecordUnitTest {

    @Test
    public void createRecordWithSingleValue_createsCorrectly() {
        String key = "key";
        String value = "value";

        IniRecord record = new IniRecord(key, value);

        assertEquals(value, record.getValue());
    }

    @Test
    public void createRecordWithMultiValue_createsCorrectly() {
        String key = "key";
        String value1 = "value1";
        String value2 = "value2";
        String value3 = "value3";

        IniRecord record = new IniRecord(key, value1, value2, value3);

        assertEquals(value1, record.getValues()[0]);
        assertEquals(value2, record.getValues()[1]);
        assertEquals(value3, record.getValues()[2]);
    }

    @Test
    public void createRecord_trimValues() {
        String key = "key";
        String value = " value ";

        IniRecord record = new IniRecord(key, value);

        assertEquals("value", record.getValue());
    }

    @Test
    public void setValue_trimValues() {
        String key = "key";
        String value = " value ";
        IniRecord record = new IniRecord(key);

        record.setValue(value);

        assertEquals("value", record.getValue());
    }

    @Test(expected = IllegalStateException.class)
    public void getValueFromEmptyRecord_throwException() {
        String key = "key";
        IniRecord record = new IniRecord(key);

        record.getValue();
    }
}
