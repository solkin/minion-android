package com.tomclaw.minion.storage;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by solkin on 28.07.17.
 */
public interface Readable {

    InputStream read() throws IOException;

}
