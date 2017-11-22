package com.tomclaw.minion.storage;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Created by solkin on 28.07.17.
 */
public class StringStorage implements Readable {

    private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");

    private String string;

    private StringStorage(String string) {
        this.string = string;
    }

    @Override
    public InputStream read() {
        return new ByteArrayInputStream(string.getBytes(UTF8_CHARSET));
    }

    public static StringStorage create(String string) {
        return new StringStorage(string);
    }

}
