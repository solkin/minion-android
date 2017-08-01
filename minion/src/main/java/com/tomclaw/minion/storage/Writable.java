package com.tomclaw.minion.storage;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by solkin on 28.07.17.
 */
public interface Writable {

    OutputStream write() throws IOException;

}
