package com.tomclaw.minion.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by solkin on 28.07.17.
 */
@SuppressWarnings("WeakerAccess")
public class FileStorage implements Readable, Writable {

    private File file;

    public FileStorage(File file) {
        this.file = file;
    }

    @Override
    public InputStream read() throws FileNotFoundException {
        return new FileInputStream(file);
    }

    @Override
    public OutputStream write() throws FileNotFoundException {
        return new FileOutputStream(file);
    }

    public static FileStorage create(File file) {
        return new FileStorage(file);
    }

}
